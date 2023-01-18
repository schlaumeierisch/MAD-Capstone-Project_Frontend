package nl.hva.frontend.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import nl.hva.frontend.R
import nl.hva.frontend.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navController = navHostFragment.navController

        binding.includeMain.bottomNavigation.setupWithNavController(navHostFragment.navController)

        // set up the bottom navigation menu
        bottomNavigationMenuSetup()
    }

    private fun bottomNavigationMenuSetup() {
        navController.addOnDestinationChangedListener { _, nd: NavDestination, _ ->
            if (nd.id == R.id.homeFragment || nd.id == R.id.settingsFragment) {
                binding.includeMain.bottomNavigation.visibility = View.VISIBLE
            } else {
                binding.includeMain.bottomNavigation.visibility = View.GONE
            }
        }
    }
}