package br.com.astrosoft.framework.saci

import br.com.astrosoft.framework.saci.beans.LojaSaci
import br.com.astrosoft.framework.saci.beans.NotaSaci
import br.com.astrosoft.framework.saci.beans.UserSaci
import br.com.astrosoft.framework.utils.DB

class QuerySaci : QueryDB(driver, url, username, password, sqldir) {

  fun findNotaEntrada(storeno: Int, nfname: String, invse: String): List<NotaSaci> {
    val sql = "/sqlSaci/findNotaEntrada.sql"
    return query(sql) { q ->
      q.addParameter("storeno", "$storeno")
              .addParameter("nfname", nfname)
              .addParameter("invse", invse)
              .executeAndFetch(NotaSaci::class.java)
    }
  }
  
  fun findNotaSaida(storeno: Int, nfno: String, nfse: String): List<NotaSaci> {
    val sql = "/sqlSaci/findNotaSaida.sql"
    return query(sql) { q ->
      q.addParameter("storeno", "$storeno")
              .addParameter("nfno", nfno)
              .addParameter("nfse", nfse)
              .executeAndFetch(NotaSaci::class.java)
    }
  }
  
  fun findLojas(storeno: Int): List<LojaSaci> {
    val sql = "/sqlSaci/findLojas.sql"
    return query(sql) { q ->
      q.executeAndFetch(LojaSaci::class.java).filter { it.storeno == storeno || storeno == 0 }
    }
  }
  
  fun findUser(login: String?): UserSaci? {
    login ?: return null
    val sql = "/sqlSaci/userSenha.sql"
    return query(sql) { q ->
      q.addParameter("login", login)
              .executeAndFetch(UserSaci::class.java)
              .firstOrNull()
    }
  }
  
  /*
  fun findLoc(storeno: Int?, localizacao: String? = "", prdno: String? = "", grade: String? = ""): List<PrdLoc> {
    val sql = "/sqlSaci/findLoc.sql"
    return query(sql) { q ->
      q.addParameter("storeno", storeno ?: 0)
              .addParameter("localizacao", localizacao ?: "")
              .addParameter("prdno", prdno ?: "")
              .addParameter("grade", grade ?: "")
              .executeAndFetch(PrdLoc::class.java)
    }
  }
  */
  /*
  fun findLocStr(storeno: Int?, prdno: String? = "", grade: String? = ""): String{
    val loja = storeno ?: 0
    prdno ?: return ""
    grade ?: return ""
    val listLoc = findLoc(storeno = loja, prdno = prdno, grade = grade).mapNotNull { it.localizacao }.filter { it.isNotBlank() }
    return listLoc.joinToString()
  }
  */
  /*
  fun findLocais(storeno: Int?): List<String> {
    storeno ?: return emptyList()
    return findLoc(storeno).mapNotNull { it.localizacao }.distinct()
  }
  */
  companion object {
    private val db = DB("saci")
    internal val driver = db.driver
    internal val url = db.url
    internal val username = db.username
    internal val password = db.password
    internal val sqldir = db.sqldir
    
    val ipServer = db.url.split("/").getOrNull(2)
  }
}

val saci = QuerySaci()