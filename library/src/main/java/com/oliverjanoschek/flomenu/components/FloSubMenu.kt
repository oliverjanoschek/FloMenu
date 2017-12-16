package com.oliverjanoschek.flomenu.components

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import com.oliverjanoschek.flomenu.R
import kotlinx.android.synthetic.main.flo_sub_menu.view.*

@SuppressLint("ResourceType")
class FloSubMenu @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var fabDrawable:Int = R.drawable.ic_action_add
    private var buttonBackgroundColor = ContextCompat.getColor(context, R.color.colorPrimary)
    private var buttonBackgroundRippleColor = ContextCompat.getColor(context, R.color.colorButtonRippleLight)
    private var cardBackgroundColor = ContextCompat.getColor(context, R.color.colorTextLabelBackground)
    private var cardBackgroundPressedColor = ContextCompat.getColor(context, R.color.colorTextLabelBackgroundPressed)
    private var textLabelColor = ContextCompat.getColor(context, R.color.colorTextLabel)
    private var textLabelColorPressed = ContextCompat.getColor(context, R.color.colorTextLabelPressed)

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.flo_sub_menu, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it,
                    R.styleable.FloSubMenu, 0, 0)

            buttonBackgroundColor = typedArray.getColor(R.styleable.FloSubMenu_S_color_button_background, ContextCompat.getColor(context, R.color.colorAccent))
            buttonBackgroundRippleColor = typedArray.getColor(R.styleable.FloSubMenu_S_color_button_background_ripple, buttonBackgroundColor)
            textLabelColor = typedArray.getColor(R.styleable.FloSubMenu_S_color_text_label, ContextCompat.getColor(context, R.color.colorTextLabel))
            textLabelColorPressed = typedArray.getColor(R.styleable.FloSubMenu_S_color_text_label_pressed, ContextCompat.getColor(context,R.color.colorTextLabelPressed))
            cardBackgroundColor = typedArray.getColor(R.styleable.FloSubMenu_S_color_label_background, ContextCompat.getColor(context, R.color.colorTextLabelBackground))
            cardBackgroundPressedColor = typedArray.getColor(R.styleable.FloSubMenu_S_color_label_background_pressed, ContextCompat.getColor(context, R.color.colorTextLabelBackgroundPressed))

            val textLabel = typedArray.getText(R.styleable.FloSubMenu_S_text_label)
            textView.text = textLabel ?: context.getText(R.string.flo_sub_menu_label_default_text)
            textView.setTextColor(textLabelColor)
            
            fabDrawable = R.styleable.FloSubMenu_S_drawable
            
            floatingActionButton.setImageDrawable(typedArray.getDrawable(fabDrawable))
            floatingActionButton.backgroundTintList = ColorStateList.valueOf(buttonBackgroundColor)
            floatingActionButton.rippleColor = buttonBackgroundRippleColor
            floatingActionButton.isEnabled = true
            floatingActionButton.isClickable = true


            setLabelBackgroundColor(textLabelColor, cardBackgroundColor)

            floatingActionButton.setOnTouchListener({ view, motionEvent ->
                onTouchFSM(view, motionEvent)
            })

            cardView.setOnTouchListener({ view, motionEvent ->
                onTouchFSM(view, motionEvent)
            })

            typedArray.recycle()

            if ( !isInEditMode ) {
                visibility = View.GONE
            }
        }
    }

    private fun setLabelBackgroundColor(textColor:Int, backgroundColor:Int)
    {
        textView.setTextColor(textColor)
        cardView.setCardBackgroundColor(backgroundColor)
    }

    private fun onTouchFSM(view: View, motionEvent: MotionEvent) : Boolean {
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                setLabelBackgroundColor(textLabelColorPressed, cardBackgroundPressedColor)
                view.isPressed = true
            }
            MotionEvent.ACTION_UP -> {
                if ( motionEvent.x >= 0 && motionEvent.x <= view.width && motionEvent.y >= 0 && motionEvent.y <= view.height) {
                    view.performClick()
                }
                view.isPressed = false
                setLabelBackgroundColor(textLabelColor, cardBackgroundColor)
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
