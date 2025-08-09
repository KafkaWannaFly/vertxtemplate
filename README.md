# Java (21) Vertx Application Template

Starter package of high performance REST API server uses [Vertx](https://vertx.io/) (v5.0.2) and PostgresSQL (latest version).

## Features
- **Vertx**: Asynchronous, event-driven application framework.
- **PostgresSQL**: Powerful, open source object-relational database system.
- **Liquibase**: Database schema management tool. Plus, I write some custom Gradle tasks to make it easier to manage migrations.
- **Dagger**: Compiler-time dependency injection framework. Faster than runtime frameworks like Spring. Sample code lives in `AppModule.java` and `IAppComponent.java`.
- **Log4j**: A reliable, fast and flexible logging framework. You can add more configuration in `log4j2.xml`.

## Getting Started

Run the database container using Docker compose:

```shell
docker compose up db -d
````

And start the application:

```shell
./gradlew run
```

This app is built with N-tier architecture controller -> service -> repository. The main entry point is `AppLauncher.java`. The sample CRUD endpoints live at `FilmController.java`. The service layer is at `FilmService.java`, and the repository layer is at `FilmRepo.java`.

I use Vertx's codegen feature to simplify the data mapping between DB response and the data objects with `@DataObject`, `@RowMapped`, `@ParametersMapped` annotations. Check out classes in `models/` folder for details.

Generated code can be found in `build/generated/sources/annotationProcessor/java`

## Configuration

App-specific configuration can be found at `src/main/resources/application.yaml`. The data is mapped to `configs/Config.java`. You can override value in the file by setting environment variables like this:
```yaml
app:
  http:
    port: 8888
```
You can override the `port` by setting the environment variable `APP_HTTP_PORT` to a different value, e.g. `export APP_HTTP_PORT=9999`.

## Data Migration

### Update the database schema by running the following command:

```shell
./gradlew update
```

### Rollback all migration by running the following command:

```shell
./gradlew rollback -PliquibaseCommandValue="v0.1"
```

### Rollback a certain amount of migrations by running the following command:

```shell
./gradlew rollbackCount -PliquibaseCommandValue=<number_of_migrations>
```

### Add a new migration by running the following command:

```shell
./gradlew addChangeSet -Pn=migration_name
```

## Format and Lint

```shell
./gradlew spotlessApply
```