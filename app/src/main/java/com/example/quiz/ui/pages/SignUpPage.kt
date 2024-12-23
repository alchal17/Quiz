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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.quiz.R
import com.example.quiz.ui.elements.MainTopBar
import com.example.quiz.ui.theme.Champagne
import com.example.quiz.ui.theme.DarkCyan
import com.example.quiz.ui.theme.DarkPurple
import com.example.quiz.ui.theme.DesertSand

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
        FloatingActionButton(onFloatingActionButtonClick, containerColor = DesertSand) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Go back"
            )
        }
    }, containerColor = DarkPurple, topBar = {
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
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Champagne,
                            unfocusedContainerColor = Champagne,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedLabelColor = DarkCyan,
                            unfocusedLabelColor = DarkCyan
                        ),
                        value = usernameValue,
                        onValueChange = onUsernameChange,
                        singleLine = true,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onAccountPick,
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(containerColor = Champagne)
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
                        colors = ButtonDefaults.buttonColors(containerColor = DarkCyan)
                    ) { Text("Create an account") }
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        visible = true
    }
}