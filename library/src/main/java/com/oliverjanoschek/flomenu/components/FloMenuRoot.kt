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

    private var fabDrawable:Int = R.drawable.ic_action_add
    private var fabDrawableToggled:Int = R.drawable.ic_action_add
    private var buttonBackgroundColor = ContextCompat.getColor(context, R.color.colorPrimary)
    private var buttonBackgroundRippleColor = ContextCompat.getColor(context, R.color.colorButtonRippleLight)
    private var cardBackgroundColor = ContextCompat.getColor(context, R.color.colorTextLabelBackground)
    private var cardBackgroundPressedColor = ContextCompat.getColor(context, R.color.colorTextLabelBackgroundPressed)
    private var labelTextColor = ContextCompat.getColor(context, R.color.colorTextLabel)
    private var labelText = context.getText(R.string.flo_sub_menu_label_default_text)
    private var labelTextColorPressed = ContextCompat.getColor(context, R.color.colorTextLabelPressed)

    private var animationIn: Animation = AnimationUtils.loadAnimation(context, R.anim.scale_in)
    private var animationOut: Animation = AnimationUtils.loadAnimation(context, R.anim.scale_out)

    private var animationToggleIn: Animation = AnimationUtils.loadAnimation(context, R.anim.toggle_in)
    private var animationToggleOut: Animation = AnimationUtils.loadAnimation(context, R.anim.toggle_out)

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.flo_menu_root, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it,
                    R.styleable.FloMenuRoot, 0, 0)

            setAttributesFromMenu(
                    typedArray.getColor(R.styleable.FloMenuRoot_R_color_button_background, ContextCompat.getColor(context, R.color.colorAccent)),
                    typedArray.getColor(R.styleable.FloMenuRoot_R_color_button_background_ripple, buttonBackgroundColor),
                    typedArray.getResourceId(R.styleable.FloMenuRoot_R_drawable, R.drawable.ic_action_default),
                    typedArray.getResourceId(R.styleable.FloMenuRoot_R_drawable_toggled, R.drawable.ic_action_default),
                    typedArray.getText(R.styleable.FloMenuRoot_R_text_label) ?: context.getText(R.string.flo_sub_menu_label_default_text),
                    typedArray.getColor(R.styleable.FloMenuRoot_R_color_text_label, ContextCompat.getColor(context, R.color.colorTextLabel)),
                    typedArray.getColor(R.styleable.FloMenuRoot_R_color_text_label_pressed, ContextCompat.getColor(context,R.color.colorTextLabelPressed)),
                    typedArray.getColor(R.styleable.FloMenuRoot_R_color_label_background, ContextCompat.getColor(context, R.color.colorTextLabelBackground)),
                    typedArray.getColor(R.styleable.FloMenuRoot_R_color_label_background_pressed, ContextCompat.getColor(context, R.color.colorTextLabelBackgroundPressed))
            )

            floatingActionButtonRoot.isEnabled = true
            floatingActionButtonRoot.isClickable = true

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

    fun setAttributesFromMenu(buttonBackgroundColorAttr:Int, buttonBackgroundRippleColorAttr:Int, fabDrawableAttr:Int, fabDrawableToggledAttr:Int, labelTextAttr:CharSequence, labelTextColorAttr:Int, labelTextColorPressedAttr:Int, cardBackgroundColorAttr:Int, cardBackgroundPressedColorAttr:Int) {

        fabDrawable = fabDrawableAttr
        fabDrawableToggled = fabDrawableToggledAttr

        buttonBackgroundColor = buttonBackgroundColorAttr
        buttonBackgroundRippleColor = buttonBackgroundRippleColorAttr

        labelText = labelTextAttr

        labelTextColor = labelTextColorAttr
        labelTextColorPressed = labelTextColorPressedAttr

        cardBackgroundColor = cardBackgroundColorAttr
        cardBackgroundPressedColor = cardBackgroundPressedColorAttr

        floatingActionButtonRoot.setImageDrawable(ContextCompat.getDrawable(context, fabDrawable))
        floatingActionButtonRoot.backgroundTintList = ColorStateList.valueOf(buttonBackgroundColor)
        floatingActionButtonRoot.rippleColor = buttonBackgroundRippleColor

        textViewRoot.text = labelText
        textViewRoot.setTextColor(labelTextColor)

        setLabelBackgroundColor(labelTextColor, cardBackgroundColor)
    }

    fun setRootButtonStyle(drawable: Int, drawableToggled: Int) {
        if (drawable != 0){
            fabDrawable = drawable
        }
        if (drawableToggled != 0) {
            fabDrawableToggled = drawableToggled
        }
        floatingActionButtonRoot.setImageDrawable(ContextCompat.getDrawable(context, fabDrawable))
    }

    fun toggleLabel(toggle:Boolean) {

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

        val animation = when (toggle) {
            true -> {
                animationIn
            }
            false -> {
                animationOut
            }
        }

        animation.repeatCount = 1

        if (!toggle) {
            animation.setAnimationListener( object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {
                }
                override fun onAnimationStart(animation: Animation?) {
                }
                override fun onAnimationEnd(animation: Animation?) {
                    cardViewRoot.visibility = View.GONE
                }
            })
        } else {
            cardViewRoot.visibility = View.VISIBLE
        }

        cardViewRoot.startAnimation(animation)
    }

    private fun setLabelBackgroundColor(textColor:Int, backgroundColor:Int)
    {
        textViewRoot.setTextColor(textColor)
        cardViewRoot.setCardBackgroundColor(backgroundColor)
    }

    private fun onTouchRoot(view: View, motionEvent: MotionEvent) : Boolean {

        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                setLabelBackgroundColor(labelTextColorPressed, cardBackgroundPressedColor)
                view.isPressed = true
            }
            MotionEvent.ACTION_UP -> {
                if ( motionEvent.x >= 0 && motionEvent.x <= view.width && motionEvent.y >= 0 && motionEvent.y <= view.height) {
                    view.performClick()
                }
                view.isPressed = false
                setLabelBackgroundColor(labelTextColor, cardBackgroundColor)
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
