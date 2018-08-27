SELECT CAST(IFNULL(X.xrouteno, '') AS CHAR) AS rota,
       N.storeno,
       N.nfno,
       N.nfse,
       N.issuedate                          AS date,
       P.prdno                              AS prdno,
       P.grade,
       P.qtty                               AS quant,
       C.name                               AS clifor,
       CASE WHEN N.nfse = '66'
                 THEN 'ACERTO_S'
            WHEN N.nfse = '3'
                 THEN 'ENT_RET'
            WHEN tipo = 0
                 THEN 'VENDA'
            WHEN tipo = 1
                 THEN 'TRANSFERENCIA_S'
            WHEN tipo = 2
                 THEN 'DEV_FOR'
            ELSE 'INVALIDA'
           END                              AS tipo,
       localizacao,
       IFNULL(S.qtty_varejo / 1000, 0)      AS saldo,
       MID(N.remarks, 1, 100)               AS observacao,
       MID(PD.name, 1, 37)                  AS descricao,
       MID(PD.name, 38, 3)                  AS un
FROM sqldados.nf AS                  N
       INNER JOIN sqldados.xaprd AS  P USING (storeno, pdvno, xano)
       INNER JOIN sqldados.prdloc AS L USING (storeno, prdno, grade)
       LEFT JOIN  sqldados.prd AS    PD
         ON PD.no = P.prdno
       LEFT JOIN  sqldados.stk AS    S USING (storeno, prdno, grade)
       LEFT JOIN  sqldados.custp AS  C
         ON C.no = N.custno
       LEFT JOIN  sqldados.inv AS    I
         ON N.invno = I.invno
       LEFT JOIN  sqldados.xfr AS    X
         ON X.no = I.xfrno
WHERE N.storeno = :storeno AND
      N.nfno = :nfno AND
      N.nfse = :nfse AND
      N.status <> 1;