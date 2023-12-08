package model;

import androidx.annotation.NonNull;

import com.google.firebase.database.PropertyName;

import java.io.Serializable;

public class Expense implements Serializable {
    private String Category, Description, name, Department, email, expenseID;
    private Double Amount;

    public Expense(Double amount, String description) {
        Description = description;
        Amount = amount;
    }
    public Expense(String category, String description, String name, String department, String email, String expenseID, Double amount) {
        Category = category;
        Description = description;
        this.name = name;
        Department = department;
        this.email = email;
        this.expenseID = expenseID;
        Amount = amount;
    }


    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    @PropertyName("Description")
    public String getDescription() {
        return Description;
    }

    @PropertyName("Description")
    public void setDescription(String description) {
        Description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(String expenseID) {
        this.expenseID = expenseID;
    }

    @PropertyName("Amount")
    public Double getAmount() {
        return Amount;
    }

    @PropertyName("Amount")
    public void setAmount(Double amount) {
        Amount = amount;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
