package br.com.astrosoft.saciEtq.model

import br.com.astrosoft.saciEtq.model.finder.LocalFinder
import br.com.astrosoft.framework.model.BaseModel
import io.ebean.annotation.Length
import javax.persistence.CascadeType.MERGE
import javax.persistence.CascadeType.PERSIST
import javax.persistence.CascadeType.REFRESH
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "locais")
class Local : BaseModel() {
  @ManyToOne(cascade = [PERSIST, MERGE, REFRESH])
  var loja: Loja? = null
  @ManyToOne(cascade = [PERSIST, MERGE, REFRESH])
  var localCD: LocalCD? = null
  
  companion object Find : LocalFinder()
}
