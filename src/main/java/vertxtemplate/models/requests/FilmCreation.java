package vertxtemplate.models.requests;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.json.annotations.JsonGen;
import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.templates.annotations.ParametersMapped;
import io.vertx.sqlclient.templates.annotations.RowMapped;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

@DataObject
@JsonGen
@ParametersMapped
@RowMapped
@Data
public class FilmCreation {
    @NotBlank
    @Length(max = 255) private String title;

    private String description;

    @PositiveOrZero
    private Integer releaseYear;

    @PositiveOrZero
    private Integer length;

    @DecimalMin("1.0")
    @DecimalMax("5.0")
    private Float rating;

    public static FilmCreation fromJson(@NonNull Object json) {
        var item = new FilmCreation();
        FilmCreationConverter.fromJson(JsonObject.mapFrom(json), item);
        return item;
    }
}
