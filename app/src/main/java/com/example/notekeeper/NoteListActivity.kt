package com.example.notekeeper

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notekeeper.databinding.ActivityNoteListBinding

class NoteListActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityNoteListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNoteListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)



        binding.fab.setOnClickListener { view ->
            val activityIntent = Intent(this, NoteActivity::class.java)
              startActivity(activityIntent)
        }

      val listItem = findViewById<RecyclerView>(R.id.listItems)
        listItem.layoutManager = LinearLayoutManager(this)

        listItem.adapter = NoteRecyclerAdapter(this, DataManager.loadNotes())



    }

    override fun onResume() {
        super.onResume()
        val listItem = findViewById<RecyclerView>(R.id.listItems)
        listItem.adapter?.notifyDataSetChanged()

    }

}