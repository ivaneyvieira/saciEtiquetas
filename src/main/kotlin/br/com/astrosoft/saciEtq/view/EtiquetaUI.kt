package br.com.astrosoft.saciEtq.view

import br.com.astrosoft.framework.model.DB
import br.com.astrosoft.framework.utils.SystemUtils
import br.com.astrosoft.saciEtq.model.Loja
import br.com.astrosoft.saciEtq.model.Usuario
import br.com.astrosoft.saciEtq.view.pages.EntradaView
import br.com.astrosoft.saciEtq.view.pages.SaidaView
import br.com.astrosoft.saciEtq.view.pages.UsuarioView

import com.github.vok.karibudsl.autoViewProvider
import com.github.vok.karibudsl.onLeftClick
import com.github.vok.karibudsl.valoMenu
import com.vaadin.annotations.JavaScript
import com.vaadin.annotations.Theme
import com.vaadin.annotations.Title
import com.vaadin.annotations.VaadinServletConfiguration
import com.vaadin.annotations.Viewport
import com.vaadin.icons.VaadinIcons
import com.vaadin.navigator.Navigator
import com.vaadin.navigator.PushStateNavigation
import com.vaadin.navigator.ViewDisplay
import com.vaadin.server.Page
import com.vaadin.server.VaadinRequest
import com.vaadin.server.VaadinService
import com.vaadin.server.VaadinServlet
import com.vaadin.shared.Position
import com.vaadin.ui.Notification
import com.vaadin.ui.Notification.Type.ERROR_MESSAGE
import com.vaadin.ui.UI
import com.vaadin.ui.themes.ValoTheme
import org.slf4j.LoggerFactory
import org.slf4j.bridge.SLF4JBridgeHandler
import javax.servlet.ServletContextEvent
import javax.servlet.ServletContextListener
import javax.servlet.annotation.WebListener
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie

private val log = LoggerFactory.getLogger(EtiquetaUI::class.java)

@Theme("mytheme")
@Title("Controle de estoque")
@Viewport("width=device-width, initial-scale=1.0")
@JavaScript("https://code.jquery.com/jquery-2.1.4.min.js",
            "https://code.responsivevoice.org/responsivevoice.js")
@PushStateNavigation
class EtiquetaUI : UI() {
  val title = "<h3>Estoque <strong>Engecopi</strong></h3>"
  val versao = SystemUtils.readFile("/versao.txt")
  
  override fun init(request: VaadinRequest?) {
    val user = user
    if (user == null) {
      content = LoginForm("$title <p align=\"right\">$versao</p>")
    } else {
      val content = valoMenu {
        this.appTitle = title
        
        section("Usuário: " + user.loginName)
        menuButton("Sair", icon = VaadinIcons.OUT) {
          onLeftClick {
            LoginService.logout()
          }
        }
        section("Movimentação")
        menuButton("Entrada", VaadinIcons.INBOX, view = EntradaView::class.java)
        menuButton("Saída", VaadinIcons.OUTBOX, view = SaidaView::class.java)
        if (user.admin) {
          menuButton("Usuários", VaadinIcons.USER, view = UsuarioView::class.java)
        }
      }
      
      // Read more about navigators here: https://github.com/mvysny/karibu-dsl
      navigator = Navigator(this, content as ViewDisplay)
      navigator.addProvider(autoViewProvider)
      
      setErrorHandler { e ->
        log.error("Erro não identificado ${e.throwable}", e.throwable)
        // when the exception occurs, show a nice notification
        Notification("Oops", "\n" +
                             "Ocorreu um erro e lamentamos muito isso. Já está trabalhando na correção!",
                     ERROR_MESSAGE)
                .apply {
                  styleName += " " + ValoTheme.NOTIFICATION_CLOSABLE
                  position = Position.TOP_CENTER
                  show(Page.getCurrent())
                }
      }
    }
  }
  
  companion object {
    val estoqueUI
      get() = UI.getCurrent() as? EtiquetaUI
    val user: Usuario?
      get() {
        val user = LoginService.currentUser ?: return null
        val login = user.login ?: return null
        return Usuario.findUsuario(login)
      }
    val loja: Loja?
      get() = user?.loja
  }
}

@WebListener
class Bootstrap : ServletContextListener {
  override fun contextDestroyed(sce: ServletContextEvent?) {
    log.info("Shutting down");
    log.info("Destroying VaadinOnKotlin")
    log.info("Shutdown complete")
  }
  
  override fun contextInitialized(sce: ServletContextEvent?) {
    log.info("Starting up")
  }
}

@WebServlet(urlPatterns = ["/*"], name = "MyUIServlet", asyncSupported = true)
@VaadinServletConfiguration(ui = EtiquetaUI::class, productionMode = false)
class MyUIServlet : VaadinServlet() {
  companion object {
    init {
      // Vaadin logs into java.util.logging. Redirect that, so that all logging goes through slf4j.
      SLF4JBridgeHandler.removeHandlersForRootLogger()
      SLF4JBridgeHandler.install()
      DB.initDB()
    }
  }
}



fun setCookie(nome: String, valor: String) {
  // Create a new cookie
  val myCookie = Cookie(nome, valor)
  
  // Make cookie expire in 2 minutes
  myCookie.maxAge = 60 * 60 * 24 * 5
  // Set the cookie path.
  myCookie.path = VaadinService.getCurrentRequest().contextPath
  
  // Save cookie
  VaadinService.getCurrentResponse().addCookie(myCookie)
}

fun UI.getCokies(name: String): String? {
  val cookie = VaadinService.getCurrentRequest().cookies.toList().firstOrNull { it.name == name }
  cookie?.let {
    setCookie(it.name, it.value)
  }
  return cookie?.value
}