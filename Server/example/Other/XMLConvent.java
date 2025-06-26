package org.example.Other;

import org.example.Command.CollectionManager;
import org.example.Command.IDGenerator;
import org.example.Option.Coordinates;
import org.example.Option.Dragon;
import org.example.Option.DragonCharacter;
import org.example.Option.Person;
import org.example.Option.Color;
//коть, ты обижаешься?:(
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class XMLConvent {
    static ClassReader classReader = new ClassReader();

    public static void XMLWrite(CollectionManager cm) throws IOException {
        String fileName = System.getenv("FILE_NAME");
        if (fileName == null) {
            fileName = "finish.xml";
        }

        ArrayList<ArrayList<String>> XMLDragons = new ArrayList<>();
        for (Dragon dragon : cm.getAll()) {
            XMLDragons.add(dragon.toXML());
        }

        try {
            ClassWriter.writeFile(fileName, XMLDragons);
            System.setProperty("OLD_FILE_NAME", fileName);
        } catch (IOException e) {
            System.err.println("Ошибка при записи XML: " + e.getMessage());
            throw e;
        }
    }

    public static PriorityQueue<Dragon> XMLRead() throws IOException, ParseException {
        PriorityQueue<Dragon> priorityQueue = new PriorityQueue<>(Comparator.comparing(Dragon::getID));
        HashSet<Long> ids = new HashSet<>();
        String fileName = System.getenv("OLD_FILE_NAME");
        if (fileName == null) fileName = "finish.xml";
        fileName = "finish.xml";
        classReader.readFile(fileName);
        try {
            classReader.skipLine();
            while (!Objects.equals(classReader.readLine(), "/<dragons>")) {
                var a = classReader.XMLReadLine();
                if (a == null) break;
                long id = Long.parseLong(a);
                if (ids.contains(id)) {
                    throw new IllegalArgumentException();
                }
                ids.add(id);
                String name = classReader.XMLReadLine();
                classReader.skipLine();
                Coordinates coordinates = new Coordinates(Double.parseDouble(classReader.XMLReadLine()), Integer.parseInt(classReader.XMLReadLine()));
                classReader.skipLine();
                SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
                Date creationDate = format.parse(classReader.XMLReadLine());
                int age = Integer.parseInt(classReader.XMLReadLine());
                int wingspan = Integer.parseInt(classReader.XMLReadLine());
                Color color = Color.valueOf(classReader.XMLReadLine());
                String nullTester = classReader.XMLReadLine();
                DragonCharacter dragonCharacter;
                if (nullTester.equals("null")) {
                    dragonCharacter = null;
                } else {
                    dragonCharacter = DragonCharacter.valueOf(nullTester);
                }
                Person killer = null;
                try {
                    nullTester = classReader.XMLReadLine();
                } catch (IndexOutOfBoundsException e) {
                    String nameK = (classReader.XMLReadLine());
                    double weightK = (Double.parseDouble(classReader.XMLReadLine()));
                    String pass = (classReader.XMLReadLine());
                    Color colorK = (Color.valueOf(classReader.XMLReadLine()));
                    killer = new Person(nameK, weightK, pass, colorK);
                }
                Dragon dragon = new Dragon(id, name, coordinates,
                        creationDate, age, wingspan, color, dragonCharacter, killer);
                priorityQueue.offer(dragon);
                classReader.skipLine();
                Long maxID = 0L;
                for (Long i : ids) {
                    if (i > maxID) maxID = i;
                }
                IDGenerator.setFirstID(maxID);
            }
        } catch (NullPointerException | IllegalArgumentException | IndexOutOfBoundsException error) {
            System.out.println("Произошла ошибка при считывании файла");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return priorityQueue;
    }
}


