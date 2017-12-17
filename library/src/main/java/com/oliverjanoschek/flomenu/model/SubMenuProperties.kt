package com.oliverjanoschek.flomenu.model


/**
 * Created by Oliver Janoschek on 17/12/2017.
 */
data class SubMenuProperties(val icon:Int, val buttonColor:Int, val buttonRippleColor:Int, val labelText:String, val labelTextColor:Int, val labelTextColorPressed:Int, val labelBackgroundColor:Int, val labelBackgroundColorPressed:Int, val onClickListenerLambda: () -> Unit)