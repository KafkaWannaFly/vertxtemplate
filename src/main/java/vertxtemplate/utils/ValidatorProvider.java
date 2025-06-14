package vertxtemplate.utils;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.Getter;

public class ValidatorProvider {
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Getter
    private static final Validator validator = factory.getValidator();
}
