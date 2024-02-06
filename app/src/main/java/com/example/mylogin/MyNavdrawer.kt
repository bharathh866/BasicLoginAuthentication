package com.example.mylogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.mylogin.fragments.ContactUs
import com.example.mylogin.fragments.Home
import com.example.mylogin.fragments.Services
import com.google.android.material.navigation.NavigationView

class MyNavdrawer : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navdrawer)
drawerLayout=findViewById(R.id.nav_draw)

        val toolbar=findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val navigationview=findViewById<NavigationView>(R.id.nav_view)
        navigationview.setNavigationItemSelectedListener(this)

        val toggle=ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_nav,R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        if(savedInstanceState==null){
            replacefragment(Home())
            navigationview.setCheckedItem(R.id.nav_home)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_home->replacefragment(Home())
            R.id.nav_services->replacefragment(Services())
            R.id.nav_contactus->replacefragment(ContactUs())
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    private fun replacefragment(fragment : Fragment){
        val transaction: FragmentTransaction=supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.commit()


    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        else{
            onBackPressedDispatcher.onBackPressed()
        }
    }
}

