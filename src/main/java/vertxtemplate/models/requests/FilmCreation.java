package vertxtemplate.models.requests;

public record FilmCreation(String title, String description, Integer releaseYear, Integer length, Float rating) {
}
