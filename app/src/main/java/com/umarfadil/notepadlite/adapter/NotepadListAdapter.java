package com.umarfadil.notepadlite.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.umarfadil.notepadlite.R;
import com.umarfadil.notepadlite.listener.RecyclerViewItemClickListener;
import com.umarfadil.notepadlite.model.Note;
import com.umarfadil.notepadlite.util.TimeUtil;

import io.realm.RealmResults;

/**
 * Created by alex on 10/02/17.
 */

public class NotepadListAdapter extends RecyclerView.Adapter<NotepadListAdapter.NotepadViewHolder> {

    private RealmResults<Note> notepadLists;
    private RecyclerViewItemClickListener recyclerViewItemClickListener;

    public NotepadListAdapter(RecyclerViewItemClickListener recyclerViewItemClickListener) {
        this.recyclerViewItemClickListener = recyclerViewItemClickListener;
    }

    public void setNotepadLists(RealmResults<Note> notepadLists) {
        this.notepadLists = notepadLists;
        notifyDataSetChanged();
    }

    public Note getItem(int position) {
        return notepadLists.get(position);
    }

    @Override
    public NotepadListAdapter.NotepadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_notepadlite, parent, false);

        final NotepadViewHolder notepadViewHolder = new NotepadViewHolder(view);
        notepadViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPos = notepadViewHolder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (recyclerViewItemClickListener != null) {
                        recyclerViewItemClickListener.onItemClick(adapterPos, notepadViewHolder.itemView);
                    }
                }
            }
        });
        return notepadViewHolder;
    }

    @Override
    public void onBindViewHolder(NotepadViewHolder holder, int position) {
        final Note note = notepadLists.get(position);
        holder.note.setText(note.getNote().length() > 50 ? note.getNote().substring(0, 50) : note.getNote());
        holder.date.setText(TimeUtil.unixToTimeAgo(note.getDateModified()));
    }

    @Override
    public int getItemCount() {
        return notepadLists.size();
    }

    public class NotepadViewHolder extends RecyclerView.ViewHolder {

        TextView note;
        TextView date;

        public NotepadViewHolder(View itemView) {
            super(itemView);

            note = (TextView) itemView.findViewById(R.id.note);
            date = (TextView) itemView.findViewById(R.id.date);
        }
    }
}
