package com.tatsu73.aroundimageedittextlibrary;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
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

    private static final int DEFAULT_FIRST_BACKGROUND_COLOR = Color.argb(255, 255, 0, 0);
    private static final int DEFAULT_SECOND_BACKGROUND_COLOR = Color.argb(255, 0, 255, 0);
    private static final int DEFAULT_FIRST_IMAGE_SOURCE = R.drawable.ic_close_grey_18dp;
    private static final int DEFAULT_SECOND_IMAGE_SOURCE = R.drawable.ic_check_grey_18dp;
    private Context Context;
    private EditText EditText;
    private ImageView ImageView;
    private Boolean condition = false;
    private Boolean imageCondition;
    private int firstBackGroundColor;
    private int secondBackgroundColor;
    private int firstImage;
    private int secondImage;

    public AroundImageEditText(Context context){
        super(context);
    }

    public AroundImageEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        Context = context;
        setAttribute(attrs);
    }
    public AroundImageEditText(Context context,AttributeSet attrs,int defStyleAttr){
        super(context, attrs, defStyleAttr);
        Context = context;
        setAttribute(attrs);
    }

    private void setAttribute(AttributeSet attrs){
        ImageView = new ImageView(Context);

        TypedArray typedArray = Context.obtainStyledAttributes(attrs, R.styleable.AroundImageEditText);
        firstBackGroundColor = typedArray.getColor(R.styleable.AroundImageEditText_aietFirstImageBackgroundColor, DEFAULT_FIRST_BACKGROUND_COLOR);
        firstImage = typedArray.getResourceId(R.styleable.AroundImageEditText_aietFirstImage, DEFAULT_FIRST_IMAGE_SOURCE);
        secondBackgroundColor = typedArray.getColor(R.styleable.AroundImageEditText_aietFirstImageBackgroundColor, DEFAULT_SECOND_BACKGROUND_COLOR);
        secondImage = typedArray.getResourceId(R.styleable.AroundImageEditText_aietFirstImage, DEFAULT_SECOND_IMAGE_SOURCE);
        ImageView.setImageResource(firstImage);
        ImageView.setBackgroundColor(firstBackGroundColor);
        ImageView.setBackgroundResource(R.drawable.default_first_background);

        ImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                moveImage();
            }
        });
        addView(ImageView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        typedArray.recycle();
    }

    @Override
    public final void addView(View child, int index, ViewGroup.LayoutParams layoutParams) {
        if (child instanceof EditText) {
            if (EditText != null) {
                throw new IllegalArgumentException("Can only have one EditText subview");
            }
            final LayoutParams lp = new LayoutParams(layoutParams);
            lp.gravity =Gravity.CENTER_VERTICAL;
            lp.leftMargin = ImageView.getWidth()/2;
            layoutParams = lp;
            setEditText((EditText) child);
        }
        super.addView(child, index, layoutParams);
    }

    private void moveImage(){
        float toX = EditText.getWidth();
        PropertyValuesHolder holderX;
        if(!condition){
            holderX = PropertyValuesHolder.ofFloat("translationX", 0f, toX);
        } else {
            holderX = PropertyValuesHolder.ofFloat("translationX", 0f, -toX);
        }

        PropertyValuesHolder holderAround = PropertyValuesHolder.ofFloat("rotation", 0f, 360f);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(
                ImageView, holderX, holderAround);
        objectAnimator.setDuration(700);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }
            @Override
            public void onAnimationCancel(Animator animation) {
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                if (condition&&imageCondition) {
                    changeImage();
                }
                changeCondition();
            }
        });
        objectAnimator.start();
    }

    private void changeImage(){
        final Animation ani_out = AnimationUtils.loadAnimation(Context, android.R.anim.fade_out);
        final Animation ani_in  = AnimationUtils.loadAnimation(Context, android.R.anim.fade_in);
        ani_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                ImageView.setImageResource(secondImage);
                ImageView.setBackgroundColor(secondBackgroundColor);
                ImageView.setBackgroundResource(R.drawable.default_second_background);
                ani_in.setAnimationListener(new Animation.AnimationListener() {
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
                ImageView.startAnimation(ani_in);
            }
        });
        ImageView.startAnimation(ani_out);
    }

    public void setJudge(Boolean imageCondition){
        EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (EditText.getText().toString().equals("")) {
                    imageCondition = false;
                } else {
                    imageCondition = true;
                }
            }
        });
    }

    private void setEditText(final EditText editText){
        EditText = editText;
        EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(editText.getText().toString().equals("")){
                    imageCondition = false;
                } else {
                    imageCondition = true;
                }
            }
        });

        EditText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                onFocusChanged();
            }
        });
    }

    private void onFocusChanged() {
        moveImage();
    }

    private EditText getEditText(){
        return EditText;
    }

    private void changeCondition(){
        if(condition){
            condition = false;
        } else {
            condition = true;
        }
    }
}
