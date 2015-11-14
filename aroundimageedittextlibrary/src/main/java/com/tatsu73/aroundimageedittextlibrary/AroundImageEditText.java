package com.tatsu73.aroundimageedittextlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by saito.tatsuro on 2015/11/14.
 */
public class AroundImageEditText extends FrameLayout{

    Context tContext;
    EditText tEditText;
    ImageView tImageView;

    public AroundImageEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        tContext = context;
        setAttribute(attrs);
    }

    private void setAttribute(AttributeSet attrs){
        final TypedArray typedArray = tContext.obtainStyledAttributes(attrs, R.styleable.AroundImageEditText);

        final int defaultPadding = (int) TypedValue.applyDimension();
        final int paddingLeft = typedArray.getDimensionPixelSize(R.styleable.AroundImageEditText_aietPaddingLeft, defaultPadding);
        final int paddingTop = typedArray.getDimensionPixelSize(R.styleable.AroundImageEditText_aietPaddingTop, 0);
        final int paddingRight = typedArray.getDimensionPixelSize(R.styleable.AroundImageEditText_aietPaddingRight, 0);
        final int paddingBottom = typedArray.getDimensionPixelSize(R.styleable.AroundImageEditText_aietPaddingBottom, 0);


        typedArray.recycle();
    }

    @Override
    public final void addView(View child,int index,ViewGroup.LayoutParams layoutParams) {
        if (child instanceof EditText) {
            if (tEditText != null) {
                throw new IllegalArgumentException("Can only have one EditText subview");
            }

            final LayoutParams lp = new LayoutParams(layoutParams);
            lp.gravity =Gravity.CENTER_VERTICAL;
            lp.leftMargin = tImageView.getWidth()/2;
            layoutParams = lp;

            setEditText((EditText) child);
        }
        super.addView(child,index,layoutParams);
    }


    /*
    private float[] getImageSize(ImageView imageView){
        float[] floats = new float[1];

        Rect rect = imageView.getDrawable().getBounds();
        float scaleX = (float) imageView.getWidth() / (float) rect.width();
        float scaleY = (float) imageView.getHeight() / (float) rect.height();
        float scale = Math.min(scaleX, scaleY);
        float width = scale * rect.width();
        float height = scale * rect.height();

        floats[0] = width;
        floats[1] =height;

        return floats;
    }
    */

    private void setEditText(EditText editText){
        tEditText = editText;
        tEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tEditText.getText().toString();
            }
        });

    }




}
