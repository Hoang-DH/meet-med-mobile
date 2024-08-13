package com.example.chatapp.domain.core.navigation

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController

interface BaseNavigator {
    val navController: NavController?
    fun openScreen(@IdRes id: Int, bundle: Bundle? = null)
    fun navigateUp(): Boolean?
    fun currentFragmentId(): Int?
    fun bind(navController: NavController)
    fun unbind()
}