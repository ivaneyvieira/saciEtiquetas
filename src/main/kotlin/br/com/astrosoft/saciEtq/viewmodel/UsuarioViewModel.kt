package br.com.astrosoft.saciEtq.viewmodel

import br.com.astrosoft.framework.viewmodel.CrudViewModel
import br.com.astrosoft.framework.viewmodel.EntityVo
import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.saciEtq.model.LocalCD
import br.com.astrosoft.saciEtq.model.Loja
import br.com.astrosoft.saciEtq.model.Usuario
import br.com.astrosoft.saciEtq.model.query.QUsuario

class UsuarioViewModel(view: IView) :
        CrudViewModel<Usuario, QUsuario, UsuarioCrudVo>(view, UsuarioCrudVo::class) {
  val lojas = Loja.all()
  
  override val query: QUsuario
    get() = Usuario.where()
  
  override fun UsuarioCrudVo.toModel(): Usuario {
    val usuario = entityVo?: Usuario()
    val vo = this
    return usuario.apply {
      loginName = vo.loginName
      loja = vo.loja
      locais = vo.locais
      this.nome = vo.nome
    }
  }
  
  override fun Usuario.toVO(): UsuarioCrudVo {
    val user = this
    return UsuarioCrudVo().apply {
      entityVo = user
      loginName = user.loginName
      loja = user.loja
      locais = user.locais
      nome = user.nome
    }
  }
}

class UsuarioCrudVo : EntityVo<Usuario>() {
  var loginName: String = ""
  var loja: Loja? = null
  var nome: String = ""
  var locais: MutableSet<LocalCD> = HashSet()
  
  var locaisLoja: MutableSet<LocalCD> = HashSet()
  val localStr : String = ""
  override fun findEntity(): Usuario? {
    return Usuario.findUsuario(loginName)
  }
}