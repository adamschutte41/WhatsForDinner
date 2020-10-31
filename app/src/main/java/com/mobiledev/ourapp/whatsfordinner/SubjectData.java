package com.mobiledev.ourapp.whatsfordinner;

/**
 * Credit to: https://www.tutorialspoint.com/how-to-display-a-list-of-images-and-text-in-a-listview-in-android
 */

class SubjectData {
    String SubjectName;
    String Link;
    String Image;
    public SubjectData(String subjectName, String link, String image) {
        this.SubjectName = subjectName;
        this.Link = link;
        this.Image = image;
    }
}