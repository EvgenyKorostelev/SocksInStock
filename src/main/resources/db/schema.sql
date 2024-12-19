create schema if not exists catalogue;

create table catalogue.t_socks(
    id            serial primary key,
    c_color varchar(50) not null check (length(trim(c_color)) >= 3),
    c_percentage_cotton INT not null check (c_percentage_cotton >= 0) check (c_percentage_cotton <= 100),
    c_pieces INT not null check (c_pieces > 0)
    );