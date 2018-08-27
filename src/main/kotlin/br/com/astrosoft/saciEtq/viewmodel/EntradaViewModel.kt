package br.com.astrosoft.saciEtq.viewmodel

import br.com.astrosoft.framework.saci.beans.NotaSaci
import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.saciEtq.model.Loja
import br.com.astrosoft.saciEtq.model.Nota
import br.com.astrosoft.saciEtq.model.TipoMov
import br.com.astrosoft.saciEtq.model.TipoMov.ENTRADA
import br.com.astrosoft.saciEtq.model.Usuario
import br.com.astrosoft.saciEtq.model.query.QNota

class EntradaViewModel(view: IView,  usuario: Usuario) :
        NotaViewModel<EntradaVo>(view, usuario, EntradaVo::class) {
  override val tipoMov: TipoMov
    get() = ENTRADA
  
  override fun createNota(): EntradaVo {
    return EntradaVo()
  }
}

class EntradaVo : NotaVo() {
  override fun notasSaci(): List<NotaSaci> {
    return Nota.findNotaEntradaSaci(nota, loja)
  }
  
  override fun findEntity(): Nota? {
    return Nota.findNotaEntrada(loja, nota)
  }
}

