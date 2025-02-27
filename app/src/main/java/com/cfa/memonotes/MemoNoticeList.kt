package com.cfa.memonotes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cfa.memonotes.classes.Note
import com.cfa.memonotes.classes.NoteAdapter
import com.cfa.memonotes.utils.delNote
import com.cfa.memonotes.utils.loadNotes
import com.cfa.memonotes.utils.persistNote
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MemoNoticeList : AppCompatActivity(), View.OnClickListener {

    val TAG = MemoNoticeList::class.java.simpleName

    lateinit var coordinator: CoordinatorLayout
    lateinit var notes: MutableList<Note>
    lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo_notice_list)

        coordinator = findViewById(R.id.coordinator)

        val note_toolbar =findViewById<Toolbar>(R.id.note_toolbar)
        setSupportActionBar(note_toolbar)

        val new_note_fab = findViewById<FloatingActionButton>(R.id.new_fab_note)
        new_note_fab.setOnClickListener(this)

        notes = loadNotes(this)

        adapter = NoteAdapter(notes, this)

        val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.note_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onClick(v: View?) {
        if(v?.tag != null){//si je suis sur un element de liste
            showDetailNote(v.tag as Int)

        }else{
            //je suis susr le boutton le flottant
            when(v?.id){
                R.id.new_fab_note -> createNewNote()
            }
        }
    }

    private fun showDetailNote(i: Int) {
        val note: Note = if(i < 0) Note() else notes[i]
        val intent = Intent( this, MemoNoteDetail::class.java)
        intent.putExtra(MemoNoteDetail.NOTE_EXTRA, note as Parcelable)
        intent.putExtra(MemoNoteDetail.NOTE_EXTRA_INDEX, i)
        startActivity(intent)
    }

    private val editNoteLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == Activity.RESULT_OK){
            processEditNoteDetail(result.data!!)
        }
    }



    private fun processEditNoteDetail(data: Intent) {
        val noteIndex = data.getIntExtra(MemoNoteDetail.NOTE_EXTRA_INDEX, -1)
        when(data.action){
            MemoNoteDetail.ACTION_SAVE_NOTE -> {
                val note = data?.getParcelableExtra<Note>((MemoNoteDetail.NOTE_EXTRA, Note::class.java))
                saveNote(note, noteIndex)
            }
            MemoNoteDetail.ACTION_DELETE_NOTE->{
                deletNote(noteIndex)
            }
        }
    }

    private fun saveNote(note: Note?, noteIndex: Int) {
        persistNote(this, note as Note)
        if(noteIndex < 0){
            notes.add(0, note)
        }else{
            notes[noteIndex] = note
        }
        adapter.notifyDataSetChanged()
    }

    private fun deletNote(noteIndex: Int) {
        if(noteIndex < 0) return
            val note = notes.removeAt(noteIndex)
            delNote(this, note)
        adapter.notifyDataSetChanged()//on met à jour la table de liste
        Toast.makeText(this, "\"${note.titre}\" est bien supprimé", Toast.LENGTH_SHORT).show()
    }

    private fun createNewNote() {
        showDetailNote(-1)
    }

}