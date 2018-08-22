package br.com.astrosoft.framework.view

import com.github.vok.karibudsl.flow.flexLayout
import com.github.vok.karibudsl.flow.verticalLayout
import com.vaadin.flow.component.ClickEvent
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.dialog.Dialog
import com.vaadin.flow.component.html.Label
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.data.binder.BeanValidationBinder
import com.vaadin.flow.shared.Registration
import de.kaesdingeling.hybridmenu.utils.Styles.content
import org.apache.commons.lang3.StringUtils.center
import kotlin.reflect.KClass

open class DialogPopup<BEAN : Any>(
        val caption: String, classBean: KClass<BEAN>
                                  ) : Dialog() {
  val binder = BeanValidationBinder(classBean.java)
  val form = VerticalLayout().apply {
    setSizeFull()
  }
  
  private val btnOk: Button = Button("Confirma")
  
  private val btnCancel = Button("Cancela")
  
  val toolBar = buildToolBar()
  
  init {
    add(Label(caption))
  }
  
  fun show() {
    open()
  }
  
  fun initForm(condigForm: (VerticalLayout) -> Unit) {
    condigForm(form)
  }
  
  private fun buildToolBar(): Component {
    val espaco = Label()
    val tool = HorizontalLayout()
    tool.width = "100%"
    tool.isSpacing = true

    btnOk.addClickListener { this.btnOkClick() }
    btnCancel.addClickListener { this.btnCancelClick() }
    return tool
  }
  
  fun addClickListenerOk(listener: (ClickEvent<Button>) -> Unit): Registration {
    return btnOk.addClickListener(listener)
  }
  
  fun addClickListenerCancel(listener: (ClickEvent<Button>) -> Unit): Registration {
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
  flexLayout {
    
    w = 100.perc
 
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