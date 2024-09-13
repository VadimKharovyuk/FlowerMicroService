package com.example.paypal.service;

import com.example.paypal.model.PaymentRecord;
import com.example.paypal.repository.PaymentRepository;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class PayService {
    private final APIContext apiContext;
    private final PaymentRepository paymentRepository;

    public Payment createPay(
            Double total,
            String currency,
            String method,
            String intent,
            String description,
            String cancelUrl,
            String successUrl
    ) {
        // Форматируем сумму до двух знаков после запятой
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format(Locale.forLanguageTag(currency), "%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription(description);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method);

        Payment payment = new Payment();
        payment.setIntent(intent);
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);

        payment.setRedirectUrls(redirectUrls);

        try {
            // Создаем платеж и возвращаем результат
            Payment createdPayment = payment.create(apiContext);

            // Проверяем, что информация о плательщике не null
            String payerId = null;
            if (createdPayment.getPayer() != null && createdPayment.getPayer().getPayerInfo() != null) {
                payerId = createdPayment.getPayer().getPayerInfo().getPayerId();
            }

            // Сохраняем запись о платеже в базе данных
            PaymentRecord paymentRecord = new PaymentRecord();
            paymentRecord.setPaymentId(createdPayment.getId());
            paymentRecord.setPayerId(payerId != null ? payerId : "N/A"); // Обработаем случай, если payerId не доступен
            paymentRecord.setIntent(createdPayment.getIntent());
            paymentRecord.setCurrency(createdPayment.getTransactions().get(0).getAmount().getCurrency());
            paymentRecord.setTotal(Double.parseDouble(createdPayment.getTransactions().get(0).getAmount().getTotal()));
            paymentRecord.setStatus(createdPayment.getState());
            paymentRecord.setCreatedAt(LocalDateTime.now());
            paymentRecord.setUpdatedAt(LocalDateTime.now());

            paymentRepository.save(paymentRecord);

            return createdPayment;
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            // Добавьте обработку исключений и логирование по мере необходимости
            throw new RuntimeException("Ошибка при создании платежа", e);
        }
    }

    public Payment executePay(
            String paymentId,
            String payerID) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerID);

        Payment executedPayment = payment.execute(apiContext, paymentExecution);

        // Обновляем запись о платеже в базе данных
        PaymentRecord paymentRecord = paymentRepository.findByPaymentId(paymentId);
        if (paymentRecord != null) {
            paymentRecord.setStatus(executedPayment.getState());
            paymentRecord.setUpdatedAt(LocalDateTime.now());
            paymentRepository.save(paymentRecord);
        }

        return executedPayment;
    }

    public PaymentRecord getPaymentRecord(String paymentId) {
        return paymentRepository.findByPaymentId(paymentId);
    }
}