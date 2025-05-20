package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class ItemDecoration extends RecyclerView.ItemDecoration {

    private final Paint paint;

    public ItemDecoration() {
        paint = new Paint();
        paint.setColor(0xFFCCCCCC);  // Gri renk
        paint.setStrokeWidth(2);     // Kalınlık
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        int childCount = parent.getChildCount();
        float percentage = 0.95f; // %80 genişlik

        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            float fullWidth = child.getWidth();
            float lineWidth = fullWidth * percentage;
            float startX = child.getLeft() + (fullWidth - lineWidth) / 2f;
            float endX = startX + lineWidth;
            float y = child.getBottom();

            c.drawLine(startX, y, endX, y, paint);
        }
    }
}
