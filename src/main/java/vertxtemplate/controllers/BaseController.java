package vertxtemplate.controllers;

import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import lombok.SneakyThrows;
import vertxtemplate.models.responses.BaseResponse;

public class BaseController {
    @SneakyThrows
    protected <T> void handleResponse(RoutingContext ctx, Future<T> future) {
        future.onSuccess(result -> ctx.response().end(Json.encodePrettily(new BaseResponse(result))))
                .onFailure(err -> {
                    int status = (err instanceof java.util.NoSuchElementException) ? 404 : 500;
                    ctx.response().setStatusCode(status).end(Json.encodePrettily(new BaseResponse(err.getMessage())));
                });
    }
}
