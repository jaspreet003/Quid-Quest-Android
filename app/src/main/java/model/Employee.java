package model;

import java.io.Serializable;

public class Employee implements Serializable {

    String firstName, lastName, email;
    int accNum, transNum, insNum;

    public Employee(String firstName, String lastName, String email, int accNum, int transNum, int insNum) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.accNum = accNum;
        this.transNum = transNum;
        this.insNum = insNum;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAccNum() {
        return accNum;
    }

    public void setAccNum(int accNum) {
        this.accNum = accNum;
    }

    public int getTransNum() {
        return transNum;
    }

    public void setTransNum(int transNum) {
        this.transNum = transNum;
    }

    public int getInsNum() {
        return insNum;
    }

    public void setInsNum(int insNum) {
        this.insNum = insNum;
    }
}
