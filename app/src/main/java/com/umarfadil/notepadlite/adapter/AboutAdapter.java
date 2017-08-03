package com.umarfadil.notepadlite.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.umarfadil.notepadlite.R;
import com.umarfadil.notepadlite.model.About;

import java.util.List;

/**
 * Created by alex on 14/02/17.
 */

public class AboutAdapter extends RecyclerView.Adapter<AboutAdapter.ViewHolder> {

    private List<About> dataList;

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtViewTitle;
        public TextView txtViewSubTitle;
        public ImageView imgViewIcon;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.item_title);
            txtViewSubTitle = (TextView) itemLayoutView.findViewById(R.id.item_subtitle);
            imgViewIcon = (ImageView) itemLayoutView.findViewById(R.id.item_icon);
        }
    }

    public AboutAdapter(List<About> dataList) {
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //create new View
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_about, parent, false);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // - get data from your itemsAbout at this position
        // - replace the contents of the view with that itemsAbout
        About data = dataList.get(position);
        viewHolder.txtViewTitle.setText(data.getTitle());
        viewHolder.txtViewSubTitle.setText(data.getSubtitle());
        viewHolder.imgViewIcon.setImageResource(data.getImageUrl());
    }

    // Return the size of your itemsAbout (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataList.size();
    }
}