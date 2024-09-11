package com.example.flowershop.pay;

    public class NumberParser {
        public static int parseToThreeDigitNumber(String input) throws IllegalArgumentException {
            // Проверяем, что строка не пустая и содержит только цифры
            if (input == null || input.isEmpty() || !input.matches("\\d+")) {
                throw new IllegalArgumentException("Строка содержит недопустимые символы или пуста");
            }

            // Преобразуем строку в число
            int number = Integer.parseInt(input);

            // Проверяем, что число трёхзначное
            if (number < 100 || number > 999) {
                throw new IllegalArgumentException("Число должно быть трёхзначным");
            }

            return number;
        }

        public static void validateCvv(String cvv) throws IllegalArgumentException {
            // Проверяем, что строка содержит ровно 3 цифры
            if (cvv == null || !cvv.matches("\\d{3}")) {
                throw new IllegalArgumentException("CVV должен состоять из трёх цифр.");
            }
        }
}
