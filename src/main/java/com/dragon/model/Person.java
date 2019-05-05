package com.dragon.model;

import java.util.Date;

/**
 * @author wanglei
 * @since 1.0.0
 */
public class Person {
    private Integer age;
    private String name;
    private Long days;
    private Double income;
    private Date birthDay;
    private Float sonar;

    public Person(){

    }
    public Person(Integer age, String name, Long days, Double income, Date birthDay, Float sonar) {
        this.age = age;
        this.name = name;
        this.days = days;
        this.income = income;
        this.birthDay = birthDay;
        this.sonar = sonar;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDays() {
        return days;
    }

    public void setDays(Long days) {
        this.days = days;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public Float getSonar() {
        return sonar;
    }

    public void setSonar(Float sonar) {
        this.sonar = sonar;
    }
}
