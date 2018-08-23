package br.com.astrosoft.framework.model

import io.ebean.Ebean
import javax.persistence.RollbackException
import io.ebean.config.ServerConfig
import io.ebean.EbeanServerFactory
import io.ebean.Query
import io.ebean.SqlQuery
import io.ebean.SqlUpdate

object Transaction {
  
  private fun inTransaction(): Boolean {
    return Ebean.currentTransaction() != null
  }
  
  @Throws(Exception::class)
  fun execTransacao(lambda: () -> Unit) {
    try {
      Ebean.beginTransaction()
      lambda()
      Ebean.commitTransaction()
    } catch (e: RollbackException) {
      Ebean.rollbackTransaction()
      throw e
    } catch (exception: Exception) {
      Ebean.rollbackTransaction()
      throw exception
    } catch (error: Error) {
      Ebean.rollbackTransaction()
      throw Exception(error)
    } finally {
      Ebean.endTransaction()
    }
  }
  
  fun commit() {
    if (inTransaction())
      Ebean.commitTransaction()
    Ebean.beginTransaction()
  }
  
  fun rollback() {
    if (inTransaction())
      Ebean.rollbackTransaction()
    Ebean.beginTransaction()
  }
  
  fun createSqlUpdate(sql: String): SqlUpdate? {
    return Ebean.createSqlUpdate(sql)
  }
  
  fun <T> find(javaClass: Class<T>): Query<T>? {
    return Ebean.find(javaClass)
  }
  
  fun createSqlQuery(sql: String): SqlQuery? {
    return Ebean.createSqlQuery(sql)
  }
}
