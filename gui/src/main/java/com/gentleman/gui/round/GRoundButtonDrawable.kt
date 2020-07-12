package com.gentleman.gui.round

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.AttributeSet
import com.gentleman.gui.R

/**
 * GradientDrawable 在API21以下它并没有实现onStateChange方法，
 * 而onStateChange在view中的默认实现是直接返回false，所以它就不会随着状态的变化刷新UI了。
 * 1、设置描边大小、描边颜色
 * 2、设置背景颜色
 */
class GRoundButtonDrawable : GradientDrawable() {

    /**
     * 圆角大小是否自适应为 View 的高度的一般
     */
    private var mRadiusAdjustBounds = true
    private var mStrokeWidth = 0
    private var mStrokeColors: ColorStateList? = null
    private var mFillColors: ColorStateList? = null;
    private var mGradientColors: IntArray? = null;


    fun setBgData(colors: ColorStateList?) {
        if (hasNativeStateListAPI()) {
            super.setColor(colors)
        } else {
            mFillColors = colors
            val currentColor = colors?.getColorForState(getState(), 0) ?: Color.TRANSPARENT
            setColor(currentColor)
        }

    }

    /**
     * 设置渐变颜色
     */
    fun setBgGradientData(orientation: Int, gradientColors: IntArray?) {
        mGradientColors = gradientColors;
        setOrientation(getOrientation(orientation))
        colors = gradientColors
    }

    private fun getOrientation(orientation: Int): Orientation {
        return when (orientation) {
            Orientation.BOTTOM_TOP.ordinal -> Orientation.BOTTOM_TOP
            Orientation.TR_BL.ordinal -> Orientation.TR_BL
            Orientation.RIGHT_LEFT.ordinal -> Orientation.RIGHT_LEFT
            Orientation.BR_TL.ordinal -> Orientation.BR_TL
            Orientation.BOTTOM_TOP.ordinal -> Orientation.BOTTOM_TOP
            Orientation.BL_TR.ordinal -> Orientation.BL_TR
            Orientation.LEFT_RIGHT.ordinal -> Orientation.LEFT_RIGHT
            Orientation.TL_BR.ordinal -> Orientation.TL_BR
            else -> Orientation.BOTTOM_TOP
        }
    }


    /**
     * 设置描边和描边颜色
     */
    fun setStrokeData(width: Int, colors: ColorStateList?) {
        mStrokeWidth = width;
        mStrokeColors = colors;

        if (hasNativeStateListAPI()) {
            super.setStroke(mStrokeWidth, colors)
        } else {
            val currentColor: Int = colors?.getColorForState(state, 0) ?: Color.TRANSPARENT
            setStroke(width, currentColor)
        }
    }

    fun setStrokeColors(colors: ColorStateList?) {
        setStrokeData(mStrokeWidth, colors)
    }

    /**
     * 设置圆角大小是否自动适应为 View 的高度的一半
     */
    fun setIsRadiusAdjustBounds(isRadiusAdjustBounds: Boolean) {
        mRadiusAdjustBounds = isRadiusAdjustBounds
    }


    private fun hasNativeStateListAPI(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
    }

    override fun onStateChange(stateSet: IntArray?): Boolean {
        var superRet: Boolean = super.onStateChange(stateSet)
        if (mFillColors != null) {
            val color = mFillColors!!.getColorForState(stateSet, 0)
            setColor(color)
            superRet = true
        }

        if (mStrokeColors != null) {
            val color = mStrokeColors!!.getColorForState(stateSet, 0)
            setStroke(mStrokeWidth, color)
            superRet = true
        }

        return superRet
    }


    override fun isStateful(): Boolean {
        return (mFillColors != null && mFillColors!!.isStateful
                || mStrokeColors != null && mStrokeColors!!.isStateful
                || super.isStateful())
    }


    override fun onBoundsChange(r: Rect) {
        super.onBoundsChange(r)
        if (mRadiusAdjustBounds) {
            cornerRadius = Math.min(r.width(), r.height()) / 2.toFloat()
        }
    }

    companion object {
        @JvmStatic
        fun fromAttributeSet(
            context: Context,
            attrs: AttributeSet,
            defStyleAttr: Int
        ): GRoundButtonDrawable {
            val typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.GUIRoundButton, defStyleAttr, 0)
            val colorBg =
                typedArray.getColorStateList(R.styleable.GUIRoundButton_gui_backgroundColor)
            val colorStartBg =
                typedArray.getColor(R.styleable.GUIRoundButton_gui_backgroundStartColor, -1);
            val colorEndBg =
                typedArray.getColor(R.styleable.GUIRoundButton_gui_backgroundEndColor, -1);
            val colorOrientation =
                typedArray.getInt(R.styleable.GUIRoundButton_gui_gradient_orientation, 0)

            val colorBorder =
                typedArray.getColorStateList(R.styleable.GUIRoundButton_gui_borderColor);
            val borderWidth =
                typedArray.getDimensionPixelSize(R.styleable.GUIRoundButton_gui_borderWidth, 0)
            var isRadiusAdjustBounds =
                typedArray.getBoolean(R.styleable.GUIRoundButton_gui_isRadiusAdjustBounds, false)
            val mRadius =
                typedArray.getDimensionPixelSize(R.styleable.GUIRoundButton_gui_radius, 0)
            val mRadiusTopLeft =
                typedArray.getDimensionPixelSize(R.styleable.GUIRoundButton_gui_radiusTopLeft, 0)
            val mRadiusTopRight =
                typedArray.getDimensionPixelSize(R.styleable.GUIRoundButton_gui_radiusTopRight, 0)
            val mRadiusBottomLeft = typedArray.getDimensionPixelSize(
                R.styleable.GUIRoundButton_gui_radiusBottomLeft,
                0
            )
            val mRadiusBottomRight = typedArray.getDimensionPixelSize(
                R.styleable.GUIRoundButton_gui_radiusBottomRight,
                0
            )

            typedArray.recycle()

            val drawable = GRoundButtonDrawable();

            if (colorStartBg == -1 || colorEndBg == -1) {
                drawable.setBgData(colorBg)
            } else {
                val colorArray: IntArray = intArrayOf(colorStartBg, colorEndBg)
                drawable.setBgGradientData(colorOrientation, colorArray)
            }

            drawable.setStrokeData(borderWidth, colorBorder)
            if (mRadiusTopLeft > 0 || mRadiusTopRight > 0 || mRadiusBottomLeft > 0 || mRadiusBottomRight > 0) {
                val radii = floatArrayOf(
                    mRadiusTopLeft.toFloat(), mRadiusTopLeft.toFloat(),
                    mRadiusTopRight.toFloat(), mRadiusTopRight.toFloat(),
                    mRadiusBottomRight.toFloat(), mRadiusBottomRight.toFloat(),
                    mRadiusBottomLeft.toFloat(), mRadiusBottomLeft
                        .toFloat()
                )
                drawable.cornerRadii = radii
                isRadiusAdjustBounds = false
            } else {
                drawable.cornerRadius = mRadius.toFloat()
                if (mRadius > 0) {
                    isRadiusAdjustBounds = false
                }
            }
            drawable.setIsRadiusAdjustBounds(isRadiusAdjustBounds)
            return drawable

        }
    }

}