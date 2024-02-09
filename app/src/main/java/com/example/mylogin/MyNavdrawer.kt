package com.example.mylogin




import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.example.mylogin.Repository.Userpreferencesrepository
import com.example.mylogin.fragments.ContactUs
import com.example.mylogin.fragments.Home
import com.example.mylogin.fragments.Services
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyNavdrawer : Fragment(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var userpreferencesrepository: Userpreferencesrepository

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_navdrawer, container, false)

        drawerLayout = view.findViewById(R.id.nav_draw)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)

        val navigationView = view.findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        userpreferencesrepository = (requireActivity() as MainActivity).userPreferencerepo
        val toggle = ActionBarDrawerToggle(
            requireActivity(),
            drawerLayout,
            toolbar,
            R.string.open_nav,
            R.string.close_nav
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        return view
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {


        when (item.itemId) {
            R.id.nav_home -> replaceFragment(Home(), true)
            R.id.nav_services -> replaceFragment(Services(), true)
            R.id.nav_contactus -> replaceFragment(ContactUs(), true)
            R.id.nav_logout -> {
                logoutUser()
                val fragmentTransaction =
                    requireActivity().supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.flfragment, LoginFragment())
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()

            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = false) {
        val transaction: FragmentTransaction =
            requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    fun logoutUser() {
        // Your logout logic

        // Upon logout, call logoutUser function
        lifecycleScope.launch(Dispatchers.IO) {
            userpreferencesrepository.logoutUser()
        }


    }
}


