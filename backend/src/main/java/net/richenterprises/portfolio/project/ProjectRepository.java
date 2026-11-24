package net.richenterprises.portfolio.project;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

/*
* Database access for projects. Spring Data writes the implementation at runtime,
* deriving each query from the method name.
*/
interface ProjectRepository extends JpaRepository<Project, Long> {
    
    /* All published projects in display order, with their tags loaded in the same query. */
    @EntityGraph(attributePaths = "tags")
    List<Project> findByPublishedTrueOrderByDisplayOrderAsc();

    /** Published projects marked as featured, for the Home page. */
    @EntityGraph(attributePaths = "tags")
    List<Project> findByPublishedTrueAndFeaturedTrueOrderByDisplayOrderAsc();   


     /** One published project by its URL slug, if it exists. */
    @EntityGraph(attributePaths = "tags")
    Optional<Project> findBySlugAndPublishedTrue(String slug);
}
