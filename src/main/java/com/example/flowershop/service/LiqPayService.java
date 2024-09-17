//package com.example.flowershop.service;
//
//import java.nio.charset.StandardCharsets;
//import java.security.MessageDigest;
//import java.util.Base64;
//import java.util.HashMap;
//import java.util.Map;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.stereotype.Service;
//
//@Service
//public class LiqPayService {
//
//    private static final String PUBLIC_KEY = "sandbox_i64788150484";
//    private static final String PRIVATE_KEY = "sandbox_qv7RfYuHjLekfjNcRnTgKqQ3IBZWJxz8yvsiIGk3";
//
//    public String generatePaymentForm(String amount, String currency, String description, String orderId) throws Exception {
//        // Формируем параметры
//        Map<String, String> params = new HashMap<>();
//        params.put("version", "3");
//        params.put("public_key", PUBLIC_KEY);
//        params.put("action", "pay");
//        params.put("amount", amount);
//        params.put("currency", currency);
//        params.put("description", description);
//        params.put("order_id", orderId);
//
//        // Конвертируем параметры в JSON
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(params);
//
//        // Кодируем JSON в base64
//        String data = Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
//
//        // Генерируем подпись
//        String signature = generateSignature(data);
//
//        // Возвращаем HTML-форму
//        return generateHtmlForm(data, signature);
//    }
//
//    private String generateSignature(String data) throws Exception {
//        String signatureString = PRIVATE_KEY + data + PRIVATE_KEY;
//        MessageDigest md = MessageDigest.getInstance("SHA-1");
//        byte[] digest = md.digest(signatureString.getBytes(StandardCharsets.UTF_8));
//        return Base64.getEncoder().encodeToString(digest);
//    }
//
//    private String generateHtmlForm(String data, String signature) {
//        return "<form method=\"POST\" action=\"https://www.liqpay.ua/api/3/checkout\" accept-charset=\"utf-8\">\n" +
//                "<input type=\"hidden\" name=\"data\" value=\"" + data + "\"/>\n" +
//                "<input type=\"hidden\" name=\"signature\" value=\"" + signature + "\"/>\n" +
//                "<input type=\"image\" src=\"//static.liqpay.ua/buttons/payUk.png\"/>\n" +
//                "</form>";
//    }
//}


package com.example.flowershop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class LiqPayService {

    private static final String PUBLIC_KEY = "sandbox_i64788150484";
    private static final String PRIVATE_KEY = "sandbox_qv7RfYuHjLekfjNcRnTgKqQ3IBZWJxz8yvsiIGk3";

    public String generatePaymentForm(String amount, String currency, String description, String orderId) throws Exception {
        // Параметры платежа
        Map<String, String> params = new HashMap<>();
        params.put("version", "3");
        params.put("public_key", PUBLIC_KEY);
        params.put("action", "pay");
        params.put("amount", amount);
        params.put("currency", currency);
        params.put("description", description);
        params.put("order_id", orderId);

        // Конвертация параметров в JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(params);

        // Кодировка JSON в base64
        String data = Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));

        // Генерация подписи
        String signature = generateSignature(data);

        // Возвращение HTML-формы для оплаты
        return generateHtmlForm(data, signature);
    }

    private String generateSignature(String data) throws Exception {
        String signatureString = PRIVATE_KEY + data + PRIVATE_KEY;
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] digest = md.digest(signatureString.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(digest);
    }

    private String generateHtmlForm(String data, String signature) {
        return "<form method=\"POST\" action=\"https://www.liqpay.ua/api/3/checkout\" accept-charset=\"utf-8\">\n" +
                "<input type=\"hidden\" name=\"data\" value=\"" + data + "\"/>\n" +
                "<input type=\"hidden\" name=\"signature\" value=\"" + signature + "\"/>\n" +
                "<input type=\"image\" src=\"//static.liqpay.ua/buttons/payUk.png\"/>\n" +
                "</form>";
    }
}