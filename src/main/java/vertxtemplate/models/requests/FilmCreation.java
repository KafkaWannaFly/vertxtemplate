package vertxtemplate.models.requests;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.format.SnakeCase;
import io.vertx.sqlclient.templates.annotations.ParametersMapped;
import lombok.Data;

@Data
@DataObject
@ParametersMapped(formatter = SnakeCase.class)
public class FilmCreation {
    private String title;
    private String description;
    private Integer releaseYear;
    private Integer length;
    private Float rating;
}
