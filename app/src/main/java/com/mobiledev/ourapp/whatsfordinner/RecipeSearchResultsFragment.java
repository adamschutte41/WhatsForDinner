package com.mobiledev.ourapp.whatsfordinner;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 *
 */
public class RecipeSearchResultsFragment extends Fragment {
    private TextView mResponseText;
    private String ResponseText;

    public RecipeSearchResultsFragment(String response){
        ResponseText = response;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v;
        Activity activity = getActivity();
        if(activity != null){
            v = inflater.inflate(R.layout.fragment_recipe_search_results, container, false);
        }
        else{
            v = inflater.inflate(R.layout.fragment_recipe_search_results, container, false);
        }
        mResponseText = v.findViewById(R.id.response_text);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mResponseText.setText(ResponseText);
    }
}