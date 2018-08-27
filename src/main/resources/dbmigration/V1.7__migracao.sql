-- apply changes
alter table notas drop index uq_notas_loja_id_nota;
alter table notas add constraint uq_notas_loja_id_nota_prdno_grade unique  (loja_id,nota,prdno,grade);
