package com.webviewblock.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.webviewblock.R
import com.webviewblock.di.Injectable
import com.webviewblock.domain.interfaces.ActivityToolbarBehaviour
import com.webviewblock.presentation.BaseActivity
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class HomeActivity : BaseActivity<HomeActivityViewModel>(R.layout.activity_main),
    ActivityToolbarBehaviour, HasSupportFragmentInjector,
    Injectable {

    companion object {
        val TAG = HomeActivity::class.java.simpleName
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController

        navController?.let { it.setGraph(R.navigation.nav_graph) }

        createActionBar()
    }

    private fun displayHomeUp() {
        val isRoot = !this.hasBackStack()

        supportActionBar?.let {
            it.setDisplayShowHomeEnabled(!isRoot)
            it.setDisplayHomeAsUpEnabled(!isRoot)
            it.setHomeButtonEnabled(!isRoot)
        }
    }

    private fun createActionBar() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayShowTitleEnabled(false)
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayUseLogoEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_arrow_left)
        }
        toolbar.setNavigationOnClickListener {
            if (hasBackStack()) {
                onBackPressed()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragments = supportFragmentManager.fragments
        for (item in fragments) {
            item?.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        findNavController(R.id.nav_host)?.let { backClicked(it) }
    }

    override fun setupObservers() {

    }

    override fun createViewModel(): HomeActivityViewModel {
        return ViewModelProvider(this, viewModelFactory)
            .get(HomeActivityViewModel::class.java)
    }

    override fun setToolbarTitle(title: String) {
        val actionbar_title = findViewById<TextView>(R.id.actionbar_title) as TextView
        actionbar_title?.text = title
        displayHomeUp()
    }


}
