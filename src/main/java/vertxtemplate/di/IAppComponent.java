package vertxtemplate.di;

import dagger.Component;
import vertxtemplate.configs.Config;
import vertxtemplate.controllers.AppControllers;

@Component(modules = AppModule.class)
public interface IAppComponent {
    Config config();

    AppControllers appControllers();
}
