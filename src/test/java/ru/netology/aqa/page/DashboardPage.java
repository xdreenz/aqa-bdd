package ru.netology.aqa.page;

import com.codeborne.selenide.ElementsCollection;

import lombok.val;
import ru.netology.aqa.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private final ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
        $("[data-test-id = 'dashboard']").shouldBe(visible);
    }

    public int getCardBalance(DataHelper.CardInfo cardInfo) {
        return extractBalance(cards.findBy(text(cardInfo.getNumber().substring(15))) .getText());
    }

    public TransferPage selectCardToTransfer(DataHelper.CardInfo cardInfo) {
        var cardBlock = cards.findBy(attribute("data-test-id", cardInfo.getTestId()));
        cardBlock.$("button").click();
        return new TransferPage();
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}