package com.oliverjanoschek.flomenuexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.oliverjanoschek.flomenu.model.SubMenuProperties
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

/**
* Created by Oliver Janoschek on 12/17/2017.
*/

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
                FM.setRootButtonIcons(android.R.drawable.ic_dialog_alert, android.R.drawable.ic_dialog_map)
            }
        }
        btn_style_2.setOnClickListener {
            if (!FM.toggle){
                FM.setRootButtonIcons(R.drawable.ic_action_default, R.drawable.ic_action_add)
            }
        }

        btn_alpha_1.setOnClickListener {
            if (!FM.toggle){
                FM.setMenuBGColor(R.color.colorAccent)
                FM.setMenuBGAlpha(0.3f)
            }
        }

        btn_alpha_2.setOnClickListener {
            if (!FM.toggle){
                FM.setMenuBGColor(R.color.colorPrimary)
                FM.setMenuBGAlpha(0.9f)
            }
        }

        btn_createmenu_1.setOnClickListener {

            val fsmList = listOf(
                    SubMenuProperties(
                            R.drawable.abc_edit_text_material,
                            R.color.colorAccent,
                            R.color.colorButtonRippleLight,
                            resources.getString(android.R.string.dialog_alert_title),
                            R.color.colorTextLabel,
                            R.color.colorTextLabelPressed,
                            R.color.colorTextLabelBackground,
                            R.color.colorTextLabelBackgroundPressed,
                            { toast("SUB MENU 1 CLICKED!!!")}),
                    SubMenuProperties(
                            R.drawable.abc_ic_voice_search_api_material,
                            R.color.colorPrimary,
                            R.color.colorButtonRippleDark,
                            resources.getString(android.R.string.search_go),
                            R.color.colorTextLabel,
                            R.color.colorTextLabelPressed,
                            R.color.colorTextLabelBackground,
                            R.color.colorTextLabelBackgroundPressed,
                            {toast("SUB MENU 2 CLICKED!!!")}),
                    SubMenuProperties(
                            R.drawable.abc_ab_share_pack_mtrl_alpha,
                            R.color.colorAccentPressed,
                            R.color.colorAccent,
                            resources.getString(android.R.string.copyUrl),
                            R.color.colorTextLabelBackground,
                            R.color.colorTextLabelBackgroundPressed,
                            R.color.colorTextLabel,
                            R.color.colorTextLabelPressed,
                            {toast("SUB MENU 3 CLICKED!!!")})
            )

            FM.createSubMenu(fsmList)
            FM.setRootButtonText(resources.getText(R.string.password_toggle_content_description))
            FM.setRootButtonColors(
                    android.R.color.holo_blue_bright,
                    android.R.color.holo_blue_dark)
            FM.setRootButtonLabelColors(
                    android.R.color.holo_orange_dark,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_blue_bright,
                    android.R.color.holo_blue_dark)
            FM.setRootButtonIcons(android.R.drawable.ic_dialog_alert, android.R.drawable.ic_dialog_map)
        }

        btn_createmenu_2.setOnClickListener {

            val fsmList = listOf(
                    SubMenuProperties(
                            R.drawable.abc_ic_menu_cut_mtrl_alpha,
                            R.color.colorPrimaryDark,
                            R.color.colorPrimary,
                            resources.getString(android.R.string.cut),
                            R.color.colorTextLabel,
                            R.color.colorTextLabelPressed,
                            R.color.colorTextLabelBackground,
                            R.color.colorTextLabelBackgroundPressed,
                            { toast("SUB MENU 1 CLICKED!!!")}),
                    SubMenuProperties(
                            R.drawable.abc_ic_menu_copy_mtrl_am_alpha,
                            R.color.colorPrimaryDark,
                            R.color.colorPrimary,
                            resources.getString(android.R.string.copy),
                            R.color.colorTextLabel,
                            R.color.colorTextLabelPressed,
                            R.color.colorTextLabelBackground,
                            R.color.colorTextLabelBackgroundPressed,
                            {toast("SUB MENU 2 CLICKED!!!")}),
                    SubMenuProperties(
                            R.drawable.abc_ic_menu_paste_mtrl_am_alpha,
                            R.color.colorPrimaryDark,
                            R.color.colorPrimary,
                            resources.getString(android.R.string.paste),
                            R.color.colorTextLabel,
                            R.color.colorTextLabelPressed,
                            R.color.colorTextLabelBackground,
                            R.color.colorTextLabelBackgroundPressed,
                            {toast("SUB MENU 3 CLICKED!!!")})
            )

            FM.createSubMenu(fsmList)
            FM.setRootButtonText("FloMenu is awesome")
            FM.setRootButtonColors(
                    R.color.colorAccent,
                    R.color.colorButtonRippleLight)
            FM.setRootButtonLabelColors(
                    R.color.colorTextLabel,
                    R.color.colorTextLabelBackground,
                    R.color.colorTextLabelPressed,
                    R.color.colorTextLabelBackgroundPressed)
            FM.setRootButtonIcons(R.drawable.ic_action_default, R.drawable.ic_action_add)
        }
    }
}
