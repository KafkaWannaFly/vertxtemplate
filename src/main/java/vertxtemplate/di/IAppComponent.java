package vertxtemplate.di;

import dagger.Component;
import vertxtemplate.configs.Config;
import vertxtemplate.controllers.AppControllers;
import vertxtemplate.verticles.HttpVerticle;

@Component(modules = AppModule.class)
public interface IAppComponent {
    Config config();

    AppControllers appControllers();

    HttpVerticle httpVerticle();
}
