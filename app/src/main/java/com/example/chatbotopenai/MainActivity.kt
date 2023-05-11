package com.example.chatbotopenai

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.chatbotopenai.databinding.ActivityMainBinding

import com.google.android.gms.ads.rewarded.RewardedAd

class MainActivity : AppCompatActivity() {
    lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(mainBinding.root)
        //MobileAds.initialize(this);


    }



}