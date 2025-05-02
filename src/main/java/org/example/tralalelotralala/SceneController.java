package org.example.tralalelotralala;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import java.util.List;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class SceneController {

    // Company Properties
    @FXML
    private TextField nameField;

    @FXML
    private TextField locationField;

    @FXML
    private TextField typeField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private HBox companyButtonContainer;

    @FXML private PieChart pieChart;

    @FXML
    public static int destinationId; // <- where you store clicked company's ID

    @FXML
    public static int employeEditId;

    @FXML
    public static int balance;
    @FXML
    public static int profit;


    @FXML
    public Label totalspend;

    @FXML
    public Label MostProdProftiLabel;
    @FXML
    public Label companyName;

    // Employee Properites

    @FXML
    private TextField employeeNameField;
    @FXML
    private TextField employeeSurnameField;

    @FXML
    private TextField employeeSalaryField;

    @FXML
    private TextField employeeSexField;
    @FXML
    private TextField employeeJobTypeField;
    @FXML
    private TextField employeeRoleField;

    @FXML
    private HBox employeesButtonContainer;

    // Prouct Properties

    @FXML
    private TextField productNameField;
    @FXML
    private TextField productPriceField;
    @FXML
    private TextField productDescriptionField;
    @FXML
    private TextField productCostField;
    @FXML
    private CheckBox  productIsDigitalField;

    @FXML
    private HBox productsButtonContainer;
    // Expenses Properties

    @FXML
    private TextField expenseNameField;
    @FXML
    private TextField expenseDescriptionField;

    @FXML
    private TextField expenseAmountField;

    @FXML
    private HBox expensesButtonContainer;


    private static int cost;

    private static List<Company> allCompanies = new ArrayList<>();
    @FXML
    public void initialize() {
        getTotalSpendings();
        if(companyButtonContainer != null)
            loadCompanyButtons();

        if(employeesButtonContainer != null)
            loadEmployeesButtons();

        if(productsButtonContainer != null)
            loadProductsButtons();

        if(pieChart != null)
            GetPieChart();

        if(findCompanyById(destinationId) != null && MostProdProftiLabel!= null)
            getMostPofitableProductName();
    }

    public void openCompanyCreationScene(ActionEvent event) {
        MainApp.switchScene("scene2.fxml");
    }
    public void onCreateCompany(ActionEvent event) {
        String name = nameField.getText();
        String location = locationField.getText();
        String type = typeField.getText();
        String description = descriptionArea.getText();

        int _id = (int) CompanySaver.loadCompanies().stream().count();
        System.out.println("Id is " + _id);

        Company company = new Company(_id,name, location, type, description);
        CompanySaver.saveCompany(company);

        System.out.println("Company created and saved successfully: " + company.getName());

        MainApp.switchScene("scene1.fxml");
    }
    public void loadCompanyButtons() {
        // Load the list of companies from the file
        List<Company> companies = CompanySaver.loadCompanies();

        // Clear any previous buttons in the container
        companyButtonContainer.getChildren().clear();

        // Ensure it's laid out horizontally (in case it's not already an HBox)
        if (companyButtonContainer instanceof HBox hbox) {
            hbox.setSpacing(10); // Add space between buttons
            hbox.setAlignment(Pos.CENTER); // Center alignment
        }

        // Generate a button for each company
        for (Company company : companies) {
            Button button = new Button(company.getName()); // Set company name

            // Assign the company ID as user data
            button.setUserData(company.getId());

            // Style the button directly (inline)
            button.setStyle(
                    "-fx-background-color: #2c2c2c;" +     // Dark gray background
                            "-fx-text-fill: white;" +              // White text
                            "-fx-background-radius: 12;" +         // Rounded corners
                            "-fx-padding: 10 20 10 20;" +          // Padding inside button
                            "-fx-font-size: 14px;"                 // Font size
            );

            // Set button action
            button.setOnAction(this::handleCompanyButtonClick);

            // Add to container
            companyButtonContainer.getChildren().add(button);
        }
    }
    public void handleCompanyButtonClick(ActionEvent event) {
        // Get the clicked button
        Button clickedButton = (Button) event.getSource();

        // Get the company ID from the button's user data
        destinationId = (int) clickedButton.getUserData();
       // getTotalSpendings();
        // Print the destination ID
        System.out.println("Destination ID: " + destinationId);
        getTotalSpendings();
        MainApp.switchScene("companypage.fxml");
    }
    public void BackToMainMeny(ActionEvent event) {
        MainApp.switchScene("scene1.fxml");
    }
    public void openEmloyeCreationScene(ActionEvent event) {
        MainApp.switchScene("employeecreation.fxml");
    }
    public void BackToCompanyScene(ActionEvent event) {
        MainApp.switchScene("companypage.fxml");
    }
    public void openEmployeeCreationFiled(ActionEvent event) {
        MainApp.switchScene("createemployee.fxml");
        loadEmployeesButtons();
    }
    public void BackToEmployeeCreationScene(ActionEvent event) {
        MainApp.switchScene("employeecreation.fxml");
    }
    public void onCreateEmployee(ActionEvent event) {
        Company company = findCompanyById(destinationId);
        if (company != null) {
            System.out.printf("Name is " + company.getName() );

            try {
                String name = employeeNameField.getText();
                String surname = employeeSurnameField.getText();
                double salary = Double.parseDouble(employeeSalaryField.getText());
                String sex = employeeSexField.getText();
                String jobtype = employeeJobTypeField.getText();
                String role = employeeRoleField.getText();
                // new id
                int id = (int) company.getEmployees().stream().count();
                System.out.printf("Employees " + company.getEmployees().stream().count());

              Employee employee = new Employee(id,name, surname, salary, sex, jobtype, role);
                company.addEmployee(employee);   // <--- Add employee to correct company

                CompanySaver.saveCompanies(allCompanies);  // <--- Save all companies again (UPDATE the file!)

                System.out.println("Employee added and companies updated!");

            } catch (NumberFormatException e) {
                System.out.println("Invalid salary input! Please enter a valid number.");
            }
        }
    }
    private Company findCompanyById(int destinationId) {
        allCompanies = CompanySaver.loadCompanies();
        System.out.println(allCompanies.stream().count());
        for (Company company : allCompanies) {
            if (company.getId() == destinationId) {
                return company;
            }
        }
        return null; // Not found
    }
    public void openProductsListScene() {
        MainApp.switchScene("allproducts.fxml");
    }
    public void openProductCreationScene(ActionEvent event) {
        MainApp.switchScene("productcreation.fxml");
    }
    public void onCreateProduct(ActionEvent event){
        Company company = findCompanyById(destinationId);

        if (company != null) {
            System.out.printf("Id is " + company.getId());

            try {
                String name = productNameField.getText();
                int price = Integer.parseInt(productPriceField.getText());
                String description = productDescriptionField.getText();
                int cost = Integer.parseInt(productCostField.getText());
                boolean isdigital = productIsDigitalField.isSelected();  // <-- checkbox

                // new product id based on count
                int id = (int) company.getProducts().stream().count();
                System.out.printf("Products count: " + company.getProducts().stream().count());

                Product product = new Product(id,name, price, description, cost, isdigital);
                              // <-- make sure Product class has setId()
                company.addProduct(product);         // <-- implement addProduct in Company

                CompanySaver.saveCompanies(allCompanies); // Save all companies again

                System.out.println("Product added and companies updated!");

            } catch (NumberFormatException e) {
                System.out.println("Invalid number input! Please enter valid integers.");
            }
        }
    }
    public void getTotalSpendings(){
        // loop through all employees mounthly salary
        // loop through all products

        cost = 0;
        Company company  = findCompanyById(destinationId);
        if(company != null) {

            for (Employee employee : company.getEmployees()){
                 cost += (int) employee.getSalary();
            }
            for (Product product : company.getProducts()){
                cost +=  product.getCost();
            }
            for (Expense expense : company.getExpenses()){
                cost +=  expense.getAmount();
            }
        }
        System.out.printf("Total Cost iS x " + cost);

        if(totalspend != null)
            totalspend.setText("Total Spendings are | "+ cost);
    }
    public void GetPieChart() {
        Company company = findCompanyById(destinationId);
        if (company == null) return;

        int employeeCost = 0;
        int productCost = 0;
        int expenseCost = 0;

        for (Employee employee : company.getEmployees()) {
            employeeCost += (int) employee.getSalary();
        }
        for (Product product : company.getProducts()) {
            productCost += product.getCost();
        }
        for (Expense expense : company.getExpenses()) {
            expenseCost += expense.getAmount();
        }

        int totalCost = employeeCost + productCost + expenseCost;

        if (totalspend != null)
            totalspend.setText("Total Spendings: " + totalCost);

        // Create PieChart data
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Salary", employeeCost),
                new PieChart.Data("Products", productCost),
                new PieChart.Data("Additonal Expenses", expenseCost)
        );

        // Assign to a PieChart in your FXML
        pieChart.setData(pieChartData);
        pieChart.setTitle("Spendings Breakdown");
    }
    public void removeCompany(ActionEvent event) {
        // Load all companies
        List<Company> companies = CompanySaver.loadCompanies();

        // Filter out the company with the given ID
        companies.removeIf(company -> company.getId() == destinationId);

        // Save the updated list back to the JSON file
        CompanySaver.saveCompanies(companies);

        System.out.println("Company with ID " + destinationId + " removed.");
        // go back to main page
        BackToMainMeny(event);
    }
    public void openAdditonalSpendingListScene(ActionEvent event) {
        MainApp.switchScene("allspendings.fxml");
    }
    public void openAddSpendingScene(ActionEvent event){
        MainApp.switchScene("addspending.fxml");
    }
    public void onAddExpense(ActionEvent event) {
        Company company = findCompanyById(destinationId);
        if (company != null) {
            System.out.printf("Name is " + company.getName() );

            try {
                String name = expenseNameField.getText();
                String description = expenseDescriptionField.getText();
                int amount = Integer.parseInt(expenseAmountField.getText());

                // new id
                int id = (int) company.getExpenses().stream().count();

                Expense expense = new Expense(id,name,description,amount);

                company.addExpense(expense);
                CompanySaver.saveCompanies(allCompanies);  // <--- Save all companies again (UPDATE the file!)

                System.out.println("Additonal Expense added and companies updated!");

            } catch (NumberFormatException e) {
                System.out.println("Invalid amount input! Please enter a valid number.");
            }
        }
    }
    public void loadEmployeesButtons() {
        // Load the list of companies from the file

        Company company =  findCompanyById(destinationId);
        // Clear any previous buttons in the container
        employeesButtonContainer.getChildren().clear();

        // Ensure it's laid out horizontally (in case it's not already an HBox)
        if (employeesButtonContainer instanceof HBox hbox) {
            hbox.setSpacing(10); // Add space between buttons
            hbox.setAlignment(Pos.CENTER); // Center alignment
        }

        // Generate a button for each company
        for (Employee employee : company.getEmployees()) {
            Button button = new Button(employee.getName()); // Set company name

            // Assign the company ID as user data
            button.setUserData(employee.getId());

            // Style the button directly (inline)
            button.setStyle(
                    "-fx-background-color: #2c2c2c;" +     // Dark gray background
                            "-fx-text-fill: white;" +              // White text
                            "-fx-background-radius: 12;" +         // Rounded corners
                            "-fx-padding: 10 20 10 20;" +          // Padding inside button
                            "-fx-font-size: 14px;"                 // Font size
            );

            // Set button action
            button.setOnAction(this::handleEmployeesButtonClick);

            // Add to container
            employeesButtonContainer.getChildren().add(button);
        }
    }
    public void handleEmployeesButtonClick (ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        employeEditId = (int) clickedButton.getUserData();
        System.out.printf("Pressed employee with id " + clickedButton.getUserData());
        openEditEmployeesScene();
    }
    public void loadProductsButtons() {
        // Load the list of companies from the file

        Company company =  findCompanyById(destinationId);
        // Clear any previous buttons in the container
        productsButtonContainer.getChildren().clear();

        // Ensure it's laid out horizontally (in case it's not already an HBox)
        if (productsButtonContainer instanceof HBox hbox) {
            hbox.setSpacing(10); // Add space between buttons
            hbox.setAlignment(Pos.CENTER); // Center alignment
        }

        // Generate a button for each company
        for (Product product : company.getProducts()) {
            Button button = new Button(product.getName()); // Set company name

            // Assign the company ID as user data
            button.setUserData(product.getId());

            // Style the button directly (inline)
            button.setStyle(
                    "-fx-background-color: #2c2c2c;" +     // Dark gray background
                            "-fx-text-fill: white;" +              // White text
                            "-fx-background-radius: 12;" +         // Rounded corners
                            "-fx-padding: 10 20 10 20;" +          // Padding inside button
                            "-fx-font-size: 14px;"                 // Font size
            );

            // Set button action
            button.setOnAction(this::handleProductssButtonClick);

            // Add to container
            productsButtonContainer.getChildren().add(button);
        }
    }
    public void handleProductssButtonClick (ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        System.out.printf("Pressed product with id " + clickedButton.getUserData());
    }
    public void getMostPofitableProductName(){
        Company company =  findCompanyById(destinationId);

        if(company!= null){
            List<Product> products = company.getProducts();
            if(products.stream().count() >0){
            Product maxProduct =  products.getFirst();
            for (Product product : products) {

                if((product.getPrice()-product.getCost() ) > ( maxProduct.getPrice()-maxProduct.getCost())){
                    maxProduct = product;
                }
            }
                MostProdProftiLabel.setText(" Most Profitable Product - "+maxProduct.getName());
                return;
            }
        }
        else
        {
            MostProdProftiLabel.setText(" Most Profitable Product - NAN ");
        }
    }
    public void openEditEmployeesScene() {
        MainApp.switchScene("editemployee.fxml");

       /* Company company = findCompanyById(destinationId);
        if (company != null)
        {
            System.out.printf("Name is " + company.getName() );

            try {
                for(Employee employee : company.getEmployees()){
                    if(employee.getId() == employeEditId){
                        employeeNameField.setText(employee.getName());
                        employeeSurnameField.setText(employee.getSurname());
                        employeeSalaryField.setText(String.valueOf(employee.getSalary()));
                        employeeSexField.setText(employee.getSex());
                        employeeJobTypeField.setText(employee.getJobType());
                        employeeRoleField.setText(employee.getRole());
                    }
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid salary input! Please enter a valid number.");
            }
        }*/

    }
    public void onChangeEmployee(ActionEvent event) {
        Company company = findCompanyById(destinationId);
        if (company != null) {
            System.out.printf("Name is " + company.getName() );

            try {
                String name = employeeNameField.getText();
                String surname = employeeSurnameField.getText();
                double salary = Double.parseDouble(employeeSalaryField.getText());
                String sex = employeeSexField.getText();
                String jobtype = employeeJobTypeField.getText();
                String role = employeeRoleField.getText();
                // new id

                for(Employee employee : company.getEmployees()){
                    if(employee.getId() == employeEditId){
                        employee.setName(name);
                        employee.setSurname(surname);
                        employee.setSalary(salary);
                        employee.setSex(sex);
                        employee.setJobType(jobtype);
                        employee.setRole(role);
                    }
                }

                  // <--- Add employee to correct company

                CompanySaver.saveCompanies(allCompanies);  // <--- Save all companies again (UPDATE the file!)

                System.out.println("Edited Company");

            } catch (NumberFormatException e) {
                System.out.println("Invalid salary input! Please enter a valid number.");
            }
        }
    }
    public void removeEmployee(ActionEvent event) {
        Company company = findCompanyById(destinationId);
        if (company != null) {
            System.out.printf("Name is " + company.getName() );
            List<Employee> employees = company.getEmployees();
            employees.removeIf(emp -> emp.getId() == employeEditId);

            // Re-save the updated list of companies
            List<Company> allCompanies = CompanySaver.loadCompanies();
            for (Company c : allCompanies) {
                if (c.getId() == company.getId()) {
                    c.setEmployees(employees);
                    break;
                }
            }
            CompanySaver.saveCompanies(allCompanies);

            System.out.println("Employee with ID " + employeEditId + " removed successfully.");


            // go back
            MainApp.switchScene("employeecreation.fxml");
        }
    }
}
