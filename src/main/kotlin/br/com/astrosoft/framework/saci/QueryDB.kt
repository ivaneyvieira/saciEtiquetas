package br.com.astrosoft.framework.saci

import br.com.astrosoft.framework.utils.SystemUtils
import com.jolbox.bonecp.BoneCPDataSource
import org.sql2o.Connection
import org.sql2o.Query
import org.sql2o.Sql2o

open class QueryDB(
        val driver: String, val url: String, val username: String,
        val password: String, val sqldir: String
                  ) {
  private val sql2o: Sql2o
  
  init {
    registerDriver(driver)
    val ds = BoneCPDataSource()
    ds.jdbcUrl =url
    ds.username=username
    ds.password=password
    ds.minConnectionsPerPartition = 5
    ds.maxConnectionsPerPartition = 10
    ds.partitionCount = 1
    this.sql2o = Sql2o(ds)
  }
  
  private fun registerDriver(driver: String) {
    try {
      Class.forName(driver)
    } catch (e: ClassNotFoundException) {
      throw RuntimeException(e)
    }
  }
  
  protected fun <T> query(file: String, lambda: (Query) -> T): T {
    return buildQuery(file) { con, query ->
      val ret = lambda(query)
      con.close()
      ret
    }
  }
  
  private inline fun <C : AutoCloseable, R> C.trywr(block: (C) -> R): R {
    this.use {
      return block(this)
    }
  }
  
  protected fun execute(
          file: String, vararg params: Pair<String, String>,
          monitor: (String, Int, Int) -> Unit = { _, _, _ -> }
                       ) {
    var sqlScript = SystemUtils.readFile(sqldir + file)
    sql2o.beginTransaction().trywr { con ->
      params.forEach { sqlScript = sqlScript?.replace(":${it.first}", it.second) }
      val sqls = sqlScript?.split(";").orEmpty()
      val count = sqls.size
      sqls.filter { it.trim() != "" }.forEachIndexed { index, sql ->
        println(sql)
        val query = con.createQuery(sql)
        query.executeUpdate()
        val parte = index + 1
        val caption = "Parte $parte/$count"
        monitor(caption, parte, count)
      }
      monitor("", count, count)
      con.commit()
    }
  }
  
  private fun <T> buildQuery(file: String, proc: (Connection, Query) -> T): T {
    val sql = SystemUtils.readFile(sqldir + file)
    this.sql2o.open().trywr { con ->
      val query = con.createQuery(sql)
      println("SQL2O ==> $sql")
      return proc(con, query)
    }
  }
}
