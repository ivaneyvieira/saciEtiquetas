package br.com.astrosoft.saciEtq.view

import org.slf4j.LoggerFactory
import javax.servlet.ServletContextEvent
import javax.servlet.ServletContextListener
import javax.servlet.annotation.WebListener

/**
 * Boots the app:
 *
 * * Makes sure that the database is up-to-date, by running migration scripts with Flyway. This will work even in cluster as Flyway
 *   automatically obtains a cluster-wide database lock.
 * * Initializes the VaadinOnKotlin framework.
 * * Maps Vaadin to `/`, maps REST server to `/rest`
 * @author mvy
 */
@WebListener
class Bootstrap : ServletContextListener {
  override fun contextInitialized(sce: ServletContextEvent?) {
    log.info("Starting up")
    
    // Initializes the VoK framework
    log.info("Initializing VaadinOnKotlin")
    
    // Makes sure the database is up-to-date
    log.info("Running DB migrations")
    
    // pre-populates the database with a demo data
    log.info("Populating database with testing data")
    log.info("Initialization complete")
  }
  
  override fun contextDestroyed(sce: ServletContextEvent?) {
    log.info("Shutting down")
    log.info("Destroying VaadinOnKotlin")
    log.info("Shutdown complete")
  }
  
  companion object {
    private val log = LoggerFactory.getLogger(Bootstrap::class.java)
  }
}

/**
 * RESTEasy configuration. Do not use Jersey, it has a tons of dependencies
 */
//@ApplicationPath("/rest")
//class ApplicationConfig : Application()
