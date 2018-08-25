package br.com.astrosoft.saciEtq.viewmodel

import br.com.astrosoft.framework.saci.beans.NotaEntradaSaci
import br.com.astrosoft.framework.utils.localDate
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
      this.rota = nota.rota
      this.nota = nota.nota
      this.tipoNota = nota.tipoNota
      this.data = nota.data
      this.saldo = nota.saldo
      this.fornecedor = nota.fornecedor
      this.observacao = nota.observacao
    }
  }
  
  override fun EntradaVo.toModel(): Nota {
    val nota = this
    return Nota().apply {
      this.loja = nota.loja
      this.rota = nota.rota ?: ""
      this.nota = nota.nota ?: ""
      this.tipoNota = nota.tipoNota ?: ""
      this.data = nota.data
      this.saldo = nota.saldo ?: 0
      this.fornecedor = nota.fornecedor ?: ""
      this.impresso = false
      this.observacao = nota.observacao ?: ""
      this.cliente = ""
    }
  }
  
  fun findLojas(loja: Loja?): List<Loja>? {
    loja ?: return Loja.all()
    return listOf(loja)
  }
  
  override fun add(bean: EntradaVo) {
    bean.produtos.forEach { produto ->
      bean.toModel().apply {
        this.prdno = produto.prdno
        this.grade = produto.grade
        this.name = produto.name
        this.un = produto.un
        this.localCD = produto.localCD
        this.quantidade = produto.quantidade
      }.insert()
    }
  }
  
  fun imprimir(entityVo: Nota?): String {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}

class EntradaVo : EntityVo<Nota>() {
  override fun findEntity(): Nota? {
    return Nota.findNotaEntrada(loja, nota)
  }
  
  var usuario: Usuario? = null
  var loja: Loja? = null
    set(value) {
      field = value
      atualiza()
    }
  var rota: String? = ""
  var nota: String? = ""
    set(value) {
      field = value
      atualiza()
    }
  var tipoNota: String? = ""
  var data: LocalDate = LocalDate.now()
  var saldo: Int? = 0
  var fornecedor: String? = ""
  var observacao: String? = ""
  val produtos = ArrayList<ProdutoVO>()
  
  fun notasEntradaSaci(): List<NotaEntradaSaci> {
    //TODO("Falta fazer o filtro de localizacaoes")
    return Nota.findNotaEntradaSaci(nota, loja)
  }
  
  fun atualiza() {
    if (entityVo == null) {
      val notasSaci = notasEntradaSaci()
      notasSaci.firstOrNull()?.let { notaSaci ->
        this.rota = notaSaci.rota ?: ""
        this.tipoNota = notaSaci.tipo ?: ""
        this.data = notaSaci.date?.localDate() ?: LocalDate.now()
        this.saldo = notaSaci.saldo ?: 0
        this.fornecedor = notaSaci.vendName ?: ""
        this.observacao = notaSaci.observacao ?: ""
      }
      produtos.clear()
      val prds = notasSaci.map { notaSaci ->
        ProdutoVO().apply {
          prdno = notaSaci.prdno ?: ""
          grade = notaSaci.grade ?: ""
          name = notaSaci.descricao ?: ""
          un = notaSaci.un ?: ""
          localCD = LocalCD.findLoc(notaSaci.localizacao)
          quantidade = notaSaci.quant ?: 0
        }
      }
      produtos.addAll(prds)
    }
  }
  
  val quantidade
    get() = entityVo?.quantidade
  val prdno
    get() = entityVo?.prdno
  val name
    get() = entityVo?.name
  val grade
    get() = entityVo?.grade
  val localizacao
    get() = entityVo?.localCD?.descricao
}

class ProdutoVO {
  var prdno: String = ""
  var grade: String = ""
  var name: String = ""
  var un: String = ""
  var localCD: LocalCD? = null
  var quantidade: Int = 0
}