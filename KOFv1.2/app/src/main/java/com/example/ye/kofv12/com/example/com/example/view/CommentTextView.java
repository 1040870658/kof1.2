package com.example.ye.kofv12.com.example.com.example.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TextView;

import com.example.ye.kofv12.R;

/**
 * Created by yechen on 2017/6/3.
 */

public class CommentTextView extends TextView {
    public static final float DEFAULT_STROKE_WIDTH = 1.0f;    // 默认边框宽度, 1dp
    public static final float DEFAULT_CORNER_RADIUS = 2.0f;   // 默认圆角半径, 2dp
    public static final float DEFAULT_LR_PADDING = 6f;      // 默认左右内边距
    public static final float DEFAULT_TB_PADDING = 2f;      // 默认上下内边距
    private float strokeWidth = 3f;
    private float cornerRadius = 3f;
    private RectF mRect;
    private Paint mPaint = new Paint();
    private int color;
    public CommentTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        strokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                DEFAULT_STROKE_WIDTH, displayMetrics);
        cornerRadius =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                DEFAULT_CORNER_RADIUS, displayMetrics);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CommentTextView);
        strokeWidth = ta.getDimensionPixelSize(R.styleable.CommentTextView_strokeWidth,(int)strokeWidth);
        cornerRadius = ta.getDimensionPixelSize(R.styleable.CommentTextView_cornerRadius,(int)cornerRadius);
        color = ta.getColor(R.styleable.CommentTextView_textcolor,Color.WHITE);
        ta.recycle();
        mRect = new RectF();
        int paddingLeft = getPaddingLeft() == 0 ? (int)DEFAULT_LR_PADDING : getPaddingLeft();
        paddingLeft = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, paddingLeft, displayMetrics);
        int paddingRight = getPaddingRight() == 0 ? (int)DEFAULT_LR_PADDING:getPaddingRight();
        paddingRight = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, paddingRight, displayMetrics);
        int paddingTop = getPaddingTop() == 0 ?(int)DEFAULT_TB_PADDING : getPaddingTop();
        paddingTop = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, paddingTop, displayMetrics);
        int paddingBottom = getPaddingBottom() == 0 ?  (int)DEFAULT_TB_PADDING : getPaddingBottom();
        paddingBottom = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, paddingBottom, displayMetrics);
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    public CommentTextView(Context context) {
        super(context);
    }

    public CommentTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        strokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                DEFAULT_STROKE_WIDTH, displayMetrics);
        cornerRadius =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                DEFAULT_CORNER_RADIUS, displayMetrics);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CommentTextView);
        strokeWidth = ta.getDimensionPixelSize(R.styleable.CommentTextView_strokeWidth,(int)strokeWidth);
        cornerRadius = ta.getDimensionPixelSize(R.styleable.CommentTextView_cornerRadius,(int)cornerRadius);
        color = ta.getColor(R.styleable.CommentTextView_textcolor,Color.WHITE);
        ta.recycle();
        mRect = new RectF();
        int paddingLeft = getPaddingLeft() == 0 ? (int)DEFAULT_LR_PADDING : getPaddingLeft();
        paddingLeft = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, paddingLeft, displayMetrics);
        int paddingRight = getPaddingRight() == 0 ? (int)DEFAULT_LR_PADDING:getPaddingRight();
        paddingRight = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, paddingRight, displayMetrics);
        int paddingTop = getPaddingTop() == 0 ?(int)DEFAULT_TB_PADDING : getPaddingTop();
        paddingTop = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, paddingTop, displayMetrics);
        int paddingBottom = getPaddingBottom() == 0 ?  (int)DEFAULT_TB_PADDING : getPaddingBottom();
        paddingBottom = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, paddingBottom, displayMetrics);
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.STROKE);     // 空心效果
        mPaint.setAntiAlias(true);               // 设置画笔为无锯齿
        mPaint.setStrokeWidth(strokeWidth);      // 线宽
        mPaint.setColor(color);

        // 画空心圆角矩形
        mRect.left = mRect.top = 0.5f * strokeWidth;
        mRect.right = getMeasuredWidth() - strokeWidth;
        mRect.bottom = getMeasuredHeight() - strokeWidth;
        canvas.drawRoundRect(mRect, cornerRadius, cornerRadius, mPaint);
        setTextColor(color);
    }
}
