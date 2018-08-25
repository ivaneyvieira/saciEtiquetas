package br.com.astrosoft.saciEtq.model

import br.com.astrosoft.saciEtq.model.finder.UsuarioFinder
import br.com.astrosoft.framework.model.BaseModel
import io.ebean.annotation.Formula
import io.ebean.annotation.Index
import io.ebean.annotation.Length
import javax.persistence.CascadeType.MERGE
import javax.persistence.CascadeType.PERSIST
import javax.persistence.CascadeType.REFRESH
import javax.persistence.Entity
import javax.persistence.JoinTable
import javax.persistence.Lob
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.validation.constraints.Size

@Entity
@Table(name = "usuarios")
class Usuario : BaseModel() {
  @Size(max = 8)
  @Index(unique = true)
  var loginName: String = ""
  @ManyToOne(cascade = [PERSIST, MERGE, REFRESH])
  var loja: Loja? = null
  @ManyToMany(cascade = [PERSIST, MERGE, REFRESH])
  @JoinTable(name = "users_locais")
  var locais: MutableSet<LocalCD> = HashSet()
  @Length(50)
  var nome: String = ""
  
  @Formula(select = "(login_name = 'ADM' OR login_name = 'YASMINE')")
  var admin: Boolean = false
  
  companion object Find : UsuarioFinder() {
    fun findUsuario(loginName: String?): Usuario? {
      if (loginName.isNullOrBlank()) return null
      return where().loginName.eq(loginName).findOne()
    }
  }
}

