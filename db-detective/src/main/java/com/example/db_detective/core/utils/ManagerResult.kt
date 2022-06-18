package com.example.db_detective.core.utils

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

sealed class ManagerResult<out SUCCESS, out ERROR> {
    data class Success<SUCCESS>(val data: SUCCESS) : ManagerResult<SUCCESS, Nothing>()
    data class Failure<ERROR>(val errorMsg: ERROR) : ManagerResult<Nothing, ERROR>()
}

fun <SUCCESS, ERROR> Fragment.handleManagerSuccessOrShowToast(result: ManagerResult<SUCCESS, ERROR>, onSuccess: (SUCCESS) -> Unit) {
    when(result) {
        is ManagerResult.Success -> onSuccess.invoke(result.data)
        is ManagerResult.Failure -> {
            Toast.makeText(requireContext(), result.errorMsg.toString(), Toast.LENGTH_LONG).show()
        }
    }
}

fun <SUCCESS, ERROR> Activity.handleManagerSuccessOrShowToast(result: ManagerResult<SUCCESS, ERROR>, onSuccess: (SUCCESS) -> Unit) {
    when(result) {
        is ManagerResult.Success -> onSuccess.invoke(result.data)
        is ManagerResult.Failure -> {
            Toast.makeText(this, result.errorMsg.toString(), Toast.LENGTH_LONG).show()
        }
    }
}