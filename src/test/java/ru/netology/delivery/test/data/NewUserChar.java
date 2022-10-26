package ru.netology.delivery.test.data;

public class NewUserChar {

    public NewUserChar(String city, String name, String phone) {
        this.city = city;
        this.name = name;
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    private final String city;

    public String getName() {
        return name;
    }

    private final String name;

    public String getPhone() {
        return phone;
    }

    private final String phone;

}