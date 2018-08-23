package br.com.astrosoft.framework.view

import com.vaadin.ui.Component
import de.steinwedel.messagebox.ButtonOption
import de.steinwedel.messagebox.MessageBox

object MessageDialog {
  fun info(caption: String = "Informação", message: String) {
    MessageBox.createInfo()
            .withCaption(caption)
            .withHtmlMessage(message)
            .withCloseButton(ButtonOption.caption("Fechar"))
            .open()
  }
  
 
  fun warning(caption: String = "Aviso", message: String) {
    MessageBox.createWarning()
            .withCaption(caption)
            .withHtmlMessage(message)
            .withCloseButton(ButtonOption.caption("Fechar"))
            .open()
  }
  
  fun error(caption: String = "Erro", message: String) {
    MessageBox.createError()
            .withCaption(caption)
            .withHtmlMessage(message)
            .withCloseButton(ButtonOption.caption("Fechar"))
            .open()
  }
  
  fun question(
          caption: String = "Questão", message: String,
          execYes: () -> Unit = {}, execNo: () -> Unit = {}
              ) {
    MessageBox.createQuestion()
            .withCaption(caption)
            .withHtmlMessage(message)
            .withYesButton(execYes, arrayOf(ButtonOption.caption("Sim")))
            .withNoButton(execNo, arrayOf(ButtonOption.caption("Não")))
            .open()
  }
  
  fun question(
          caption: String = "Questão", message: Component,
          execYes: (Component) -> Unit = {}, execNo: (Component) -> Unit = {}
              ) {
    MessageBox.createQuestion()
            .withCaption(caption)
            .withMessage(message)
            .withYesButton({ execYes(message) }, arrayOf(ButtonOption.caption("Sim")))
            .withNoButton({ execNo(message) }, arrayOf(ButtonOption.caption("Não")))
            .open()
  }
}