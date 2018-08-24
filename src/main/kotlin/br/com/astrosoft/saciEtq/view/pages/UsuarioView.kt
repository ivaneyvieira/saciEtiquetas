package br.com.astrosoft.saciEtq.view.pages

import br.com.astrosoft.framework.view.CrudLayoutView
import br.com.astrosoft.framework.view.bindItensSet
import br.com.astrosoft.framework.view.reloadBinderOnChange
import br.com.astrosoft.framework.view.row
import br.com.astrosoft.saciEtq.model.LocalCD
import br.com.astrosoft.saciEtq.model.Loja
import br.com.astrosoft.saciEtq.view.EtiquetaUI
import br.com.astrosoft.saciEtq.viewmodel.UsuarioCrudVo
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
class UsuarioView() : CrudLayoutView<UsuarioCrudVo, UsuarioViewModel>() {
  val usuario = EtiquetaUI.user!!
  
  override val viewModel: UsuarioViewModel
    get() = UsuarioViewModel(this)
  
  override fun layoutForm(
          formLayout: VerticalLayout, operation: CrudOperation?, binder: Binder<UsuarioCrudVo>, readOnly: Boolean
                         ) {
    formLayout.apply {
      row {
        textField {
          expandRatio = 1f
          caption = "Login Saci"
          bind(binder).bind(UsuarioCrudVo::loginName)
          addValueChangeListener {
            binder.readBean(binder.bean)
          }
        }
        textField {
          expandRatio = 4f
          caption = "Nome"
          isReadOnly = true
          bind(binder).bind(UsuarioCrudVo::nome)
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
          bind(binder).bind(UsuarioCrudVo::loja)
          reloadBinderOnChange(binder)
        }
      }
      row {
        twinColSelect<LocalCD>("Localizações") {
          //value = emptySet()
        
          bindItensSet(binder, UsuarioCrudVo::locaisLoja)
          bind(binder).bind(UsuarioCrudVo::locais)
        }
      }
    }
  }
  
  init {
    form("Usuários") {
      gridCrud(viewModel.crudClass.java) {

        column(UsuarioCrudVo::loginName) {
          expandRatio = 1
          caption = "Usuário"
          setSortProperty("loginName")
        }
        column(UsuarioCrudVo::nome) {
          expandRatio = 5
          caption = "Nome"
        }
        column(UsuarioCrudVo::loja) {
          expandRatio = 1
          caption = "Loja"
          setRenderer({ loja -> loja?.sigla ?: "Todas" }, TextRenderer())
        }
        column(UsuarioCrudVo::localStr) {
          expandRatio = 1
          caption = "Localização"
          setSortProperty("localizacaoes")
        }
      }
    }
  }
}