create table if not exists users
(
    id   bigint not null
        primary key,
    name varchar(255)
);

create table if not exists tasks
(
    id          bigint not null
        primary key,
    date        date,
    description varchar(255),
    task_status varchar(255),
    title       varchar(255),
    user_id     bigint
        constraint fk6s1ob9k4ihi75xbxe2w0ylsdh
            references users
);