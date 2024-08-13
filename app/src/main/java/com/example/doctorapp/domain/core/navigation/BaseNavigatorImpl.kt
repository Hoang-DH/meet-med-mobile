package com.example.chatapp.domain.core.navigation

import android.os.Bundle
import androidx.navigation.NavController

abstract class BaseNavigatorImpl: BaseNavigator {
    override var navController: NavController? = null
    override fun bind(navController: NavController) {
        this.navController = navController
    }
    override fun openScreen(id: Int, bundle: Bundle?) {
        navController?.navigate(id, bundle)
    }
    override fun navigateUp(): Boolean? {
        return navController?.navigateUp()
    }
    override fun currentFragmentId(): Int? {
        return navController?.currentDestination?.id
    }
    override fun unbind() {
        this.navController = null
    }
}