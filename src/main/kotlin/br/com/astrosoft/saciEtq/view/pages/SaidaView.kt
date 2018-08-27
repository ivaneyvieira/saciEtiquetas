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
import br.com.astrosoft.saciEtq.viewmodel.EntradaVo
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
class SaidaView : NotaView<SaidaVo, SaidaViewModel>() {
  override fun layoutForm(
          formLayout: VerticalLayout, operation: CrudOperation?, binder: Binder<SaidaVo>, readOnly: Boolean
                         ) {
    if (operation == ADD) {
      binder.bean.loja = usuario.loja
      binder.bean.usuario = usuario
    }
    formLayout.apply {
      grupo("Nota fiscal de Saída") {
        row {
          textField("Nota Fiscal") {
            expandRatio = 2f
            isReadOnly = operation != ADD
            bind(binder).bind(SaidaVo::nota)
            reloadBinderOnChange(binder)
          }
          comboBox<Loja>("Loja") {
            expandRatio = 2f
            isReadOnly = operation != ADD
            default { it.sigla }
          
            setItems(viewModel.findLojas(usuario.loja))
          
            bind(binder).bind(SaidaVo::loja)
            reloadBinderOnChange(binder)
          }
          textField("Tipo") {
            expandRatio = 2f
            isReadOnly = true
            bind(binder).bind(SaidaVo::tipoNota)
          }
          textField("Rota") {
            expandRatio = 1f
            isReadOnly = true
            bind(binder).bind(SaidaVo::rota)
          }
        }
        row {
          textField("Observação") {
            expandRatio = 2f
            isReadOnly = true
            bind(binder).bind(SaidaVo::observacao)
          }
        }
        row {
          dateField("Data") {
            expandRatio = 1f
            isReadOnly = true
            bind(binder).bind(SaidaVo::data)
          }
          textField("Cliente") {
            expandRatio = 3f
            isReadOnly = true
            bind(binder).bind(SaidaVo::clifor)
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
          setSortProperty("name")
        }
        column(SaidaVo::grade) {
          caption = "Grade"
          setSortProperty("grade")
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
        column(SaidaVo::clifor) {
          caption = "Cliente"
          setSortProperty("nota.cliente")
        }
      }
    }
  }
  
  override val viewModel: SaidaViewModel
    get() = SaidaViewModel(this, usuario)
}