package br.com.astrosoft.saciEtq.view.pages

import br.com.astrosoft.framework.utils.SystemUtils
import br.com.astrosoft.framework.view.CrudLayoutView
import br.com.astrosoft.framework.view.default
import br.com.astrosoft.framework.view.row
import br.com.astrosoft.saciEtq.model.TipoMov
import br.com.astrosoft.saciEtq.view.EtiquetaUI
import br.com.astrosoft.saciEtq.viewmodel.EtiquetaViewModel
import br.com.astrosoft.saciEtq.viewmodel.EtiquetaVo
import com.github.vok.karibudsl.AutoView
import com.github.vok.karibudsl.alignment
import com.github.vok.karibudsl.bind
import com.github.vok.karibudsl.button
import com.github.vok.karibudsl.comboBox
import com.github.vok.karibudsl.expandRatio
import com.github.vok.karibudsl.h
import com.github.vok.karibudsl.px
import com.github.vok.karibudsl.textArea
import com.github.vok.karibudsl.textField
import com.github.vok.karibudsl.w
import com.vaadin.data.Binder
import com.vaadin.icons.VaadinIcons
import com.vaadin.ui.Alignment
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.renderers.TextRenderer
import org.vaadin.crudui.crud.CrudOperation

@AutoView
class EtiquetaView : CrudLayoutView<EtiquetaVo, EtiquetaViewModel>() {
  override fun layoutForm(
          formLayout: VerticalLayout,
          operation: CrudOperation?,
          binder: Binder<EtiquetaVo>,
          readOnly: Boolean
                         ) {
    formLayout.apply {
      w = 600.px
      h = 600.px
      row {
        textField("Título") {
          expandRatio = 4f
          bind(binder).bind(EtiquetaVo::titulo)
        }
        comboBox<TipoMov>("Tipo") {
          expandRatio = 1f
          default { it.descricao }
          setItems(TipoMov.values().toList())
          bind(binder).bind(EtiquetaVo::tipoMov)
        }
        button("Ajuda") {
          alignment = Alignment.BOTTOM_RIGHT
          expandRatio = 1f
          icon = VaadinIcons.BOOK
          addClickListener {
            showInfo(SystemUtils.readFile("/html/variaveis.html") ?: "")
          }
        }
      }
      row {
        textArea("Template") {
          h = 400.px
          expandRatio = 1f
          bind(binder).bind(EtiquetaVo::template)
        }
      }
    }
  }
  
  init {
    form("Etiquetas") {
      gridCrud(viewModel.crudClass.java) {
        setDeleteOperationVisible(EtiquetaUI.user?.admin ?: false)
        column(EtiquetaVo::titulo) {
          expandRatio = 1
          caption = "Título"
          setSortProperty("titulo")
        }
        column(EtiquetaVo::tipoMov) {
          setRenderer({ it?.descricao ?: "" }, TextRenderer())
          caption = "Tipo"
          setSortProperty("tipoMov")
        }
      }
    }
  }
  
  override val viewModel: EtiquetaViewModel
    get() = EtiquetaViewModel(this)
}