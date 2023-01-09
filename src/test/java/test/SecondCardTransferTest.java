package ru.netology.web.test;

import org.junit.jupiter.api.*;
import ru.netology.web.page.*;

import static com.codeborne.selenide.Selenide.open;
import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.*;

class SecondCardTransferTest {

    DashboardPage dashboardPage;
    TransferMoneyPage transferMoneyPage;
    Card firstCard = getFirstCard();
    Card secondCard = getSecondCard();

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        LoginPage loginPage = new LoginPage();

        loginPage.validLogin(getAuth());
        VerificationPage verificationPage = new VerificationPage();

        verificationPage.validVerify(getVerificationCode());
        dashboardPage = new DashboardPage();
        transferMoneyPage = new TransferMoneyPage();
        dashboardPage.clickTopUpButton(secondCard);
    }

    @AfterEach
    void returnInitialData(TestInfo testInfo) {
        if (testInfo.getTags().contains("SkipCleanup")) {
            return;
        }
        getInitialData(dashboardPage, firstCard, secondCard);
    }

    // Проверка ошибки при переводе нулевой суммы
    @Test
    void shouldGetErrorIfAmountNull() {
        int amount = 0;
        transferMoneyPage.topUpCard(amount, firstCard, secondCard);

        transferMoneyPage.getErrorNoneAmount();
    }

    // Проверка успешности перевода в рамках наличных средств
    @Test
    void shouldSuccessIfBelowLimit() {
        int amount = 2000;
        transferMoneyPage.topUpCard(amount, firstCard, secondCard);

        assertEquals(parseInt("12000"), dashboardPage.getCardBalance(secondCard));
        assertEquals(parseInt("8000"), dashboardPage.getCardBalance(firstCard));
    }

    // Проверка ошибки при переводе больше лимита на карте
    @Test
    void shouldGetErrorIfAboveLimit() {
        int amount = 50000;
        transferMoneyPage.topUpCard(amount, firstCard, secondCard);

        transferMoneyPage.getErrorInsufficientFunds();
    }


    // Проверка успешности отмены перевода
    @Test
    void shouldCancelTransaction() {
        transferMoneyPage.cancelTransaction();
    }

    // Проверка ошибки при отправке пустой формы
    @Test
    @Tag("SkipCleanup")
    void shouldGetErrorIfEmptyForm() {
        transferMoneyPage.getErrorEmptyForm();
    }
}