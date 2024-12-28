package vertxtemplate.controllers;

import jakarta.inject.Inject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = @Inject)
@Getter
public class AppControllers {
    private final FilmController filmController;
}
