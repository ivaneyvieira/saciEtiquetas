select CAST(IFNULL(X.xrouteno, '') AS CHAR) as rota, N.storeno, nfname as nfno, invse as nfse,
  N.date, P.prdno, P.grade, P.qtty/1000 as quant, V.name as vendName,
  CASE
    WHEN invse = '66' then 'ACERTO_E'
    WHEN type = 0 then "COMPRA"
    WHEN type = 1 then "TRANSFERENCIA_E"
    WHEN type = 2 then "DEV_CLI"
    WHEN type = 10 AND N.remarks LIKE 'DEV%' then "DEV_CLI"
    ELSE "INVALIDA"
  END AS tipo,
  L.localizacao AS localizacao, IFNULL(S.qtty_varejo/1000, 0) as saldo,
  MID(N.remarks, 1, 100) as observacao, MID(PD.name, 1, 37) as descricao, MID(PD.name, 38, 3) as un
from sqldados.inv AS N
  inner join sqldados.iprd AS P
  USING(invno)
  inner join sqldados.vend AS V
    ON V.no = N.vendno
  left join sqldados.xfr AS X
    ON X.no = N.xfrno
  left join sqldados.prd AS PD
    ON PD.no = P.prdno
  inner join sqldados.prdloc AS L
    ON N.storeno = L.storeno
    AND P.prdno = L.prdno
    AND P.grade = L.grade
  left join sqldados.stk AS S
    ON N.storeno = S.storeno
    AND P.prdno = S.prdno
    AND P.grade = S.grade
where N.storeno = :storeno
      and nfname = :nfname
      and invse = :invse
      AND N.bits & POW(2, 4) = 0
      AND N.auxShort13 & pow(2, 15) = 0
      AND invse <> '';