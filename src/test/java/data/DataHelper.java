package ru.netology.web.data;

import lombok.Value;
import lombok.val;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.TransferMoneyPage;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class Auth {
        String login;
        String password;
    }

    public static Auth getAuth() {
        return new Auth("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    public static VerificationCode getVerificationCode() {
        return new VerificationCode("12345");
    }

    @Value
    public static class Card {
        String id;
        String number;
    }

    public static Card getFirstCard() {
        return new Card("92df3f1c-a033-48e6-8390-206f6b1f56c0", "5559 0000 0000 0001");
    }

    public static Card getSecondCard() {
        return new Card("0f3f5c2a-249e-4c3d-8287-09f7a039391d", "5559 0000 0000 0002");
    }

    public static void getInitialData(DashboardPage dashboardPage, Card firstCard, Card secondCard) {
        val moneyTransferPage = new TransferMoneyPage();

        int firstCardBalance = dashboardPage.getCardBalance(firstCard);
        int secondCardBalance = dashboardPage.getCardBalance(secondCard);

        if (firstCardBalance > secondCardBalance) {
            int amount = (firstCardBalance + secondCardBalance) / 2 - secondCardBalance;
            dashboardPage.clickTopUpButton(secondCard);
            moneyTransferPage.topUpCard(amount, firstCard, secondCard);
        } else {
            if (firstCardBalance < secondCardBalance) {
                int amount = (secondCardBalance + firstCardBalance) / 2 - firstCardBalance;
                dashboardPage.clickTopUpButton(firstCard);
                moneyTransferPage.topUpCard(amount, secondCard, firstCard);
            }
        }
        new DashboardPage();
    }
}