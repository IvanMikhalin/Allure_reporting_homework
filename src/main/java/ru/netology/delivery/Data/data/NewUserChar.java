package ru.netology.delivery.Data.data;

public class NewUserChar {

    public NewUserChar(String city, String data, String name, String phone) {
        this.city = city;
        this.data = data;
        this.name = name;
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    private final String city;

    public String getData() {
        return data;
    }

    private final String data;

    public String getName() {
        return name;
    }

    private final String name;

    public String getPhone() {
        return phone;
    }

    private final String phone;

}