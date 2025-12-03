package com.example.tantargylista

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tantargylista.data.AppDatabase
import com.example.tantargylista.data.Subject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SubjectViewModel(application: Application) : AndroidViewModel(application) {

    private val subjectDao =
        AppDatabase.getDatabase(application).subjectDao()

    private val _subjects = MutableStateFlow<List<Subject>>(emptyList())
    val subjects: StateFlow<List<Subject>> = _subjects

    init {
        viewModelScope.launch {
            subjectDao.getAllSubjects().collectLatest {
                _subjects.value = it
            }
        }
    }

    fun addSubject(name: String, credit: Int) {
        viewModelScope.launch {
            subjectDao.insertSubject(
                Subject(name = name, credit = credit)
            )
        }
    }

    fun updateSubject(subject: Subject) {
        viewModelScope.launch {
            subjectDao.updateSubject(subject)
        }
    }

    fun deleteSubject(subject: Subject) {
        viewModelScope.launch {
            subjectDao.deleteSubject(subject)
        }
    }
}
