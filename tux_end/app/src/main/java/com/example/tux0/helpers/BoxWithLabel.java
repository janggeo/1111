package com.example.tux0.helpers;

import android.graphics.RectF;

public class BoxWithLabel {
    public RectF rect;
    public String label;

    public BoxWithLabel(RectF rect, String label){
        this.rect = rect;
        this.label = label;
    }
    public String getLabel(){return label;}


}
