package ru.netology.delivery.generator;

import com.github.javafaker.Faker;
import ru.netology.delivery.Data.data.NewUserChar;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.io.FileReader;
import java.util.Scanner;


public class DataGenerator {
    public static NewUserChar generateNewUser(String locale, int shiftDay) {
        return new NewUserChar(generateCity(locale), generateDate(shiftDay), generateName(locale), generatePhone(locale));
    }

    public static String generateCity(String locale) {
        String[] cities = new String[]{
                "Москва", "Майкоп", "Уфа", "Нижний Новгород", "Махачкала", "Магас", "Нальчик", "Элиста", "Черкесск", "Петрозаводск", "Сыктывкар",
                "Саранск", "Якутск", "Владикавказ", "Казань", "Ижевск", "Грозный", "Чебоксары", "Барнаул"
        };
        Faker faker = new Faker(new Locale(locale));
        return cities[faker.number().numberBetween(0, cities.length - 1)];
    }

    public static String generateDate(int addDays) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateName(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String generatePhone(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.phoneNumber().phoneNumber();
    }
}