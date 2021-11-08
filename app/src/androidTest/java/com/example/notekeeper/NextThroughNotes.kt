package com.example.notekeeper

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.*
import org.hamcrest.CoreMatchers.*

// add ViewAssertions class imports

@RunWith(AndroidJUnit4::class)
class nextThroughNoteTest {

    @Rule
    @JvmField
    val noteListActivity = ActivityScenarioRule(NoteListActivity::class.java)

    @Test
    fun nextThroughNotes() {
        // step 1:  select a note from our ListView in NoteListActivity
        onData(
            allOf(
                instanceOf(NoteInfo::class.java),
                equalTo(DataManager.notes[0])
            )
        ).perform(click())

        // step 2: go through each note in DataManager.notes list and check whether the contents of
        // the notes in the list match with the contents of the 3 views in MainActivity
        for (index in 0..DataManager.notes.lastIndex) {

            val note = DataManager.notes[index]

            onView(withId(R.id.spinnerCourses)).check(
                matches(
                    withSpinnerText(note.course?.title)
                )
            )

            onView(withId(R.id.textNoteTitle)).check(
                matches(withText(note.title))
            )

            onView(withId(R.id.textNoteText)).check(
                matches(withText(note.text))
            )

            if (index != DataManager.notes.lastIndex)
                onView(allOf(withId(R.id.action_next), isEnabled())).perform(click())
        }

        onView(withId(R.id.action_next)).check(
            matches(not(isEnabled()))
        )
    }
}