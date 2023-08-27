package com.fininace.funds.income.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fininace.funds.income.data.Goal
import com.fininace.funds.income.data.GoalType
import com.fininace.funds.income.data.goalDatabase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date

class GoalsViewModel(application: Application) : AndroidViewModel(application) {

    private val goalDatabase by goalDatabase()

    val goals = MutableLiveData<List<Goal>>()

    fun fetch() {
        viewModelScope.launch {
            goalDatabase.goalRepository.findAll().collectLatest {
                goals.postValue(it)
            }
        }
    }

    fun add(title: String, amount: Int, date: Date) {
        viewModelScope.launch {
            goalDatabase.goalRepository.add(
                Goal(
                    title = title,
                    endDate = date,
                    type = GoalType.Active,
                    current = 0,
                    total = amount
                )
            )

            fetch()
        }
    }

    fun delete(goal: Goal) {
        viewModelScope.launch {
            goalDatabase.goalRepository.delete(goal)
            fetch()
        }
    }

    fun topUp(goal: Goal, amount: Int) {
        viewModelScope.launch {
            goalDatabase.goalRepository.update(goal.copy(current = goal.current + amount))
            fetch()
        }
    }

    fun withdraw(goal: Goal, amount: Int) {
        viewModelScope.launch {
            goalDatabase.goalRepository.update(goal.copy(current = goal.current - amount))
            fetch()
        }
    }

    fun archive(goal: Goal) {
        viewModelScope.launch {
            val type = if (goal.type == GoalType.Active) GoalType.Archived else GoalType.Active
            goalDatabase.goalRepository.update(goal.copy(type = type))
            fetch()
        }
    }
}