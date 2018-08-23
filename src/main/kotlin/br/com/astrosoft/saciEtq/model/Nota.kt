package br.com.astrosoft.saciEtq.model

import br.com.astrosoft.saciEtq.model.finder.NotaFinder
import br.com.astrosoft.framework.model.BaseModel
import io.ebean.annotation.Length
import java.time.LocalDate
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "notas")
class Nota: BaseModel() {
  @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
  var loja : Loja? = null
  @Length(4)
  var rota : String = ""
  @Length(16)
  var nota : String = ""
  @Length(20)
  var tipoNota : String = ""
  
  var data : LocalDate = LocalDate.now()
  var saldo : Int = 0
  @Length(16)
  var prdno : String = ""
  @Length(8)
  var grade : String = ""
  @Length(40)
  var name : String = ""
  @Length(4)
  var un : String=""
  @Length(30)
  var loc : String=""
}
