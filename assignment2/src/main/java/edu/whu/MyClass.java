package edu.whu;

public class MyClass {

    private int num;
    private String name;

    @InitMethod
    public void init() {
        System.out.println("init method was invoked successfully!");
        this.name = "jack";
    }

    public void func1() {
        System.out.println("func1 was invoked successfully!");
    }

    public void func2() {
        System.out.println("func2 was invoked successfully!");
    }

    @InitMethod
    public void func3() {
        System.out.println("func3 was invoked successfully!");
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MyClass{" +
                "num=" + num +
                ", name='" + name + '\'' +
                '}';
    }
}
