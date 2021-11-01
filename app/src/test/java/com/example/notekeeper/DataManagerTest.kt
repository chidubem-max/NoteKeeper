package com.example.notekeeper

import junit.framework.TestCase
import org.junit.Assert.assertEquals
import org.junit.Test

class DataManagerTest {


    fun setUp() {
        DataManager.notes.clear()
        DataManager.initializeNotes()
    }

    @Test
    fun testAddNotes() {
        val course = DataManager.courses["android_async"]!!
        val noteTitle = "This is a new note"
        val noteText = "This is the body of the note"


        val index = DataManager.addNotes(course, noteTitle, noteText)
        val note = DataManager.notes[index]

        assertEquals(course, note.course)
        assertEquals(noteTitle, note.title)
        assertEquals(noteText, note.text)
    }

    @Test
    fun findSimilarNotes() {
        val course = DataManager.courses["android_async"]!!
        val noteTitle = "This a test note"
        val noteText1 = "This is the body of my test note"
        val noteText2 = "This is the body of my second test"

        val index1 = DataManager.addNotes(course, noteTitle, noteText1)
        val index2 = DataManager.addNotes(course, noteTitle, noteText2)

        val note1 = DataManager.findNotes(course, noteTitle, noteText1)
        val foundIndex1 = DataManager.notes.indexOf(note1)
        assertEquals(index1, foundIndex1)

        val note2 = DataManager.findNotes(course, noteTitle, noteText2)
        val foundIndex2 = DataManager.notes.indexOf(note2)
        assertEquals(index2, foundIndex2)
    }


}