package vertxtemplate.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;
import vertxtemplate.configs.Config;
import vertxtemplate.controllers.FilmController;
import vertxtemplate.services.IFilmService;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class DaggerAppComponent {
  private DaggerAppComponent() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private AppModule appModule;

    private Builder() {
    }

    public Builder appModule(AppModule appModule) {
      this.appModule = Preconditions.checkNotNull(appModule);
      return this;
    }

    public IAppComponent build() {
      Preconditions.checkBuilderRequirement(appModule, AppModule.class);
      return new AppComponentImpl(appModule);
    }
  }

  private static final class AppComponentImpl implements IAppComponent {
    private final AppModule appModule;

    private final AppComponentImpl appComponentImpl = this;

    private AppComponentImpl(AppModule appModuleParam) {
      this.appModule = appModuleParam;

    }

    private IFilmService iFilmService() {
      return AppModule_ProvideFilmServiceFactory.provideFilmService(appModule, AppModule_ProvideFilmRepoFactory.provideFilmRepo(appModule));
    }

    @Override
    public Config config() {
      return AppModule_ProvideConfigFactory.provideConfig(appModule);
    }

    @Override
    public FilmController filmController() {
      return new FilmController(iFilmService());
    }
  }
}
