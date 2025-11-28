package com.workintech.library.base;

import java.util.Objects;

public abstract class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }
    public abstract void whoyouare();

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
}
