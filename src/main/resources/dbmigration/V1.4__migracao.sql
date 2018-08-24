-- apply changes
alter table notas add constraint uq_notas_loja_id_nota unique  (loja_id,nota);
