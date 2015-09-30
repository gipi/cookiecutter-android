package br.com.quintoandar.template.model;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class Dummy extends RealmObject {

    @PrimaryKey
    private String name;

    private int number;

    @Ignore
    private int dontPersistMe;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
