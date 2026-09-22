package com.example.ui.placement

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.content.PlacementTestQuestions
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.OrangeContainer
import com.example.ui.theme.OrangePrimary
import com.example.ui.theme.OrangeSecondary
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun PlacementTestScreen(
    currentIndex: Int,
    answers: Map<Int, Int>,
    onSelectAnswer: (questionId: Int, optionIndex: Int) -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onSubmit: () -> Unit
) {
    val questions = PlacementTestQuestions.questions
    val currentQuestion = questions.getOrNull(currentIndex) ?: questions[0]
    val selectedOption = answers[currentQuestion.id]
    val totalCount = questions.size
    val progress = (currentIndex + 1).toFloat() / totalCount

    Surface(
        color = DarkBackground,
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            // Header with Back, Section Title and Progress
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = onPrevious,
                    enabled = currentIndex > 0,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Previous Question",
                        tint = if (currentIndex > 0) TextPrimary else DarkBorder
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Question ${currentIndex + 1} of $totalCount",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = TextPrimary
                    )
                    Text(
                        text = "Section: ${currentQuestion.section}",
                        fontSize = 12.sp,
                        color = OrangeSecondary,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // Answered counter pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(OrangeContainer)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "${answers.size}/$totalCount",
                        fontSize = 11.sp,
                        color = OrangeSecondary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Progress Bar
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = OrangePrimary,
                trackColor = DarkSurfaceVariant
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Question Card & Options
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                // Question Card
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(DarkSurface)
                        .border(1.dp, DarkBorder, RoundedCornerShape(16.dp))
                        .padding(20.dp)
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = currentQuestion.section.uppercase(),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                color = OrangeSecondary,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = "CEFR ${currentQuestion.levelWeight}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextMuted
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = currentQuestion.questionText,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextPrimary,
                            lineHeight = 26.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Select the best answer:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Options
                val labels = listOf("A", "B", "C", "D")
                currentQuestion.options.forEachIndexed { index, optionText ->
                    val isSelected = selectedOption == index
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (isSelected) OrangeContainer else DarkSurface)
                            .border(
                                1.dp,
                                if (isSelected) OrangePrimary else DarkBorder,
                                RoundedCornerShape(14.dp)
                            )
                            .clickable {
                                onSelectAnswer(currentQuestion.id, index)
                            }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) OrangePrimary else DarkSurfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = labels.getOrElse(index) { "$index" },
                                color = if (isSelected) Color.White else TextSecondary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Text(
                            text = optionText,
                            color = if (isSelected) TextPrimary else TextSecondary,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            fontSize = 15.sp,
                            modifier = Modifier.weight(1f)
                        )

                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = OrangePrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Navigation Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (currentIndex > 0) {
                    OutlinedButton(
                        onClick = onPrevious,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(0.4f)
                            .height(50.dp)
                    ) {
                        Text("Previous", color = TextSecondary)
                    }
                }

                Button(
                    onClick = {
                        if (currentIndex == totalCount - 1) {
                            onSubmit()
                        } else {
                            onNext()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = OrangePrimary,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .weight(if (currentIndex > 0) 0.6f else 1f)
                        .height(50.dp)
                        .testTag("placement_next_btn")
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (currentIndex == totalCount - 1) "Complete Assessment" else "Next Question",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}
