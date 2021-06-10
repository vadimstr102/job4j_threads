package ru.job4j.cache;

import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (key, value) -> {
            if (value.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            Base updatedModel = new Base(key, value.getVersion() + 1);
            updatedModel.setName(model.getName());
            return updatedModel;
        }) != null;
    }

    public boolean delete(Base model) {
        return memory.remove(model.getId()) != null;
    }

    public Base get(int key) {
        return memory.get(key);
    }

    public int size() {
        return memory.size();
    }
}
