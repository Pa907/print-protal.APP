package com.example.data

import kotlinx.coroutines.flow.Flow

class DocumentRepository(private val documentDao: DocumentDao) {
    val recentDocuments: Flow<List<RecentDocument>> = documentDao.getRecentDocuments()

    suspend fun insert(document: RecentDocument) = documentDao.insertDocument(document)

    suspend fun clearAll() = documentDao.clearHistory()
}
