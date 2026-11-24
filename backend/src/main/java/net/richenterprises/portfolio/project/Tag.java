package net.richenterprises.portfolio.project;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/*
* A technology label such as "Java" or "PostgreSQL". Maps to the tags table.
*/
@Entity
@Table(name = "Tags")
public class Tag {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String name;

    /** Required by JPA: Hibernate creates an empty object, then fills its fields from a row */
    protected Tag() {
    }

    public Long getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

}

