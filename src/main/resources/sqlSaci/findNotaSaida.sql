select CAST(IFNULL(X.xrouteno, '') AS CHAR) as rota, N.storeno,
  N.nfno, N.nfse, N.issuedate as date,P.prdno as prdno, P.grade, P.qtty as quant,
  C.name as clienteName,
  CASE
    WHEN N.nfse = '66' then 'ACERTO_S'
    WHEN N.nfse = '3' then 'ENT_RET'
    WHEN tipo = 0 then 'VENDA'
    WHEN tipo = 1 then 'TRANSFERENCIA_S'
    WHEN tipo = 2 then 'DEV_FOR'
    ELSE 'INVALIDA'
  END AS tipo, localizacao, IFNULL(S.qtty_varejo/1000, 0) as saldo,
  MID(N.remarks, 1, 100) as observacao
from sqldados.nf AS N
  inner join sqldados.xaprd AS P
  USING(storeno, pdvno, xano)
  INNER join sqldados.prdloc AS L
    USING(storeno, prdno, grade)
  LEFT JOIN sqldados.stk AS S
    USING(storeno, prdno, grade)
  left join sqldados.custp AS C
    ON C.no = N.custno
  left join sqldados.inv AS I
    ON N.invno = I.invno
  left join sqldados.xfr AS X
    ON X.no = I.xfrno
where N.storeno  = :storeno
      and N.nfno = :nfno
      and N.nfse = :nfse
      AND N.status <> 1;