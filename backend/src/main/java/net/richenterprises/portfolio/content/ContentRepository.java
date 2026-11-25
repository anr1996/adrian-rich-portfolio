package net.richenterprises.portfolio.content;

import java.time.LocalDate;

import java.util.List;


import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

/**
* Read-only queries for the simple content tables. These tables have no
* relationships and no business rules, so plain SQL mapped straight into
* response records is the simplest correct tool.
*/
@Repository
class ContentRepository {

    private final JdbcClient jdbc;

    ContentRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    /** The single profile row. The schena guarantees exactly one. */
    ProfileResponse findProfile() {
        return jdbc.sql("SELECT full_name, headline, bio, location FROM profile WHERE id = 1")
            .query(ProfileResponse.class)
            .single();
    }

    /** All links in display order. */
    List<LinkResponse> findLinks() {
        return jdbc.sql("SELECT label, url FROM links ORDER BY display_order")
            .query(LinkResponse.class)
            .list();
    }

    /** Education entries in display order. */
    List<EducationResponse> findEducation() {
        return jdbc.sql("""
                SELECT institution, credential, status, start_date, end_date, description
                FROM education
                ORDER BY display_order
                """)
                .query(EducationResponse.class)
                .list();
    }
    
    /** Certifications in display order. */
    List<CertificationResponse> findCertifications() {
        return jdbc.sql("""
                SELECT name, issuer, issued_on, expires_on
                FROM certifications
                ORDER BY display_order
                """)
                .query(CertificationResponse.class)
                .list();
    }

    /** Work experience in display order, with each description line as a bullet. */
    List<ExperienceResponse> findExperience() {
        return jdbc.sql("""
                SELECT title, organization, location, start_date, end_date, description
                FROM experience
                ORDER BY display_order
                """)
                .query((rs, rowNum) -> new ExperienceResponse(
                    rs.getString("title"),
                    rs.getString("organization"),
                    rs.getString("location"),
                    rs.getObject("start_date", LocalDate.class),
                    rs.getObject("end_date", LocalDate.class),
                    toBullets(rs.getString("description"))))
                    .list();
    }

    /** Skills grouped by category, in display order within each category. */
    List<SkillResponse> findSkills() {
        return jdbc.sql("""
                SELECT name, category
                FROM skills
                ORDER BY category, display_order
                """)
                .query(SkillResponse.class)
                .list();
    }

    /** Splits a description stored one bullet per line; no description means no bullets. */
    private static List<String> toBullets(String description) {
        return description == null ? List.of() : description.lines().toList();
    }
}


