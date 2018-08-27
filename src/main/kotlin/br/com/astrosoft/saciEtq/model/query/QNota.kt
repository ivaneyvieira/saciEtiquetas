package br.com.astrosoft.saciEtq.model.query

import br.com.astrosoft.saciEtq.model.Nota
import br.com.astrosoft.saciEtq.model.TipoMov
import br.com.astrosoft.saciEtq.model.query.assoc.QAssocLocalCD
import br.com.astrosoft.saciEtq.model.query.assoc.QAssocLoja
import br.com.astrosoft.saciEtq.model.query.assoc.QAssocUsuario
import io.ebean.EbeanServer
import io.ebean.typequery.PBoolean
import io.ebean.typequery.PEnum
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDate
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.PString
import io.ebean.typequery.TQRootBean
import io.ebean.typequery.TypeQueryBean

/**
 * Query bean for Nota.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QNota : TQRootBean<Nota, QNota> {

  companion object {
    /**
     * shared 'Alias' instance used to provide
     * properties to select and fetch clauses
     */
    val _alias = QNota(true)
  }

  lateinit var id: PLong<QNota>
  lateinit var createdAt: PLocalDateTime<QNota>
  lateinit var updatedAt: PLocalDateTime<QNota>
  lateinit var version: PInteger<QNota>
  lateinit var loja: QAssocLoja<QNota>
  lateinit var rota: PString<QNota>
  lateinit var nota: PString<QNota>
  lateinit var tipoNota: PString<QNota>
  lateinit var data: PLocalDate<QNota>
  lateinit var saldo: PInteger<QNota>
  lateinit var prdno: PString<QNota>
  lateinit var grade: PString<QNota>
  lateinit var name: PString<QNota>
  lateinit var un: PString<QNota>
  lateinit var tipoMov: PEnum<QNota,TipoMov>
  lateinit var localCD: QAssocLocalCD<QNota>
  lateinit var usuario: QAssocUsuario<QNota>
  lateinit var quantidade: PInteger<QNota>
  lateinit var clifor: PString<QNota>
  lateinit var impresso: PBoolean<QNota>
  lateinit var observacao: PString<QNota>


  /**
   * Construct with a given EbeanServer.
   */
  constructor(server: EbeanServer) : super(Nota::class.java, server)

  /**
   * Construct using the default EbeanServer.
   */
  constructor() : super(Nota::class.java)

  /**
   * Construct for Alias.
   */
  private constructor(dummy: Boolean) : super(dummy)
}
