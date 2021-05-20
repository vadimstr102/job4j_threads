package ru.job4j.synchronization;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final Map<Integer, User> storage = new HashMap<>();

    public synchronized boolean add(User user) {
        return storage.putIfAbsent(user.getId(), user) == null;
    }

    public synchronized boolean update(User user) {
        return storage.replace(user.getId(), user) != null;
    }

    public synchronized boolean delete(User user) {
        return storage.remove(user.getId()) != null;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        User userFrom = storage.get(fromId);
        User userTo = storage.get(toId);
        if (userFrom != null && userTo != null && userFrom.getAmount() >= amount) {
            userFrom.setAmount(userFrom.getAmount() - amount);
            userTo.setAmount(userTo.getAmount() + amount);
            return true;
        }
        return false;
    }
}
