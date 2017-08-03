package com.umarfadil.notepadlite.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.umarfadil.notepadlite.R;
import com.umarfadil.notepadlite.adapter.NotepadListAdapter;
import com.umarfadil.notepadlite.db.RealmDB;
import com.umarfadil.notepadlite.listener.RecyclerViewItemClickListener;
import com.umarfadil.notepadlite.model.Note;
import com.umarfadil.notepadlite.util.DividerItemDecoration;

import io.realm.RealmResults;
import io.realm.Sort;

public class MainActivity extends AppCompatActivity implements RecyclerViewItemClickListener {

    private RecyclerView lvNotepad;
    private AdView mAdView;

    private NotepadListAdapter notepadListAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Ads
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                // Check the LogCat to get your test device ID
                .addTestDevice("BECC55A5F44BB09BB9EC621C29395E65")
                .build();
        mAdView.loadAd(adRequest);

        //AdsListener
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Toast.makeText(getApplicationContext(), "Ad is loaded!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClosed() {
                Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Toast.makeText(getApplicationContext(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                Toast.makeText(getApplicationContext(), "Ad is opened!", Toast.LENGTH_SHORT).show();
            }
        });

        //Recyclerview
        lvNotepad = (RecyclerView) findViewById(R.id.lvNote);
        notepadListAdapter = new NotepadListAdapter(this);
        linearLayoutManager = new LinearLayoutManager(this);

        lvNotepad.setLayoutManager(linearLayoutManager);
        lvNotepad.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        lvNotepad.setAdapter(notepadListAdapter);

        //Floating Action Button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                SaveActivity.start(MainActivity.this, 0);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    private void loadData() {
        if (retrieve() != null)
            notepadListAdapter.setNotepadLists(retrieve());
    }

    public RealmResults<Note> retrieve() {
        RealmResults<Note> result = (RealmResults<Note>) new RealmDB(this).getAllData(Note.class);
        result.sort("dateModified", Sort.DESCENDING);
        return result;
    }

    @Override
    public void onItemClick(int position, View view) {
        SaveActivity.start(this, notepadListAdapter.getItem(position).getId());
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
