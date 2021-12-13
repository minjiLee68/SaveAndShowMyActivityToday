package com.sophia.saveandshowmyactivitytoday.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sophia.saveandshowmyactivitytoday.entity.Check
import com.sophia.saveandshowmyactivitytoday.entity.DetailPlan
import com.sophia.saveandshowmyactivitytoday.entity.DetailPlanCheck
import com.sophia.saveandshowmyactivitytoday.entity.TodoEntity
import com.sophia.saveandshowmyactivitytoday.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {
    val listLiveData = repository.getAll

    fun addTodo(content: String, year: Int, month: Int, day: Int, date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val todo = TodoEntity(content, year, month, day, date)
            repository.addTodo(todo)
        }
    }

    fun updateTodo(todo: TodoEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTodo(todo)
        }
    }

    fun deleteTodo(todo: TodoEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTodo(todo)
        }
    }

    fun readDateData(year: Int, month: Int, day: Int): LiveData<List<TodoEntity>> =
        repository.readDateData(year, month, day)

    val checkLiveData = repository.getCheck

    fun addCheck(content: String, date: String, year: Int, month: Int, day: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val check = Check(content, date, year, month, day)
            repository.addCheck(check)
        }
    }

    fun readCheckedDateData(year: Int, month: Int, day: Int): LiveData<List<Check>> =
        repository.readCheckedDateData(year, month, day)

    fun addPlan(content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val detail = DetailPlan(content)
            repository.addPlan(detail)
        }
    }

    fun deletePlan(deletePlan: DetailPlan) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletePlan(deletePlan)
        }
    }

    fun detailPlanLiveData(): LiveData<List<DetailPlan>> = repository.detailPlanList

    val planCheckLive = repository.planCheckList

    fun  addPlanCheck(plan: String, checkId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val planText = DetailPlanCheck(plan,checkId)
            repository.addPlanCheck(planText)
        }
    }

    fun deletePlanCheck(planCheck: DetailPlanCheck) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletePlanCheck(planCheck)
        }
    }
}