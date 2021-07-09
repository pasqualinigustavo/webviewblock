package com.webviewblock.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.webviewblock.R
import com.webviewblock.di.Injectable
import java.util.*
import javax.inject.Inject

abstract class BaseActivity<ViewModel : BaseViewModel> : AppCompatActivity, Injectable {

    protected val TAG: String
    protected var layoutId: Int = 0

    private var tryToClose: Date? = null
    private var blockOnBackPress = false
    protected var questionBeforeClose = true

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: ViewModel

    constructor(layoutId: Int) : super() {
        this.TAG = this.javaClass.simpleName
        this.layoutId = layoutId
    }

    protected abstract fun setupObservers()

    abstract fun createViewModel(): ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        init()
    }

    protected fun init() {
        viewModel = createViewModel()
        setupObservers()
        viewModel.onAttached()
    }

    fun backClicked(navController: NavController) {
        onSupportNavigateUp(navController, true)
    }

    protected fun hasBackStack(): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host)
        if (navHostFragment?.childFragmentManager != null)
            return navHostFragment.childFragmentManager.backStackEntryCount > 0
        return false
    }

    private fun onSupportNavigateUp(
        navController: NavController,
        onBackPressed: Boolean
    ): Boolean {
        if (this.blockOnBackPress) {
            return false
        }

        if (!questionBeforeClose) {
            if (!onBackPressed)
                return navController.navigateUp() || super.onSupportNavigateUp()
            else super.onBackPressed()
        }

        if (this.hasBackStack()) {
            if (!onBackPressed)
                return navController.navigateUp() || super.onSupportNavigateUp()
            else {
                super.onBackPressed()
                return false
            }
        }

        if (this.tryToClose != null) {
            val tryToCloseAgain = Date()
            val diff = tryToCloseAgain.time - tryToClose!!.time
            val diffSec = diff / 1000

            if (diffSec > 1) {
                this.tryToClose = null
            } else {
                if (!onBackPressed)
                    return navController.navigateUp() || super.onSupportNavigateUp()
                else super.onBackPressed()
            }
        }

        this.tryToClose = Date()
        Toast.makeText(this, getString(R.string.message_close_app), Toast.LENGTH_SHORT).show()
        return false
    }
}

