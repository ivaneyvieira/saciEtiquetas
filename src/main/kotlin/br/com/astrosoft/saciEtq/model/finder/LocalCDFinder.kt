package br.com.astrosoft.saciEtq.model.finder

import br.com.astrosoft.saciEtq.model.LocalCD
import br.com.astrosoft.saciEtq.model.query.QLocalCD
import io.ebean.Finder

open class LocalCDFinder : Finder<Long, LocalCD>(LocalCD::class.java) {

  val alias = QLocalCD._alias

  /**
   * Start a new typed query.
   */
  fun where(): QLocalCD {
     return QLocalCD(db())
  }

  /**
   * Start a new document store query.
   */
  fun text(): QLocalCD {
     return QLocalCD(db()).text()
  }
}
