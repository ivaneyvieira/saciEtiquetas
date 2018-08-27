package br.com.astrosoft.saciEtq.view.pages

import br.com.astrosoft.framework.view.CrudLayoutView
import br.com.astrosoft.framework.view.bindItens
import br.com.astrosoft.framework.view.dateFormat
import br.com.astrosoft.framework.view.default
import br.com.astrosoft.framework.view.grupo
import br.com.astrosoft.framework.view.intFormat
import br.com.astrosoft.framework.view.integerField
import br.com.astrosoft.framework.view.reloadBinderOnChange
import br.com.astrosoft.framework.view.row
import br.com.astrosoft.saciEtq.model.Loja
import br.com.astrosoft.saciEtq.view.EtiquetaUI
import br.com.astrosoft.saciEtq.viewmodel.EntradaViewModel
import br.com.astrosoft.saciEtq.viewmodel.EntradaVo
import com.github.vok.karibudsl.AutoView
import com.github.vok.karibudsl.bind
import com.github.vok.karibudsl.comboBox
import com.github.vok.karibudsl.dateField
import com.github.vok.karibudsl.expandRatio
import com.github.vok.karibudsl.textField
import com.vaadin.data.Binder
import com.vaadin.icons.VaadinIcons
import com.vaadin.ui.Button
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.renderers.TextRenderer
import org.vaadin.crudui.crud.CrudOperation
import org.vaadin.crudui.crud.CrudOperation.ADD
import org.vaadin.crudui.crud.CrudOperation.UPDATE

@AutoView("")
class EntradaView : NotaView<EntradaVo, EntradaViewModel>() {
  
  
  override fun layoutForm(
          formLayout: VerticalLayout, operation: CrudOperation?, binder: Binder<EntradaVo>, readOnly: Boolean
                         ) {
    if (operation == ADD) {
      binder.bean.loja = usuario.loja
      binder.bean.usuario = usuario
    }
    formLayout.apply {
      grupo("Nota fiscal de entrada") {
        row {
          textField("Nota Fiscal") {
            expandRatio = 2f
            isReadOnly = operation != ADD
            bind(binder).bind(EntradaVo::nota)
            reloadBinderOnChange(binder)
          }
          comboBox<Loja>("Loja") {
            expandRatio = 2f
            isReadOnly = operation != ADD
            default { it.sigla }
            
            setItems(viewModel.findLojas(usuario.loja))
            
            bind(binder).bind(EntradaVo::loja)
            reloadBinderOnChange(binder)
          }
          textField("Tipo") {
            expandRatio = 2f
            isReadOnly = true
            bind(binder).bind(EntradaVo::tipoNota)
          }
          textField("Rota") {
            expandRatio = 1f
            isReadOnly = true
            bind(binder).bind(EntradaVo::rota)
          }
        }
        row {
          textField("Observação") {
            expandRatio = 2f
            isReadOnly = true
            bind(binder).bind(EntradaVo::observacao)
          }
        }
        row {
          dateField("Data") {
            expandRatio = 1f
            isReadOnly = true
            bind(binder).bind(EntradaVo::data)
          }
          textField("Fornecedor") {
            expandRatio = 3f
            isReadOnly = true
            bind(binder).bind(EntradaVo::clifor)
          }
        }
      }
    }
  }
  
  init {
    form("Entrada de produtos") {
      gridCrud(viewModel.crudClass.java) {
        column(EntradaVo::nota) {
          caption = "Número NF"
          setSortProperty("nota")
        }
        grid.addComponentColumn { item ->
          val button = Button()
          
          print {
            val print = viewModel.imprimir(item.entityVo)
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
        column(EntradaVo::loja) {
          caption = "Loja NF"
          setRenderer({ loja -> loja?.sigla ?: "" }, TextRenderer())
          setSortProperty("loja.sigla")
        }
        column(EntradaVo::data) {
          caption = "Data Nota"
          dateFormat()
          
          setSortProperty("data")
        }
        column(EntradaVo::quantidade) {
          caption = "Quantidade"
          intFormat()
          setSortProperty("quantidade")
        }
        column(EntradaVo::prdno) {
          caption = "Código"
          setSortProperty("prdno")
        }
        column(EntradaVo::name) {
          caption = "Descrição"
          expandRatio = 1
          setSortProperty("name")
        }
        column(EntradaVo::grade) {
          caption = "Grade"
          setSortProperty("grade")
        }
        column(EntradaVo::localizacao) {
          caption = "Local"
          setSortProperty("localCD.descricao")
        }
        column(EntradaVo::usuario) {
          caption = "Usuário"
          setRenderer({ it?.loginName ?: "" }, TextRenderer())
          setSortProperty("usuario.loginName")
        }
        column(EntradaVo::rota) {
          caption = "Rota"
        }
        column(EntradaVo::clifor) {
          caption = "Fornecedor"
          setSortProperty("fornecedor")
        }
      }
    }
  }
  
  override val viewModel: EntradaViewModel
    get() = EntradaViewModel(this, usuario)
}