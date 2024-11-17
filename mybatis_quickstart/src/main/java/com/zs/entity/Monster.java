package com.zs.entity;

import java.io.Serializable;

public class Monster implements Serializable {//二级缓存可能需要使用序列化技术
    private Integer id;
    private Integer age;
    private String name;

    public Monster(int id, int age, String name) {
        this.id = id;
        this.age = age;
        this.name = name;
    }

    public Monster() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Monster{" +
                "id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
