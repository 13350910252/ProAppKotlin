package com.yinp.proappkotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yinp.proappkotlin.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val bind: ActivityTestBinding = ActivityTestBinding.inflate(layoutInflater)
//        val bind: ActivityTestBinding = ActivityTestBinding.inflate(layoutInflater)
//        val bind: ActivityTestBinding = DataBindingUtil.setContentView(this, R.layout.activity_test)
//        setContentView(bind.root)
        setContentView(R.layout.activity_test)
    }
}