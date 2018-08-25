package br.com.astrosoft.saciEtq.model

import br.com.astrosoft.framework.model.BaseModel
import br.com.astrosoft.saciEtq.model.finder.LocalCDFinder
import io.ebean.annotation.Formula
import io.ebean.annotation.Index
import io.ebean.annotation.Length
import javax.persistence.CascadeType.MERGE
import javax.persistence.CascadeType.PERSIST
import javax.persistence.CascadeType.REFRESH
import javax.persistence.Entity
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "locaiscd")
class LocalCD : BaseModel() {
  
  companion object Find : LocalCDFinder() {
    fun findLoc(localizacao: String?): LocalCD? {
      return where().or()
              .descricao.eq(localizacao)
              .abreviada.eq(localizacao)
              .endOr()
              .findOne()
    }
  }
  
  @Length(30)
  @Index(unique = true)
  var descricao: String = ""
  @Formula(select = "SUBSTRING_INDEX(IFNULL(descricao, ''), '.', 1)")
  var abreviada: String = ""
  
  @ManyToMany(mappedBy = "locais", cascade = [PERSIST, MERGE, REFRESH])
  var users: List<Usuario> = emptyList()
}
