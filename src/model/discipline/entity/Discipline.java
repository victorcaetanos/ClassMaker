package model.discipline.entity;

public class Discipline {
    private int id;
    private String name;
    private String code;
    private String description;
    private boolean inactive;

    public Discipline() {
    }

    public Discipline(String name, String code, String description) {
        this.name = name;
        this.description = description;
        this.code = code;
    }

    public Discipline(int id, String name, String code, String description) {
        this(name, code, description);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(final boolean inactive) {
        this.inactive = inactive;
    }
}
