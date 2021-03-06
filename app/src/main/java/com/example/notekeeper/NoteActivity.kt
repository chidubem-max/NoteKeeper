package com.example.notekeeper

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.example.notekeeper.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class NoteActivity : AppCompatActivity() {

    private var tag = "NoteActivity"
    private var  notePosition = POSITION_NOT_SET

   val noteGetTogetherHelper = NoteGetTogetherHelper(this, lifecycle)


    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val adapterCourses = ArrayAdapter<CourseInfo>(this, android.R.layout.simple_spinner_item, DataManager .courses.values.toList())
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

       val spinnerCourse = findViewById<Spinner>(R.id.spinnerCourses)
        spinnerCourse.adapter = adapterCourses


        notePosition = savedInstanceState?.getInt(NOTE_POSITION, POSITION_NOT_SET) ?:
            intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET)

        if(notePosition != POSITION_NOT_SET)
            displayNote()
        else{
            createNewNote()


        }
        Log.d(tag, "onCreate")
    }


    private fun createNewNote() {
        notePosition = DataManager.addNote(NoteInfo())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState?.putInt(NOTE_POSITION, notePosition)
    }

    private fun displayNote() {

        Log.i(tag, "Displaying note for Position $notePosition")
        val note = DataManager.loadNote(notePosition)

        val noteTitle = findViewById<EditText>(R.id.textNoteTitle)
        val noteText = findViewById<EditText>(R.id.textNoteText)

        noteTitle.setText(note.title)
        noteText.setText(note.text)

        val coursePosition = DataManager.courses.values.indexOf(note.course)
        val spinnerCourse = findViewById<Spinner>(R.id.spinnerCourses)
        spinnerCourse.setSelection(coursePosition)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_next -> {
                moveNext()

                true

            }

            R.id.action_get_together -> {
                noteGetTogetherHelper.sendMessage(DataManager.loadNote(notePosition ))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun moveNext() {
        ++notePosition
        displayNote()
        invalidateOptionsMenu()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (DataManager.isLastNoteId(notePosition)){
            val menuItem = menu?.findItem(R.id.action_next)
            if(menuItem != null) {
                 menuItem.icon = getDrawable(R.drawable.ic_baseline_block_24)
                menuItem.isEnabled = false

            }

        }
        return super.onPrepareOptionsMenu(menu)
    }


    override fun onPause() {
        super.onPause()
        saveNote()

        Log.d(tag, "onPause")
    }


//    private fun showMessage(message: String) {
//        Snackbar.make(textNoteTitle, message, Snackbar.LENGTH_LONG).show()
//    }
    private fun saveNote() {
        val note = DataManager.loadNote(notePosition)

        val noteTitle = findViewById<EditText>(R.id.textNoteTitle)
        val noteText = findViewById<EditText>(R.id.textNoteText)
        val spinnerCourse = findViewById<Spinner>(R.id.spinnerCourses)



        note.title = noteTitle.text.toString()
        note.text = noteText.text.toString()
        note.course = spinnerCourse.selectedItem as CourseInfo
    }
}