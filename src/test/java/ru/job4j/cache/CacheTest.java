package ru.job4j.cache;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CacheTest {
    Cache cache = new Cache();
    Base model1 = new Base(1, 1);
    Base model2 = new Base(2, 1);
    Base model3 = new Base(3, 1);

    @Test
    public void whenAddModels() {
        assertTrue(cache.add(model1));
        assertTrue(cache.add(model2));
        assertTrue(cache.add(model3));
        assertThat(cache.size(), is(3));
    }

    @Test
    public void whenUpdateModel() {
        cache.add(model1);
        cache.add(model2);
        cache.add(model3);
        assertThat(cache.get(2).getVersion(), is(1));
        assertNull(cache.get(2).getName());

        Base updatedModel2 = new Base(2, 1);
        updatedModel2.setName("Model2");
        assertTrue(cache.update(updatedModel2));
        assertThat(cache.get(2).getVersion(), is(2));
        assertThat(cache.get(2).getName(), is("Model2"));
    }

    @Test
    public void whenDeleteModel() {
        cache.add(model1);
        cache.add(model2);
        cache.add(model3);
        assertThat(cache.size(), is(3));

        assertTrue(cache.delete(model1));
        assertThat(cache.size(), is(2));
    }
}
