package vertxtemplate.configs;

import io.smallrye.config.ConfigMapping;
import lombok.NonNull;

@ConfigMapping(prefix = "db")
public record Db(
        @NonNull String host,
        @NonNull String port,
        @NonNull String user,
        @NonNull String password,
        @NonNull String name) {
    public String url() {
        return String.format("jdbc:postgresql://%s:%s/%s", host, port, name);
    }
}
