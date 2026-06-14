package com.example.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.data.AppDatabase
import com.example.data.DocumentRepository
import com.example.data.RecentDocument
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DocumentViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java, "printsahyogi-db"
    ).build()

    private val repository = DocumentRepository(db.documentDao())

    private val _isHindi = MutableStateFlow(false)
    val isHindi: StateFlow<Boolean> = _isHindi

    val recentDocs: StateFlow<List<RecentDocument>> = repository.recentDocuments
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun toggleLanguage() {
        _isHindi.value = !_isHindi.value
    }

    fun addMockDocument(name: String, type: String) {
        viewModelScope.launch {
            repository.insert(
                RecentDocument(
                    name = name,
                    type = type,
                    filePath = "/mock/path"
                )
            )
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            repository.clearAll()
        }
    }
}
