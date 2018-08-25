package br.com.astrosoft.saciEtq.model.finder

import br.com.astrosoft.saciEtq.model.Loja
import br.com.astrosoft.saciEtq.model.query.QLoja
import io.ebean.Finder

open class LojaFinder : Finder<Long, Loja>(Loja::class.java) {

  val alias = QLoja._alias

  /**
   * Start a new typed query.
   */
  fun where(): QLoja {
     return QLoja(db())
  }

  /**
   * Start a new document store query.
   */
  fun text(): QLoja {
     return QLoja(db()).text()
  }
}
