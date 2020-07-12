package com.gentleman.gui.round;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.gentleman.gui.GUIViewHelper;

/**
 * @author xuzhihua
 */
public class GRoundLinearLayout extends LinearLayout {
    public GRoundLinearLayout(Context context) {
        super(context);
        init(context,null,0);
    }

    public GRoundLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs,0);
    }

    public GRoundLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        GRoundButtonDrawable drawable = GRoundButtonDrawable.fromAttributeSet(context, attrs, defStyleAttr);
        GUIViewHelper.setBackgroundKeepingPadding(this,drawable);
    }
}
