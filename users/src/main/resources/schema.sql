create table if not exists users
(
    id         serial primary key,
    login      varchar(100)                not null unique,
    created_at timestamp without time zone not null
);

create table if not exists posts
(
    id          serial primary key,
    description varchar(1000)                 not null,
    author_id   integer references users (id) not null,
    created_at  timestamp without time zone   not null
);