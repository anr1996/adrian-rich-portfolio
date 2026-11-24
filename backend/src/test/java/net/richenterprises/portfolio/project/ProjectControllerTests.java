package net.richenterprises.portfolio.project;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import net.richenterprises.portfolio.TestcontainersConfiguration;

/**
* Tests the public projects endpoints through Spring MVC, against the seed data
* in a real PostgreSQL 18 container.
*/
@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTests {
    @Autowired
    private MockMvcTester mvc;

    @Test
    void listReturnOnlyPublishedProjectsInDisplayOrder() {
        assertThat(mvc.get().uri("/api/projects"))
        .hasStatusOk()
        .bodyJson()
        .extractingPath("$[*].slug")
        .asArray()
        .containsExactly("rich-social-cpp", "xgboost-regression");  
    }

    @Test
    void featuredFilterReturnsOnlyFeaturedPublishedProjects() {
        assertThat(mvc.get().uri("/api/projects").param("featured", "true"))
            .hasStatusOk()
            .bodyJson()
            .extractingPath("$[*].slug")
            .asArray()
            .containsExactly("rich-social-cpp");
    }
    
    @Test
    void projectBySlugIncludesTagsInAlphabeticalOrder() {
        assertThat(mvc.get().uri("/api/projects/rich-social-cpp"))
            .hasStatusOk()
            .bodyJson()
            .extractingPath("$.tags")
            .asArray()
            .containsExactly("C++", "Docker", "PostgreSQL", "wxWidgets");
    }

    @Test
    void unpublishedProjectReturns404() {
        assertThat(mvc.get().uri("/api/projects/adrian-rich-portfolio"))
            .hasStatus(HttpStatus.NOT_FOUND);
    }

}
