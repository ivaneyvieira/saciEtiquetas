package br.com.astrosoft.saciEtq.viewmodel

import br.com.astrosoft.framework.viewmodel.CrudViewModel
import br.com.astrosoft.framework.viewmodel.EntityVo
import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.saciEtq.model.Loja
import br.com.astrosoft.saciEtq.model.Usuario
import br.com.astrosoft.saciEtq.model.query.QUsuario
import io.ebean.annotation.Length
import javax.persistence.CascadeType
import javax.persistence.ManyToOne

class UsuarioViewModel(view: IView, override val query: QUsuario) : CrudViewModel<Usuario, QUsuario, UsuarioCrudVo>(view, UsuarioCrudVo::class) {
  override fun update(bean: UsuarioCrudVo) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
  
  override fun add(bean: UsuarioCrudVo) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
  
  override fun delete(bean: UsuarioCrudVo) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
  
  override fun Usuario.toVO(): UsuarioCrudVo {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}

class UsuarioCrudVo : EntityVo<Usuario>() {
  var loginName: String = ""
  var impressora: String = ""
  var loja: Loja? = null
  var localizacaoes: String = ""
  val nome: String = ""
  
  override fun findEntity(): Usuario? {
    return Usuario.findUsuario(loginName)
  }
}