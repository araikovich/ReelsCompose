package com.example.thescore.compose.reelscompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.thescore.compose.reelscompose.presentation.screen.ReelsScreen
import com.example.thescore.compose.reelscompose.presentation.theme.ReelsComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReelsComposeTheme(dynamicColor = false) {
                ReelsScreen()
            }
        }
    }
}