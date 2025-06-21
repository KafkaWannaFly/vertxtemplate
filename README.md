# Vertxtemplate

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