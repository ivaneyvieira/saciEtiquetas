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

class EntradaView : CrudLayoutView<EntradaVo, EntradaViewModel>() {
  val usuario = EtiquetaUI.user!!
  
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
            bind(binder).bind(EntradaVo::fornecedor)
          }
        }
        
      }
      
      grupo("Produto") {
        row {
          textField("Código") {
            expandRatio = 2f
            isReadOnly = operation != ADD
            bind(binder).bind(EntradaVo::prdno)
            reloadBinderOnChange(binder)
          }
          textField("Descrição") {
            expandRatio = 5f
            isReadOnly = true
            bind(binder).bind(EntradaVo::name)
          }
          textField("Grade") {
            expandRatio = 1f
            isReadOnly = true
            bind(binder).bind(EntradaVo::grade)
          }
          integerField("Qtd Entrada") {
            expandRatio = 1f
            isReadOnly = true
            this.bind(binder)
                    .bind(EntradaVo::quantidade)
          }
        }
      }
    }
  }
  
  init {
    form("Entrada de produtos") {
      gridCrud(viewModel.crudClass.java) {
        column(EntradaVo::nota) {
          //isSortable = true
          caption = "Número NF"
          setSortProperty("nota.numero")
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
        }
        column(EntradaVo::data) {
          caption = "Data Nota"
          dateFormat()
          
          setSortProperty("nota.data", "data", "hora")
        }
        column(EntradaVo::quantidade) {
          caption = "Quantidade"
          intFormat()
        }
        column(EntradaVo::prdno) {
          caption = "Código"
          setSortProperty("produto.codigo")
        }
        column(EntradaVo::name) {
          caption = "Descrição"
        }
        column(EntradaVo::grade) {
          caption = "Grade"
          setSortProperty("produto.grade")
        }
        column(EntradaVo::localizacao) {
          caption = "Local"
        }
        column(EntradaVo::usuario) {
          caption = "Usuário"
          setRenderer({ it?.loginName ?: "" }, TextRenderer())
          setSortProperty("usuario.loginName")
        }
        column(EntradaVo::rota) {
          caption = "Rota"
        }
        column(EntradaVo::fornecedor) {
          caption = "Fornecedor"
          setSortProperty("nota.fornecedor")
        }
      }
    }
  }
  
  override val viewModel: EntradaViewModel
    get() = EntradaViewModel(this, usuario)
}