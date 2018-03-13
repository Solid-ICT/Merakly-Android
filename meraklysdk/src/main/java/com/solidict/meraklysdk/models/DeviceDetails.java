package com.solidict.meraklysdk.models;

import java.io.Serializable;

/**
 * Created by muazekici on 13.03.2018.
 */

public class DeviceDetails implements Serializable {

    int age;
    int gender;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
