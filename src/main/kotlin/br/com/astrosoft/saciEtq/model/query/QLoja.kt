package br.com.astrosoft.saciEtq.model.query

import br.com.astrosoft.saciEtq.model.Loja
import br.com.astrosoft.saciEtq.model.query.assoc.QAssocLocal
import br.com.astrosoft.saciEtq.model.query.assoc.QAssocUsuario
import io.ebean.EbeanServer
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.PString
import io.ebean.typequery.TQRootBean
import io.ebean.typequery.TypeQueryBean

/**
 * Query bean for Loja.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QLoja : TQRootBean<Loja, QLoja> {

  companion object {
    /**
     * shared 'Alias' instance used to provide
     * properties to select and fetch clauses
     */
    val _alias = QLoja(true)
  }

  lateinit var id: PLong<QLoja>
  lateinit var createdAt: PLocalDateTime<QLoja>
  lateinit var updatedAt: PLocalDateTime<QLoja>
  lateinit var version: PInteger<QLoja>
  lateinit var numero: PInteger<QLoja>
  lateinit var sigla: PString<QLoja>
  lateinit var usuarios: QAssocUsuario<QLoja>
  lateinit var localis: QAssocLocal<QLoja>


  /**
   * Construct with a given EbeanServer.
   */
  constructor(server: EbeanServer) : super(Loja::class.java, server)

  /**
   * Construct using the default EbeanServer.
   */
  constructor() : super(Loja::class.java)

  /**
   * Construct for Alias.
   */
  private constructor(dummy: Boolean) : super(dummy)
}
