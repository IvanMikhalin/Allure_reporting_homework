package ru.netology.delivery.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.Data.data.NewUserChar;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static java.time.Duration.ofSeconds;
import static ru.netology.delivery.generator.DataGenerator.generateNewUser;

public class CardOrderingTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldCheckComplitedForm() {
        NewUserChar user = generateNewUser("ru", 3);
        $("[data-test-id=city] input").setValue(user.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(user.getData());
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(user.getPhone());
        $("[data-test-id='agreement']").click();
        $(".button_size_m").click();
        $("[data-test-id='success-notification'").should(visible, ofSeconds(15)).shouldHave(text("Встреча успешно запланирована на " + user.getData()));
    }


    @Test
    void shouldCheckEnglishLettersInFieldName() {
        NewUserChar user = generateNewUser("fr_FR", 3);
        $("[data-test-id=city] input").setValue(user.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(user.getData());
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(user.getPhone());
        $("[data-test-id='agreement']").click();
        $(".button_size_m").click();
        $("[data-test-id='name']").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }


    @Test
    void shouldAcceptDifficultSurname() {
        NewUserChar user = generateNewUser("ru", 3);
        $("[data-test-id=city] input").setValue(user.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(user.getData());
        $("[data-test-id='name'] input").setValue("Иван Иванов-Петров");
        $("[data-test-id='phone'] input").setValue(user.getPhone());
        $("[data-test-id='agreement']").click();
        $(".button_size_m").click();
        $("[data-test-id='success-notification'").should(visible, ofSeconds(15)).shouldHave(text("Встреча успешно запланирована на " + user.getData()));
    }

    @Test
    void shouldThrowMessageIfFieldСityIsEmpty() {
        NewUserChar user = generateNewUser("ru", 3);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(user.getData());
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(user.getPhone());
        $("[data-test-id='agreement']").click();
        $(".button_size_m").click();
        $(withText("Поле обязательно для заполнения")).shouldBe(visible);
    }

    @Test
    void shouldThrowMessageIfFieldDataIsEmpty() {
        NewUserChar user = generateNewUser("ru", 3);
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
        NewUserChar user = generateNewUser("ru", 3);
        $("[data-test-id=city] input").setValue(user.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(user.getData());
        $("[data-test-id='phone'] input").setValue(user.getPhone());
        $("[data-test-id='agreement']").click();
        $(".button_size_m").click();
        $(withText("Поле обязательно для заполнения")).shouldBe(visible);
    }

    @Test
    void shouldThrowMessageIfFieldPhoneIsEmpty() {
        NewUserChar user = generateNewUser("ru", 3);
        $("[data-test-id=city] input").setValue(user.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(user.getData());
        $("[data-test-id='name'] input").setValue(user.getName());
        //$("[data-test-id='phone'] input").setValue(user.getPhone());
        $("[data-test-id='agreement']").click();
        $(".button_size_m").click();
        $(withText("Поле обязательно для заполнения")).shouldBe(visible);
    }

    @Test
    void shouldThrowMessageIfNoSuchRussianCity() {
        NewUserChar user = generateNewUser("ru", 3);
        $("[data-test-id=city] input").setValue("Экибастуз");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(user.getData());
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(user.getPhone());
        $("[data-test-id='agreement']").click();
        $(".button_size_m").click();
        $(withText("Доставка в выбранный город недоступна")).shouldBe(visible);
    }


    @Test
    void shouldNotSubmitFormIfNotAgreedWithConditions() {
        NewUserChar user = generateNewUser("ru", 3);
        $("[data-test-id=city] input").setValue(user.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(user.getData());
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(user.getPhone());
        $(".button_size_m").click();
        $("[data-test-id=agreement].input_invalid").shouldBe(visible);
    }

    @Test
    void shouldNotSubmitFormIfDateLessThenThreeDaysFromCurrentDate() {
        NewUserChar user = generateNewUser("ru", 1);
        $("[data-test-id=city] input").setValue(user.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(user.getData());
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(user.getPhone());
        $(".button_size_m").click();
        $(withText("Заказ на выбранную дату невозможен")).shouldBe(visible);
    }

    @Test
    void shouldCheckPlannedDateOverWrite() {
        int a = 3;
        String newDate = LocalDate.now().plusDays(a + 1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        NewUserChar user = generateNewUser("ru", a);
        $("[data-test-id=city] input").setValue(user.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(user.getData());
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(user.getPhone());
        $("[data-test-id='agreement']").click();
        $(".button_size_m").click();
        $("[data-test-id='success-notification'").should(visible, ofSeconds(15)).shouldHave(text("Встреча успешно запланирована на " + user.getData()));
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(newDate);
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='replan-notification'").should(visible, ofSeconds(15)).shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $$("button").find(exactText("Перепланировать")).click();
        $("[data-test-id='success-notification'").should(visible, ofSeconds(15)).shouldHave(text("Встреча успешно запланирована на " + newDate));

    }

}
