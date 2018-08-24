package br.com.astrosoft.saciEtq.model

import br.com.astrosoft.framework.model.BaseModel
import br.com.astrosoft.saciEtq.model.finder.LocalCDFinder
import io.ebean.annotation.Formula
import io.ebean.annotation.Length
import javax.persistence.CascadeType.MERGE
import javax.persistence.CascadeType.PERSIST
import javax.persistence.CascadeType.REFRESH
import javax.persistence.Entity
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "locaisCD")
class LocalCD : BaseModel() {

  companion object Find : LocalCDFinder()
  @Length(30)
  var descricao: String = ""
  @Formula(select = "SUBSTRING_INDEX(IFNULL(descricao, ''), '.', 1)")
  var abreviada: String =""
  
  @ManyToMany(mappedBy = "locais", cascade = [PERSIST, MERGE, REFRESH])
  var users: List<Usuario> = emptyList()
}
