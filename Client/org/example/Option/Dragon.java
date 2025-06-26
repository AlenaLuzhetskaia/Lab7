package org.example.Option;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Dragon implements Serializable {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate = new Date(); //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int age; //Значение поля должно быть больше 0
    private int wingspan; //Значение поля должно быть больше 0
    private Color color; //Поле не может быть null
    private DragonCharacter character; //Поле может быть null
    private Person killer; //Поле может быть null

    public Dragon(long id, String name, Coordinates coordinates, Date creationDate, int age, int wingspan, Color color, DragonCharacter character, Person killer) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.age = age;
        this.wingspan = wingspan;
        this.color = color;
        this.character = character;
        this.killer = killer;
    }


    public  String toString() {
        return "id: " + id + "\n" + "name: " + name + "\n" + "coordinates: " + coordinates.toString() + "\n" +
                "creationDate: " + creationDate + "\n" + "age: " + age + "\n" + "wingspan: " + wingspan + "\n" +
                "color: " + color + "\n" + "character: " + character + "\n" + "killer: " + killer;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setWingspan(int wingspan) {
        this.wingspan = wingspan;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setCharacter(DragonCharacter character) {
        this.character = character;
    }

    public Long getID() {
        return id;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setKiller(Person killer) {
        this.killer = killer;
    }

    public Person getKiller(){
        return this.killer;
    }

    public Color getColor() {
        if (color == null) {
            throw new IllegalStateException("Color cannot be null");
        }
        return color;
    }

    public DragonCharacter getCharacter() {
        return character;
    }

    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) {
            throw new IllegalArgumentException("Координаты не могут быть null");
        }
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public int getWingspan() {
        return wingspan;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> toXML() {
        ArrayList<String> xmlData = new ArrayList<>();
        xmlData.add("\t<dragon>");

        xmlData.add("\t\t<id>" + id + "</id>");
        xmlData.add("\t\t<name>" + name + "</name>");
        xmlData.add("\t\t<coordinates>\n" + coordinates.toXML() + "\t\t</coordinates>");
        xmlData.add("\t\t<creationDate>" + creationDate + "</creationDate>");
        xmlData.add("\t\t<age>" + age + "</age>");
        xmlData.add("\t\t<wingspan>" + wingspan + "</wingspan>");
        xmlData.add("\t\t<color>" + color + "</color>");
        xmlData.add("\t\t<character>" + character + "</character>");
        if (killer == null) {
            xmlData.add("\t\t<killer>" + null + "</killer>");
        } else {
            xmlData.add("\t\t<killer>\n" + killer.toXML() + "\t\t</killer>");
        }
        xmlData.add("\t</dragon>");
        return xmlData;
    }

    public void setId(Long id) {
        this.id = id;
    }
}