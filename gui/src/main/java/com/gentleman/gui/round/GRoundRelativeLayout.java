package com.gentleman.gui.round;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.gentleman.gui.GUIViewHelper;

/**
 * @author Gentleman
 */
public class GRoundRelativeLayout extends RelativeLayout {
    public GRoundRelativeLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    public GRoundRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public GRoundRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        GRoundButtonDrawable drawable = GRoundButtonDrawable.fromAttributeSet(context, attrs, defStyleAttr);
        GUIViewHelper.setBackgroundKeepingPadding(this, drawable);
    }
}
