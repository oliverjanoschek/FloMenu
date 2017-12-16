package com.oliverjanoschek.flomenu.components

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import com.oliverjanoschek.flomenu.R
import kotlinx.android.synthetic.main.flo_menu.view.*
import kotlinx.android.synthetic.main.flo_menu_root.view.*
import kotlinx.android.synthetic.main.flo_sub_menu.view.*

class FloMenu @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var toggle = false

    init {
        LayoutInflater.from(context).inflate(R.layout.flo_menu, this, true)
        closeFloMenu(true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it,
                    R.styleable.FloMenu, 0, 0)
            
            val buttonBackgroundColor = typedArray.getColor(R.styleable.FloMenu_M_color_button_background, ContextCompat.getColor(context, R.color.colorAccent))
            val buttonBackgroundRippleColor = typedArray.getColor(R.styleable.FloMenu_M_color_button_background_ripple, buttonBackgroundColor)

            val textLabel = typedArray.getText(R.styleable.FloMenuRoot_R_text_label) ?: context.getText(R.string.flo_sub_menu_label_default_text)
            val textLabelColor = typedArray.getColor(R.styleable.FloMenuRoot_R_color_text_label, ContextCompat.getColor(context, R.color.colorTextLabel))
            val textLabelColorPressed = typedArray.getColor(R.styleable.FloMenuRoot_R_color_text_label, ContextCompat.getColor(context,R.color.colorTextLabelPressed))

            val cardBackgroundColor = typedArray.getColor(R.styleable.FloMenuRoot_R_color_label_background, ContextCompat.getColor(context, R.color.colorTextLabelBackground))
            val cardBackgroundPressedColor = typedArray.getColor(R.styleable.FloMenuRoot_R_color_label_background_pressed, ContextCompat.getColor(context, R.color.colorTextLabelBackgroundPressed))

            val fabDrawable = typedArray.getResourceId(R.styleable.FloMenuRoot_R_drawable, R.drawable.ic_action_default)
            val fabDrawableToggled = typedArray.getResourceId(R.styleable.FloMenuRoot_R_drawable_toggled, R.drawable.ic_action_default)

            typedArray.recycle()

            FMR.setAttributesFromMenu(buttonBackgroundColor, buttonBackgroundRippleColor, fabDrawable, fabDrawableToggled ,textLabel, textLabelColor, textLabelColorPressed, cardBackgroundColor, cardBackgroundPressedColor)
        }

        FBG.setOnClickListener {
            closeFloMenu()
        }

        if(!isInEditMode) {
            FBG.visibility = View.GONE
        }
    }

    private var contentView = findViewById<LinearLayout>(R.id.FloSubMenuRoot)

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if ( contentView == null ){
            super.addView(child, index, params)
        } else {
            contentView?.addView(child, index, params)
        }
    }

    val rootButton:View = FMR

    private fun getAllFSM() : List<Int>
    {
        return if (contentView != null )
        {
            var result : List<Int> = ArrayList()

            (0 until contentView.childCount)
                    .filter { contentView.getChildAt(it) is FloSubMenu }
                    .forEach { result += it }
            result
        }
        else {
            ArrayList()
        }
    }

    fun toggleFloMenu() {
        when (toggle) {
            false -> {
                openFloMenu()
            }
            true -> {
                closeFloMenu()
            }
        }
    }

    fun createClickListener(view:View, onItemClick: () -> Unit ) {
        when (view) {
            is FloMenuRoot -> {
                view.floatingActionButtonRoot.setOnClickListener {
                    onItemClick()
                    toggleFloMenu()
                }
                view.cardViewRoot.setOnClickListener {
                    onItemClick()
                    closeFloMenu()
                }
            }
            is FloSubMenu -> {
                view.floatingActionButton.setOnClickListener {
                    onItemClick()
                    closeFloMenu()
                }
                view.cardView.setOnClickListener {
                    onItemClick()
                    closeFloMenu()
                }
            }
            else -> {}
        }
    }

    private fun openFloMenu() {
        toggle = true
        FMR?.toggleLabel(true)
        FBG?.isClickable = true
        for ( fsm in getAllFSM()) {
            contentView.getChildAt(fsm).isClickable = true
        }
        setMenuVisibility(View.VISIBLE)
        setMenuAnimation(true)
    }

    private fun closeFloMenu(isInstant:Boolean = false) {

        if (!toggle) return

        FBG?.isClickable = false
        FMR?.toggleLabel(false)
        for ( fsm in getAllFSM()) {
            contentView.getChildAt(fsm).isClickable = false
        }
        toggle = when (isInstant) {
            true -> {
                setMenuVisibility(View.GONE)
                false
            }
            false -> {
                setMenuAnimation(false)
                false
            }
        }
    }

    private fun setMenuVisibility(value:Int) {
        for ( fsm in getAllFSM()) {
            contentView.getChildAt(fsm).visibility = value
        }

        FBG.visibility = value
    }

    private fun setMenuAnimation(isFadeInAnimation:Boolean) {
        if (!toggle && !isFadeInAnimation) return

        val bgAnimation : Animation = if (isFadeInAnimation) { AnimationUtils.loadAnimation(context, R.anim.fade_in) } else { AnimationUtils.loadAnimation(context, R.anim.fade_out) }
        bgAnimation.repeatCount = 1

        if (!isFadeInAnimation) {
            bgAnimation.setAnimationListener( object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {
                }
                override fun onAnimationStart(animation: Animation?) {
                }
                override fun onAnimationEnd(animation: Animation?) {
                    for ( fsm in getAllFSM()) {
                        contentView.getChildAt(fsm).visibility = View.GONE
                    }
                    FBG.visibility = View.GONE
                }
            })
        }
        for ( fsm in getAllFSM()) {

            val elementAnimation : Animation = if (isFadeInAnimation) { AnimationUtils.loadAnimation(context, R.anim.scale_in) } else { AnimationUtils.loadAnimation(context, R.anim.scale_out) }
            elementAnimation.repeatCount = 1

            contentView.getChildAt(fsm).startAnimation(elementAnimation)
        }

        FBG.startAnimation(bgAnimation)
    }
}
