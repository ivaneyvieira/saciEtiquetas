-- apply changes
create table locais (
  id                            bigint auto_increment not null,
  loja_id                       bigint,
  localizacao                   varchar(30) not null,
  created_at                    datetime(6) not null,
  updated_at                    datetime(6) not null,
  version                       integer not null,
  constraint pk_locais primary key (id)
);

create table lojas (
  id                            bigint auto_increment not null,
  numero                        integer not null,
  sigla                         varchar(2) not null,
  created_at                    datetime(6) not null,
  updated_at                    datetime(6) not null,
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
  loc                           varchar(30) not null,
  created_at                    datetime(6) not null,
  updated_at                    datetime(6) not null,
  version                       integer not null,
  constraint pk_notas primary key (id)
);

create table usuarios (
  id                            bigint auto_increment not null,
  login_name                    varchar(8) not null,
  impressora                    varchar(30) not null,
  loja_id                       bigint,
  localizacaoes                 varchar(4000) not null,
  nome                          varchar(50) not null,
  created_at                    datetime(6) not null,
  updated_at                    datetime(6) not null,
  version                       integer not null,
  constraint uq_usuarios_login_name unique (login_name),
  constraint pk_usuarios primary key (id)
);

create index ix_locais_loja_id on locais (loja_id);
alter table locais add constraint fk_locais_loja_id foreign key (loja_id) references lojas (id) on delete restrict on update restrict;

create index ix_notas_loja_id on notas (loja_id);
alter table notas add constraint fk_notas_loja_id foreign key (loja_id) references lojas (id) on delete restrict on update restrict;

create index ix_usuarios_loja_id on usuarios (loja_id);
alter table usuarios add constraint fk_usuarios_loja_id foreign key (loja_id) references lojas (id) on delete restrict on update restrict;

