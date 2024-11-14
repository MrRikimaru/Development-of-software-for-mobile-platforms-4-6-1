package com.example.notepad;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button saveButton;
    private List<String> notes;
    private NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        notes = loadNotes();
        adapter = new NoteAdapter(notes, this::showDeleteNoteDialog);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Внутри onCreate()

        Button addNoteButton = findViewById(R.id.addNoteButton);
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddNoteDialog();
            }
        });
    }

    private List<String> loadNotes() {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        Set<String> notesSet = prefs.getStringSet("notes", new HashSet<String>());
        return new ArrayList<>(notesSet);
    }

    private void saveNotes(List<String> notes) {
        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        Set<String> notesSet = new HashSet<>(notes);
        editor.putStringSet("notes", notesSet);
        editor.apply();
    }
    private void showAddNoteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Добавить заметку");

        LayoutInflater inflater = LayoutInflater.from(this);
        final EditText input = (EditText) inflater.inflate(R.layout.dialog_add_note, null);
        builder.setView(input);

        builder.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text = input.getText().toString();
                notes.add(text);
                adapter.notifyDataSetChanged();
                saveNotes(notes);
            }
        });
        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
//    @Override
//    public void onNoteLongClicked(int position) {
//        showDeleteNoteDialog(position);
//    }

    private void showDeleteNoteDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Удалить заметку");
        builder.setMessage("Вы уверены, что хотите удалить эту заметку?");

        builder.setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                notes.remove(position);
                adapter.notifyDataSetChanged();
                saveNotes(notes);
            }
        });
        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}