package org.example.Option;

import java.io.Serializable;

public class Person implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private double weight; //Значение поля должно быть больше 0
    private String passportID; //Строка не может быть пустой, Длина строки не должна быть больше 43, Поле не может быть null
    private Color eyeColor; //Поле не может быть null
    public Person(String name, double weight, String passportID, Color color){
        this.name = name;
        this.weight = weight;
        this.passportID = passportID;
        this.eyeColor = color;
    }

    public void setName(String name) {
        this.name = (name == null) ? "Не существует": name;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    public void setEyeColor(Color color) {
        this.eyeColor = color;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public double getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }

    public String getPassportID() {
        return passportID;
    }

    public String toString() {
        return "\n\tName: " + name + "\n\tWeight: " + weight + "\n\tPassportID: " + passportID + "\n\tEyeColor: " + eyeColor;
    }

    public String toXML() {
        return "\t\t\t<name>" + name + "</name>\n" + "\t\t\t<weight>" + weight + "</weight>\n" + "\t\t\t<passportID>" + passportID +
                "</passportID>\n" + "\t\t\t<eyeColor>" + eyeColor + "</eyeColor>\n";
    }

}

