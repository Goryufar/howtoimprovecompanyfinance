package org.example.tralalelotralala;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.ArrayList;

public class Company {

    private static int nextId = 1;
    private int id = 0;
    private String name;
    private String location;
    private String type;
    private String description;
    private List<Employee> employees;
    private List<Product> products;
    private List<Expense> expenses;

    public Company(String name, String location, String type, List<Employee> employees,List<Product> products,List<Expense> expenses) {
        this.id = nextId++;
        this.name = name;
        this.location = location;
        this.type = type;
        this.employees = employees != null ? employees : new ArrayList<>();
        this.products = products != null ? products : new ArrayList<>();
        this.expenses = expenses != null ? expenses : new ArrayList<>();
    }

    public Company(int id,String name, String location, String type, String description) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.type = type;
        this.description = description;
        this.employees = new ArrayList<>();  // <-- initialize
        this.products = new ArrayList<>();  // <-- initialize
        this.expenses = new ArrayList<>();  // <-- initialize
    }
    // Getters and Setters
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Employee> getEmployees() {
        return employees;
    }
    public List<Product> getProducts() {
        return products;
    }
    public List<Expense> getExpenses() {
        return expenses;
    }
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }
    public void addProduct(Product product) {
        products.add(product);
    }
    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public int getEmployeesCount() {
         return (int) employees.stream().count();
    }
    public int getProdcutsCount() {
        return (int) products.stream().count();
    }
    public int getExpensesCount() {
        int amount = 0;
        for(int i=0; i < (int) expenses.stream().count();i++)
            amount+=expenses.get(i).getAmount();

        return amount;
    }
    public int  getProductExpenses() {
        int amount = 0;
        for(int i=0; i < (int) products.stream().count();i++)
            amount+=products.get(i).getCost();

        return amount;
    }
    public int  getEmployeeExpenses() {
        int amount = 0;
        for(int i=0; i < (int) employees.stream().count();i++)
            amount+=employees.get(i).getSalary();

        return amount;
    }

    public int getTotalExpenses(){
        int amount = 0;
        amount += getExpensesCount();
        amount += getProductExpenses();
        amount += getEmployeeExpenses();
        return amount;
    }

    // Method to convert Company object to JSON
    public JSONObject toJSON() {
        JSONObject companyJson = new JSONObject();
        companyJson.put("id", this.id);
        companyJson.put("name", this.name);
        companyJson.put("location", this.location);
        companyJson.put("type", this.type);
        companyJson.put("description", this.description);

        // Adding employees array
        JSONArray employeesArray = new JSONArray();
        for (Employee employee : employees) {
            JSONObject empJson = new JSONObject();
            empJson.put("id", employee.getId());
            empJson.put("name", employee.getName());
            empJson.put("surname", employee.getSurname());
            empJson.put("salary", employee.getSalary());
            empJson.put("sex", employee.getSex());
            empJson.put("jobType", employee.getJobType());
            empJson.put("role", employee.getRole());
            employeesArray.put(empJson);
        }
        companyJson.put("employees", employeesArray);

        // Adding productsArray
        JSONArray productsArray = new JSONArray();
        for (Product product : products) {
            JSONObject productJson = new JSONObject();
            productJson.put("id",product.getId());
            productJson.put("name",product.getName());
            productJson.put("price",product.getPrice());
            productJson.put("description",product.getDescription());
            productJson.put("cost",product.getCost());
            productJson.put("isdigital",product.getWetherDigital());
            productsArray.put(productJson);
        }
        companyJson.put("products", productsArray);

        JSONArray expenseArray = new JSONArray();
        for (Expense expense : expenses) {
            JSONObject expenseJson = new JSONObject();
            expenseJson.put("id",expense.getId());
            expenseJson.put("name",expense.getName());
            expenseJson.put("description",expense.getDescription());
            expenseJson.put("amount",expense.getAmount());

            expenseArray.put(expenseJson);
        }
        companyJson.put("expenses", expenseArray);

        return companyJson;
    }
}
