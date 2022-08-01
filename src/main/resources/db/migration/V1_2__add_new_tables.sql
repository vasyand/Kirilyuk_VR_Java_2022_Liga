create table if not exists projects
(
    id          bigint
        default nextval('serial')
        primary key,
    description varchar(255),
    title       varchar(255)
);

create table if not exists comments
(
    id      bigint
        default nextval('serial')
        primary key,
    message varchar(255),
    task_id bigint
        constraint fki7pp0331nbiwd2844kg78kfwb
            references tasks
             on DELETE cascade
);

create table if not exists users_projects
(
    users_id    bigint not null
        constraint fkhcr3t491sjt9yk1kmckf6vjo2
            references users,
    projects_id bigint not null
        constraint fk1wq47u13qkt3icnwbuhw4sei3
            references projects
);

alter table users
    DROP COLUMN name RESTRICT,
    ADD COLUMN email varchar(255),
    ADD COLUMN first_name varchar(255),
    ADD COLUMN middle_name varchar(255),
    ADD COLUMN last_name varchar(255),
    ADD COLUMN password varchar(255),
    ADD COLUMN role varchar(30);


alter table tasks
    add column project_id bigint
        constraint fksfhn82y57i3k9uxww1s007acc
            references projects
            on DELETE set null;


