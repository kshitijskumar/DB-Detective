package com.example.dbdetectivedemo.testdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "test_table")
data class TestEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val name: String,
    val description: String
)