package vertxtemplate.models.responses;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.json.annotations.JsonGen;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@DataObject
@JsonGen
public class BaseResponse {
    private Object data;
}
