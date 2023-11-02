package ru.netology.aqa.data;

import lombok.Value;

public class DataHelper {
    private DataHelper() {}

    @Value
    public static class VerificationCode {
        String code;
    }

    public static VerificationCode getVerificationCode() {
        return new VerificationCode("12345");
    }
}