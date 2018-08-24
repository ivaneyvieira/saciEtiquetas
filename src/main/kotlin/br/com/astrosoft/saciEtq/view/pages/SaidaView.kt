package br.com.astrosoft.saciEtq.view.pages

import br.com.astrosoft.framework.view.CrudLayoutView
import br.com.astrosoft.framework.view.dateFormat
import br.com.astrosoft.framework.view.default
import br.com.astrosoft.framework.view.grupo
import br.com.astrosoft.framework.view.intFormat
import br.com.astrosoft.framework.view.integerField
import br.com.astrosoft.framework.view.reloadBinderOnChange
import br.com.astrosoft.framework.view.row
import br.com.astrosoft.saciEtq.model.Loja
import br.com.astrosoft.saciEtq.view.EtiquetaUI
import br.com.astrosoft.saciEtq.viewmodel.SaidaViewModel
import br.com.astrosoft.saciEtq.viewmodel.SaidaVo
import com.github.vok.karibudsl.AutoView
import com.github.vok.karibudsl.bind
import com.github.vok.karibudsl.comboBox
import com.github.vok.karibudsl.dateField
import com.github.vok.karibudsl.expandRatio
import com.github.vok.karibudsl.textField
import com.github.vok.karibudsl.verticalLayout
import com.vaadin.data.Binder
import com.vaadin.icons.VaadinIcons
import com.vaadin.ui.Button
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.renderers.TextRenderer
import org.vaadin.crudui.crud.CrudOperation
import org.vaadin.crudui.crud.CrudOperation.ADD

@AutoView("saida")
class SaidaView : CrudLayoutView<SaidaVo, SaidaViewModel>() {
  val usuario = EtiquetaUI.user!!
  
  override fun layoutForm(
          formLayout: VerticalLayout, operation: CrudOperation?, binder: Binder<SaidaVo>, readOnly: Boolean
                         ) {
    if (operation == ADD) {
      binder.bean.loja = usuario.loja
      binder.bean.usuario = usuario
    }
    formLayout.apply {
      grupo("Nota fiscal de saída") {
        verticalLayout {
          row {
            textField("Nota fiscal") {
              expandRatio = 2f
              isReadOnly = operation != ADD
              bind(binder).bind(SaidaVo::nota)
              reloadBinderOnChange(binder)
            }
            comboBox<Loja>("Loja") {
              expandRatio = 2f
              default { it.sigla }
              isReadOnly = operation != ADD
              setItems(viewModel.findLojas(usuario.loja))
              bind(binder).asRequired("A loja deve ser informada").bind(SaidaVo::loja)
              reloadBinderOnChange(binder)
            }
            textField("Tipo") {
              expandRatio = 2f
              isReadOnly = true
              bind(binder).bind(SaidaVo::tipoNota)
            }
            dateField("Data") {
              expandRatio = 1f
              isReadOnly = true
              bind(binder).bind(SaidaVo::data)
            }
            textField("Rota") {
              expandRatio = 1f
              isReadOnly = true
              bind(binder).bind(SaidaVo::rota)
            }
          }
          row {
            textField("Observação da nota fiscal") {
              expandRatio = 1f
              bind(binder).bind(SaidaVo::observacao)
            }
          }
        }
      }
      grupo("Produto") {
        verticalLayout {
          row {
            textField("Código") {
              expandRatio = 2f
              isReadOnly = operation != ADD
            
              bind(binder).bind(SaidaVo::prdno)
            }
            textField("Descrição") {
              expandRatio = 5f
              isReadOnly = true
              bind(binder).bind(SaidaVo::name)
            }
            textField("Grade") {
              expandRatio = 1f
              isReadOnly = true
              bind(binder).bind(SaidaVo::grade)
            }
            integerField("Qtd. Saída") {
              expandRatio = 1f
            
              bind(binder)
                      .bind(SaidaVo::quantidade)
              reloadBinderOnChange(binder)
            }
          }
        }
      }
    }
  }
  
  init {
    form("Expedição") {
      gridCrud(viewModel.crudClass.java) {
        column(SaidaVo::nota) {
          caption = "Número NF"
          setSortProperty("nota.numero")
        }
        grid.addComponentColumn { item ->
          val button = Button()
          print {
            val print = viewModel.imprimir(item.nota)
            print
          }.extend(button)
          val impresso = item?.entityVo?.impresso ?: true
          button.isEnabled = impresso == false || usuario.admin
          button.icon = VaadinIcons.PRINT
          button.addClickListener {
            val print = item?.entityVo?.impresso ?: true
            it.button.isEnabled = print == false || usuario.admin
            refreshGrid()
          }
          button
        }
        column(SaidaVo::loja) {
          caption = "Loja NF"
          setRenderer({ loja -> loja?.sigla ?: "" }, TextRenderer())
        }
        column(SaidaVo::data) {
          caption = "Data"
          dateFormat()
          setSortProperty("nota.data", "data", "hora")
        }
        column(SaidaVo::quantidade) {
          caption = "Quantidade"
          intFormat()
        }
        column(SaidaVo::prdno) {
          caption = "Código"
          setSortProperty("prdno")
        }
        column(SaidaVo::name) {
          expandRatio = 1
          caption = "Descrição"
        }
        column(SaidaVo::grade) {
          caption = "Grade"
          setSortProperty("name")
        }
        column(SaidaVo::localizacao) {
          caption = "Localização"
        }
        column(SaidaVo::usuario) {
          caption = "Usuário"
          setRenderer({ it?.loginName ?: "" }, TextRenderer())
          setSortProperty("usuario.loginName")
        }
        column(SaidaVo::rota) {
          caption = "Rota"
        }
        column(SaidaVo::cliente) {
          caption = "Cliente"
          setSortProperty("nota.cliente")
        }
      }
    }
  }
  
  override val viewModel: SaidaViewModel
    get() = SaidaViewModel(this, usuario)
}