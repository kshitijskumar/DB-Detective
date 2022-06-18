package com.example.db_detective.core.mappers

import com.example.db_detective.model.DBDetectiveRowModel
import com.example.db_detective.model.DBDetectiveTableModel

fun DBDetectiveTableModel.toDBDetectiveRowModel(): DBDetectiveRowModel {
    val rowsList = mutableListOf<List<String>>()
    val columnNames = this.columns.map { it.columnName }
    rowsList.add(columnNames)
    (0 until this.totaRowsCount).forEach { rowIndex ->
        val rowEntries = this.columns.map { it.columnAllValues[rowIndex] }
        rowsList.add(rowEntries)
    }

    return DBDetectiveRowModel(rowsList)
}