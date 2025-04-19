package vertxtemplate.controllers;

import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import lombok.SneakyThrows;
import vertxtemplate.models.responses.BaseResponse;

import java.util.Map;

public class BaseController {
    @SneakyThrows
    protected <T> void handleResponse(RoutingContext ctx, Future<T> future) {
        future.onSuccess(result -> ctx.response()
                .end(Json.encodePrettily(new BaseResponse(result)))
                .onFailure(err -> ctx.response()
                        .setStatusCode(500)
                        .end(Json.encodePrettily(Map.of("error", err.getMessage())))));
    }
}
