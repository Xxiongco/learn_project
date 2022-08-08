package com.panda.domain;

import lombok.Data;
import lombok.ToString;

import java.util.Set;

@Data
public class Student extends Person{
    private Integer id;
    private String num;

    @Override
    public String toString() {
        return "Student{ " +
                super.toString() +
                ", id=" + id +
                ", num='" + num + '\'' +
                '}';
    }
}
