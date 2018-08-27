package br.com.astrosoft.saciEtq.view.pages

import br.com.astrosoft.framework.view.CrudLayoutView
import br.com.astrosoft.framework.view.bindItensSet
import br.com.astrosoft.framework.view.reloadBinderOnChange
import br.com.astrosoft.framework.view.row
import br.com.astrosoft.saciEtq.model.Loja
import br.com.astrosoft.saciEtq.view.EtiquetaUI
import br.com.astrosoft.saciEtq.viewmodel.UsuarioVo
import br.com.astrosoft.saciEtq.viewmodel.UsuarioViewModel
import com.github.vok.karibudsl.AutoView
import com.github.vok.karibudsl.bind
import com.github.vok.karibudsl.comboBox
import com.github.vok.karibudsl.expandRatio
import com.github.vok.karibudsl.textField
import com.github.vok.karibudsl.twinColSelect
import com.vaadin.data.Binder
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.renderers.TextRenderer
import org.vaadin.crudui.crud.CrudOperation

@AutoView("usuario")
class UsuarioView() : CrudLayoutView<UsuarioVo, UsuarioViewModel>() {
  val usuario = EtiquetaUI.user!!
  
  override val viewModel: UsuarioViewModel
    get() = UsuarioViewModel(this)
  
  override fun layoutForm(
          formLayout: VerticalLayout, operation: CrudOperation?, binder: Binder<UsuarioVo>, readOnly: Boolean
                         ) {
    formLayout.apply {
      row {
        textField {
          expandRatio = 1f
          caption = "Login Saci"
          bind(binder).bind(UsuarioVo::loginName)
          addValueChangeListener {
            binder.readBean(binder.bean)
          }
        }
        textField {
          expandRatio = 4f
          caption = "Nome"
          isReadOnly = true
          bind(binder).bind(UsuarioVo::nome)
        }
      }
      row {
        comboBox<Loja> {
          expandRatio = 1f
          caption = "Loja"
          isEmptySelectionAllowed = true
          isTextInputAllowed = false
          this.emptySelectionCaption = "Todas"
          setItems(viewModel.lojas)
          setItemCaptionGenerator { it.sigla }
          bind(binder).bind(UsuarioVo::loja)
          reloadBinderOnChange(binder)
        }
      }
      row {
        twinColSelect<String>("Localizações") {
          bindItensSet(binder, UsuarioVo::locaisLoja)
          bind(binder).bind(UsuarioVo::locais)
        }
      }
    }
  }
  
  init {
    form("Usuários") {
      gridCrud(viewModel.crudClass.java) {

        column(UsuarioVo::loginName) {
          expandRatio = 1
          caption = "Usuário"
          setSortProperty("loginName")
        }
        column(UsuarioVo::nome) {
          expandRatio = 5
          caption = "Nome"
        }
        column(UsuarioVo::loja) {
          expandRatio = 1
          caption = "Loja"
          setRenderer({ loja -> loja?.sigla ?: "Todas" }, TextRenderer())
        }
        column(UsuarioVo::localStr) {
          expandRatio = 1
          caption = "Localização"
          setSortProperty("localizacaoes")
        }
      }
    }
  }
}