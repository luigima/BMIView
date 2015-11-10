package com.luigima.bmiview;

class BodyCategory {
    public int bodyCategory;
    public int color;
    public String text;
    public float valueMale, valueFemale;

    BodyCategory(int bodyCategory, int color, String text, float valueMale, float valueFemale) {
        this.bodyCategory = bodyCategory;
        this.color = color;
        this.text = text;
        this.valueMale = valueMale;
        this.valueFemale = valueFemale;
    }
}

