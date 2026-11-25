package net.richenterprises.portfolio.content;

import java.time.LocalDate;
import java.util.List;

/** A job. endDate is null for a current role: bullets are the description lines. */
public record ExperienceResponse(
    String title,
    String organization,
    String location,
    LocalDate startDate,
    LocalDate endDate,
    List<String> bullets) {
}
