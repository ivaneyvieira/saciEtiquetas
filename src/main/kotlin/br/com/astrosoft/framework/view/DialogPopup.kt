package br.com.astrosoft.framework.view

import com.github.vok.karibudsl.cssLayout
import com.github.vok.karibudsl.perc
import com.github.vok.karibudsl.verticalLayout
import com.github.vok.karibudsl.w
import com.vaadin.data.BeanValidationBinder
import com.vaadin.shared.Registration
import com.vaadin.ui.Button
import com.vaadin.ui.Component
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.Label
import com.vaadin.ui.UI
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.Window
import com.vaadin.ui.themes.ValoTheme
import kotlin.reflect.KClass

open class DialogPopup<BEAN : Any>(
        caption: String, classBean: KClass<BEAN>
                                  ) : Window(caption) {
  val binder = BeanValidationBinder(classBean.java)
  val form = VerticalLayout().apply {
    setSizeFull()
  }
  
  private val btnOk: Button = Button("Confirma").apply {
    addStyleName(ValoTheme.BUTTON_PRIMARY)
  }
  private val btnCancel = Button("Cancela")
  
  val toolBar = buildToolBar()
  
  init {
    isClosable = false
    isResizable = false
    isModal = true
    addStyleName(ValoTheme.PANEL_WELL)
  }
  
  fun show() {
    center()
    content = VerticalLayout(form, toolBar)
    UI.getCurrent().addWindow(this)
  }
  
  fun initForm(condigForm: (VerticalLayout) -> Unit) {
    condigForm(form)
  }
  
  private fun buildToolBar(): Component {
    val espaco = Label()
    val tool = HorizontalLayout()
    tool.setWidth("100%")
    tool.isSpacing = true
    tool.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR)
    tool.addComponents(espaco, btnOk, btnCancel)
    tool.setExpandRatio(espaco, 1f)
    btnOk.addClickListener({ this.btnOkClick() })
    btnCancel.addClickListener({ this.btnCancelClick() })
    return tool
  }
  
  fun addClickListenerOk(listener: (Button.ClickEvent) -> Unit): Registration {
    return btnOk.addClickListener(listener)
  }
  
  fun addClickListenerCancel(listener: (Button.ClickEvent) -> Unit): Registration {
    return btnCancel.addClickListener(listener)
  }
  
  private fun btnCancelClick() {
    close()
  }
  
  private fun btnOkClick() {
    val status = binder.validate()
    if (!status.hasErrors())
      close()
  }
}

fun VerticalLayout.grupo(caption: String = "", block: VerticalLayout.() -> Unit) {
  cssLayout(caption) {
    w = 100.perc
    addStyleName(ValoTheme.LAYOUT_CARD)
    verticalLayout { this.block() }
  }
}

fun VerticalLayout.row(block: HorizontalLayout.() -> Unit) {
  val horizontalLayout = HorizontalLayout()
  horizontalLayout.w = 100.perc
  horizontalLayout.block()
  horizontalLayout.iterator().forEach { component ->
    component.w = 100.perc
  }
  addComponent(horizontalLayout)
}