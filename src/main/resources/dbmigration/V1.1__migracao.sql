-- apply changes
alter table locais add constraint uq_locais_loja_id_local_cd_id unique  (loja_id,local_cd_id);
