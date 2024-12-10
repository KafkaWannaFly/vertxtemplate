package vertxtemplate.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import vertxtemplate.repos.AppRepos;
import vertxtemplate.services.IFilmService;

@ScopeMetadata
@QualifierMetadata
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
public final class AppModule_ProvideFilmServiceFactory implements Factory<IFilmService> {
  private final AppModule module;

  private final Provider<AppRepos> appReposProvider;

  public AppModule_ProvideFilmServiceFactory(AppModule module,
      Provider<AppRepos> appReposProvider) {
    this.module = module;
    this.appReposProvider = appReposProvider;
  }

  @Override
  public IFilmService get() {
    return provideFilmService(module, appReposProvider.get());
  }

  public static AppModule_ProvideFilmServiceFactory create(AppModule module,
      Provider<AppRepos> appReposProvider) {
    return new AppModule_ProvideFilmServiceFactory(module, appReposProvider);
  }

  public static IFilmService provideFilmService(AppModule instance, AppRepos appRepos) {
    return Preconditions.checkNotNullFromProvides(instance.provideFilmService(appRepos));
  }
}
