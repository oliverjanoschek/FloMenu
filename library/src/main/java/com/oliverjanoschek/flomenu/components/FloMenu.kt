package com.oliverjanoschek.flomenu.components

import android.content.Context
import android.content.res.ColorStateList
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import com.oliverjanoschek.flomenu.R
import com.oliverjanoschek.flomenu.model.SubMenuProperties
import kotlinx.android.synthetic.main.flo_menu.view.*
import kotlinx.android.synthetic.main.flo_menu_root.view.*
import kotlinx.android.synthetic.main.flo_sub_menu.view.*

/**
* Created by Oliver Janoschek on 12/12/2017.
*/

class FloMenu @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var toggle = false
        private set

    private var bgAlpha = 0.9f

    init {

        LayoutInflater.from(context).inflate(R.layout.flo_menu, this, true)
        closeFloMenu(true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it,
                    R.styleable.FloMenu, 0, 0)

            val buttonBackgroundColor = typedArray.getResourceId(R.styleable.FloMenu_M_color_button_background, R.color.colorAccent)

            setRootButtonColors(
                    buttonBackgroundColor,
                    typedArray.getResourceId(R.styleable.FloMenu_M_color_button_background_ripple, buttonBackgroundColor)
            )

            setRootButtonText(typedArray.getText(R.styleable.FloMenu_M_text_label) ?: context.getText(R.string.flo_sub_menu_label_default_text))

            setRootButtonLabelColors(
                    typedArray.getResourceId(R.styleable.FloMenu_M_color_text_label, R.color.colorTextLabel),
                    typedArray.getResourceId(R.styleable.FloMenu_M_color_label_background, R.color.colorTextLabelBackground),
                    typedArray.getResourceId(R.styleable.FloMenu_M_color_text_label_pressed, R.color.colorTextLabelPressed),
                    typedArray.getResourceId(R.styleable.FloMenu_M_color_label_background_pressed, R.color.colorTextLabelBackgroundPressed)
            )

            setRootButtonIcons(
                    typedArray.getResourceId(R.styleable.FloMenu_M_drawable, R.drawable.ic_action_default),
                    typedArray.getResourceId(R.styleable.FloMenu_M_drawable_toggled, R.drawable.ic_action_add)
            )

            setMenuBGColor(typedArray.getResourceId(R.styleable.FloMenu_M_color_menu_background,R.color.colorPrimary))

            setMenuBGAlpha(typedArray.getFloat(R.styleable.FloMenu_M_color_menu_alpha, 0.9f))

            typedArray.recycle()
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

    fun setRootButtonIcons(drawable:Int, drawableToggled:Int = 0) {

        if (drawable != 0 && drawableToggled != 0) {
            FMR.setRootButtonIcons(drawable, drawableToggled)
        }

    }

    fun setRootButtonColors(color:Int, rippleColor:Int = 0) {

        if (color != 0 && rippleColor != 0) {
            FMR.setRootButtonColors(color, rippleColor)
        }

    }

    fun setRootButtonLabelColors(textColor:Int, labelColor:Int, textColorPressed:Int = 0, labelColorPressed:Int = 0) {

        if (textColor != 0 && labelColor != 0) {
            FMR.setLabelColors(FloMenuRoot.MODE_UPDATE, textColor, labelColor, textColorPressed, labelColorPressed)
        }

    }

    fun setRootButtonText(text:CharSequence) {

        if (text != "") {
            FMR.setLabelText(text)
        }

    }

    fun setMenuBGColor(color:Int = 0) {

        if ( color != 0 ) {
            FBG.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context,color))
        }

    }

    fun setMenuBGAlpha(newAlpha:Float = -1.0f) {

        if ( newAlpha != -1.0f ) {
            bgAlpha = newAlpha
        }

    }

    fun createSubMenu(list : List<SubMenuProperties>) {

        contentView.removeAllViews()

        for (submenu in list) {
            val fsm = FloSubMenu(context)
            fsm.clipChildren = false
            fsm.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)

            fsm.setProperties(submenu)

            createClickListener(fsm, submenu.onClickListenerLambda )

            contentView.addView(fsm)
            fsm.visibility = View.GONE
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

    private fun toggleFloMenu() {

        when (toggle) {
            false -> {
                openFloMenu()
            }
            true -> {
                closeFloMenu()
            }
        }

    }

    private fun openFloMenu() {

        FBG?.isClickable = true
        FBG.alpha = bgAlpha
        for ( fsm in getAllFSM()) {
            contentView.getChildAt(fsm).isClickable = true
        }
        setMenuVisibility(View.VISIBLE)
        setMenuAnimation(true)

        FMR?.toggleState(true)

        toggle = true

    }

    private fun closeFloMenu(isInstant:Boolean = false) {

        if (!toggle) return

        FBG?.isClickable = false
        FBG.alpha = 0.0f

        for ( fsm in getAllFSM()) {
            contentView.getChildAt(fsm).isClickable = false
        }
        when (isInstant) {
            true -> {
                setMenuVisibility(View.GONE)
            }
            false -> {
                setMenuAnimation(false)
            }
        }

        FMR?.toggleState(false)

        toggle = false

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
