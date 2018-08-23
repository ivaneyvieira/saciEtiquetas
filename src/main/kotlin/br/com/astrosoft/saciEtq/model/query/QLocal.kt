package br.com.astrosoft.saciEtq.model.query

import br.com.astrosoft.saciEtq.model.Local
import br.com.astrosoft.saciEtq.model.query.assoc.QAssocLoja
import io.ebean.EbeanServer
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.PString
import io.ebean.typequery.TQRootBean
import io.ebean.typequery.TypeQueryBean

/**
 * Query bean for Local.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QLocal : TQRootBean<Local, QLocal> {

  companion object {
    /**
     * shared 'Alias' instance used to provide
     * properties to select and fetch clauses
     */
    val _alias = QLocal(true)
  }

  lateinit var id: PLong<QLocal>
  lateinit var createdAt: PLocalDateTime<QLocal>
  lateinit var updatedAt: PLocalDateTime<QLocal>
  lateinit var version: PInteger<QLocal>
  lateinit var loja: QAssocLoja<QLocal>
  lateinit var localizacao: PString<QLocal>


  /**
   * Construct with a given EbeanServer.
   */
  constructor(server: EbeanServer) : super(Local::class.java, server)

  /**
   * Construct using the default EbeanServer.
   */
  constructor() : super(Local::class.java)

  /**
   * Construct for Alias.
   */
  private constructor(dummy: Boolean) : super(dummy)
}
