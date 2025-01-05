package vertxtemplate.configs;

import io.smallrye.config.ConfigMapping;
import lombok.NonNull;

@ConfigMapping(prefix = "app")
public interface Config {
    @NonNull
    Http http();

    @NonNull
    Db db();

    @ConfigMapping(prefix = "http")
    interface Http {
        @NonNull
        Integer port();
    }

    @ConfigMapping(prefix = "db")
    interface Db {
        @NonNull
        String host();

        int port();

        @NonNull
        String user();

        @NonNull
        String password();

        @NonNull
        String name();

        default String url() {
            return String.format("jdbc:postgresql://%s:%s/%s", host(), port(), name());
        }
    }
}
