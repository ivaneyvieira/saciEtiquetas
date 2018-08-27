-- apply changes
create table etiquetas (
  id                            bigint auto_increment not null,
  titulo                        varchar(60) not null,
  tipo_mov                      varchar(7),
  template                      longtext not null,
  created_at                    datetime(6) not null,
  updated_at                    datetime(6) not null,
  version                       integer not null,
  constraint ck_etiquetas_tipo_mov check ( tipo_mov in ('ENTRADA','SAIDA')),
  constraint pk_etiquetas primary key (id)
);

