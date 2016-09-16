package com.example.ye.kofv12.com.example.com.example.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.widget.ImageView;

/**
 * Created by ye on 2016/9/6.
 */
public class RoundImageView extends ImageView {
    public RoundImageView(Context context) {
        super(context);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        int saveCount = canvas.getSaveCount();
        canvas.save();
        super.onDraw(canvas);
        final RectF rectF = new RectF(0, 0, getWidth(), getHeight());
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawRoundRect(rectF, 2, 2, mPaint);

        canvas.restoreToCount(saveCount);
    }
}
