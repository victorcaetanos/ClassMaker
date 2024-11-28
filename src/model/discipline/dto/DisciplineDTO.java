package model.discipline.dto;

import java.util.Objects;

public class DisciplineDTO {
    private String id;
    private String name;
    private String code;
    private String description;
    private String periodo;

    public DisciplineDTO() {
    }

    public DisciplineDTO(String id, String name, String code, String description, String  periodo) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.periodo = periodo;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
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

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DisciplineDTO other = (DisciplineDTO) obj;
        return Objects.equals(this.getId(), other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
