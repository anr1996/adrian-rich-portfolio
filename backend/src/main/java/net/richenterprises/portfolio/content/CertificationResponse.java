package net.richenterprises.portfolio.content;

import java.time.LocalDate;

/** A certification or micro-credential. expiresOn is null when it never expires. */
public record CertificationResponse(
        String name,
        String issuer,
        LocalDate issuedOn,
        LocalDate expiresOn) {
}
