package com.tatsu73.aroundimageedittextlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by saito.tatsuro on 2015/11/14.
 */
public class AroundImageEditText extends FrameLayout{

    Context context;
    EditText editText;
    ImageView imageView;

    public AroundImageEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setAttribute(attrs);
    }

    private void setAttribute(AttributeSet attrs){
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AroundImageEditText);

        final int defaultPadding = (int) TypedValue.applyDimension();
        final int paddingLeft = typedArray.getDimensionPixelSize(R.styleable.AroundImageEditText_aietPaddingLeft, defaultPadding);
        final int paddingTop = typedArray.getDimensionPixelSize(R.styleable.AroundImageEditText_aietPaddingTop, 0);
        final int paddingRight = typedArray.getDimensionPixelSize(R.styleable.AroundImageEditText_aietPaddingRight, 0);
        final int paddingBottom = typedArray.getDimensionPixelSize(R.styleable.AroundImageEditText_aietPaddingBottom, 0);


    }

    




}
