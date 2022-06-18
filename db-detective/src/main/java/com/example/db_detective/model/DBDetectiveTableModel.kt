package com.example.db_detective.model

data class DBDetectiveTableModel(
    val totaRowsCount: Int,
    val columns: List<DBDetectiveColumnModel>
)

data class DBDetectiveColumnModel(
    val columnName: String,
    val columnAllValues: List<String>
)

data class DBDetectiveRowModel(
    val rowEntries: List<List<String>> // 1st element of this list would be the column names
)
