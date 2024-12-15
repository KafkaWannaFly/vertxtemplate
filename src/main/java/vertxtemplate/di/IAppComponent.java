package vertxtemplate.di;

import dagger.Component;
import vertxtemplate.configs.Config;
import vertxtemplate.controllers.AppControllers;
import vertxtemplate.repos.AppRepos;
import vertxtemplate.services.AppServices;
import vertxtemplate.verticles.HttpVerticle;

@Component(modules = AppModule.class)
public interface IAppComponent {
    Config config();

    AppControllers appControllers();

    AppServices appServices();

    AppRepos appRepos();

    HttpVerticle httpVerticle();
}
