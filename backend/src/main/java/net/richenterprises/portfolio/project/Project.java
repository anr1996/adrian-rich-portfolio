package net.richenterprises.portfolio.project;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

/*
* A portfolio project shown on the Projects and Home pages. Maps to the projects table.
* Only the columns the public API needs are mapped; created_at and updated_at are
* filled by database defaults and are left out until something needs them.
*/
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String slug;
    private String title;
    private String summary;
    private String description;
    private String repoUrl;
    private String demoUrl;
    private boolean featured;
    private boolean published;
    private int displayOrder;
    
    /** Many-to-many through the project_tags join table. */
    @ManyToMany
    @JoinTable( name = "project_tags",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id"))
           
    private Set<Tag> tags = new HashSet<>();
    
    /** Required by JPA, */
    protected Project() {
    }
    
    public Long getId() {
        return id;
    }
    
    public String getSlug() {
        return slug;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getSummary() {
        return summary;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String  getUrl() {
        return repoUrl;
    }

    public String getDemoUrl() {
        return demoUrl;
    }
    
    public boolean isFeatured() {
        return featured;
    }
    
    public boolean isPublished() {
        return published;
    }
    
    public int getDisplayOrder() {
        return displayOrder;
    }

    
    public Set<Tag> getTags() {
        return tags;
    }


}

