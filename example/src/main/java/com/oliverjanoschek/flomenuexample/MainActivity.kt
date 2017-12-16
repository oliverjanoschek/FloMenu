package com.oliverjanoschek.flomenuexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

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
    }
}
