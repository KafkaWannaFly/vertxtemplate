package vertxtemplate.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import io.vertx.sqlclient.Pool;
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
public final class AppModule_ProvideFilmRepoFactory implements Factory<IFilmRepo> {
  private final AppModule module;

  private final Provider<Pool> poolProvider;

  public AppModule_ProvideFilmRepoFactory(AppModule module, Provider<Pool> poolProvider) {
    this.module = module;
    this.poolProvider = poolProvider;
  }

  @Override
  public IFilmRepo get() {
    return provideFilmRepo(module, poolProvider.get());
  }

  public static AppModule_ProvideFilmRepoFactory create(AppModule module,
      Provider<Pool> poolProvider) {
    return new AppModule_ProvideFilmRepoFactory(module, poolProvider);
  }

  public static IFilmRepo provideFilmRepo(AppModule instance, Pool pool) {
    return Preconditions.checkNotNullFromProvides(instance.provideFilmRepo(pool));
  }
}
