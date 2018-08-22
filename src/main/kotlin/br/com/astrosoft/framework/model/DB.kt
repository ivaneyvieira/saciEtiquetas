package br.com.astrosoft.framework.model

import br.com.astrosoft.framework.model.AppException
import br.com.astrosoft.framework.model.BaseModel
import br.com.astrosoft.framework.model.DevException
import br.com.astrosoft.framework.model.Transaction
import io.ebean.RawSqlBuilder
import kotlin.reflect.full.isSubclassOf

import javax.persistence.PersistenceException

object DB {
  fun <R> xa(lambda: () -> R): R {
    return try {
      lambda()
    } catch (e: AppException) {
      throw e
    } catch (e: Throwable) {
      throw DevException(e, "Erro desconhecido")
    }
  }
  
  @Throws(PersistenceException::class)
  fun executeSqls(
          sqls: List<String>, vararg params: Pair<String, Any?>
                 ) {
    sqls.forEach { sql ->
      println(sql)
      val update = Transaction.createSqlUpdate(sql)
      params.forEach { param ->
        update?.setParameter(param.first, param.second)
      }
      update?.execute()
    }
  }
  
  fun String.split(): List<String> {
    return this.replace("::=", ":=").replace(":=", "::=").split(";").map { it.replace('\n', ' ') }.map { it.trim() }
            .filter { it.isNotEmpty() }
  }
  
  @Throws(PersistenceException::class)
  fun sciptSql(
          sqlScript: String, vararg params: Pair<String, Any>
              ) {
    xa {
      val sqls = sqlScript.split()
      executeSqls(sqls, * params)
    }
  }
  
  @Throws(PersistenceException::class)
  inline fun <reified T> sqlEntity(
          sqlScript: String, vararg params: Pair<String, Any?>
                                  ): List<T> {
    return xa {
      val sqls = sqlScript.split()
      val sqlsScript = sqls.dropLast(1)
      
      executeSqls(sqlsScript, * params)
      
      val sql = sqls.last()
      println(sql)
      
      when {
        T::class.isSubclassOf(BaseModel::class) -> {
          
          val rawSql = RawSqlBuilder.parse(sql).create()
          
          val query = Transaction.find(T::class.java)?.setRawSql(rawSql)
          params.forEach { param ->
            query?.setParameter(param.first, param.second)
          }
          
          query?.findList().orEmpty()
        }
        else                                    -> {
          val sqlQuery = Transaction.createSqlQuery(sql)
          val constructor = T::class.constructors.first()
          params.forEach { param ->
            sqlQuery?.setParameter(param.first, param.second)
          }
          sqlQuery?.findList()?.map { sqlRow ->
            val arrayPar: List<Any?> = constructor.parameters.map { par ->
              sqlRow[par.name]
            }
            
            constructor.call(arrayPar.toTypedArray())
          }.orEmpty()
        }
      }
    }
  }
  
  @Throws(PersistenceException::class)
  inline fun <reified T> sqlScalar(
          sqlScript: String, vararg params: Pair<String, Any>
                                  ): List<T> {
    return xa {
      val sqls = sqlScript.split()
      val sqlsScript = sqls.dropLast(1)
      
      executeSqls(sqlsScript, * params)
      
      val sql = sqls.last()
      println(sql)
      
      val sqlQuery = Transaction.createSqlQuery(sql)
      
      params.forEach { param ->
        sqlQuery?.setParameter(param.first, param.second)
      }
      sqlQuery?.findList()?.filter { it is T }?.map { it as T }
    }.orEmpty()
  }
}


