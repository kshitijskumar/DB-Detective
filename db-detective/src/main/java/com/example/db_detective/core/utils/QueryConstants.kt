package com.example.db_detective.core.utils

object QueryConstants {

    const val ALL_TABLES_FROM_DB = "SELECT * FROM sqlite_master WHERE type='table' AND name!='android_metadata' order by name"
    const val COLUMN_NAMES_OF_TABLE = "SELECT * FROM <tableName> LIMIT 0"



}