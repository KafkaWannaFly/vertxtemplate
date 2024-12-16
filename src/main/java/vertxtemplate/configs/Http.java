package vertxtemplate.configs;

import io.smallrye.config.ConfigMapping;
import lombok.NonNull;

@ConfigMapping(prefix = "http")
public record Http(@NonNull Integer port) {
}
