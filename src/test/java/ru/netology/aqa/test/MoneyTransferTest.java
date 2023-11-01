package ru.netology.aqa.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.aqa.data.DataHelper;
import ru.netology.aqa.page.DashboardPage;
import ru.netology.aqa.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

class MoneyTransferTest {

    DashboardPage dashboardPage;
    DataHelper.CardInfo card1Info;
    DataHelper.CardInfo card2Info;
    int card1BalanceBefore;
    int card2BalanceBefore;

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
        card1Info = DataHelper.getCard1Info();
        card2Info = DataHelper.getCard2Info();
        card1BalanceBefore = dashboardPage.getCardBalance(card1Info);
        card2BalanceBefore = dashboardPage.getCardBalance(card2Info);
    }
    @Test
    void shouldTransferMoneyBetweenOwnCards() { //  Card2 => Card1, Happy path
        var amountToTransfer = DataHelper.generateValidAmountToTransfer(card2BalanceBefore);
        var transferPage = dashboardPage.selectCardToTransfer(card1Info);
        dashboardPage = transferPage.makeValidTransfer(card2Info.getNumber(), amountToTransfer);
        var card1BalanceAfter = dashboardPage.getCardBalance(card1Info);
        var card2BalanceAfter = dashboardPage.getCardBalance(card2Info);
        Assertions.assertEquals(card1BalanceAfter - amountToTransfer, card1BalanceBefore);  //Баланс 1-й карты увеличился на верную величину
        Assertions.assertEquals(card2BalanceAfter + amountToTransfer, card2BalanceBefore);  //Баланс 2-й карты уменьшился на верную величину
    }


    @Test
    void shouldNotTransferMoneyBetweenOwnCards() {  //Card2 => Card1, Sad path
        var AmountToTransfer = DataHelper.generateInvalidAmountToTransfer(card2BalanceBefore);
        var transferPage = dashboardPage.selectCardToTransfer(card1Info);
        transferPage.makeTransfer(card2Info.getNumber(), AmountToTransfer);
        var card1BalanceAfter = dashboardPage.getCardBalance(card1Info);
        var card2BalanceAfter = dashboardPage.getCardBalance(card2Info);
        Assertions.assertEquals(card1BalanceBefore, card1BalanceAfter);  //Баланс 1-й карты не должен измениться
        Assertions.assertEquals(card2BalanceBefore, card2BalanceAfter);  //Баланс 2-й карты не должен измениться
        //Ошибку не ищу, т.к. тест всё равно упадёт на ассертах, к тому же точный текст ошибки неизвестен
    }

}
