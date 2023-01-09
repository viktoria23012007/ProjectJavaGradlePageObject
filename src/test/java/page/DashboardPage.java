package ru.netology.web.page;

import com.codeborne.selenide.*;
import lombok.val;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.web.data.DataHelper.*;

public class DashboardPage {

    public DashboardPage() {
        SelenideElement heading = $("[data-test-id=dashboard]");
        heading.shouldBe(visible);
    }

    public int getCardBalance(Card card) {
        String id = card.getId();
        val text = $("[data-test-id='" + id + "']").getText();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        String balanceStart = "баланс: ";
        String balanceFinish = " р.";
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public void clickTopUpButton(Card card) {
        String id = card.getId();
        $("[data-test-id='" + id + "']")
                .find("[data-test-id='action-deposit']")
                .click();
        new TransferMoneyPage();
    }


}