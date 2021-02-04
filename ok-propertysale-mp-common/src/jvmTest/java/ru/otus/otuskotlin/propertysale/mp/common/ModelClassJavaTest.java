package ru.otus.otuskotlin.propertysale.mp.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ModelClassJavaTest {

    @Test
    public void sharedModelTest() {
        SharedModel sm = new SharedModel("id-1", "name-1");
        assertEquals("id-1", sm.getId());
    }

    @Test
    public void kotlinFieldTest() {
        assertEquals("my name", new SharedModel().getMyName());
    }

    @Test
    public void jvmOverloadsTest() {
        assertEquals(new SharedModel("one", ""), new SharedModel("one"));
    }
}
