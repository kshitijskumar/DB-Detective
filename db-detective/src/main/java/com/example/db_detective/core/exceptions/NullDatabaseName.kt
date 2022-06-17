package com.example.db_detective.core.exceptions

import java.lang.Exception

data class NullDatabaseName(val msg: String) : Exception(msg)