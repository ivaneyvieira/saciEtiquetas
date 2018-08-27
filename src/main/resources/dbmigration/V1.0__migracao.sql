-- apply changes
create table locais (
  id                            bigint auto_increment not null,
  loja_id                       bigint,
  local_cd_id                   bigint,
  created_at                    datetime not null,
  updated_at                    datetime not null,
  version                       integer not null,
  constraint pk_locais primary key (id)
);

create table locaiscd (
  id                            bigint auto_increment not null,
  descricao                     varchar(30) not null,
  created_at                    datetime not null,
  updated_at                    datetime not null,
  version                       integer not null,
  constraint uq_locaiscd_descricao unique (descricao),
  constraint pk_locaiscd primary key (id)
);

create table lojas (
  id                            bigint auto_increment not null,
  numero                        integer not null,
  sigla                         varchar(2) not null,
  created_at                    datetime not null,
  updated_at                    datetime not null,
  version                       integer not null,
  constraint uq_lojas_numero unique (numero),
  constraint pk_lojas primary key (id)
);

create table notas (
  id                            bigint auto_increment not null,
  loja_id                       bigint,
  rota                          varchar(4) not null,
  nota                          varchar(16) not null,
  tipo_nota                     varchar(20) not null,
  data                          date not null,
  saldo                         integer not null,
  prdno                         varchar(16) not null,
  grade                         varchar(8) not null,
  name                          varchar(40) not null,
  un                            varchar(4) not null,
  tipo_mov                      varchar(7) not null,
  local_cd_id                   bigint,
  quantidade                    integer not null,
  cliente                       varchar(255) not null,
  fornecedor                    varchar(255) not null,
  impresso                      tinyint(1) default 0 not null,
  created_at                    datetime not null,
  updated_at                    datetime not null,
  version                       integer not null,
  constraint ck_notas_tipo_mov check ( tipo_mov in ('ENTRADA','SAIDA')),
  constraint uq_notas_loja_id_nota unique (loja_id,nota),
  constraint pk_notas primary key (id)
);

create table usuarios (
  id                            bigint auto_increment not null,
  login_name                    varchar(8) not null,
  loja_id                       bigint,
  nome                          varchar(50) not null,
  created_at                    datetime not null,
  updated_at                    datetime not null,
  version                       integer not null,
  constraint uq_usuarios_login_name unique (login_name),
  constraint pk_usuarios primary key (id)
);

create table users_locais (
  usuarios_id                   bigint not null,
  locaiscd_id                   bigint not null,
  constraint pk_users_locais primary key (usuarios_id,locaiscd_id)
);

create index ix_locais_loja_id on locais (loja_id);
alter table locais add constraint fk_locais_loja_id foreign key (loja_id) references lojas (id) on delete restrict on update restrict;

create index ix_locais_local_cd_id on locais (local_cd_id);
alter table locais add constraint fk_locais_local_cd_id foreign key (local_cd_id) references locaiscd (id) on delete restrict on update restrict;

create index ix_notas_loja_id on notas (loja_id);
alter table notas add constraint fk_notas_loja_id foreign key (loja_id) references lojas (id) on delete restrict on update restrict;

create index ix_notas_local_cd_id on notas (local_cd_id);
alter table notas add constraint fk_notas_local_cd_id foreign key (local_cd_id) references locaiscd (id) on delete restrict on update restrict;

create index ix_usuarios_loja_id on usuarios (loja_id);
alter table usuarios add constraint fk_usuarios_loja_id foreign key (loja_id) references lojas (id) on delete restrict on update restrict;

create index ix_users_locais_usuarios on users_locais (usuarios_id);
alter table users_locais add constraint fk_users_locais_usuarios foreign key (usuarios_id) references usuarios (id) on delete restrict on update restrict;

create index ix_users_locais_locaiscd on users_locais (locaiscd_id);
alter table users_locais add constraint fk_users_locais_locaiscd foreign key (locaiscd_id) references locaiscd (id) on delete restrict on update restrict;

