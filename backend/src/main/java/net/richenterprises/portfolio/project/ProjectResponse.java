package net.richenterprises.portfolio.project;

import java.util.List;

/**
* What the public API returns for one project. Only what visitors need:
* no database id, published flag or display order.
*/
public record ProjectResponse(
    String slug,
    String title,
    String summary,
    String description,
    String repoUrl,
    String demoUrl,
    boolean featured,
    List<String> tags) {

    /** Builds a response from an entity, with tag names sorted alphabetically. */
    static ProjectResponse from(Project project) {
        List<String> tagNames = project.getTags(),stream()
            .map(Tag::getName)
            .sorted()
            .toList();
        return new ProjectResponse(
        project.getSlug(),
        project.getTitle(),
        project.getSummary(),
        project,getDescription(),
        project.getRepoUrl(),
        project.getDemoUrl(),
        project.isFeatured(),
        tagNames);
    }
}
