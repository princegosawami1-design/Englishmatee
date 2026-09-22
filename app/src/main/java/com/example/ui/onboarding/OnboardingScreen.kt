package com.example.ui.onboarding

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.OrangeContainer
import com.example.ui.theme.OrangeDark
import com.example.ui.theme.OrangePrimary
import com.example.ui.theme.OrangeSecondary
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun OnboardingScreen(
    onComplete: (name: String, confidence: String, goal: String, dailyTime: String, preferred: String) -> Unit
) {
    var step by remember { mutableStateOf(1) }

    var name by remember { mutableStateOf("") }
    var selectedConfidence by remember { mutableStateOf("Intermediate") }
    var selectedGoal by remember { mutableStateOf("Speak English confidently") }
    var selectedDailyTime by remember { mutableStateOf("20 minutes") }
    var selectedPractice by remember { mutableStateOf("Speaking with AI Tutor, Vocabulary Builder") }

    val confidenceOptions = listOf(
        "Beginner" to "I know basic words, but struggle to make full sentences.",
        "Elementary" to "I can talk about daily routines with simple structures.",
        "Intermediate" to "I understand most topics, but want higher fluency and confidence.",
        "Upper-Intermediate" to "I communicate well, but want to eliminate grammar flaws.",
        "Advanced" to "I want native-like nuance, idioms, and effortless eloquence."
    )

    val goalOptions = listOf(
        "Speak English confidently",
        "Improve grammar",
        "Improve vocabulary",
        "Improve pronunciation",
        "School and exams",
        "Job interview",
        "Daily conversation",
        "Travel English"
    )

    val timeOptions = listOf(
        "10 minutes" to "Quick & casual daily touchpoint",
        "20 minutes" to "Balanced daily learning cadence (Recommended)",
        "30 minutes" to "Accelerated speaking & grammar mastery",
        "45 minutes" to "Intensive preparation",
        "60+ minutes" to "Total immersion mode"
    )

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
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Brush.linearGradient(listOf(OrangePrimary, OrangeDark))),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.School,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "EnglishMate",
                        fontWeight = FontWeight.Black,
                        fontSize = 20.sp,
                        color = TextPrimary
                    )
                }

                // Step dots
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    (1..4).forEach { i ->
                        Box(
                            modifier = Modifier
                                .size(if (i == step) 20.dp else 8.dp, 8.dp)
                                .clip(CircleShape)
                                .background(if (i == step) OrangePrimary else if (i < step) OrangeSecondary.copy(alpha = 0.5f) else DarkBorder)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Step Content
            Box(modifier = Modifier.weight(1f)) {
                when (step) {
                    1 -> {
                        // Name & Welcome
                        Column {
                            Text(
                                text = "Welcome to EnglishMate 👋",
                                fontWeight = FontWeight.Black,
                                fontSize = 26.sp,
                                color = TextPrimary,
                                lineHeight = 32.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Your personal, adaptive English-learning companion. Let's tailor the curriculum to your goals.",
                                color = TextSecondary,
                                fontSize = 15.sp,
                                lineHeight = 22.sp
                            )

                            Spacer(modifier = Modifier.height(32.dp))

                            Text(
                                text = "What should we call you?",
                                color = TextPrimary,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                placeholder = { Text("Enter your name or nickname", color = TextMuted) },
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = OrangePrimary,
                                    unfocusedBorderColor = DarkBorder,
                                    focusedContainerColor = DarkSurface,
                                    unfocusedContainerColor = DarkSurface,
                                    focusedTextColor = TextPrimary,
                                    unfocusedTextColor = TextPrimary
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("onboarding_name_input")
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(DarkSurfaceVariant)
                                    .border(1.dp, DarkBorder, RoundedCornerShape(14.dp))
                                    .padding(16.dp)
                            ) {
                                Column {
                                    Text(
                                        text = "🎯 Next up:",
                                        color = OrangeSecondary,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "You'll answer a few quick questions followed by a 40-question Placement Test to determine your exact CEFR level (A1 to C2).",
                                        color = TextSecondary,
                                        fontSize = 13.sp,
                                        lineHeight = 19.sp
                                    )
                                }
                            }
                        }
                    }

                    2 -> {
                        // Current Confidence
                        Column {
                            Text(
                                text = "How confident do you feel in English?",
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                color = TextPrimary,
                                lineHeight = 30.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Choose the description that best reflects your current speaking & comprehension level.",
                                color = TextSecondary,
                                fontSize = 14.sp
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                items(confidenceOptions) { (title, desc) ->
                                    val isSelected = selectedConfidence == title
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(14.dp))
                                            .background(if (isSelected) OrangeContainer else DarkSurface)
                                            .border(
                                                1.dp,
                                                if (isSelected) OrangePrimary else DarkBorder,
                                                RoundedCornerShape(14.dp)
                                            )
                                            .clickable { selectedConfidence = title }
                                            .padding(16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = title,
                                                fontWeight = FontWeight.Bold,
                                                color = if (isSelected) OrangeSecondary else TextPrimary,
                                                fontSize = 16.sp
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = desc,
                                                color = TextSecondary,
                                                fontSize = 13.sp,
                                                lineHeight = 18.sp
                                            )
                                        }
                                        if (isSelected) {
                                            Box(
                                                modifier = Modifier
                                                    .size(24.dp)
                                                    .clip(CircleShape)
                                                    .background(OrangePrimary),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Check,
                                                    contentDescription = null,
                                                    tint = Color.White,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    3 -> {
                        // Learning Goal
                        Column {
                            Text(
                                text = "What is your primary English goal?",
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Your daily plan and Live AI Tutor prompts will prioritize this area.",
                                color = TextSecondary,
                                fontSize = 14.sp
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                items(goalOptions) { goal ->
                                    val isSelected = selectedGoal == goal
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(14.dp))
                                            .background(if (isSelected) OrangeContainer else DarkSurface)
                                            .border(
                                                1.dp,
                                                if (isSelected) OrangePrimary else DarkBorder,
                                                RoundedCornerShape(14.dp)
                                            )
                                            .clickable { selectedGoal = goal }
                                            .padding(horizontal = 16.dp, vertical = 14.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = goal,
                                            fontWeight = FontWeight.SemiBold,
                                            color = if (isSelected) OrangeSecondary else TextPrimary,
                                            fontSize = 15.sp
                                        )
                                        if (isSelected) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = null,
                                                tint = OrangePrimary,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    4 -> {
                        // Daily Study Time
                        Column {
                            Text(
                                text = "Daily study commitment",
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Small, consistent daily practice creates permanent fluency.",
                                color = TextSecondary,
                                fontSize = 14.sp
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                items(timeOptions) { (time, desc) ->
                                    val isSelected = selectedDailyTime == time
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(14.dp))
                                            .background(if (isSelected) OrangeContainer else DarkSurface)
                                            .border(
                                                1.dp,
                                                if (isSelected) OrangePrimary else DarkBorder,
                                                RoundedCornerShape(14.dp)
                                            )
                                            .clickable { selectedDailyTime = time }
                                            .padding(16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = time,
                                                fontWeight = FontWeight.Bold,
                                                color = if (isSelected) OrangeSecondary else TextPrimary,
                                                fontSize = 16.sp
                                            )
                                            Spacer(modifier = Modifier.height(3.dp))
                                            Text(
                                                text = desc,
                                                color = TextSecondary,
                                                fontSize = 13.sp
                                            )
                                        }
                                        if (isSelected) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = null,
                                                tint = OrangePrimary,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Bottom CTA
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (step < 4) {
                        step += 1
                    } else {
                        onComplete(
                            name,
                            selectedConfidence,
                            selectedGoal,
                            selectedDailyTime,
                            selectedPractice
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = OrangePrimary,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("onboarding_continue_btn")
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (step < 4) "Continue" else "Start 40-Question Assessment",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
