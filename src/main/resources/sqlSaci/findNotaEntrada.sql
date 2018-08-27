SELECT CAST(IFNULL(X.xrouteno, '') AS CHAR) AS rota,
       N.storeno,
       nfname                               AS nfno,
       invse                                AS nfse,
       N.date,
       P.prdno,
       P.grade,
       P.qtty / 1000                        AS quant,
       V.name                               AS clifor,
       CASE WHEN invse = '66'
                 THEN 'ACERTO_E'
            WHEN type = 0
                 THEN "COMPRA"
            WHEN type = 1
                 THEN "TRANSFERENCIA_E"
            WHEN type = 2
                 THEN "DEV_CLI"
            WHEN type = 10 AND N.remarks LIKE 'DEV%'
                 THEN "DEV_CLI"
            ELSE "INVALIDA"
           END                              AS tipo,
       L.localizacao                        AS localizacao,
       IFNULL(S.qtty_varejo / 1000, 0)      AS saldo,
       MID(N.remarks, 1, 100)               AS observacao,
       MID(PD.name, 1, 37)                  AS descricao,
       MID(PD.name, 38, 3)                  AS un
FROM sqldados.inv AS                 N
       INNER JOIN sqldados.iprd AS   P USING (invno)
       INNER JOIN sqldados.vend AS   V
         ON V.no = N.vendno
       LEFT JOIN  sqldados.xfr AS    X
         ON X.no = N.xfrno
       LEFT JOIN  sqldados.prd AS    PD
         ON PD.no = P.prdno
       INNER JOIN sqldados.prdloc AS L
         ON N.storeno = L.storeno
              AND P.prdno = L.prdno
              AND P.grade = L.grade
       LEFT JOIN  sqldados.stk AS    S
         ON N.storeno = S.storeno
              AND P.prdno = S.prdno
              AND P.grade = S.grade
WHERE N.storeno = :storeno AND
      nfname = :nfname AND
      invse = :invse AND
      N.bits & POW(2, 4) = 0 AND
      N.auxShort13 & pow(2, 15) = 0 AND
      invse <> '';