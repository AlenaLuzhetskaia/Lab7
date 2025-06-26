package org.example.Command;

import org.example.Option.Dragon;
import org.example.Other.DBRouter;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.concurrent.locks.ReentrantLock;
import org.example.Option.Color;
import org.example.Option.DragonCharacter;

public class CollectionManager {
    private final PriorityQueue<Dragon> dragonPriorityQueue = new PriorityQueue<>(Comparator.comparing(Dragon::getID));
    private final Date creationDate = new Date();
    private final ReentrantLock lock = new ReentrantLock();

    public void add(Dragon dragon) {
        lock.lock();
        try {
            dragonPriorityQueue.offer(dragon);
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try {
            return dragonPriorityQueue.size();
        } finally {
            lock.unlock();
        }
    }

    public Date creationDate() {
        return creationDate;
    }
    
    public int searchId(long id) {
    lock.lock();
    try {
        int index = 0;
        for (Dragon dragon : dragonPriorityQueue) {
            if (dragon.getID() == id) {
                return index;
            }
            index++;
        }
        return -1;
    } finally {
        lock.unlock();
    }
}

    public PriorityQueue<Dragon> getDragonPriorityQueue() {
        lock.lock();
        try {
            return new PriorityQueue<>(dragonPriorityQueue);
        } finally {
            lock.unlock();
        }
    }

    public Dragon getById(long id) {
        lock.lock();
        try {
            for (Dragon dragon : dragonPriorityQueue) {
                if (dragon.getID() == id) {
                    return dragon;
                }
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

    public void clear() {
        lock.lock();
        try {
            dragonPriorityQueue.clear();
        } finally {
            lock.unlock();
        }
    }

    public boolean removeByID(long id) {
        lock.lock();
        try {
            Iterator<Dragon> iterator = dragonPriorityQueue.iterator();
            while (iterator.hasNext()) {
                Dragon dragon = iterator.next();
                if (dragon.getID() == id) {
                    iterator.remove();
                    return true;
                }
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public boolean isMax(Dragon dragon) {
        lock.lock();
        try {
            if (dragonPriorityQueue.isEmpty()) {
                return true;
            }
            for (Dragon d : dragonPriorityQueue) {
                if (d.getAge() > dragon.getAge()) {
                    return false;
                }
            }
            return true;
        } finally {
            lock.unlock();
        }
    }

    public void removeGreater(int age) {
        lock.lock();
        try {
            Iterator<Dragon> iterator = dragonPriorityQueue.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getAge() > age) {
                    iterator.remove();
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public void removeAllByAge(int age) {
        lock.lock();
        try {
            Iterator<Dragon> iterator = dragonPriorityQueue.iterator();
            while (iterator.hasNext()) {
                Dragon dragon = iterator.next();
                if (dragon.getAge() == age) {
                    iterator.remove();
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean removeAnyByColor(Color color) {
        lock.lock();
        try {
            Iterator<Dragon> iterator = dragonPriorityQueue.iterator();
            while (iterator.hasNext()) {
                Dragon dragon = iterator.next();
                if (dragon.getColor() == color) {
                    iterator.remove();
                    return true;
                }
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public int countByCharacter(DragonCharacter character) {
        lock.lock();
        try {
            int count = 0;
            for (Dragon dragon : dragonPriorityQueue) {
                if (dragon.getCharacter() == character) {
                    count++;
                }
            }
            return count;
        } finally {
            lock.unlock();
        }
    }

    public PriorityQueue<Dragon> getAll() {
        lock.lock();
        try {
            return new PriorityQueue<>(dragonPriorityQueue);
        } finally {
            lock.unlock();
        }
    }
    public void removeAllByAgeAndOwner(int age, String owner) {
    lock.lock();
    try {
        Iterator<Dragon> iterator = dragonPriorityQueue.iterator();
        while (iterator.hasNext()) {
            Dragon dragon = iterator.next();
            if (dragon.getAge() == age) {
                String dragonOwner = DBRouter.getDragonOwnerById(dragon.getID());
                if (dragonOwner != null && dragonOwner.equals(owner)) {
                    iterator.remove();
                }
            }
        }
    } finally {
        lock.unlock();
    }
}
    public void removeGreaterByOwner(int age, String owner) {
        lock.lock();
        try {
            Iterator<Dragon> iterator = dragonPriorityQueue.iterator();
            while (iterator.hasNext()) {
                Dragon dragon = iterator.next();
                if (dragon.getAge() > age) {
                    String dragonOwner = org.example.Other.DBRouter.getDragonOwnerById(dragon.getID());
                    if (dragonOwner != null && dragonOwner.equals(owner)) {
                        iterator.remove();
                    }
                }
            }
        } finally {
            lock.unlock();
        }
    }
    public boolean updateById(long id, Dragon newDragon) {
        lock.lock();
        try {
            for (Dragon dragon : dragonPriorityQueue) {
                if (dragon.getID() == id) {
                    dragon.setName(newDragon.getName());
                    dragon.setCoordinates(newDragon.getCoordinates());
                    dragon.setCreationDate(newDragon.getCreationDate());
                    dragon.setAge(newDragon.getAge());
                    dragon.setWingspan(newDragon.getWingspan());
                    dragon.setColor(newDragon.getColor());
                    dragon.setCharacter(newDragon.getCharacter());
                    dragon.setKiller(newDragon.getKiller());
                    return true;
                }
            }
            return false;
        } finally {
            lock.unlock();
        }
    }
    public void clearByOwner(String owner) {
        lock.lock();
        try {
            Iterator<Dragon> iterator = dragonPriorityQueue.iterator();
            while (iterator.hasNext()) {
                Dragon dragon = iterator.next();
                String dragonOwner = DBRouter.getDragonOwnerById(dragon.getID());
                if (owner.equals(dragonOwner)) {
                    iterator.remove();
                }
            }
        } finally {
            lock.unlock();
        }
    }
}