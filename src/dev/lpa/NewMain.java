package dev.lpa;

import java.util.Arrays;

class Parent {
    static String staticField = "Parent's static field";
    String instanceField = "Parent's instance field";
}

class Child extends Parent {
    static String staticField = "Child's static field";
    String instanceField = "Child's instance field";
}

public class NewMain {
    public static void main(String[] args) {
        Parent parent = new Parent();
        Child child = new Child();
        Parent parentAsChild = new Child();

        System.out.println("Static fields:");
        System.out.println(Parent.staticField); // Access Parent's static field
        System.out.println(Child.staticField); // Access Child's static field
        System.out.println(parentAsChild.staticField); // Access Parent's static field (resolved at compile-time)

        System.out.println("\nInstance fields:");
        System.out.println(parent.instanceField); // Access Parent's instance field
        System.out.println(child.instanceField); // Access Child's instance field
        System.out.println(parentAsChild.instanceField);
    }


}
