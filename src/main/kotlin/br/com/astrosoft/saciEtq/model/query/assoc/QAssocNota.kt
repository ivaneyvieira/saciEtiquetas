package br.com.astrosoft.saciEtq.model.query.assoc

import br.com.astrosoft.saciEtq.model.Nota
import br.com.astrosoft.saciEtq.model.TipoMov
import br.com.astrosoft.saciEtq.model.query.QNota
import io.ebean.typequery.PEnum
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDate
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.PString
import io.ebean.typequery.TQAssocBean
import io.ebean.typequery.TQProperty
import io.ebean.typequery.TypeQueryBean

/**
 * Association query bean for AssocNota.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QAssocNota<R>(name: String, root: R) : TQAssocBean<Nota,R>(name, root) {

  lateinit var id: PLong<R>
  lateinit var createdAt: PLocalDateTime<R>
  lateinit var updatedAt: PLocalDateTime<R>
  lateinit var version: PInteger<R>
  lateinit var loja: QAssocLoja<R>
  lateinit var rota: PString<R>
  lateinit var nota: PString<R>
  lateinit var tipoNota: PString<R>
  lateinit var data: PLocalDate<R>
  lateinit var saldo: PInteger<R>
  lateinit var prdno: PString<R>
  lateinit var grade: PString<R>
  lateinit var name: PString<R>
  lateinit var un: PString<R>
  lateinit var tipoMov: PEnum<R,TipoMov>
  lateinit var localCD: QAssocLocalCD<R>

  // type safe fetch(properties) using varargs not supported yet ...
}
