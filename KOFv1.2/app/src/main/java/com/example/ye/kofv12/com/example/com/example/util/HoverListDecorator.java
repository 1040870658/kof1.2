package com.example.ye.kofv12.com.example.com.example.util;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.ye.kofv12.com.example.model.DecoratorModel;

import java.util.List;

/**
 * Created by yechen on 2017/6/16.
 */

public class HoverListDecorator extends RecyclerView.ItemDecoration {
    private DecoratorModel decoratorModel;
    private List<Integer> positions;
    private Paint mPaint;
    private int currentHoverTop;
    private int potentialHoverTop;
    private int potentialHoverText;
    private boolean hasFindPotential = false;

    public HoverListDecorator(Resources resources, List<List> datas,List<String> titles){
        init();
        decoratorModel = new DecoratorModel(resources, datas, titles);
    }
    public HoverListDecorator(DecoratorModel decoratorModel){
        this.decoratorModel = decoratorModel;
        init();
    }
    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(DecoratorModel.TEXT_SIZE);
        potentialHoverText = 0;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        positions = decoratorModel.getGroupPositions();
        for(int e : positions){
            if(position == e){
                outRect.top = DecoratorModel.HOVER_HEIGHT;
                return;
            }
        }
        outRect.set(0,0,0,0);

    }

    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        if( 0 < childCount){
            View view = parent.getChildAt(0);
            int absolutePos = parent.getChildAdapterPosition(view);
            int group = decoratorModel.getGroup(absolutePos);
            currentHoverTop = parent.getPaddingTop();
            int move = currentHoverTop + DecoratorModel.HOVER_HEIGHT - potentialHoverTop;
            if(hasFindPotential && move > 0){
                currentHoverTop -= move;
            }
            decoratorModel.setHoverText(group);
           // Log.e("groupText",decoratorModel.getHoverText());
            mPaint.setColor(decoratorModel.getGroupColor().get(group));
            c.drawRect(parent.getPaddingLeft(), currentHoverTop,
                    parent.getRight() - parent.getPaddingRight(), currentHoverTop + DecoratorModel.HOVER_HEIGHT, mPaint);
            mPaint.setColor(decoratorModel.getGroupTextColor().get(group));
            c.drawText(decoratorModel.getHoverText(),
                    parent.getPaddingLeft() + DecoratorModel.PADDING_LEFT, currentHoverTop + DecoratorModel.HOVER_TEXT_TOP , mPaint);
        }
    }
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        positions = decoratorModel.getGroupPositions();
        hasFindPotential = false;
        int childcount = parent.getChildCount();
        for(int i = 0;i < childcount;i ++) {
            if(i == 0)
                continue;
            View view = parent.getChildAt(i);
            int absolutePos = parent.getChildAdapterPosition(view);
            int group = decoratorModel.getGroup(absolutePos);
            if(decoratorModel.isFirst(absolutePos)) {
                if(!hasFindPotential) {
                    potentialHoverTop = view.getTop() - DecoratorModel.NORMAL_HEIGHT;
                    potentialHoverText = group;
                    hasFindPotential = true;
                }
                mPaint.setColor(decoratorModel.getGroupColor().get(group));
                c.drawRect(parent.getPaddingLeft(),view.getTop() - DecoratorModel.NORMAL_HEIGHT ,
                        parent.getRight() - parent.getPaddingRight(), view.getTop(), mPaint);
                mPaint.setColor(decoratorModel.getGroupTextColor().get(group));
                c.drawText(decoratorModel.getGroupText().get(group),
                        parent.getPaddingLeft() + DecoratorModel.PADDING_LEFT, view.getTop() - DecoratorModel.TEXT_TOP, mPaint);
            }
        }
    }
}
