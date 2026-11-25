package net.richenterprises.portfolio.content;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import net.richenterprises.portfolio.TestcontainersConfiguration;

/** 
 * Tests the public content endpoints against the seed data in a real
 * PostgreSQL 18 container.
 */
@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
class ContentControllerTests {
    
    @Autowired
    private MockMvcTester mvc;
    
    @Test
    void profileReturnsTheSingleProfileRow() {
        assertThat(mvc.get().uri("/api/profile"))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.fullName")
                .isEqualTo("Adrian Rich");    
    }
    
    @Test
    void linksAreInDisplayOrder() {
        assertThat(mvc.get().uri("/api/links"))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$[*].label")
                .asArray()
                .containsExactly("GitHub", "LinkedIn");
    }

    @Test
    void educationDatesAreIsoStrings() {
        assertThat(mvc.get().uri("/api/education"))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$[1].startDate")
                .isEqualTo("2025-03-01");
    }
    
    @Test
    void certificationsHaveNoExpiryDate() {
        assertThat(mvc.get().uri("/api/certifications"))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$[0].expiresOn")
                .isNull();
    }
    
    @Test
    void experienceDescriptionIsSplitIntoBullets() {
        assertThat(mvc.get().uri("/api/experience"))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$[0].bullets")
                .asArray()
                .hasSize(2);
    }

    @Test
    void skillsAreGroupedByCategory() {
        assertThat(mvc.get().uri("/api/skills"))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$[*].category")
                .asArray()
                .hasSize(20)
                .startsWith("AI/ML", "AI/ML", "AI/ML", "Databases");
    }
}




