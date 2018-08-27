package br.com.astrosoft.saciEtq.model.query

import br.com.astrosoft.saciEtq.model.Etiqueta
import br.com.astrosoft.saciEtq.model.TipoMov
import io.ebean.EbeanServer
import io.ebean.typequery.PEnum
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.PString
import io.ebean.typequery.TQRootBean
import io.ebean.typequery.TypeQueryBean

/**
 * Query bean for Etiqueta.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QEtiqueta : TQRootBean<Etiqueta, QEtiqueta> {

  companion object {
    /**
     * shared 'Alias' instance used to provide
     * properties to select and fetch clauses
     */
    val _alias = QEtiqueta(true)
  }

  lateinit var id: PLong<QEtiqueta>
  lateinit var createdAt: PLocalDateTime<QEtiqueta>
  lateinit var updatedAt: PLocalDateTime<QEtiqueta>
  lateinit var version: PInteger<QEtiqueta>
  lateinit var titulo: PString<QEtiqueta>
  lateinit var tipoMov: PEnum<QEtiqueta,TipoMov>
  lateinit var template: PString<QEtiqueta>


  /**
   * Construct with a given EbeanServer.
   */
  constructor(server: EbeanServer) : super(Etiqueta::class.java, server)

  /**
   * Construct using the default EbeanServer.
   */
  constructor() : super(Etiqueta::class.java)

  /**
   * Construct for Alias.
   */
  private constructor(dummy: Boolean) : super(dummy)
}
