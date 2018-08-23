package br.com.astrosoft.saciEtq.model.finder

import br.com.astrosoft.saciEtq.model.Nota
import br.com.astrosoft.saciEtq.model.query.QNota
import io.ebean.Finder

open class NotaFinder : Finder<Long, Nota>(Nota::class.java) {

  val alias = QNota._alias

  /**
   * Start a new typed query.
   */
  fun where(): QNota {
     return QNota(db())
  }

  /**
   * Start a new document store query.
   */
  fun text(): QNota {
     return QNota(db()).text()
  }
}
