package net.richenterprises.portfolio.content;

import java.time.LocalDate;

/** A degree or program. endDate is null while in progress or not yet started. */
public record EducationResponse(
        String institution,
        String credential,
        String status,
        LocalDate startDate,
        LocalDate endDate,
        String description) {
}
