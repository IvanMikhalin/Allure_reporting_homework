package ru.netology.delivery.test;

import com.codeborne.selenide.logevents.LogEventListener;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.test.data.DateGenerator;
import ru.netology.delivery.test.data.NewUserChar;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static java.time.Duration.ofSeconds;
import static ru.netology.delivery.test.data.DataGenerator.generateNewDate;
import static ru.netology.delivery.test.data.DataGenerator.generateNewUser;

public class CardOrderingTest {

    @BeforeAll
    static void setupAll(){
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void cleanEverything(){
        SelenideLogger.removeListener("allure");
    }


    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldCheckComplitedForm() {
        NewUserChar user = generateNewUser("ru");
        DateGenerator date = generateNewDate(3);
        $("[data-test-id=city] input").setValue(user.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date.getData());
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(user.getPhone());
        $("[data-test-id='agreement']").click();
        $(".button_size_m").click();
        $("[data-test-id='success-notification'").should(visible, ofSeconds(15)).shouldHave(text("Встреча успешно запланирована на " + date.getData()));
    }


    @Test
    void shouldCheckEnglishLettersInFieldName() {
        NewUserChar user = generateNewUser("fr_FR");
        DateGenerator date = generateNewDate(3);
        $("[data-test-id=city] input").setValue(user.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date.getData());
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(user.getPhone());
        $("[data-test-id='agreement']").click();
        $(".button_size_m").click();
        $("[data-test-id='name']").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }


    @Test
    void shouldAcceptDifficultSurname() {
        NewUserChar user = generateNewUser("ru");
        DateGenerator date = generateNewDate(3);
        $("[data-test-id=city] input").setValue(user.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date.getData());
        $("[data-test-id='name'] input").setValue("Иван Иванов-Петров");
        $("[data-test-id='phone'] input").setValue(user.getPhone());
        $("[data-test-id='agreement']").click();
        $(".button_size_m").click();
        $("[data-test-id='success-notification'").should(visible, ofSeconds(15)).shouldHave(text("Встреча успешно запланирована на " + date.getData()));
    }

    @Test
    void shouldThrowMessageIfFieldСityIsEmpty() {
        NewUserChar user = generateNewUser("ru");
        DateGenerator date = generateNewDate(3);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date.getData());
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(user.getPhone());
        $("[data-test-id='agreement']").click();
        $(".button_size_m").click();
        $(withText("Поле обязательно для заполнения")).shouldBe(visible);
    }

    @Test
    void shouldThrowMessageIfFieldDataIsEmpty() {
        NewUserChar user = generateNewUser("ru");
        $("[data-test-id=city] input").setValue(user.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(user.getPhone());
        $("[data-test-id='agreement']").click();
        $(".button_size_m").click();
        $(withText("Неверно введена дата")).shouldBe(visible);
    }

    @Test
    void shouldThrowMessageIfFieldNameIsEmpty() {
        NewUserChar user = generateNewUser("ru");
        DateGenerator date = generateNewDate(3);
        $("[data-test-id=city] input").setValue(user.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date.getData());
        $("[data-test-id='phone'] input").setValue(user.getPhone());
        $("[data-test-id='agreement']").click();
        $(".button_size_m").click();
        $(withText("Поле обязательно для заполнения")).shouldBe(visible);
    }

    @Test
    void shouldThrowMessageIfFieldPhoneIsEmpty() {
        NewUserChar user = generateNewUser("ru");
        DateGenerator date = generateNewDate(3);
        $("[data-test-id=city] input").setValue(user.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date.getData());
        $("[data-test-id='name'] input").setValue(user.getName());
        //$("[data-test-id='phone'] input").setValue(user.getPhone());
        $("[data-test-id='agreement']").click();
        $(".button_size_m").click();
        $(withText("Поле обязательно для заполнения")).shouldBe(visible);
    }

    @Test
    void shouldThrowMessageIfNoSuchRussianCity() {
        NewUserChar user = generateNewUser("ru");
        DateGenerator date = generateNewDate(3);
        $("[data-test-id=city] input").setValue("Экибастуз");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date.getData());
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(user.getPhone());
        $("[data-test-id='agreement']").click();
        $(".button_size_m").click();
        $(withText("Доставка в выбранный город недоступна")).shouldBe(visible);
    }


    @Test
    void shouldNotSubmitFormIfNotAgreedWithConditions() {
        NewUserChar user = generateNewUser("ru");
        DateGenerator date = generateNewDate(3);
        $("[data-test-id=city] input").setValue(user.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date.getData());
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(user.getPhone());
        $(".button_size_m").click();
        $("[data-test-id=agreement].input_invalid").shouldBe(visible);
    }

    @Test
    void shouldNotSubmitFormIfDateLessThenThreeDaysFromCurrentDate() {
        NewUserChar user = generateNewUser("ru");
        DateGenerator date = generateNewDate(1);
        $("[data-test-id=city] input").setValue(user.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date.getData());
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(user.getPhone());
        $(".button_size_m").click();
        $(withText("Заказ на выбранную дату невозможен")).shouldBe(visible);
    }

    @Test
    void shouldCheckPlannedDateOverWrite() {
        int a = 3;
        String newDate = LocalDate.now().plusDays(a + 1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        NewUserChar user = generateNewUser("ru");
        DateGenerator date = generateNewDate(a);
        $("[data-test-id=city] input").setValue(user.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date.getData());
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(user.getPhone());
        $("[data-test-id='agreement']").click();
        $(".button_size_m").click();
        $("[data-test-id='success-notification'").should(visible, ofSeconds(15)).shouldHave(text("Встреча успешно запланирована на " + date.getData()));
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(newDate);
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='replan-notification'").should(visible, ofSeconds(15)).shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $$("button").find(exactText("Перепланировать")).click();
        $("[data-test-id='success-notification'").should(visible, ofSeconds(15)).shouldHave(text("Встреча успешно запланирована на " + newDate));

    }

}
