package ru.netology.aqa.data;

import lombok.Value;

import java.util.Random;

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