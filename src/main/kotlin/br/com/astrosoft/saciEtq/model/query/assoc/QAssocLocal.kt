package br.com.astrosoft.saciEtq.model.query.assoc

import br.com.astrosoft.saciEtq.model.Local
import br.com.astrosoft.saciEtq.model.query.QLocal
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.PString
import io.ebean.typequery.TQAssocBean
import io.ebean.typequery.TQProperty
import io.ebean.typequery.TypeQueryBean

/**
 * Association query bean for AssocLocal.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QAssocLocal<R>(name: String, root: R) : TQAssocBean<Local,R>(name, root) {

  lateinit var id: PLong<R>
  lateinit var createdAt: PLocalDateTime<R>
  lateinit var updatedAt: PLocalDateTime<R>
  lateinit var version: PInteger<R>
  lateinit var loja: QAssocLoja<R>
  lateinit var localizacao: PString<R>

  // type safe fetch(properties) using varargs not supported yet ...
}
