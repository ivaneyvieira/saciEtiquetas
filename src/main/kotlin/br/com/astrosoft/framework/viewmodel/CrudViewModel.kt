package br.com.astrosoft.framework.viewmodel

import br.com.astrosoft.framework.model.BaseModel
import io.ebean.typequery.TQRootBean
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.reflect.KClass

abstract class CrudViewModel<MODEL : BaseModel, Q : TQRootBean<MODEL, Q>, VO : EntityVo<MODEL>>
(view: IView, val crudClass: KClass<VO>) : ViewModel(view) {
  var crudBean: VO? = null
  override fun execUpdate() {}
  
  fun update(bean: VO) {
    bean.toModel().update()
  }
  
  fun add(bean: VO) {
    bean.toModel().insert()
  }
  
  fun delete(bean: VO) {
    bean.toModel().delete()
  }
  
  fun update() = exec {
    crudBean?.let { bean -> update(bean) }
  }
  
  fun add() = exec {
    crudBean?.let { bean -> add(bean) }
  }
  
  fun delete() = exec {
    crudBean?.let { bean -> delete(bean) }
  }
  
  //Query Lazy
  
  abstract val query: Q
  
  abstract fun MODEL.toVO(): VO
  
  abstract fun VO.toModel(): MODEL
  
  open fun Q.filterString(text: String): Q {
    return this
  }
  
  open fun Q.filterInt(int: Int): Q {
    return this
  }
  
  open fun Q.filterDate(date: LocalDate): Q {
    return this
  }
  
  fun Q.filterBlank(filter: String): Q {
    return if (filter.isBlank()) this
    else {
      val date = parserDate(filter)
      val int = filter.toIntOrNull()
      val q1 = or().filterString(filter)
      val q2 = date?.let { q1.filterDate(it) } ?: q1
      val q3 = int?.let { q2.filterInt(it) } ?: q2
      q3.endOr()
    }
  }
  
  open fun Q.orderQuery(): Q {
    return this
  }
  
  private fun parserDate(filter: String): LocalDate? {
    val frm = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return try {
      LocalDate.parse(filter, frm)
    } catch (e: Exception) {
      null
    }
  }
  
  fun Q.makeSort(sorts: List<Sort>): Q {
    return if (sorts.isEmpty())
      this.orderQuery()
    else {
      val orderByClause = sorts.joinToString { "${it.propertyName} ${if (it.descending) "DESC" else "ASC"}" }
      orderBy(orderByClause)
    }
  }
  
  open fun findQuery(offset: Int, limit: Int, filter: String, sorts: List<Sort>): List<VO> = execList {
    println("LAZY ==> offset = $offset limit = $limit filter = $filter")
    query.filterBlank(filter)
            .setFirstRow(offset)
            .setMaxRows(limit)
            .makeSort(sorts)
            .findList()
            .map { model ->
              model.toVO().apply {
                entityVo = model
              }
            }
  }
  
  open fun countQuery(filter: String): Int = execInt {
    query.filterBlank(filter)
            .findCount()
  }
}

data class Sort(val propertyName: String, val descending: Boolean = false)

abstract class EntityVo<MODEL : BaseModel> {
  open var entityVo: MODEL? = null
  
  fun toEntity(): MODEL? {
    return entityVo ?: findEntity()
  }
  
  abstract fun findEntity(): MODEL?
}