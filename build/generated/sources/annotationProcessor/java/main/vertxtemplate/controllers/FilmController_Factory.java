package vertxtemplate.controllers;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import vertxtemplate.services.AppServices;

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
  private final Provider<AppServices> appServicesProvider;

  public FilmController_Factory(Provider<AppServices> appServicesProvider) {
    this.appServicesProvider = appServicesProvider;
  }

  @Override
  public FilmController get() {
    return newInstance(appServicesProvider.get());
  }

  public static FilmController_Factory create(Provider<AppServices> appServicesProvider) {
    return new FilmController_Factory(appServicesProvider);
  }

  public static FilmController newInstance(AppServices appServices) {
    return new FilmController(appServices);
  }
}
