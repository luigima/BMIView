package com.luigima.bmiview;

class BodyCategory {
    public int bodyCategory;
    public int color;
    public String text;
    private float valueMale, valueFemale;

    BodyCategory(int bodyCategory, int color, String text, float valueMale, float valueFemale) {
        this.bodyCategory = bodyCategory;
        this.color = color;
        this.text = text;
        this.valueMale = valueMale;
        this.valueFemale = valueFemale;
    }

    /**
     * Returns the bmi category limit for a given gender
     *
     * @param  gender  0 = men, 1 = women
     * @return      bmi category limit
     */
    public float getLimit(int gender) {
        if(gender == 0) {
            return valueMale;
        } else if( gender == 1) {
            return valueFemale;
        }
        return 0;
    }
}

