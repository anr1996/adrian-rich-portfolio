package net.richenterprises.portfolio.project;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
* Public, read-only endpoints for projects. No login required.
*/

@RestController
@RequestMapping("/api/projects")
class ProjectController {

    private final ProjectService service;
    
    ProjectController(ProjectService service) {
        this.service = service;
    }

    /** GET /api/projects, or GET /api/projects?featured=true for the Home page. */
    @GetMapping
    List<ProjectResponse> list(@RequestParam(defaultValue = "false") boolean featured){
        return service.findPublished(featured);
    }

    /** GET /api/projects/{slug}, for example /api/projects/rich-social-cpp. */
    @GetMapping("/{slug}")
    ProjectResponse get(@PathVariable String slug) {
        return service.findPublishedBySlug(slug);
    }

    /** Turns "no such project" into HTTP 404 with a Problem Details body. */
    @ExceptionHandler(ProjectNotFoundException.class)
    ProblemDetail handleNotFound(ProjectNotFoundException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }
}



