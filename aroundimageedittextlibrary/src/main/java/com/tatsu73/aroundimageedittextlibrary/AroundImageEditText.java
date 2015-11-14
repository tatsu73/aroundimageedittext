package com.tatsu73.aroundimageedittextlibrary;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by saito.tatsuro on 2015/11/14.
 */
public class AroundImageEditText extends FrameLayout{

    private Context tContext;
    private EditText tEditText;
    private ImageView tImageView;
    private int firstColor;
    private int secondColor;
    private int firstImage;
    private int secondImage;
    private static final int DEFAULT_FIRST_BACKGROUND_COLOR = Color.argb(255, 255, 0, 0);
    private static final int DEFAULT_SECOND_BACKGROUND_COLOR = Color.argb(255, 0, 255, 0);
    private static final int DEFAULT_FIRST_IMAGE_SOURCE = R.drawable.ic_close_grey_18dp;
    private static final int DEFAULT_SECOND_IMAGE_SOURCE = R.drawable.ic_check_grey_18dp;


    public AroundImageEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        tContext = context;
        setAttribute(attrs);
    }

    private void setAttribute(AttributeSet attrs){

        TypedArray typedArray = tContext.obtainStyledAttributes(attrs, R.styleable.AroundImageEditText);
        firstColor = typedArray.getColor(R.styleable.AroundImageEditText_aietFirstImageBackground, DEFAULT_FIRST_BACKGROUND_COLOR);
        secondColor = typedArray.getColor(R.styleable.AroundImageEditText_aietFirstImageBackground, DEFAULT_SECOND_BACKGROUND_COLOR);
        firstImage = typedArray.getResourceId(R.styleable.AroundImageEditText_aietFirstImage, DEFAULT_FIRST_IMAGE_SOURCE);
        secondImage = typedArray.getResourceId(R.styleable.AroundImageEditText_aietFirstImage, DEFAULT_SECOND_IMAGE_SOURCE);

        typedArray.recycle();
    }

    @Override
    public final void addView(View child, int index, ViewGroup.LayoutParams layoutParams) {
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
        super.addView(child, index, layoutParams);
    }


    private void moveImage(ImageView target, float toX){
        PropertyValuesHolder holderX = PropertyValuesHolder.ofFloat("translationX", 0f, toX);
        PropertyValuesHolder holderAround = PropertyValuesHolder.ofFloat( "rotation", 0f, 360f );
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(
                target, holderX,holderAround);

        objectAnimator.setDuration( 700 );
        objectAnimator.start();
    }

    public void changeImage(final ImageView target, final int imgRes){
        final Animation anim_out = AnimationUtils.loadAnimation(tContext, android.R.anim.fade_out);
        final Animation anim_in  = AnimationUtils.loadAnimation(tContext, android.R.anim.fade_in);
        anim_out.setAnimationListener(new Animation.AnimationListener()
        {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation)
            {
                target.setImageResource(imgRes);
                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                    }
                });
                target.startAnimation(anim_in);
            }
        });
        target.startAnimation(anim_out);
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

    private void setImage(ImageView ImageView, int resId){
        ImageView.setImageResource(resId);
    }

    private float getEditTextSize(EditText editText){
        float editSize = editText.getWidth();
        return  editSize;
    }
}
