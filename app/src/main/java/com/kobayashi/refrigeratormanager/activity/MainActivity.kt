package com.kobayashi.refrigeratormanager.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kobayashi.refrigeratormanager.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // トップ画面を生成
        if(savedInstanceState == null) {
            val mainTopFragment = MainTopFragment()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, mainTopFragment)
                .commit()
        }
    }

}