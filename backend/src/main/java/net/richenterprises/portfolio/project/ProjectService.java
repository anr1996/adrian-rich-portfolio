package net.richenterprises.portfolio.project;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* Business rules for projects: the public site only ever sees published projects.
*/

@Service
@Transactional(readOnly = true)
class ProjectService {
    private final ProjectRepository projects;

    ProjectService(ProjectRepository projects) {
        this.projects = projects;
    } 

    /** Published projects in display order; only featured ones when featuredOnly is True. */
    List<ProjectResponse> findPublished(boolean featuredOnly) {
        List<Project> found = featuredOnly
        ? projects.findByPublishedTrueAndFeaturedTrueOrderByDisplayOrderAsc()
        : projects.findByPublishedTrueOrderByDisplayOrderAsc();
    return found.stream().map(ProjectResponse::from).toList();
    }

    /** One published project, or ProjectNotFoundException if there is none. */
    ProjectResponse findPublishedBySlug(String slug) {
        return projects.findBySlugAndPublishedTrue(slug)
            .map(ProjectResponse::from)
            .orElseThrow(() -> new ProjectNotFoundException(slug));
    }
}
