package vertxtemplate.di;

import dagger.Component;
import vertxtemplate.configs.Config;
import vertxtemplate.controllers.FilmController;

@Component(modules = AppModule.class)
public interface IAppComponent {
    Config config();
    FilmController filmController();
}
