package br.com.astrosoft.framework.model

import io.ebean.annotation.Platform
import io.ebean.dbmigration.DbMigration
import java.io.IOException

object MainDbMigration {
  @Throws(IOException::class)
  @JvmStatic
  fun main(args: Array<String>) {
    // System.setProperty("ddl.migration.generate", "true")
    
    System.setProperty("ddl.migration.name", "migracao")
    //System.setProperty("ddl.migration.version", "V1.12")
    //System.setProperty("ddl.migration.pendingDropsFor", "1.10");
    val migration = DbMigration.create()
    migration.setStrictMode(false)
    migration.setPlatform(Platform.MYSQL)
    //migration.setVersion("1.12")
    System.setProperty("disableTestProperties", "true")
    migration.generateMigration()
    
    // starting EbeanServer triggers the apply of migrations
    // ... when ebean.migration.run=true
    //Transaction.server
    
    System.out.println("done")
  }
}
