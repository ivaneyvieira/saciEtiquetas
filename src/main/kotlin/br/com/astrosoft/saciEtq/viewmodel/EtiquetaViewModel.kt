package br.com.astrosoft.saciEtq.viewmodel

import br.com.astrosoft.framework.viewmodel.CrudViewModel
import br.com.astrosoft.framework.viewmodel.EViewModel
import br.com.astrosoft.framework.viewmodel.EntityVo
import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.saciEtq.model.Etiqueta
import br.com.astrosoft.saciEtq.model.TipoMov
import br.com.astrosoft.saciEtq.model.query.QEtiqueta

class EtiquetaViewModel(view: IView) : CrudViewModel<Etiqueta, QEtiqueta, EtiquetaVo>(view, EtiquetaVo::class) {
  override val query: QEtiqueta
    get() = Etiqueta.where()
  
  override fun Etiqueta.toVO(): EtiquetaVo {
    val etiqueta = this
    return EtiquetaVo().apply {
      this.entityVo = etiqueta
      this.titulo = etiqueta.titulo
      this.template = etiqueta.template
      this.tipoMov = etiqueta.tipoMov
    }
  }
  
  override fun EtiquetaVo.toModel(): Etiqueta {
    val etiqueta = this
    val model = entityVo ?: Etiqueta()
    return model.apply {
      this.titulo = etiqueta.titulo ?: ""
      this.template = etiqueta.template ?: ""
      this.tipoMov = etiqueta.tipoMov
    }
  }
  
  override fun QEtiqueta.filterString(text: String): QEtiqueta {
    return titulo.contains(text)
  }
}

class EtiquetaVo : EntityVo<Etiqueta>() {
  override fun findEntity(): Etiqueta? {
    return Etiqueta.where()
            .titulo.eq(titulo)
            .tipoMov.eq(tipoMov)
            .findOne()
  }
  
  var titulo: String? = ""
  var template: String? = ""
  var tipoMov : TipoMov? = null
}