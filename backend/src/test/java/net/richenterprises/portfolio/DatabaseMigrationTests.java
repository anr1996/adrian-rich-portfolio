package net.richenterprises.portfolio;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;

/*
* Checks that Flyway's V1 and V2 migrations produce the expected
* schema rules and seed data, using a real PostgreSQL 18 container.
*/
@Import(TestcontainersConfiguration.class)
@SpringBootTest
class DatabaseMigrationTests {
    @Autowired
    private JdbcTemplate jdbc;
    
    @Test
    void seedDataContainsExactlyOneProfile() {
        Integer count = jdbc.queryForObject("SELECT count(*) FROM profile", Integer.class);
        assertThat(count).isEqualTo(1);
    }

    @Test
    void onlyPublishingProjectsAreListedInDisplayOrder() {
        List<String> slugs = jdbc.queryForList(
            "SELECT slug FROM projects WHERE published ORDER BY display_order",
            String.class);
        assertThat(slugs).containsExactly("rich-social-cpp", "xgboost-regression");
    }

    @Test
    void secondProfileRowIsRejected() {
        assertThatThrownBy(() -> jdbc.update(

        "INSERT INTO profile (id, full_name, headline, bio) VALUES (2, 'x', 'x', 'x')"))
        .isInstanceOf(DataIntegrityViolationException.class);
    }
}
