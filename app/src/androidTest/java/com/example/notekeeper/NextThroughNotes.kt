package com.example.notekeeper

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Rule
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class NextThroughNotes : TestCase() {
    @Rule @JvmField
    val noteListActivity = ActivityScenarioRule(NoteListActivity::class.java)


    fun nextThroughNotes(){

    }

}