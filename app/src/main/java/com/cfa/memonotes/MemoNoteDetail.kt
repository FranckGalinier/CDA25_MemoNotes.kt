package com.cfa.memonotes

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MemoNoteDetail : AppCompatActivity() {
    companion object{
        val NOTE_EXTRA = "note"
        val NOTE_EXTRA_INDEX ="noteIndex"
        val REQUEST_EDIT_NOTE = 1
        val ACTION_SAVE_NOTE = "com.cfa.memonotes.actions.ACTION_SAVE_NOTE"
        val ACTION_DELETE_NOTE = "com.cfa.memonotes.actions.ACTION_DELETE_NOTE"
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_memo_note_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}