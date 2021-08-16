package ru.netology;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class DebitCardApplicationTest {
    
    @Test
    void shouldRegisteredByAccount() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Майер Евгений");
        $("[data-test-id=phone] input").setValue("+79129855670");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $("[data-test-id=order-success]")
                .shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldRegisteredByNotValidName() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Eugen Mayer");
        $("[data-test-id=phone] input").setValue("+79129855670");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $("[data-test-id=name] span.input__sub")
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldEmptyFieldLastName() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue("+79127879339");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=name] span.input__sub")
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldGetErrorMessageIfTelEmpty() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Майер Юджин");
        $("[data-test-id=phone] input").setValue("");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=phone] span.input__sub")
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldGetErrorMessageIfTelNotValid() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Каменева Дарья");
        //TODO дабы посмотреть как будет работать код с валидным значением, решил сразу посмотреть оба варианта на буквы и цифры больше нужного.
//        $("[data-test-id=phone] input").setValue("Asdsdadadasd");
        $("[data-test-id=phone] input").setValue("+799999999999999");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=phone] span.input__sub")
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldBlankRegistrationForm() {
        open("http://localhost:9999/");
        $("[data-test-id=name] input").setValue("Каменева Дарья");
        $("[data-test-id=phone] input").setValue("+79129855670");
        $(".button").click();
        $("[data-test-id=agreement].input_invalid .checkbox__text")
                .shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй"));


    }
}
