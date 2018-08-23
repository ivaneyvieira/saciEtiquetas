package br.com.astrosoft.saciEtq.model.query.assoc

import br.com.astrosoft.saciEtq.model.Usuario
import br.com.astrosoft.saciEtq.model.query.QUsuario
import io.ebean.typequery.PBoolean
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.PString
import io.ebean.typequery.TQAssocBean
import io.ebean.typequery.TQProperty
import io.ebean.typequery.TypeQueryBean

/**
 * Association query bean for AssocUsuario.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QAssocUsuario<R>(name: String, root: R) : TQAssocBean<Usuario,R>(name, root) {

  lateinit var id: PLong<R>
  lateinit var createdAt: PLocalDateTime<R>
  lateinit var updatedAt: PLocalDateTime<R>
  lateinit var version: PInteger<R>
  lateinit var loginName: PString<R>
  lateinit var impressora: PString<R>
  lateinit var loja: QAssocLoja<R>
  lateinit var localizacaoes: PString<R>
  lateinit var nome: PString<R>
  lateinit var admin: PBoolean<R>

  // type safe fetch(properties) using varargs not supported yet ...
}
