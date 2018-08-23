package br.com.astrosoft.saciEtq.model.finder

import br.com.astrosoft.saciEtq.model.Local
import br.com.astrosoft.saciEtq.model.query.QLocal
import io.ebean.Finder

open class LocalFinder : Finder<Long, Local>(Local::class.java) {

  val alias = QLocal._alias

  /**
   * Start a new typed query.
   */
  fun where(): QLocal {
     return QLocal(db())
  }

  /**
   * Start a new document store query.
   */
  fun text(): QLocal {
     return QLocal(db()).text()
  }
}
