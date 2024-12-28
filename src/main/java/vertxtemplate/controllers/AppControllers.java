package vertxtemplate.controllers;

import jakarta.inject.Inject;
import lombok.AllArgsConstructor;

@AllArgsConstructor(onConstructor_ = @Inject)
public class AppControllers {
    private final FilmController filmController;
}
