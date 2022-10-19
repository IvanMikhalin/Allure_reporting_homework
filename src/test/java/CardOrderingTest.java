import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardOrderingTest {

    String generateDate(int date) {
        return LocalDate.now().plusDays(date).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

        @Test
    void shouldCheckComplitedForm(){
            String date = generateDate(4);
            open("http://localhost:9999/");
            $("[data-test-id=city] input").setValue("Москва");
            $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
            $("[data-test-id=date] input").setValue(date);
            $("[data-test-id=name] input").setValue("Иван Иванов");
            $("[data-test-id=phone] input").setValue("+79265838984");
            $("[data-test-id=agreement]").click();
            $$("button").find(exactText("Забронировать")).click();
            $("[data-test-id=notification]").shouldHave(exactText("Успешно! " + "Встреча успешно забронирована на " + date), Duration.ofSeconds(15)).shouldBe(exist);

        }
    @Test
    void shouldCheckEnglishLettersInFieldName(){
        String date = generateDate(4);
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Ivan Ivanov");
        $("[data-test-id=phone] input").setValue("+79265838984");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Имя и Фамилия указаные неверно")).shouldBe(visible);
    }

    @Test
    void shouldCheckPhoneNumberField(){
        String date = generateDate(3);
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Иван Иванов");
        $("[data-test-id=phone] input").setValue("+7926583898");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Телефон указан неверно")).shouldBe(visible);
    }

    @Test
    void shouldAcceptDifficultSurname(){
        String date = generateDate(3);
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Иван Иванов-Петров");
        $("[data-test-id=phone] input").setValue("+79265838984");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=notification]").shouldHave(exactText("Успешно! " + "Встреча успешно забронирована на " + date), Duration.ofSeconds(15)).shouldBe(exist);
    }

    @Test
    void shouldThrowMessageIfFieldСityIsEmpty(){
        String date = generateDate(3);
        open("http://localhost:9999/");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Иван Иванов");
        $("[data-test-id=phone] input").setValue("+79265838984");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Поле обязательно для заполнения")).shouldBe(visible);
    }

    @Test
    void shouldThrowMessageIfFieldDataIsEmpty(){
        String date = generateDate(3);
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=name] input").setValue("Иван Иванов");
        $("[data-test-id=phone] input").setValue("+79265838984");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Неверно введена дата")).shouldBe(visible);
    }

    @Test
    void shouldThrowMessageIfFieldNameIsEmpty(){
        String date = generateDate(3);
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=phone] input").setValue("+79265838984");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Поле обязательно для заполнения")).shouldBe(visible);
    }
    @Test
    void shouldThrowMessageIfFieldPhoneIsEmpty(){
        String date = generateDate(3);
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Иван Иванов");
        //$("[data-test-id=phone] input").setValue("+79265838984");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Поле обязательно для заполнения")).shouldBe(visible);
    }

    @Test
    void shouldThrowMessageIfNoSuchRussianCity(){
        String date = generateDate(3);
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Экибастуз"); // или указать Moscow
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Иван Иванов");
        $("[data-test-id=phone] input").setValue("+79265838984");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Доставка в выбранный город недоступна")).shouldBe(visible);
    }

    @Test
    void shouldNotSubmitFormIfNotAgreedWithConditions(){
        String date = generateDate(3);
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Иван Иванов");
        $("[data-test-id=phone] input").setValue("+79265838984");
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=agreement].input_invalid").shouldBe(visible);
    }

    @Test
    void shouldNotSubmitFormIfDateLessThenThreeDaysFromCurrentDate(){
        String date = generateDate(1);
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Иван Иванов");
        $("[data-test-id=phone] input").setValue("+79265838984");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Заказ на выбранную дату невозможен")).shouldBe(visible);
    }

}
