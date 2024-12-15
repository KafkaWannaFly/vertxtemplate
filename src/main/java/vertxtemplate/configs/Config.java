package vertxtemplate.configs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.NonNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Config(@NonNull Http http, @NonNull Db db) {
}
