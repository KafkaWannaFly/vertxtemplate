package vertxtemplate.services;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import vertxtemplate.repos.AppRepos;

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
public final class FilmService_Factory implements Factory<FilmService> {
  private final Provider<AppRepos> appReposProvider;

  public FilmService_Factory(Provider<AppRepos> appReposProvider) {
    this.appReposProvider = appReposProvider;
  }

  @Override
  public FilmService get() {
    return newInstance(appReposProvider.get());
  }

  public static FilmService_Factory create(Provider<AppRepos> appReposProvider) {
    return new FilmService_Factory(appReposProvider);
  }

  public static FilmService newInstance(AppRepos appRepos) {
    return new FilmService(appRepos);
  }
}
