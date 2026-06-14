package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_documents")
data class RecentDocument(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val type: String, // e.g., "Aadhaar", "PAN", "Photo"
    val timestamp: Long = System.currentTimeMillis(),
    val filePath: String // path to the generated/cached PDF or image
)
