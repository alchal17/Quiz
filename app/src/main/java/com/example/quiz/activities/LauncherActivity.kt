package com.example.quiz.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.quiz.inner_data.updateSignInStatus
import com.example.quiz.ui.theme.MainColor
import com.example.quiz.ui.theme.QuizTheme
import com.example.quiz.viewmodels.QuizUserViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LauncherActivity : ComponentActivity() {

    private val quizUserViewModel: QuizUserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        val isSignedIn = sharedPreferences.getBoolean("isSignedIn", false)
        enableEdgeToEdge()
        setContent {
            QuizTheme {
                Scaffold(
                    containerColor = MainColor,
                ) { padding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
        if (isSignedIn) {
            val userID = sharedPreferences.getInt("user_id", 0)
            lifecycleScope.launch {
                val user = quizUserViewModel.findUserById(userID)
                if (user == null) {
                    Toast.makeText(this@LauncherActivity, "Account not found", Toast.LENGTH_SHORT)
                        .show()
                    updateSignInStatus(userID, false, this@LauncherActivity)
                    val intent = Intent(this@LauncherActivity, AuthActivity::class.java)
                    startActivity(intent)
                } else {
                    val intent = Intent(this@LauncherActivity, QuizActivity::class.java)
                    intent.putExtra("user_id", userID)
                    startActivity(intent)
                }
            }
        } else {
            val intent = Intent(this@LauncherActivity, AuthActivity::class.java)
            startActivity(intent)
        }
    }
}