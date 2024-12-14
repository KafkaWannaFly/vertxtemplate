create table film
(
    id           serial primary key,
    title        varchar(255) not null,
    description  text,
    release_year integer,
    length       integer,
    rating       float,
    created_at   timestamp    not null default now(),
    updated_at   timestamp    not null default now()
);