package com.example.quiz.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.quiz.API_PATH
import com.example.quiz.models.database_representation.Quiz
import com.example.quiz.ui.theme.SecondaryColor4

@Composable
fun QuizCard(
    quiz: Quiz,
    bottomText: String? = null,
    topOptions: List<@Composable () -> Unit>? = null
) {
    val imagePath: String? = quiz.imagePath
    Card(
        colors = CardDefaults.cardColors(containerColor = SecondaryColor4),
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
    ) {
        Box {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
                    .zIndex(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(quiz.name)
                if (imagePath != null) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("$API_PATH/images/get_image?path=$imagePath")
                                .build()
                        ),
                        contentScale = ContentScale.Crop,
                        contentDescription = "Image of the ${quiz.name} quiz",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(12))
                    )
                } else {
                    Spacer(modifier = Modifier)
                }
            }
            Box(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                Row(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(8.dp))
                        .background(color = Color.White)
                        .zIndex(2f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    topOptions?.forEach { it() }
                }
            }

        }
        bottomText?.let { Text(it) }
    }
}