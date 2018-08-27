package br.com.astrosoft.saciEtq.model

import br.com.astrosoft.saciEtq.model.finder.EtiquetaFinder
import br.com.astrosoft.framework.model.BaseModel
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Lob
import javax.persistence.Table
import javax.validation.constraints.Size

@Entity
@Table(name = "etiquetas")
class Etiqueta : BaseModel() {

  companion object Find : EtiquetaFinder()
  @Size(max = 60)
  var titulo: String = ""
  @Enumerated(EnumType.STRING)
  var tipoMov: TipoMov? = null
  @Lob
  var template: String = ""
}
