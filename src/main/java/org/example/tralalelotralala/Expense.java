package org.example.tralalelotralala;

public class Expense {
    private int id = 0;
    private String name;
    private String description;
    private int amount;
    // Date

    public Expense(int id,String name, String description, int amount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }
}
