package br.com.astrosoft.saciEtq.model.finder

import br.com.astrosoft.saciEtq.model.Usuario
import br.com.astrosoft.saciEtq.model.query.QUsuario
import io.ebean.Finder

open class UsuarioFinder : Finder<Long, Usuario>(Usuario::class.java) {

  val alias = QUsuario._alias

  /**
   * Start a new typed query.
   */
  fun where(): QUsuario {
     return QUsuario(db())
  }

  /**
   * Start a new document store query.
   */
  fun text(): QUsuario {
     return QUsuario(db()).text()
  }
}
