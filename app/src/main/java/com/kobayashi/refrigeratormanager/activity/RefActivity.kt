package com.kobayashi.refrigeratormanager.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.kobayashi.refrigeratormanager.R
import com.kobayashi.refrigeratormanager.model.RefrigeratorViewModel

class RefActivity : AppCompatActivity() {
    private val model : RefrigeratorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ref)

        val intent = intent
        val refName = intent.getStringExtra("refName")
        model.init(refName!!)
    }

}