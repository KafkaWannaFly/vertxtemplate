package vertxtemplate.services;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import vertxtemplate.repos.IFilmRepo;

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
  private final Provider<IFilmRepo> filmRepoProvider;

  public FilmService_Factory(Provider<IFilmRepo> filmRepoProvider) {
    this.filmRepoProvider = filmRepoProvider;
  }

  @Override
  public FilmService get() {
    return newInstance(filmRepoProvider.get());
  }

  public static FilmService_Factory create(Provider<IFilmRepo> filmRepoProvider) {
    return new FilmService_Factory(filmRepoProvider);
  }

  public static FilmService newInstance(IFilmRepo filmRepo) {
    return new FilmService(filmRepo);
  }
}
