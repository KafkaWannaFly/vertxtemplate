package vertxtemplate.models.requests;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.json.annotations.JsonGen;
import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.templates.annotations.ParametersMapped;
import io.vertx.sqlclient.templates.annotations.RowMapped;
import lombok.Data;
import lombok.NonNull;

@DataObject
@JsonGen
@ParametersMapped
@RowMapped
@Data
public class FilmCreation {
    private String title;
    private String description;
    private Integer releaseYear;
    private Integer length;
    private Float rating;

    public static FilmCreation fromJson(@NonNull Object json) {
        var item = new FilmCreation();
        FilmCreationConverter.fromJson(JsonObject.mapFrom(json), item);
        return item;
    }
}
