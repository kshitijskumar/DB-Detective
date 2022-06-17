package com.example.db_detective.core.exceptions

import java.lang.Exception

data class NoDatabaseFoundForName(val msg: String): Exception(msg)
