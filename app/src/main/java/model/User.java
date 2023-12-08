package model;

import androidx.annotation.NonNull;

public class User {

    private String fullName, email, department;

    private int accountNumber, instituteNumber, totalExpenses, transitNumber;

    private Expense expense;


    public User(String fullName, String email, String department, int accountNumber, int instituteNumber, int totalExpenses, int transitNumber, Expense expense) {
        this.fullName = fullName;
        this.email = email;
        this.department = department;
        this.accountNumber = accountNumber;
        this.instituteNumber = instituteNumber;
        this.totalExpenses = totalExpenses;
        this.transitNumber = transitNumber;
        this.expense = expense;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
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


    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
