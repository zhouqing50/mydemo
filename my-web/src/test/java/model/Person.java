package model;

import org.springframework.util.ObjectUtils;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Person {

    private String name;

    private double someDouble;

    private boolean someBoolean;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Person setName(String name) {
        this.name = name;
        return this;
    }

    public double getSomeDouble() {
        return someDouble;
    }

    public Person setSomeDouble(double someDouble) {
        this.someDouble = someDouble;
        return this;
    }

    public boolean isSomeBoolean() {
        return someBoolean;
    }

    public Person setSomeBoolean(boolean someBoolean) {
        this.someBoolean = someBoolean;
        return this;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Person)) {
            return false;
        }
        Person otherPerson = (Person) other;
        return (ObjectUtils.nullSafeEquals(this.name, otherPerson.name) &&
                ObjectUtils.nullSafeEquals(this.someDouble, otherPerson.someDouble) &&
                ObjectUtils.nullSafeEquals(this.someBoolean, otherPerson.someBoolean));
    }

    @Override
    public String toString() {
        return "Person [name=" + this.name + ", someDouble=" + this.someDouble
                + ", someBoolean=" + this.someBoolean + "]";
    }

}