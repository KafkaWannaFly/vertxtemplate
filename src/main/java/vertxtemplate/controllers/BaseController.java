package vertxtemplate.controllers;

import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import jakarta.validation.ValidationException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import vertxtemplate.models.responses.BaseResponse;
import vertxtemplate.utils.ValidatorProvider;

@Slf4j
public class BaseController {
    @SneakyThrows
    protected <T> void handleResponse(RoutingContext ctx, Future<T> future) {
        future.onSuccess(result -> ctx.response().end(Json.encodePrettily(BaseResponse.data(result))))
                .onFailure(ctx::fail);
    }

    protected <T> T extractAndValidateBody(RoutingContext ctx, Class<T> clazz) {
        var body = ctx.body().asJsonObject().mapTo(clazz);
        var validator = ValidatorProvider.getValidator();
        var violations = validator.validate(body);
        if (!violations.isEmpty()) {
            var errorMessages = violations.stream()
                    .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                    .toList();
            throw new ValidationException(String.join(", ", errorMessages));
        }

        return body;
    }
}
