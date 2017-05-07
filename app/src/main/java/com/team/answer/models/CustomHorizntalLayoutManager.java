package com.team.answer.models;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by abdelmageed on 05/05/17.
 */

public class CustomHorizntalLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = true;

    public CustomHorizntalLayoutManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollHorizontally() {
        return false ;
    }
}
