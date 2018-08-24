package br.com.astrosoft.saciEtq.viewmodel

import br.com.astrosoft.framework.viewmodel.CrudViewModel
import br.com.astrosoft.framework.viewmodel.EntityVo
import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.saciEtq.model.LocalCD
import br.com.astrosoft.saciEtq.model.Loja
import br.com.astrosoft.saciEtq.model.Nota
import br.com.astrosoft.saciEtq.model.TipoMov
import br.com.astrosoft.saciEtq.model.TipoMov.SAIDA
import br.com.astrosoft.saciEtq.model.Usuario
import br.com.astrosoft.saciEtq.model.query.QNota
import com.vaadin.ui.ComboBox.CaptionFilter
import java.time.LocalDate

class SaidaViewModel(view: IView, val usuario: Usuario) :
        CrudViewModel<Nota, QNota, SaidaVo>(view, SaidaVo::class) {
  override val query: QNota
    get() = Nota.where()
            .tipoMov.eq(TipoMov.SAIDA)
            .let { qNota ->
              val locais = usuario.locais.map { it.descricao }
              if (locais.isEmpty() || usuario.admin)
                qNota
              else
                qNota.localCD.descricao.isIn(locais)
            }
  
  override fun Nota.toVO(): SaidaVo {
    val nota = this
    return SaidaVo().apply {
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
      this.cliente = nota.cliente
    }
  }
  
  override fun SaidaVo.toModel(): Nota {
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
      this.tipoMov = SAIDA
      this.localCD = nota.localCD
      this.quantidade = nota.quantidade
      this.cliente = nota.cliente
      this.fornecedor = ""
    }
  }
  
  fun findLojas(loja: Loja?): List<Loja> {
    loja ?: return Loja.all()
    return listOf(loja)
  }
  
  fun imprimir(nota: String): String {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}

class SaidaVo: EntityVo<Nota>() {
  override fun findEntity(): Nota? {
    return Nota.findNotaEntrada(loja, nota)
  }
  
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
  var cliente : String = ""
  var observacao : String = ""
  var usuario : Usuario? = null
  var localizacao : String = ""
}