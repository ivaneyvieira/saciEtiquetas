-- apply changes
alter table notas add column local_cd_id bigint;

create index ix_notas_local_cd_id on notas (local_cd_id);
alter table notas add constraint fk_notas_local_cd_id foreign key (local_cd_id) references locaiscd (id) on delete restrict on update restrict;

