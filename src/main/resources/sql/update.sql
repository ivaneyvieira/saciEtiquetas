insert into lojas(numero, sigla, created_at, updated_at, version)
select no, sname, current_date, current_date, 0
from sqldados.store AS S
  left join lojas AS L
    ON S.no = L.numero
WHERE L.numero is null
order by S.no;

insert ignore into usuarios(login_name, nome, created_at, updated_at, version)
select login, name, current_date, current_date, 0
from sqldados.users AS U
where U.login = 'ADM';

insert ignore into locaiscd(descricao, created_at, updated_at, version)
select distinct localizacao, current_date, current_date, 0
from sqldados.prdloc;

insert ignore into locais(loja_id, local_cd_id, created_at, updated_at, version)
select distinct LJ.id, L.id, current_date, current_date, 0
from sqldados.prdloc AS CD
  inner join lojas AS LJ
    ON LJ.numero = CD.storeno
  inner join locaiscd AS L
    ON L.descricao = CD.localizacao;
