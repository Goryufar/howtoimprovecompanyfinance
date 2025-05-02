package org.example.tralalelotralala;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

public class CompanySaver {

    public static void saveCompany(Company newCompany) {
        List<Company> companies = new ArrayList<>();

        // Check if the file already exists
        File companyFile = new File("companies.json");

        // If the file exists, load existing companies
        if (companyFile.exists() && companyFile.length() > 0) {
            companies = loadCompanies();
        }

        // Add the new company to the list
        companies.add(newCompany);

        // Now save all companies (including the new one) back to the file
        saveCompanies(companies);
    }
    // This method will save a list of companies to the file
    public static void saveCompanies(List<Company> companies) {
        JSONArray companiesArray = new JSONArray();

        // Convert each company to JSON and add to the array
        for (Company company : companies) {
            companiesArray.put(company.toJSON());
        }

        // Save the JSON array to a file
        try (FileWriter file = new FileWriter("companies.json")) {
            file.write(companiesArray.toString(4)); // 4 spaces indent for nice formatting
            System.out.println("Company saved successfully to companies.json");
        } catch (IOException e) {
            System.out.println("Failed to save companies: " + e.getMessage());
        }
    }
    public static List<Company> loadCompanies() {
        List<Company> companies = new ArrayList<>();

        try (FileReader reader = new FileReader("companies.json")) {
            StringBuilder sb = new StringBuilder();
            int i;
            while ((i = reader.read()) != -1) {
                sb.append((char) i);
            }

            JSONArray companiesArray = new JSONArray(sb.toString());

            // Convert each JSON object into a Company object
            for (int j = 0; j < companiesArray.length(); j++) {
                JSONObject companyJson = companiesArray.getJSONObject(j);

                int id = companyJson.getInt("id");  // <-- Get the id from JSON!!
                String name = companyJson.getString("name");
                String location = companyJson.getString("location");
                String type = companyJson.getString("type");
                String description = companyJson.optString("description", ""); // Optional description

                Company company = new Company(id, name, location, type, description); // <-- Use the real id!!


                // Add employees if any
                if (companyJson.has("employees")) {
                    JSONArray employeesArray = companyJson.getJSONArray("employees");
                    for (int k = 0; k < employeesArray.length(); k++) {
                        JSONObject empJson = employeesArray.getJSONObject(k);

                        Employee employee = new Employee(
                                empJson.getInt("id"),
                                empJson.getString("name"),
                                empJson.getString("surname"),
                                empJson.getDouble("salary"),
                                empJson.getString("sex"),
                                empJson.getString("jobType"),
                                empJson.getString("role")
                        );
                        company.addEmployee(employee); // Add employee to company
                    }
                }
                if(companyJson.has("products")){
                    JSONArray productsArray = companyJson.getJSONArray("products");
                    for (int k = 0; k < productsArray.length(); k++) {
                        JSONObject prodJson = productsArray.getJSONObject(k);

                        Product product = new Product(
                                prodJson.getInt("id"),
                                prodJson.getString("name"),
                                prodJson.getInt("price"),
                                prodJson.getString("description"),
                                prodJson.getInt("cost"),
                                prodJson.getBoolean("isdigital")
                        );
                        company.addProduct(product); // Add employee to company
                    }
                }
                if(companyJson.has("expenses")){
                    JSONArray expensesArray = companyJson.getJSONArray("expenses");
                    for (int k = 0; k < expensesArray.length(); k++) {
                        JSONObject expJson = expensesArray.getJSONObject(k);

                        Expense expense = new Expense(
                                expJson.getInt("id"),
                                expJson.getString("name"),
                                expJson.getString("description"),
                                expJson.getInt("amount")
                        );
                        company.addExpense(expense); // Add employee to company
                    }
                }

                companies.add(company); // Add company to the list
            }

        } catch (IOException e) {
            System.out.println("Failed to load companies: " + e.getMessage());
        }

        return companies;
    }

}
