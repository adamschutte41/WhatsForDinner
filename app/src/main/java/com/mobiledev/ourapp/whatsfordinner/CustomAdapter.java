package com.mobiledev.ourapp.whatsfordinner;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

class CustomAdapter implements ListAdapter, View.OnClickListener {
    ArrayList<SubjectData> arrayList;
    Context context;
    Button saveFavorite;
    EventListener eventListener;
    SubjectData subjectData;

    public interface EventListener {
        void onEvent(String name);
    }

    public CustomAdapter(Context context, ArrayList<SubjectData> arrayList, EventListener listener) {
        this.arrayList=arrayList;
        this.context=context;
        this.eventListener = listener;
    }
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }
    @Override
    public boolean isEnabled(int position) {
        return true;
    }
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        subjectData = arrayList.get(position);
        if(convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.list_item_recipe, null);
            TextView tittle = convertView.findViewById(R.id.title);
            ImageView imag = convertView.findViewById(R.id.list_image);
            tittle.setText(subjectData.SubjectName);
            saveFavorite = convertView.findViewById(R.id.deleteFavoriteRecipe);
            saveFavorite.setTag(subjectData.SubjectName);
            saveFavorite.setOnClickListener(this);
            convertView.setTag(tittle);
            Picasso.with(context)
                    .load(subjectData.Image)
                    .into(imag);
        }
        return convertView;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getViewTypeCount() {
        return arrayList.size() + 1;
    }
    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onClick(View view) {
        String tag = (String) view.getTag();
        eventListener.onEvent(tag);
    }
}