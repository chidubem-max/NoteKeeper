package com.example.notekeeper

object DataManager {
    val courses = HashMap<String, CourseInfo>()
    val notes = ArrayList<NoteInfo>()

   init {
       initializeCourses()
       initializeNotes()
   }


    fun addNotes(course: CourseInfo, noteTitle: String, noteText: String) : Int {
        val note = NoteInfo(course, noteTitle, noteText)
        notes.add(note)
        return notes.lastIndex
    }

    fun findNotes(course: CourseInfo, noteTitle: String, noteText: String) : NoteInfo? {
        for (note in notes)
           if (course == note.course &&
                   noteTitle == note.title &&
                   noteText == note.text)
                       return note
        return null
    }

    fun initializeCourses() {
        var course = CourseInfo("android_intents", "Android Programming with intents")
        courses.set(course.courseId, course)

        course = CourseInfo("android_async", title = "Android Async Programming and Services")
        courses.set(course.courseId, course)

        course = CourseInfo("Java Fundamentals ", title = "java_lang")
        courses.set(course.courseId, course)

        course = CourseInfo("Java_Sync", title = "java")
        courses.set(course.courseId, course)
    }


    fun initializeNotes() {
        var course = courses["android_intents"]!!
        var note = NoteInfo(course, "Service default threads", "Did you know that by default an Android service will tie up the UI thread?")
        notes.add(note)
        note = NoteInfo(course, "Long running operations", "Foreground services can be tied to a notification icon")
        notes.add(note)


         course = courses["android_async"]!!
         note = NoteInfo(course, "Service default threads", "Did you know that by default an Android service will tie up the UI thread?")
        notes.add(note)
        note = NoteInfo(course, "Long running operations", "Foreground services can be tied to a notification icon")
        notes.add(note)


         course = courses["Java_Sync"]!!
        note = NoteInfo(course, "Service default threads", "Did you know that by default an Android service will tie up the UI thread?")
        notes.add(note)
        note = NoteInfo(course, "Long running operations", "Foreground services can be tied to a notification icon")
        notes.add(note)



    }


}