package com.umarfadil.notepadlite.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.umarfadil.notepadlite.R;
import com.umarfadil.notepadlite.db.RealmDB;
import com.umarfadil.notepadlite.model.Note;
import com.umarfadil.notepadlite.util.TimeUtil;

public class SaveActivity extends AppCompatActivity {

    private EditText notepadText;
    private int id;

    public static void start(Context context, int id) {
        Intent i = new Intent(context, SaveActivity.class);
        i.putExtra(SaveActivity.class.getSimpleName(), id);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        notepadText = (EditText) findViewById(R.id.notepadText);
        id = getIntent().getExtras().getInt(SaveActivity.class.getSimpleName());
        if (id != 0) {
            //get by id
            Note note = new RealmDB(this).getById(Note.class, id);
            notepadText.setText(String.valueOf(note.getNote()));
        }
    }

    // add note
    public void addNote(String noteText) {
        Note note = new Note();
        note.setId((int) (System.currentTimeMillis()) / 1000);
        note.setNote(noteText);
        note.setDateModified(String.valueOf(TimeUtil.getUnix()));

        new RealmDB(this).add(note);
    }

    // update note
    public void updateNote(int id, String noteText){
        Note note = new Note();
        note.setId(id);
        note.setNote(noteText);
        note.setDateModified(String.valueOf(TimeUtil.getUnix()));

        new RealmDB(this).update(note);
    }

    // delete note
    public void deleteNote(int id) {
        new RealmDB(this).delete(Note.class, id);
    }

    private void createOrUpdate() {
        if (!TextUtils.isEmpty(notepadText.getText().toString())) {
            if (id == 0) {
                addNote(notepadText.getText().toString());
                Toast.makeText(getApplicationContext(), "Note disimpan", Toast.LENGTH_SHORT).show();
            } else {
                updateNote(id, notepadText.getText().toString());
                Toast.makeText(getApplicationContext(), "Note diperbarui", Toast.LENGTH_SHORT).show();
            }
        } else {
            return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_save, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case android.R.id.home:
                createOrUpdate();
                finish();
                return true;
            case R.id.action_save:
                createOrUpdate();
                finish();
                return true;
            case R.id.action_delete:
                if(id != 0) deleteNote(id);
                finish();
                return true;
            case R.id.action_share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                Note note = new RealmDB(this).getById(Note.class, id);
                String shareBody = String.valueOf(note.getNote());
                String shareSub = "I will share my Notes";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {
        createOrUpdate();
        super.onBackPressed();
    }
}