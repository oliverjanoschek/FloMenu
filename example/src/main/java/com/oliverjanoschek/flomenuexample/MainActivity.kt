package com.oliverjanoschek.flomenuexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.oliverjanoschek.flomenu.model.SubMenuProperties
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FM.createClickListener(FM.rootButton, {
            when (FM.toggle) {
                true -> {}
                false -> {}
            }
        })

        FM.createClickListener(FSM_0, {
        })
        FM.createClickListener(FSM_1, {
        })
        FM.createClickListener(FSM_2, {
        })

        btn_style_1.setOnClickListener {
            if (!FM.toggle){
                FM.setRootButtonStyle(android.R.drawable.ic_dialog_alert, android.R.drawable.ic_dialog_map)
            }
        }
        btn_style_2.setOnClickListener {
            if (!FM.toggle){
                FM.setRootButtonStyle(R.drawable.ic_action_default, R.drawable.ic_action_add)
            }
        }

        btn_alpha_1.setOnClickListener {
            if (!FM.toggle){
                FM.setMenuBGAlpha(0.3f)
            }
        }

        btn_alpha_2.setOnClickListener {
            if (!FM.toggle){
                FM.setMenuBGAlpha(0.9f)
            }
        }

        btn_createmenu_1.setOnClickListener {

            val fsmList = listOf(
                    SubMenuProperties(
                            R.drawable.abc_ic_menu_cut_mtrl_alpha,
                            R.color.colorAccent,
                            R.color.colorButtonRippleLight,
                            resources.getString(android.R.string.cut),
                            R.color.colorTextLabel,
                            R.color.colorTextLabelPressed,
                            R.color.colorTextLabelBackground,
                            R.color.colorTextLabelBackgroundPressed,
                            { toast("SUB MENU 1 CLICKED!!!")}),
                    SubMenuProperties(
                            R.drawable.abc_ic_menu_copy_mtrl_am_alpha,
                            R.color.colorPrimary,
                            R.color.colorButtonRippleDark,
                            resources.getString(android.R.string.copy),
                            R.color.colorTextLabel,
                            R.color.colorTextLabelPressed,
                            R.color.colorTextLabelBackground,
                            R.color.colorTextLabelBackgroundPressed,
                            {toast("SUB MENU 2 CLICKED!!!")}),
                    SubMenuProperties(
                            R.drawable.abc_ic_menu_paste_mtrl_am_alpha,
                            R.color.colorAccentPressed,
                            R.color.colorAccent,
                            resources.getString(android.R.string.paste),
                            R.color.colorTextLabelBackground,
                            R.color.colorTextLabelBackgroundPressed,
                            R.color.colorTextLabel,
                            R.color.colorTextLabelPressed,
                            {toast("SUB MENU 3 CLICKED!!!")})
            )

            FM.createSubMenu(fsmList)
        }
    }
}
