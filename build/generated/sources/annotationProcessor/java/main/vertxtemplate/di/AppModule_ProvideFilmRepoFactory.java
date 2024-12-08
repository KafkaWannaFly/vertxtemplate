package vertxtemplate.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
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
public final class AppModule_ProvideFilmRepoFactory implements Factory<IFilmRepo> {
  private final AppModule module;

  public AppModule_ProvideFilmRepoFactory(AppModule module) {
    this.module = module;
  }

  @Override
  public IFilmRepo get() {
    return provideFilmRepo(module);
  }

  public static AppModule_ProvideFilmRepoFactory create(AppModule module) {
    return new AppModule_ProvideFilmRepoFactory(module);
  }

  public static IFilmRepo provideFilmRepo(AppModule instance) {
    return Preconditions.checkNotNullFromProvides(instance.provideFilmRepo());
  }
}
