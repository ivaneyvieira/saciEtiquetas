package br.com.astrosoft.saciEtq.viewmodel

import br.com.astrosoft.framework.saci.beans.NotaSaci
import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.saciEtq.model.Nota
import br.com.astrosoft.saciEtq.model.TipoMov
import br.com.astrosoft.saciEtq.model.TipoMov.SAIDA
import br.com.astrosoft.saciEtq.model.Usuario

class SaidaViewModel(view: IView, usuario: Usuario) :
        NotaViewModel<SaidaVo>(view, usuario, SaidaVo::class) {
  override val tipoMov: TipoMov
    get() = SAIDA
  
  override fun createNota(): SaidaVo {
    return SaidaVo()
  }
}

class SaidaVo : NotaVo() {
  override fun notasSaci(): List<NotaSaci> {
    return Nota.findNotaSaidaSaci(nota, loja)
  }
  
  override fun findEntity(): Nota? {
    return Nota.findNotaEntrada(loja, nota)
  }
}