-- apply changes
alter table notas add column tipo_mov varchar(7) not null;
alter table notas add constraint ck_notas_tipo_mov check ( tipo_mov in ('ENTRADA','SAIDA'));

