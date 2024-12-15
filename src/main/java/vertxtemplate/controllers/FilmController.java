package vertxtemplate.controllers;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import vertxtemplate.services.AppServices;

@AllArgsConstructor
public class FilmController {
    @NonNull
    private final AppServices appServices;
}
