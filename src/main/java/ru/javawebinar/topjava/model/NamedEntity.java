package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.NotBlank;
import ru.javawebinar.topjava.View;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.groups.Default;


@MappedSuperclass
public class NamedEntity extends BaseEntity {

    @NotBlank(groups = {View.ValidatedREST.class, Default.class})
    @Column(name = "name", nullable = false)
    protected String name;

    public NamedEntity() {
    }

    protected NamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return String.format("Entity %s (%s, '%s')", getClass().getName(), getId(), name);
    }
}