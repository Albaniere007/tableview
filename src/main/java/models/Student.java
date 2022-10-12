package models;

import java.sql.Date;

public class Student {
    int id;
    String name;
    Date birth;
    String adress, email;
    public Student(int id, String name, Date birth, String adress, String email) {
        this.id = id;
        this.name = name;
        this.birth = birth;
        this.adress = adress;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;


    }
}
