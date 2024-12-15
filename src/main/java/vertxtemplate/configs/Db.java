package vertxtemplate.configs;

import lombok.NonNull;

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
