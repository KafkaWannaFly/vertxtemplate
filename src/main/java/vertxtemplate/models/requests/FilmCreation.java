package vertxtemplate.models.requests;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.sqlclient.templates.annotations.ParametersMapped;
import io.vertx.sqlclient.templates.annotations.RowMapped;
import lombok.Data;

@DataObject
@ParametersMapped
@RowMapped
@Data
public class FilmCreation {
    private String title;
    private String description;
    private Integer releaseYear;
    private Integer length;
    private Float rating;
}
