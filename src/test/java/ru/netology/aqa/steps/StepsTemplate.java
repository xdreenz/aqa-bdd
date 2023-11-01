package ru.netology.aqa.steps;

import io.cucumber.java.ru.И;
import org.junit.jupiter.api.Assertions;
import ru.netology.aqa.data.DataHelper;
import ru.netology.aqa.page.DashboardPage;
import ru.netology.aqa.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class StepsTemplate {
    private DashboardPage dashboardPage;

    @И("Пусть пользователь залогинен под именем {string} и паролем {string}")
    public void loginWithNameAndPassword(String login, String password) {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(login, password);
        var verificationCode = DataHelper.getVerificationCode();
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @И("Когда пользователь переводит {int} рублей с карты с номером {string} на свою {int} карту на главной странице")
    public void makeTransfer(int amount, String sourceCardNumber, int targetCardIndex) {
        var transferPage = dashboardPage.selectCardToTransfer(targetCardIndex);
        dashboardPage = transferPage.makeTransfer(sourceCardNumber, amount);
    }

    @И("Тогда баланс его {int} карты на главной странице должен стать {int} рублей")
    public void verifyTargetCardBalance(int cardIndex, int expectedCardBalance) {
        var targetCardBalance = dashboardPage.getCardBalance(cardIndex);
        Assertions.assertEquals(expectedCardBalance, targetCardBalance);
    }
}
