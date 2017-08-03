package com.umarfadil.notepadlite.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.umarfadil.notepadlite.R;
import com.umarfadil.notepadlite.adapter.AboutAdapter;
import com.umarfadil.notepadlite.listener.RecyclerTouchListener;
import com.umarfadil.notepadlite.model.About;
import com.umarfadil.notepadlite.util.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class AboutActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<About> dataList = new ArrayList<>();
    private AboutAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        recyclerView = (RecyclerView) findViewById(R.id.rvAbout);
        mAdapter = new AboutAdapter(dataList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                About data = dataList.get(position);
                switch (position) {
                    case 0:
                        Toast.makeText(getApplicationContext(), data.getTitle() + " clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case 7:
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://play.google.com/store/apps/developer?id=Ronggosukowati+Studio"));
                        startActivity(intent);
                        break;
                    default:
                        //Toast.makeText(getApplicationContext(), "Invalid", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareAbout();
    }

    private void prepareAbout() {
        About data = new About("Application Name", "Notepad Lite", R.drawable.ic_action_curriculum);
        dataList.add(data);
        data = new About("Build Version", "1.1.0", R.drawable.ic_action_settings);
        dataList.add(data);
        data = new About("Email", "umarfadil.skom@gmail.com", R.drawable.ic_action_message);
        dataList.add(data);
        data = new About("Developer", "AlufStudio Project", R.drawable.ic_action_logo_black);
        dataList.add(data);
        data = new About("Copyright", "Copyright © 2017 \nAll right reserverd", R.drawable.ic_action_copyright);
        dataList.add(data);
        data = new About("Share", "bagikan aplikasi", R.drawable.ic_action_share);
        dataList.add(data);
        data = new About("Rate this Apps", "beri rating aplikasi", R.drawable.ic_action_book);
        dataList.add(data);
        data = new About("More Apps", "aplikasi lainnya", R.drawable.ic_action_knowledge);
        dataList.add(data);

        mAdapter.notifyDataSetChanged();
    }
}
