package com.example.notepad;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
    public TextView noteTextView;
    private final NoteAdapter.OnNoteClickListener onNoteClickListener;

    public NoteViewHolder(@NonNull View itemView, NoteAdapter.OnNoteClickListener onNoteClickListener) {
        super(itemView);
        noteTextView = itemView.findViewById(R.id.noteTextView);
        this.onNoteClickListener = onNoteClickListener;
        itemView.setOnLongClickListener(this);
    }

    @Override
    public boolean onLongClick(View v) {
        onNoteClickListener.onNoteLongClicked(getAdapterPosition());
        return true;
    }
}
