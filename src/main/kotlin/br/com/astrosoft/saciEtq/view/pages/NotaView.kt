package br.com.astrosoft.saciEtq.view.pages

import br.com.astrosoft.framework.view.CrudLayoutView
import br.com.astrosoft.saciEtq.view.EtiquetaUI
import br.com.astrosoft.saciEtq.viewmodel.NotaViewModel
import br.com.astrosoft.saciEtq.viewmodel.NotaVo

abstract class NotaView<VO : NotaVo, VIEWMODEL : NotaViewModel<VO>>: CrudLayoutView<VO, VIEWMODEL>() {
  val usuario = EtiquetaUI.user!!
}