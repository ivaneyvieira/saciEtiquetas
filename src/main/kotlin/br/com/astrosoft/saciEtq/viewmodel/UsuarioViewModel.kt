package br.com.astrosoft.saciEtq.viewmodel

import br.com.astrosoft.framework.saci.saci
import br.com.astrosoft.framework.viewmodel.CrudViewModel
import br.com.astrosoft.framework.viewmodel.EntityVo
import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.saciEtq.model.Loja
import br.com.astrosoft.saciEtq.model.Usuario
import br.com.astrosoft.saciEtq.model.query.QUsuario

class UsuarioViewModel(view: IView) :
        CrudViewModel<Usuario, QUsuario, UsuarioVo>(view, UsuarioVo::class) {
  val lojas = Loja.all()
  
  override val query: QUsuario
    get() = Usuario.where()
  
  override fun UsuarioVo.toModel(): Usuario {
    val usuario = entityVo ?: Usuario()
    val vo = this
    return usuario.apply {
      loginName = vo.loginName ?: ""
      loja = vo.loja
      locaisAbreivados = vo.locais.toList()
      this.nome = vo.nome ?: ""
    }
  }
  
  override fun Usuario.toVO(): UsuarioVo {
    val user = this
    return UsuarioVo().apply {
      entityVo = user
      loginName = user.loginName
      loja = user.loja
      locais = user.locaisAbreivados.toMutableSet()
      nome = user.nome
    }
  }
}

class UsuarioVo : EntityVo<Usuario>() {
  var loginName: String? = ""
  set(value) {
    field = value
    nome = saci.findUser(value)?.name ?: ""
  }
  var loja: Loja? = null
  var nome: String? = ""
  var locais: MutableSet<String> = HashSet()
  
  val locaisLoja: MutableSet<String>
    get() = loja?.locaisAbreivados.orEmpty().toMutableSet()
  val localStr: String get() = locais.distinct().sorted().joinToString()
  override fun findEntity(): Usuario? {
    return Usuario.findUsuario(loginName)
  }
}