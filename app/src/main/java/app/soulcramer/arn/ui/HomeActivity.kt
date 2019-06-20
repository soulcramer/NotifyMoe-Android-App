package app.soulcramer.arn.ui

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import app.soulcramer.arn.NotifyMoeActivity
import app.soulcramer.arn.R
import app.soulcramer.arn.databinding.ActivityMainBinding
import app.soulcramer.arn.util.setupWithNavController

class HomeActivity : NotifyMoeActivity() {

    private var currentNavController: LiveData<NavController>? = null

    private var toolbar: Toolbar? = null

    private val bottomNavDestinations: Set<Int> = setOf(
        R.id.profileFragment
    )

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)

        if (savedInstanceState == null) {
            //            setupBottomNavigationBar()
            //            bottomNavigationView.selectedItemId = R.id.profileFragment
        } // Else, need to wait for onRestoreInstanceState

        //        onBackPressedDispatcher.addCallback(this) {
        //            currentNavController?.value?.popBackStack()
        //        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        //        setupBottomNavigationBar()
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {

        val navGraphIds = listOf(R.navigation.nav_profile)

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = binding.bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this, Observer { navController ->
            val appBarConfiguration = AppBarConfiguration(bottomNavDestinations)
            toolbar?.setupWithNavController(navController, appBarConfiguration)
            navController.addOnDestinationChangedListener { _, destination, _ ->
                binding.bottomNavigationView.isVisible = bottomNavDestinations.contains(destination.id)
            }
        })
        currentNavController = controller
    }

    //    override fun onSupportNavigateUp(): Boolean {
    //        return currentNavController?.value?.navigateUp() ?: false
    //    }
}
