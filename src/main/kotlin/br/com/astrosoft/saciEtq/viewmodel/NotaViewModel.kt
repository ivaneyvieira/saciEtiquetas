package br.com.astrosoft.saciEtq.viewmodel

import br.com.astrosoft.framework.saci.beans.NotaSaci
import br.com.astrosoft.framework.utils.localDate
import br.com.astrosoft.framework.viewmodel.CrudViewModel
import br.com.astrosoft.framework.viewmodel.EntityVo
import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.saciEtq.model.LocalCD
import br.com.astrosoft.saciEtq.model.Loja
import br.com.astrosoft.saciEtq.model.Nota
import br.com.astrosoft.saciEtq.model.TipoMov
import br.com.astrosoft.saciEtq.model.Usuario
import br.com.astrosoft.saciEtq.model.query.QNota
import java.time.LocalDate
import kotlin.reflect.KClass

abstract class NotaViewModel<NOTA : NotaVo>(
        view: IView, val usuario: Usuario,
        classVo: KClass<NOTA>
                                           ) :
        CrudViewModel<Nota, QNota, NOTA>(view, classVo) {
  
  abstract val tipoMov: TipoMov
  
  override val query: QNota
    get() = Nota.where()
            .tipoMov.eq(tipoMov)
            .let { qNota ->
              val locais = usuario.locais.map { it.descricao }
              if (locais.isEmpty() || usuario.admin)
                qNota
              else
                qNota.localCD.descricao.isIn(locais)
            }
  
  abstract fun createNota(): NOTA
  
  override fun Nota.toVO(): NOTA {
    val nota = this
    return createNota().apply {
      this.usuario = this@NotaViewModel.usuario
      this.entityVo = nota
      this.loja = nota.loja
      this.rota = nota.rota
      this.nota = nota.nota
      this.tipoNota = nota.tipoNota
      this.data = nota.data
      this.saldo = nota.saldo
      this.clifor = nota.clifor
      this.observacao = nota.observacao
    }
  }
  
  override fun NOTA.toModel(): Nota {
    val nota = this
    return Nota().apply {
      this.loja = nota.loja
      this.rota = nota.rota ?: ""
      this.nota = nota.nota ?: ""
      this.tipoNota = nota.tipoNota ?: ""
      this.data = nota.data
      this.saldo = nota.saldo ?: 0
      this.clifor = nota.clifor ?: ""
      this.observacao = nota.observacao ?: ""
      this.tipoMov =  this@NotaViewModel.tipoMov
    }
  }
  
  fun findLojas(loja: Loja?): List<Loja>? {
    loja ?: return Loja.all()
    return listOf(loja)
  }
  
  override fun add(bean: NOTA) {
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
  
  fun imprimir(nota: Nota?): String {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}

abstract class NotaVo : EntityVo<Nota>() {
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
  var clifor: String? = ""
  var observacao: String? = ""
  val produtos = ArrayList<ProdutoVO>()
  
  fun notasSaci(): List<NotaSaci> {
    //TODO("Falta fazer o filtro de localizacaoes")
    return Nota.findNotaEntradaSaci(nota, loja)
  }
  
  fun atualiza() {
    if (entityVo == null) {
      val notasSaci = notasSaci()
      notasSaci.firstOrNull()?.let { notaSaci ->
        this.rota = notaSaci.rota ?: ""
        this.tipoNota = notaSaci.tipo ?: ""
        this.data = notaSaci.date?.localDate() ?: LocalDate.now()
        this.saldo = notaSaci.saldo ?: 0
        this.clifor = notaSaci.clifor ?: ""
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