package com.example.notekeeper

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.navigation.NavigationView
import androidx.navigation.ui.AppBarConfiguration
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notekeeper.databinding.ActivityItemsBinding

class ItemsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityItemsBinding
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarItems.toolbar)

        binding.appBarItems.fab.setOnClickListener { view ->
            startActivity(Intent(this, NoteActivity::class.java))
        }


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView



         val listItem = findViewById<RecyclerView>(R.id.listItemx)
        listItem.layoutManager = LinearLayoutManager(this)
        listItem.adapter = NoteRecyclerAdapter(this, DataManager.notes)

         toggle = ActionBarDrawerToggle(
            this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onResume() {
        super.onResume()
        val listItem = findViewById<RecyclerView>(R.id.listItemx)
        listItem.adapter?.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = binding.drawerLayout

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

           if (toggle.onOptionsItemSelected(item)) {

               return true
           }

        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {

            R.id.nav_gallery ->{Toast.makeText(applicationContext, "Clicked Gallery", Toast.LENGTH_SHORT).show()}
            R.id.nav_slideshow -> {
                Toast.makeText(applicationContext, "Clicked Slideshow", Toast.LENGTH_SHORT).show()
            }
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
