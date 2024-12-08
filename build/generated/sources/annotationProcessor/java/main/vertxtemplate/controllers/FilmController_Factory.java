package vertxtemplate.controllers;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
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
public final class FilmController_Factory implements Factory<FilmController> {
  private final Provider<IFilmService> filmServiceProvider;

  public FilmController_Factory(Provider<IFilmService> filmServiceProvider) {
    this.filmServiceProvider = filmServiceProvider;
  }

  @Override
  public FilmController get() {
    return newInstance(filmServiceProvider.get());
  }

  public static FilmController_Factory create(Provider<IFilmService> filmServiceProvider) {
    return new FilmController_Factory(filmServiceProvider);
  }

  public static FilmController newInstance(IFilmService filmService) {
    return new FilmController(filmService);
  }
}
