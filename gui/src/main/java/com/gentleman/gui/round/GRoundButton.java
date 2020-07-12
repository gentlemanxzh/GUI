package com.gentleman.gui.round;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.gentleman.gui.GUIViewHelper;
import com.gentleman.gui.R;

/**
 * @author xuzhihua
 */
public class GRoundButton extends AppCompatButton {
    private GRoundButtonDrawable mRoundBg;


    public GRoundButton(Context context) {
        super(context);
        init(context, null, 0);
    }

    public GRoundButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, R.style.RoundButton);
    }

    public GRoundButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mRoundBg = GRoundButtonDrawable.fromAttributeSet(context, attrs, defStyleAttr);
        GUIViewHelper.setBackgroundKeepingPadding(this, mRoundBg);
    }

    @Override
    public void setBackgroundColor(int color) {
        mRoundBg.setBgData(ColorStateList.valueOf(color));
    }

    public void setBgData(ColorStateList colors){
        mRoundBg.setBgData(colors);
    }

    public void setStrokeData(int width, @Nullable ColorStateList colors) {
        mRoundBg.setStrokeData(width, colors);
    }


    public void setStrokeColors(ColorStateList colors) {
        mRoundBg.setStrokeColors(colors);
    }

}
