package com.davin0115.temppro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.davin0115.temppro.navigation.SetupNavGraph
import com.davin0115.temppro.ui.theme.TempProTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TempProTheme {
                SetupNavGraph()
            }
        }
    }
}

