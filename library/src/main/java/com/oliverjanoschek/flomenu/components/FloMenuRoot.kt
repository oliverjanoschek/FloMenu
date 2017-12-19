package com.oliverjanoschek.flomenu.components

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import com.oliverjanoschek.flomenu.R
import kotlinx.android.synthetic.main.flo_menu_root.view.*

/**
* Created by Oliver Janoschek on 12/12/2017.
*/

@SuppressLint("ResourceType")
class FloMenuRoot @JvmOverloads constructor(
context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    companion object {

        val MODE_UPDATE:Int = 0
        val MODE_UP:Int = 1
        val MODE_PRESSED:Int = -1

    }

    private var fabDrawable:Int = R.drawable.ic_action_add
    private var fabDrawableToggled:Int = R.drawable.ic_action_add
    private var buttonBackgroundColor = R.color.colorPrimary
    private var buttonBackgroundRippleColor = R.color.colorButtonRippleLight
    private var cardBackgroundColor = R.color.colorTextLabelBackground
    private var cardBackgroundPressedColor = R.color.colorTextLabelBackgroundPressed
    private var labelTextColor = R.color.colorTextLabel
    private var labelText = context.getText(R.string.flo_sub_menu_label_default_text)
    private var labelTextColorPressed = R.color.colorTextLabelPressed

    private var animationToggleIn: Animation = AnimationUtils.loadAnimation(context, R.anim.toggle_in)
    private var animationToggleOut: Animation = AnimationUtils.loadAnimation(context, R.anim.toggle_out)

    init {

        LayoutInflater.from(context)
                .inflate(R.layout.flo_menu_root, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it,
                    R.styleable.FloMenuRoot, 0, 0)

            setRootButtonIcons(
                    typedArray.getResourceId(R.styleable.FloMenuRoot_R_drawable, R.drawable.ic_action_default),
                    typedArray.getResourceId(R.styleable.FloMenuRoot_R_drawable_toggled, R.drawable.ic_action_default)
            )

            setRootButtonColors(
                    typedArray.getResourceId(R.styleable.FloMenuRoot_R_color_button_background, R.color.colorAccent),
                    typedArray.getResourceId(R.styleable.FloMenuRoot_R_color_button_background_ripple, buttonBackgroundColor)
            )

            setLabelText(typedArray.getText(R.styleable.FloMenuRoot_R_text_label) ?: context.getText(R.string.flo_sub_menu_label_default_text))

            setLabelColors(typedArray.getResourceId(R.styleable.FloMenuRoot_R_color_text_label, R.color.colorTextLabel),
                    typedArray.getResourceId(R.styleable.FloMenuRoot_R_color_text_label_pressed, R.color.colorTextLabelPressed),
                    typedArray.getResourceId(R.styleable.FloMenuRoot_R_color_label_background, R.color.colorTextLabelBackground),
                    typedArray.getResourceId(R.styleable.FloMenuRoot_R_color_label_background_pressed, R.color.colorTextLabelBackgroundPressed)
            )

            floatingActionButtonRoot.isEnabled = true
            floatingActionButtonRoot.isClickable = true
            floatingActionButtonRoot.isLongClickable = false

            floatingActionButtonRoot.setOnTouchListener({ view, motionEvent ->
                onTouchRoot(view, motionEvent)
            })

            cardViewRoot.setOnTouchListener({ view, motionEvent ->
                onTouchRoot(view, motionEvent)
            })

            typedArray.recycle()

            if ( !isInEditMode ) {
                cardViewRoot.visibility = View.GONE
            }
        }

    }

    fun setRootButtonIcons(drawable: Int, drawableToggled: Int) {

        if (drawable != 0){
            fabDrawable = drawable
            floatingActionButtonRoot.setImageDrawable(ContextCompat.getDrawable(context, fabDrawable))
        }
        if (drawableToggled != 0) {
            fabDrawableToggled = drawableToggled
        }

    }

    fun setRootButtonColors(color:Int, rippleColor:Int = 0) {

        if (color != 0){
            buttonBackgroundColor = color
            floatingActionButtonRoot.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, buttonBackgroundColor))
        }
        if (rippleColor != 0) {
            buttonBackgroundRippleColor = rippleColor
            floatingActionButtonRoot.rippleColor = buttonBackgroundRippleColor
        }

    }

    fun setLabelColors(isToggle:Int = Companion.MODE_UPDATE, textColor:Int = 0, labelColor:Int = 0, textColorPressed:Int = 0, labelColorPressed:Int = 0) {

        when (isToggle) {
            Companion.MODE_UPDATE -> {
                if (textColor != 0) {
                    labelTextColor = textColor
                    textViewRoot.setTextColor(ContextCompat.getColor(context, textColor))
                }
                if (labelColor != 0) {
                    cardBackgroundColor = labelColor
                    cardViewRoot.setCardBackgroundColor(ContextCompat.getColor(context, labelColor))
                }
                when (textColorPressed) {
                    0 -> {}
                    else -> labelTextColorPressed = textColorPressed
                }
                when (labelColorPressed) {
                    0 -> {}
                    else -> cardBackgroundPressedColor = labelColorPressed
                }
            }
            Companion.MODE_UP -> {
                textViewRoot.setTextColor(ContextCompat.getColor(context, labelTextColor))
                cardViewRoot.setCardBackgroundColor(ContextCompat.getColor(context, cardBackgroundColor))
            }
            Companion.MODE_PRESSED -> {
                textViewRoot.setTextColor(ContextCompat.getColor(context, labelTextColorPressed))
                cardViewRoot.setCardBackgroundColor(ContextCompat.getColor(context, cardBackgroundPressedColor))
            }
        }

    }

    fun setLabelText(text:CharSequence = "") {

        if (labelText != "") {
            labelText = text
            textViewRoot.text = labelText
        }

    }

    fun toggleState(toggle:Boolean) {

        animationToggleIn.setAnimationListener( object : Animation.AnimationListener {
            override fun onAnimationRepeat(animationToggle: Animation?) {
            }
            override fun onAnimationStart(animationToggle: Animation?) {
            }
            override fun onAnimationEnd(animationToggle: Animation?) {
                when (toggle) {
                    true -> {
                        floatingActionButtonRoot.setImageDrawable(ContextCompat.getDrawable(context, fabDrawableToggled))
                    }
                    false -> {
                        floatingActionButtonRoot.setImageDrawable(ContextCompat.getDrawable(context, fabDrawable))
                    }
                }
              floatingActionButtonRoot.startAnimation(animationToggleOut)
            }
        })

        floatingActionButtonRoot.startAnimation(animationToggleIn)

        if (!toggle) {
            cardViewRoot.visibility = View.GONE
        } else {
            cardViewRoot.visibility = View.VISIBLE
        }

    }

    private fun onTouchRoot(view: View, motionEvent: MotionEvent) : Boolean {

        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                setLabelColors(Companion.MODE_PRESSED)
                view.isPressed = true
            }
            MotionEvent.ACTION_UP -> {
                if ( motionEvent.x >= 0 && motionEvent.x <= view.width && motionEvent.y >= 0 && motionEvent.y <= view.height) {
                    view.performClick()
                }
                view.isPressed = false
                setLabelColors(Companion.MODE_UP)
            }
            MotionEvent.ACTION_CANCEL -> {
                view.isPressed = false
            }
            MotionEvent.ACTION_MOVE -> {
                view.isPressed = false
            }
            else -> {
                view.isPressed = false
            }
        }
        return true

    }

    override fun performClick(): Boolean {

        // Calls the super implementation, which generates an AccessibilityEvent
        // and calls the onClick() listener on the view, if any
        super.performClick()

        // Handle the action for the custom click here

        return true

    }
}
