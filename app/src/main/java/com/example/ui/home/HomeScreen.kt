package com.example.ui.home

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.DailyTask
import com.example.data.models.UserProfile
import com.example.ui.AppScreen
import com.example.ui.components.AudioPlayButton
import com.example.ui.components.CefrBadge
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceHigh
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.OrangeContainer
import com.example.ui.theme.OrangeDark
import com.example.ui.theme.OrangePrimary
import com.example.ui.theme.OrangeSecondary
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun HomeScreen(
    profile: UserProfile,
    tasks: List<DailyTask>,
    onToggleTask: (DailyTask) -> Unit,
    onNavigate: (AppScreen) -> Unit,
    onStartTutorMode: (String) -> Unit,
    onSpeak: (String, Boolean) -> Unit
) {
    val completedCount = tasks.count { it.isCompleted }
    val progress = if (tasks.isNotEmpty()) completedCount.toFloat() / tasks.size else 0f

    Surface(
        color = DarkBackground,
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(10.dp))
                // Welcome Hero Banner
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(Color(0xFF2B1607), DarkSurface)
                            )
                        )
                        .border(1.dp, OrangePrimary.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                        .padding(18.dp)
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column {
                                Text(
                                    text = "Hello, ${profile.name} 👋",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Black,
                                    color = TextPrimary
                                )
                                Spacer(modifier = Modifier.height(3.dp))
                                Text(
                                    text = "Goal: ${profile.learningGoal}",
                                    fontSize = 12.sp,
                                    color = TextSecondary
                                )
                            }
                            CefrBadge(level = profile.cefrLevel, large = true)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Primary CTA: Start Tutor Call or Take Assessment
                        if (!profile.hasTakenAssessment) {
                            Button(
                                onClick = { onNavigate(AppScreen.PLACEMENT_TEST) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = OrangePrimary,
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .testTag("home_take_assessment_btn")
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.School, contentDescription = null, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Take 40-Question Assessment", fontWeight = FontWeight.Bold)
                                }
                            }
                        } else {
                            Button(
                                onClick = {
                                    onStartTutorMode("Casual Conversation")
                                    onNavigate(AppScreen.LIVE_TUTOR)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = OrangePrimary,
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .testTag("home_live_tutor_cta")
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Practice with AI Live Tutor", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                }
                            }
                        }
                    }
                }
            }

            // Today's Personalized Learning Plan
            item {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Today's Learning Plan",
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp,
                                color = TextPrimary
                            )
                            Text(
                                text = "$completedCount of ${tasks.size} completed",
                                fontSize = 12.sp,
                                color = TextMuted
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(DarkSurfaceVariant)
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Text(
                                text = "${(progress * 100).toInt()}% Done",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (progress >= 1f) SuccessGreen else OrangeSecondary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = OrangePrimary,
                        trackColor = DarkSurfaceVariant
                    )
                }
            }

            // Task list items
            items(tasks) { task ->
                DailyTaskCard(
                    task = task,
                    onToggle = { onToggleTask(task) },
                    onClick = {
                        when (task.category.lowercase()) {
                            "grammar" -> onNavigate(AppScreen.LEARN)
                            "vocabulary" -> onNavigate(AppScreen.LEARN)
                            "sentence builder" -> onNavigate(AppScreen.PRACTICE)
                            "speaking" -> onNavigate(AppScreen.PRACTICE)
                            "reading" -> onNavigate(AppScreen.LEARN)
                            "live tutor" -> {
                                onStartTutorMode("Casual Conversation")
                                onNavigate(AppScreen.LIVE_TUTOR)
                            }
                            "pronunciation" -> onNavigate(AppScreen.PRACTICE)
                            else -> onNavigate(AppScreen.LEARN)
                        }
                    }
                )
            }

            // Quick AI Tutor Modes Carousel
            item {
                Column {
                    Text(
                        text = "AI Conversation Scenarios",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Pick a scenario to practice realistic English dialogs",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    val modes = listOf(
                        Triple("Job Interview Practice", "Behavioral & skill Q&A", "💼"),
                        Triple("Restaurant Conversation", "Ordering, dietary tips, & bill", "🍽️"),
                        Triple("Travel English", "Airports, hotels, & transit", "✈️"),
                        Triple("Speaking Confidence", "Natural storytelling & hobbies", "🎙️"),
                        Triple("Grammar Correction", "Instant constructive phrasing", "✍️")
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        modes.forEach { (modeTitle, modeSubtitle, emoji) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(DarkSurface)
                                    .border(1.dp, DarkBorder, RoundedCornerShape(14.dp))
                                    .clickable {
                                        onStartTutorMode(modeTitle)
                                        onNavigate(AppScreen.LIVE_TUTOR)
                                    }
                                    .padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(CircleShape)
                                        .background(OrangeContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = emoji, fontSize = 18.sp)
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = modeTitle,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = TextPrimary
                                    )
                                    Text(
                                        text = modeSubtitle,
                                        fontSize = 12.sp,
                                        color = TextSecondary
                                    )
                                }
                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = null,
                                    tint = TextMuted,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Word of the Day
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(DarkSurface)
                        .border(1.dp, DarkBorder, RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(OrangeContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AutoAwesome,
                                        contentDescription = null,
                                        tint = OrangeSecondary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Word of the Day",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = OrangeSecondary
                                )
                            }
                            AudioPlayButton(
                                textToSpeak = "Persevere. To continue striving toward a goal despite obstacles. If you persevere with daily English practice, your fluency will transform.",
                                onSpeak = onSpeak
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = "Persevere",
                                fontWeight = FontWeight.Black,
                                fontSize = 20.sp,
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "/ˌpɜː.sɪˈvɪər/",
                                fontSize = 13.sp,
                                color = TextMuted
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "To continue striving toward a goal despite difficulties, failure, or obstacles.",
                            fontSize = 13.sp,
                            color = TextSecondary,
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(DarkSurfaceVariant)
                                .padding(10.dp)
                        ) {
                            Text(
                                text = "\"If you persevere with daily English practice, your fluency will transform.\"",
                                fontSize = 12.sp,
                                color = TextPrimary,
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                            )
                        }
                    }
                }
            }

            // Daily Fluency Tip
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(DarkSurfaceVariant)
                        .border(1.dp, DarkBorder, RoundedCornerShape(14.dp))
                        .padding(14.dp)
                ) {
                    Row(verticalAlignment = Alignment.Top) {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = null,
                            tint = OrangeSecondary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Fluency Tip: Think in English",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = "Narrate simple daily actions in your mind (e.g., 'Now I am pouring water into the kettle'). This trains your brain to bypass mental translation!",
                                fontSize = 12.sp,
                                color = TextSecondary,
                                lineHeight = 17.sp
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun DailyTaskCard(
    task: DailyTask,
    onToggle: () -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(DarkSurface)
            .border(
                1.dp,
                if (task.isCompleted) SuccessGreen.copy(alpha = 0.4f) else DarkBorder,
                RoundedCornerShape(14.dp)
            )
            .clickable { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onToggle,
            modifier = Modifier.size(32.dp)
        ) {
            Icon(
                imageVector = if (task.isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                contentDescription = if (task.isCompleted) "Completed" else "Mark Complete",
                tint = if (task.isCompleted) SuccessGreen else TextMuted,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = task.title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = if (task.isCompleted) TextMuted else TextPrimary,
                textDecoration = if (task.isCompleted) androidx.compose.ui.text.style.TextDecoration.LineThrough else null
            )
            Spacer(modifier = Modifier.height(3.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = task.category,
                    fontSize = 11.sp,
                    color = OrangeSecondary,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = " • ${task.durationMinutes} mins",
                    fontSize = 11.sp,
                    color = TextMuted
                )
            }
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(OrangeContainer)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = "+${task.xpReward} XP",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = OrangeSecondary
            )
        }
    }
}
