package br.com.astrosoft.saciEtq.model

import br.com.astrosoft.saciEtq.model.finder.LojaFinder
import br.com.astrosoft.framework.model.BaseModel
import io.ebean.annotation.Index
import io.ebean.annotation.Length
import javax.persistence.CascadeType.MERGE
import javax.persistence.CascadeType.PERSIST
import javax.persistence.CascadeType.REFRESH
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "lojas")
class Loja : BaseModel() {

  companion object Find : LojaFinder()
  @Index(unique = true)
  var numero: Int = 0
  @Length(2)
  var sigla: String =""
  @OneToMany(mappedBy = "loja", cascade = [PERSIST, MERGE, REFRESH])
  val usuarios: List<Usuario>? = null
  @OneToMany(mappedBy = "loja", cascade = [PERSIST, MERGE, REFRESH])
  val localis: List<Local>? = null
}
