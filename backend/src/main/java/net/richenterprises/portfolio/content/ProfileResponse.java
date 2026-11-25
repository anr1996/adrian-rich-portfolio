package net.richenterprises.portfolio.content;

/** The Home page profile: name, headline, bio and location. */
public record ProfileResponse(String fullName, String headline, String bio, String location) {
}
