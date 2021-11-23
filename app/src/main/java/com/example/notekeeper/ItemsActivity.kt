package com.example.notekeeper

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.navigation.NavigationView
import androidx.navigation.ui.AppBarConfiguration
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notekeeper.databinding.ActivityItemsBinding
import com.google.android.material.snackbar.Snackbar
import java.util.ArrayList

class ItemsActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    NoteRecyclerAdapter.OnNoteSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityItemsBinding
    lateinit var toggle: ActionBarDrawerToggle


    private val noteLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    private val noteRecyclerAdapter by lazy {
      val adapter =  NoteRecyclerAdapter(this, DataManager.loadNotes())
        adapter.setOnSelectedListener(this)
        adapter
    }

    private val courseLayoutManager by lazy {
        GridLayoutManager(this, 2)
    }

    private val courseRecyclerAdapter by lazy {
        CourseRecyclerAdapter(this, DataManager.courses.values.toList())

    }

    private val recentlyViewedNoteRecyclerAdapter by lazy {
        val adapter = NoteRecyclerAdapter(this, viewModel.recentlyViewedNotes)
        adapter.setOnSelectedListener(this)
        adapter
    }


    private val viewModel by lazy { ViewModelProvider(this)[ItemsActivityViewModel::class.java] }


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

        if(viewModel.isNewlyCreated &&  savedInstanceState != null)
            viewModel.restoreState(savedInstanceState)
         viewModel.isNewlyCreated = false

        handleDisplaySelection(viewModel.navDrawerDisplaySelection)

        toggle = ActionBarDrawerToggle(
            this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener(this)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if(outState != null)

            viewModel.saveState(outState)
    }

    private fun displayNotes() {
        val listItem = findViewById<RecyclerView>(R.id.listItems)
        listItem.layoutManager = noteLayoutManager
        listItem.adapter = noteRecyclerAdapter

        val navView: NavigationView = binding.navView
        navView.menu.findItem(R.id.nav_notes).isChecked = true
    }

    private fun displayCourses() {
        val listItem = findViewById<RecyclerView>(R.id.listItems)
        listItem.layoutManager = courseLayoutManager
        listItem.adapter = courseRecyclerAdapter

        val navView: NavigationView = binding.navView
        navView.menu.findItem(R.id.nav_courses).isChecked = true
    }


    private fun displayRecentlyViewedNotes() {
        val listItem = findViewById<RecyclerView>(R.id.listItems)
        listItem.layoutManager = noteLayoutManager
        listItem.adapter = recentlyViewedNoteRecyclerAdapter


        val navView: NavigationView = binding.navView
        navView.menu.findItem(R.id.nav_recent_notes).isChecked = true

    }

    override fun onResume() {
        super.onResume()
        val listItem = findViewById<RecyclerView>(R.id.listItems)
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
            R.id.nav_notes,
            R.id.nav_courses,
            R.id.nav_recent_notes -> {
                handleDisplaySelection(item.itemId)
                viewModel.navDrawerDisplaySelection = item.itemId



            }
            R.id.nav_share -> {
                handleSelection("Don't you think you've shared enough")
            }
            R.id.nav_send -> {
                handleSelection("Send")
            }
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }




    fun handleDisplaySelection(itemId: Int) {
        when (itemId) {

            R.id.nav_notes -> {
                displayNotes()
            }
            R.id.nav_courses -> {
                displayCourses()
            }
            R.id.nav_recent_notes -> {
                displayRecentlyViewedNotes()
            }

        }
    }

    override fun onNoteSelected(note: NoteInfo) {
       viewModel.addToRecentlyViewedNotes(note)

    }
        private fun handleSelection(message: String) {
            val itemList = findViewById<RecyclerView>(R.id.listItems)
            Snackbar.make(itemList, message, Snackbar.LENGTH_LONG).show()
        }
}