package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DocumentDao {
    @Query("SELECT * FROM recent_documents ORDER BY timestamp DESC LIMIT 5")
    fun getRecentDocuments(): Flow<List<RecentDocument>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDocument(document: RecentDocument)

    @Query("DELETE FROM recent_documents")
    suspend fun clearHistory()
}
