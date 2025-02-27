package com.cfa.memonotes.utils

import android.content.Context
import android.text.TextUtils
import com.cfa.memonotes.classes.Note
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.UUID

//récupére les chemin des fichiesr de notes
fun loadNotes(context: Context):MutableList<Note> {
    val notes = mutableListOf<Note>()
    val noteDir = context.filesDir
    for(fileName in noteDir.list()){
        val note = loadNote(context, fileName)
        notes.add(note)
    }
    return notes
}

//lis les notes et les cast dans l'objet
private fun loadNote(context: Context, fileName:String):Note{
    val fileInput = context.openFileInput(fileName)
    val inputStream = ObjectInputStream(fileInput)
    val note = inputStream.readObject() as Note
    inputStream.close()
    return note
}

fun persistNote(context: Context, note: Note){
    if(TextUtils.isEmpty(note.fileName)){
        note.fileName = UUID.randomUUID().toString() + ".note"
    }

    val fileOutput = context.openFileOutput(note.fileName, Context.MODE_PRIVATE)//création du fichier
    val outputStream = ObjectOutputStream(fileOutput)
    outputStream.writeObject(note)
    outputStream.close()


}

fun delNote(context: Context, note: Note){
    context.deleteFile(note.fileName)
}