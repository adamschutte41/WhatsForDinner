package com.mobiledev.ourapp.whatsfordinner;

import org.json.JSONException;
import org.json.JSONObject;

public class RecipeObject {
    private String url;
    private String label;
    private String image;

    public RecipeObject(JSONObject jsonObject) throws JSONException {
        url = jsonObject.getString("url");
        label = jsonObject.getString("label");
        image = jsonObject.getString("image");
    }

    public RecipeObject(String label, String url, String image)  {
        this.url = url;
        this.label = label;
        this.image = image;
    }



    public String getLabel() {
        return label;
    }

    public String getUrl() {
        return url;
    }

    public String getImage() {
        return image;
    }

    public String toString(){
        return "\nLabel: " + label + "\n\n";
    }
}
