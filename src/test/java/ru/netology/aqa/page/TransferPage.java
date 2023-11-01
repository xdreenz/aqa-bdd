package ru.netology.aqa.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.*;

public class TransferPage {

    private final SelenideElement amountField = $("[data-test-id = 'amount'] input");
    private final SelenideElement fromField = $("[data-test-id = 'from'] input");
    private final SelenideElement transferButton = $("[data-test-id = 'action-transfer']");

    TransferPage() {
        $("h1").shouldHave(text("Пополнение карты"));
    }

    public void makeTransfer(String sourceCardNumber, int amount) {
        amountField.setValue(String.valueOf(amount));
        fromField.setValue(sourceCardNumber);
        transferButton.click();
    }

    public DashboardPage makeValidTransfer(String sourceCardNumber, int amount) {
        makeTransfer(sourceCardNumber, amount);
        return new DashboardPage();
    }
}
