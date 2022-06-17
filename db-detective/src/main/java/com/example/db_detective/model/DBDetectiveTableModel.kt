package com.example.db_detective.model

data class DBDetectiveTableModel(
    val columns: List<DBDetectiveColumnModel>
)

data class DBDetectiveColumnModel(
    val columnName: String,
    val columnAllValues: List<String>
)
