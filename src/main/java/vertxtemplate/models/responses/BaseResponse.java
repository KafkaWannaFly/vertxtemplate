package vertxtemplate.models.responses;

import io.vertx.codegen.json.annotations.JsonGen;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonGen
@AllArgsConstructor
public class BaseResponse {
    private Object data;
}
