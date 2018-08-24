package br.com.astrosoft.saciEtq.viewmodel

import br.com.astrosoft.framework.viewmodel.CrudViewModel
import br.com.astrosoft.framework.viewmodel.EntityVo
import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.saciEtq.model.LocalCD
import br.com.astrosoft.saciEtq.model.Loja
import br.com.astrosoft.saciEtq.model.Nota
import br.com.astrosoft.saciEtq.model.TipoMov
import br.com.astrosoft.saciEtq.model.TipoMov.ENTRADA
import br.com.astrosoft.saciEtq.model.Usuario
import br.com.astrosoft.saciEtq.model.query.QNota
import br.com.astrosoft.saciEtq.view.EtiquetaUI.Companion.loja
import com.vaadin.ui.ComboBox.CaptionFilter
import io.ebean.annotation.Length
import java.time.LocalDate
import javax.persistence.CascadeType
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.ManyToOne

class EntradaViewModel(view: IView, val usuario: Usuario) :
        CrudViewModel<Nota, QNota, EntradaVo>(view, EntradaVo::class) {
  override val query: QNota
    get() = Nota.where()
            .tipoMov.eq(TipoMov.ENTRADA)
            .let { qNota ->
              val locais = usuario.locais.map { it.descricao }
              if (locais.isEmpty() || usuario.admin)
                qNota
              else
                qNota.localCD.descricao.isIn(locais)
            }
  
  override fun Nota.toVO(): EntradaVo {
    val nota = this
    return EntradaVo().apply {
      this.entityVo = nota
      this.loja = nota.loja
      this.rota= nota.rota
      this.nota = nota.nota
      this.tipoNota = nota.tipoNota
      this.data = nota.data
      this.saldo = nota.saldo
      this.prdno = nota.prdno
      this.grade = nota.grade
      this.name = nota.name
      this.un = nota.un
      this.localCD = nota.localCD
      this.quantidade = nota.quantidade
      this.fornecedor = nota.fornecedor
    }
  }
  
  override fun EntradaVo.toModel(): Nota {
    val nota = this
    return Nota().apply {
      this.loja = nota.loja
      this.rota= nota.rota
      this.nota = nota.nota
      this.tipoNota = nota.tipoNota
      this.data = nota.data
      this.saldo = nota.saldo
      this.prdno = nota.prdno
      this.grade = nota.grade
      this.name = nota.name
      this.un = nota.un
      this.tipoMov = ENTRADA
      this.localCD = nota.localCD
      this.quantidade = nota.quantidade
      this.fornecedor = nota.fornecedor
      this.cliente = ""
    }
  }
  
  fun findLojas(loja: Loja?): List<Loja>? {
    loja ?: return Loja.all()
    return listOf(loja)
  }
  
  fun imprimir(entityVo: Nota?): String {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}

class EntradaVo : EntityVo<Nota>() {
  override fun findEntity(): Nota? {
    return Nota.findNotaEntrada(loja, nota)
  }
  
  var usuario : Usuario? = null
  var loja: Loja? = null
  var rota: String = ""
  var nota: String = ""
  var tipoNota: String = ""
  var data: LocalDate = LocalDate.now()
  var saldo: Int = 0
  var prdno: String = ""
  var grade: String = ""
  var name: String = ""
  var un: String = ""
  var localCD: LocalCD? = null
  var quantidade : Int = 0
  var fornecedor : String = ""
  var observacao : String = ""
  var localizacao : String = ""
}