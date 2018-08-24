package br.com.astrosoft.saciEtq.model

import br.com.astrosoft.framework.model.BaseModel
import br.com.astrosoft.saciEtq.model.TipoMov.ENTRADA
import br.com.astrosoft.saciEtq.model.TipoMov.SAIDA
import br.com.astrosoft.saciEtq.model.finder.NotaFinder
import io.ebean.annotation.Index
import io.ebean.annotation.Length
import java.time.LocalDate
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "notas")
@Index(columnNames = ["loja_id", "nota"], unique = true)
class Nota : BaseModel() {
  @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
  var loja: Loja? = null
  @Length(4)
  var rota: String = ""
  @Length(16)
  var nota: String = ""
  @Length(20)
  var tipoNota: String = ""
  var data: LocalDate = LocalDate.now()
  var saldo: Int = 0
  @Length(16)
  var prdno: String = ""
  @Length(8)
  var grade: String = ""
  @Length(40)
  var name: String = ""
  @Length(4)
  var un: String = ""
  @Enumerated(EnumType.STRING)
  var tipoMov: TipoMov = TipoMov.ENTRADA
  @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
  var localCD: LocalCD? = null
  var quantidade : Int = 0
  var cliente : String = ""
  var fornecedor : String = ""
  var impresso : Boolean = false
  
  companion object Find : NotaFinder() {
    fun findNotaEntrada(loja: Loja?, nota: String): Nota? {
      loja ?: return null
      return where().nota.eq(nota)
              .tipoMov.eq(ENTRADA)
              .loja.id.eq(loja.id)
              .findOne()
    }
    
    fun findNotaSaida(loja: Loja?, nota: String): Nota? {
      loja ?: return null
      return where().nota.eq(nota)
              .tipoMov.eq(SAIDA)
              .loja.id.eq(loja.id)
              .findOne()
    }
  }
}

enum class TipoMov(val multiplicador: Int, val descricao: String) {
  ENTRADA(1, "Entrada"),
  SAIDA(-1, "Saida")
}