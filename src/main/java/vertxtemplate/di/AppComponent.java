package vertxtemplate.di;

import dagger.Component;
import vertxtemplate.controllers.FilmController;

@Component(modules = AppModule.class)
public interface AppComponent {
    FilmController filmController();
}
