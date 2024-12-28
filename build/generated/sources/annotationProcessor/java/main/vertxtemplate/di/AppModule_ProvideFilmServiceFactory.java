package vertxtemplate.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import vertxtemplate.repos.IFilmRepo;
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

  private final Provider<IFilmRepo> filmRepoProvider;

  public AppModule_ProvideFilmServiceFactory(AppModule module,
      Provider<IFilmRepo> filmRepoProvider) {
    this.module = module;
    this.filmRepoProvider = filmRepoProvider;
  }

  @Override
  public IFilmService get() {
    return provideFilmService(module, filmRepoProvider.get());
  }

  public static AppModule_ProvideFilmServiceFactory create(AppModule module,
      Provider<IFilmRepo> filmRepoProvider) {
    return new AppModule_ProvideFilmServiceFactory(module, filmRepoProvider);
  }

  public static IFilmService provideFilmService(AppModule instance, IFilmRepo filmRepo) {
    return Preconditions.checkNotNullFromProvides(instance.provideFilmService(filmRepo));
  }
}
