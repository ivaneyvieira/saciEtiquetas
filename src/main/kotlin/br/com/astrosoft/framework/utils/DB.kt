package br.com.astrosoft.framework.utils

import java.io.FileInputStream
import java.util.*

class DB(banco: String) {
  
  private val prop = properties()
  
  val driver = prop?.getProperty("$banco.driver") ?: ""
  val url = prop?.getProperty("$banco.url") ?: ""
  val username = prop?.getProperty("$banco.username") ?: ""
  val password = prop?.getProperty("$banco.password") ?: ""
  val sqldir = prop?.getProperty("$banco.sqldir") ?: ""
  
  companion object {
    private const val propertieFile = "/etc/engecopi/db.conf"
    
    private fun properties(): Properties? {
      val properties = Properties()
      val configFile = FileInputStream(propertieFile)
      properties.load(configFile)
      return properties
    }
  }
}