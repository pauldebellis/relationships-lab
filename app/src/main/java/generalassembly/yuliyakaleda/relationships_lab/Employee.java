package generalassembly.yuliyakaleda.relationships_lab;

/**
 * Simple Model to represent an Employee.
 */
public class Employee {
    private String firstName;
    private String lastName;
    private int salary;
    private long companyID;

    public Employee(String firstName, String lastName, int salary, long companyID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.companyID = companyID;
    }

    public Employee(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getSalary() {
        return salary;
    }

    public long getCompanyID() {
        return companyID;
    }
}

