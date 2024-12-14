# Vertxtemplate

## Data Migration

Update the database schema by running the following command:

```shell
./gradlew update
```

Rollback the database schema by running the following command:

```shell
.\gradlew rollbackCount -PliquibaseCommandValue=1
```