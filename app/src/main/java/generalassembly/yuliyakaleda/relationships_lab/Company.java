package generalassembly.yuliyakaleda.relationships_lab;

/**
 * Simple model to represent a Commpany.
 */
public class Company {
    private String name;
    private String city;
    private String state;

    public Company(String name, String city, String state) {
        this.name = name;
        this.city = city;
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }
}
