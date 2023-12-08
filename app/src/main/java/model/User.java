package model;

import java.io.Serializable;

public class User implements Serializable {
    private String Name, email, department, firstName, lastName, phoneNumber, firebaseId;
    private int accountNumber, instituteNumber, totalExpenses, transitNumber;
    private Expense expense;
    public User() {
    }

    public User(String Name, String email, String department, int accountNumber, int instituteNumber,
            int totalExpenses, int transitNumber, Expense expense) {
        this.Name = Name;
        this.email = email;
        this.department = "TBD";
        this.accountNumber = accountNumber;
        this.instituteNumber = instituteNumber;
        this.totalExpenses = totalExpenses;
        this.transitNumber = transitNumber;
        this.expense = expense;
    }

    public User(String firstName, String lastName, String email, String phoneNumber, int accNum, int transNum,
            int insNum) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.accountNumber = accNum;
        this.transitNumber = transNum;
        this.instituteNumber = insNum;
        this.department = "TBD";
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accNum) {
        this.accountNumber = accNum;
    }

    public int getTransNum() {
        return transitNumber;
    }

    public void setTransNum(int transNum) {
        this.transitNumber = transNum;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getInstituteNumber() {
        return instituteNumber;
    }

    public void setInstituteNumber(int instituteNumber) {
        this.instituteNumber = instituteNumber;
    }

    public int getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(int totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public int getTransitNumber() {
        return transitNumber;
    }

    public void setTransitNumber(int transitNumber) {
        this.transitNumber = transitNumber;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

    public static String encodeEmail(String email) {
        return email.replace('.', '_').replace('@', '-');
    }

    public static String decodeEmail(String email) {
        return email.replace('_', '.').replace('-', '@');
    }
}
