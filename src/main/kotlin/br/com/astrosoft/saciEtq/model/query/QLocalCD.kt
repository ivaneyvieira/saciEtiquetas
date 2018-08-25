package br.com.astrosoft.saciEtq.model.query

import br.com.astrosoft.saciEtq.model.LocalCD
import br.com.astrosoft.saciEtq.model.query.assoc.QAssocUsuario
import io.ebean.EbeanServer
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.PString
import io.ebean.typequery.TQRootBean
import io.ebean.typequery.TypeQueryBean

/**
 * Query bean for LocalCD.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QLocalCD : TQRootBean<LocalCD, QLocalCD> {

  companion object {
    /**
     * shared 'Alias' instance used to provide
     * properties to select and fetch clauses
     */
    val _alias = QLocalCD(true)
  }

  lateinit var id: PLong<QLocalCD>
  lateinit var createdAt: PLocalDateTime<QLocalCD>
  lateinit var updatedAt: PLocalDateTime<QLocalCD>
  lateinit var version: PInteger<QLocalCD>
  lateinit var descricao: PString<QLocalCD>
  lateinit var abreviada: PString<QLocalCD>
  lateinit var users: QAssocUsuario<QLocalCD>


  /**
   * Construct with a given EbeanServer.
   */
  constructor(server: EbeanServer) : super(LocalCD::class.java, server)

  /**
   * Construct using the default EbeanServer.
   */
  constructor() : super(LocalCD::class.java)

  /**
   * Construct for Alias.
   */
  private constructor(dummy: Boolean) : super(dummy)
}
