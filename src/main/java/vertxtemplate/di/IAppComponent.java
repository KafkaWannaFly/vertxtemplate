package vertxtemplate.di;

import dagger.Component;
import vertxtemplate.controllers.AppControllers;
import vertxtemplate.repos.AppRepos;
import vertxtemplate.services.AppServices;
import vertxtemplate.verticles.HttpVerticle;

@Component(modules = AppModule.class)
public interface IAppComponent {
    AppControllers appControllers();

    AppServices appServices();

    AppRepos appRepos();

    HttpVerticle httpVerticle();
}
