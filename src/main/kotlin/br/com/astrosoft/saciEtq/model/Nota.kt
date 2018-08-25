package br.com.astrosoft.saciEtq.model

import br.com.astrosoft.framework.model.BaseModel
import br.com.astrosoft.framework.saci.beans.NotaEntradaSaci
import br.com.astrosoft.framework.saci.beans.NotaSaidaSaci
import br.com.astrosoft.framework.saci.saci
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
  @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
  var usuario: Usuario? = null
  var quantidade: Int = 0
  var cliente: String = ""
  var fornecedor: String = ""
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
    
    fun findNotaEntradaSaci(numeroNF: String?, lojaNF: Loja?): List<NotaEntradaSaci> {
      numeroNF ?: return emptyList()
      lojaNF ?: return emptyList()
      val numero = numeroNF.split("/").getOrNull(0) ?: return emptyList()
      val serie = numeroNF.split("/").getOrNull(1) ?: ""
      return saci.findNotaEntrada(lojaNF.numero, numero, serie)
    }
    
    fun findNotaSaidaSaci(numeroNF: String?, lojaNF: Loja?): List<NotaSaidaSaci> {
      numeroNF ?: return emptyList()
      lojaNF ?: return emptyList()
      val numero = numeroNF.split("/").getOrNull(0) ?: return emptyList()
      val serie = numeroNF.split("/").getOrNull(1) ?: ""
      return saci.findNotaSaida(lojaNF.numero, numero, serie)
    }
  }
}

enum class TipoMov(val multiplicador: Int, val descricao: String) {
  ENTRADA(1, "Entrada"),
  SAIDA(-1, "Saida")
}