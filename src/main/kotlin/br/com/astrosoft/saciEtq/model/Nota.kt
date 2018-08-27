package br.com.astrosoft.saciEtq.model

import br.com.astrosoft.framework.model.BaseModel
import br.com.astrosoft.framework.saci.beans.NotaSaci
import br.com.astrosoft.framework.saci.saci
import br.com.astrosoft.saciEtq.model.TipoMov.ENTRADA
import br.com.astrosoft.saciEtq.model.TipoMov.SAIDA
import br.com.astrosoft.saciEtq.model.finder.NotaFinder
import io.ebean.annotation.Index
import io.ebean.annotation.Length
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.ManyToOne
import javax.persistence.Table
import kotlin.reflect.full.memberProperties

@Entity
@Table(name = "notas")
@Index(columnNames = ["loja_id", "nota", "prdno", "grade"], unique = true)
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
  @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
  var usuario: Usuario? = null
  var quantidade: Int = 0
  @Length(50)
  var clifor: String = ""
  var impresso: Boolean = false
  @Length(100)
  var observacao: String = ""
  
  companion object Find : NotaFinder() {
    fun findNotaEntrada(loja: Loja?, nota: String?): Nota? {
      nota ?: return null
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
    
    fun findNotaEntradaSaci(numeroNF: String?, lojaNF: Loja?): List<NotaSaci> {
      numeroNF ?: return emptyList()
      lojaNF ?: return emptyList()
      val numero = numeroNF.split("/").getOrNull(0) ?: return emptyList()
      val serie = numeroNF.split("/").getOrNull(1) ?: ""
      return saci.findNotaEntrada(lojaNF.numero, numero, serie)
    }
    
    fun findNotaSaidaSaci(numeroNF: String?, lojaNF: Loja?): List<NotaSaci> {
      numeroNF ?: return emptyList()
      lojaNF ?: return emptyList()
      val numero = numeroNF.split("/").getOrNull(0) ?: return emptyList()
      val serie = numeroNF.split("/").getOrNull(1) ?: ""
      return saci.findNotaSaida(lojaNF.numero, numero, serie)
    }
  }
  
  fun printEtiqueta() = NotaPrint(this)
}

enum class TipoMov(val multiplicador: Int, val descricao: String) {
  ENTRADA(1, "Entrada"),
  SAIDA(-1, "Saida")
}

class NotaPrint(nota: Nota) {
  val rota = nota.rota ?: ""
  val nota = nota.nota ?: ""
  val tipoNota = nota.tipoNota
  
  val data = nota.data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) ?: ""
  val sd = nota.saldo
  val quant = nota.quantidade
  val prdno = nota.prdno.trim()
  val grade = nota.grade
  val name = nota.name
  val prdnoGrade = "$prdno${if (grade == "") "" else "-$grade"}"
  val un = nota.un
  val loc = nota.localCD?.descricao ?: ""
  
  fun print(template: String): String {
    return NotaPrint::class.memberProperties.fold(template) { reduce, prop ->
      reduce.replace("[${prop.name}]", "${prop.get(this)}")
    }
  }
}
