package com.example.quiz.presentation.signIn.pages

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.quiz.R
import com.example.quiz.presentation.signIn.viewmodels.SignInResult
import com.example.quiz.presentation.signIn.viewmodels.SignInViewModel
import com.example.quiz.ui.elements.MainTopBar
import com.example.quiz.ui.theme.MainColor
import com.example.quiz.ui.theme.SecondaryColor1
import com.example.quiz.ui.theme.SecondaryColor3
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignInPage(navigateToQuizPage: (Int) -> Unit, navigateToSignUpPage: () -> Unit) {
    val signInViewModel = koinViewModel<SignInViewModel>()
    val signInResult by signInViewModel.signInResult.collectAsStateWithLifecycle()

    val context = LocalContext.current

    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(signInResult) {
        if (signInResult is SignInResult.Error) {
            Toast.makeText(
                context,
                (signInResult as SignInResult.Error).message,
                Toast.LENGTH_SHORT
            ).show()
        } else if (signInResult is SignInResult.UserFound) {
            navigateToQuizPage((signInResult as SignInResult.UserFound).userId)
        }
    }

    Scaffold(
        containerColor = MainColor,
        topBar = {
            MainTopBar()
        }) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(visible, enter = fadeIn() + slideInHorizontally()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(
                        modifier = Modifier.fillMaxWidth(0.8f),
                        onClick = { signInViewModel.pickAccount() },
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(containerColor = SecondaryColor3)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(2.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.google_logo),
                                contentDescription = "Sign in with Google",
                                modifier = Modifier
                                    .size(50.dp)
                            )
                            Text("Sign in with Google", fontSize = 20.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = AnnotatedString("Not registered yet?"),
                        style = TextStyle(
                            color = SecondaryColor1,
                            fontSize = 20.sp,
                            textDecoration = TextDecoration.Underline
                        ),
                        modifier = Modifier.clickable(onClick = navigateToSignUpPage)
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) { visible = true }
}