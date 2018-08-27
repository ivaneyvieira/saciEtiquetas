package br.com.astrosoft.saciEtq.model.query.assoc

import br.com.astrosoft.saciEtq.model.Etiqueta
import br.com.astrosoft.saciEtq.model.TipoMov
import br.com.astrosoft.saciEtq.model.query.QEtiqueta
import io.ebean.typequery.PEnum
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.PString
import io.ebean.typequery.TQAssocBean
import io.ebean.typequery.TQProperty
import io.ebean.typequery.TypeQueryBean

/**
 * Association query bean for AssocEtiqueta.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QAssocEtiqueta<R>(name: String, root: R) : TQAssocBean<Etiqueta,R>(name, root) {

  lateinit var id: PLong<R>
  lateinit var createdAt: PLocalDateTime<R>
  lateinit var updatedAt: PLocalDateTime<R>
  lateinit var version: PInteger<R>
  lateinit var titulo: PString<R>
  lateinit var tipoMov: PEnum<R,TipoMov>
  lateinit var template: PString<R>

  // type safe fetch(properties) using varargs not supported yet ...
}
