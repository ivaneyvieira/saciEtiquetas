package br.com.astrosoft.saciEtq.model.finder

import br.com.astrosoft.saciEtq.model.Etiqueta
import br.com.astrosoft.saciEtq.model.query.QEtiqueta
import io.ebean.Finder

open class EtiquetaFinder : Finder<Long, Etiqueta>(Etiqueta::class.java) {

  val alias = QEtiqueta._alias

  /**
   * Start a new typed query.
   */
  fun where(): QEtiqueta {
     return QEtiqueta(db())
  }

  /**
   * Start a new document store query.
   */
  fun text(): QEtiqueta {
     return QEtiqueta(db()).text()
  }
}
