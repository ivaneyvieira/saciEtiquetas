
package br.com.astrosoft.saciEtq.view

import br.com.astrosoft.saciEtq.view.pages.EntradaPage
import br.com.astrosoft.saciEtq.view.pages.SaidaPage
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.Tag
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.html.Image
import com.vaadin.flow.component.html.Label
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.page.BodySize
import com.vaadin.flow.component.page.Push
import com.vaadin.flow.component.page.Viewport
import com.vaadin.flow.server.VaadinSession
import com.vaadin.flow.theme.Theme
import com.vaadin.flow.theme.lumo.Lumo
import de.kaesdingeling.hybridmenu.HybridMenu
import de.kaesdingeling.hybridmenu.components.HMButton
import de.kaesdingeling.hybridmenu.components.HMLabel
import de.kaesdingeling.hybridmenu.data.MenuConfig
import de.kaesdingeling.hybridmenu.design.DesignItem
import com.vaadin.flow.router.ErrorParameter
import com.vaadin.flow.router.BeforeEnterEvent
import com.vaadin.flow.router.HasErrorParameter
import com.vaadin.flow.server.DefaultErrorHandler
import javax.servlet.http.HttpServletResponse

@BodySize(width = "100vw", height = "100vh")
@Push
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
@Theme(Lumo::class)
class MainLayout : HybridMenu() {
  override fun init(vaadinSession: VaadinSession, ui: UI): Boolean {
    withConfig(MenuConfig.get().withDesignItem(DesignItem.getWhiteDesign()).withBreadcrumbs(true))
    
    leftMenu.alignItems = FlexComponent.Alignment.END
    
    leftMenu.add(HMLabel.get()
                         .withCaption("<b>Etiquetas</b> Saci")
                         .withIcon(Image("./frontend/logo.png", "Logo")))
    
    leftMenu.add(HMButton.get()
                         .withCaption("Entrada")
                         .withIcon(VaadinIcon.INBOX)
                         
                         .withNavigateTo(EntradaPage::class.java))
    
    leftMenu.add(HMButton.get()
                         .withCaption("Saída")
                         .withIcon(VaadinIcon.OUTBOX)
                         .withNavigateTo(SaidaPage::class.java))
    
    vaadinSession.errorHandler = DefaultErrorHandler()
    
    return true
  }
}

@Tag(Tag.DIV)
class FaultyBlogPostHandler : Component(), HasErrorParameter<IllegalArgumentException> {
  
  override fun setErrorParameter(
          event: BeforeEnterEvent,
          parameter: ErrorParameter<IllegalArgumentException>
                                ): Int {
    val message = Label(parameter.customMessage)
    element.appendChild(message.element)
    
    return HttpServletResponse.SC_NOT_FOUND
  }
}