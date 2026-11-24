package net.richenterprises.portfolio.project;

/** Thrown when no published project has the requested slug. */
class ProjectNotFoundException extends RuntimeException {
    
    ProjectNotFoundException(String slug) {
        super("No published project with slug: " + slug);
    }
}
