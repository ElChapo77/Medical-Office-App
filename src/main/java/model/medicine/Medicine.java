package model.medicine;

public abstract class Medicine {

    private final String name;
    private final String description;

    public Medicine(String name) {
        this.name = name;
        this.description = "";
    }

    public Medicine(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
