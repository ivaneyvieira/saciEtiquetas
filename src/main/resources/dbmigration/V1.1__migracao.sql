-- apply changes
create table users_locais (
  usuarios_id                   bigint not null,
  locais_cd_id                  bigint not null,
  constraint pk_users_locais primary key (usuarios_id,locais_cd_id)
);

create index ix_users_locais_usuarios on users_locais (usuarios_id);
alter table users_locais add constraint fk_users_locais_usuarios foreign key (usuarios_id) references usuarios (id) on delete restrict on update restrict;

create index ix_users_locais_locaiscd on users_locais (locais_cd_id);
alter table users_locais add constraint fk_users_locais_locaiscd foreign key (locais_cd_id) references locaiscd (id) on delete restrict on update restrict;

