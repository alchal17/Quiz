package com.example.quiz.ui.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.quiz.R
import com.example.quiz.ui.elements.MainTopBar
import com.example.quiz.ui.theme.SecondaryColor4
import com.example.quiz.ui.theme.SecondaryColor3
import com.example.quiz.ui.theme.MainColor
import com.example.quiz.ui.theme.SecondaryColor2
import com.example.quiz.ui.theme.mainTextFieldColors

@Composable
fun SignUpPage(
    usernameValue: String,
    onUsernameChange: (String) -> Unit,
    onAccountPick: () -> Unit,
    onRegistration: () -> Unit,
    onFloatingActionButtonClick: () -> Unit,
    photoUrl: String?,
    email: String?
) {
    var visible by remember { mutableStateOf(false) }

    Scaffold(floatingActionButton = {
        FloatingActionButton(onFloatingActionButtonClick, containerColor = SecondaryColor4) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Go back"
            )
        }
    }, containerColor = MainColor, topBar = {
        MainTopBar()
    }) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding), contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(visible = visible, enter = fadeIn() + slideInHorizontally()) {
                Column(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        label = { Text(if (usernameValue.length in 8..20) "Enter a username" else "Enter a username(8-20)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(50),
                        colors = mainTextFieldColors(),
                        value = usernameValue,
                        onValueChange = onUsernameChange,
                        singleLine = true,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onAccountPick,
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
                            if (photoUrl == null || photoUrl == "null") Image(
                                painter = painterResource(id = R.drawable.google_logo),
                                contentDescription = "",
                                modifier = Modifier.size(40.dp)
                            ) else Image(
                                painter = rememberAsyncImagePainter(photoUrl),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(shape = CircleShape),
                            )
                            Text(
                                email ?: "Choose a Google Account", fontSize = 16.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = onRegistration,
                        colors = ButtonDefaults.buttonColors(containerColor = SecondaryColor2)
                    ) { Text("Create an account") }
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        visible = true
    }
}