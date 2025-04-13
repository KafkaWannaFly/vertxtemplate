package vertxtemplate.models.responses;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.sqlclient.templates.annotations.RowMapped;
import lombok.Data;

@Data
@DataObject
@RowMapped
public class Film {
    private Integer id;
    private String title;
    private String description;
    private Integer releaseYear;
    private Integer length;
    private Float rating;
}
