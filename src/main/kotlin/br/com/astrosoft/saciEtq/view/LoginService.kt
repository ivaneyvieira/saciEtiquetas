package br.com.astrosoft.saciEtq.view

import br.com.astrosoft.framework.saci.beans.UserSaci
import br.com.astrosoft.framework.saci.saci
import br.com.astrosoft.framework.view.Session
import com.github.vok.karibudsl.alignment
import com.github.vok.karibudsl.button
import com.github.vok.karibudsl.expandRatio
import com.github.vok.karibudsl.fillParent
import com.github.vok.karibudsl.horizontalLayout
import com.github.vok.karibudsl.label
import com.github.vok.karibudsl.onLeftClick
import com.github.vok.karibudsl.panel
import com.github.vok.karibudsl.passwordField
import com.github.vok.karibudsl.px
import com.github.vok.karibudsl.setPrimary
import com.github.vok.karibudsl.textField
import com.github.vok.karibudsl.verticalLayout
import com.github.vok.karibudsl.w
import com.google.gwt.dev.ModuleTabPanel
import com.vaadin.icons.VaadinIcons
import com.vaadin.server.Page
import com.vaadin.server.VaadinSession
import com.vaadin.shared.ui.ContentMode
import com.vaadin.ui.*
import com.vaadin.ui.themes.ValoTheme

object LoginService {
  fun login(user: UserSaci) {
    Session[UserSaci::class] = user
    Page.getCurrent().reload()
  }
  
  val currentUser: UserSaci?
    get() = Session[UserSaci::class]
  
  fun logout() {
    VaadinSession.getCurrent().close()
    Page.getCurrent()?.reload()
  }
}

class LoginForm(private val appTitle: String) : VerticalLayout() {
  private lateinit var username: TextField
  private lateinit var password: TextField
  
  init {
    setSizeFull()
    isResponsive=true
    panel {
      isResponsive=true
      w = 500.px
      alignment = Alignment.MIDDLE_CENTER
      verticalLayout {
        w = fillParent
        horizontalLayout {
          w = fillParent
          label("Login") {
            alignment = Alignment.BOTTOM_LEFT
            addStyleNames(ValoTheme.LABEL_H4, ValoTheme.LABEL_COLORED)
          }
          label(appTitle) {
            contentMode = ContentMode.HTML
            alignment = Alignment.BOTTOM_RIGHT
            styleName = ValoTheme.LABEL_H3
            expandRatio = 1f
          }
        }
        horizontalLayout {
          w = fillParent
          isResponsive=true
          username = textField("Usuário") {
            isResponsive=true
            expandRatio = 1f
            w = fillParent
            icon = VaadinIcons.USER
            styleName = ValoTheme.TEXTFIELD_INLINE_ICON
          }
          password = passwordField("Senha") {
            isResponsive=true
            expandRatio = 1f
            w = fillParent
            icon = VaadinIcons.LOCK
            styleName = ValoTheme.TEXTFIELD_INLINE_ICON
          }
          button("Login") {
            isResponsive=true
            alignment = Alignment.BOTTOM_RIGHT
            setPrimary()
            onLeftClick { login() }
          }
        }
      }
    }
  }
  
  private fun login() {
    val user = saci.findUser(username.value)
    val pass = password.value
    if (user == null || user.senha != pass) {
      Notification.show("Usuário ou senha inválidos. Por favor, tente novamente.")
      LoginService.logout()
    } else
      LoginService.login(user)
  }
}