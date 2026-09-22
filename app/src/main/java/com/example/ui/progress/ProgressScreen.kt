package com.example.ui.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.AchievementItem
import com.example.data.models.SavedCorrection
import com.example.data.models.UserProfile
import com.example.ui.components.CefrBadge
import com.example.ui.theme.AccentAmber
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceHigh
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.OrangeContainer
import com.example.ui.theme.OrangeDark
import com.example.ui.theme.OrangePrimary
import com.example.ui.theme.OrangeSecondary
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun ProgressScreen(
    profile: UserProfile,
    corrections: List<SavedCorrection>
) {
    val achievements = listOf(
        AchievementItem(
            id = "a1",
            title = "CEFR Placement Assessed",
            description = "Completed the comprehensive 40-question English evaluation",
            icon = "🎓",
            isUnlocked = profile.hasTakenAssessment
        ),
        AchievementItem(
            id = "a2",
            title = "First Live Tutor Session",
            description = "Held a live spoken conversation with AI English tutor",
            icon = "🎙️",
            isUnlocked = profile.tutorSessionsCount > 0
        ),
        AchievementItem(
            id = "a3",
            title = "Consistent Learner",
            description = "Maintained a 3+ day learning streak",
            icon = "🔥",
            isUnlocked = profile.streak >= 3
        ),
        AchievementItem(
            id = "a4",
            title = "Century Club",
            description = "Earned over 100 XP from daily lessons and speaking",
            icon = "⭐",
            isUnlocked = profile.xp >= 100
        ),
        AchievementItem(
            id = "a5",
            title = "Speaking Champion",
            description = "Accumulated 15+ minutes of active English speaking practice",
            icon = "🗣️",
            isUnlocked = profile.totalSpeakingMinutes >= 15
        )
    )

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
                Text(
                    text = "Your English Progress 📈",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    color = TextPrimary
                )
                Text(
                    text = "Track your CEFR benchmarks, speaking minutes, and mastery",
                    fontSize = 13.sp,
                    color = TextSecondary
                )
            }

            // Key Stats Grid
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MetricBox(
                        title = "Streak",
                        value = "${profile.streak} Days",
                        subtitle = "Active streak",
                        icon = Icons.Default.LocalFireDepartment,
                        color = AccentAmber,
                        modifier = Modifier.weight(1f)
                    )
                    MetricBox(
                        title = "Total XP",
                        value = "${profile.xp}",
                        subtitle = "Experience points",
                        icon = Icons.Default.AutoAwesome,
                        color = OrangeSecondary,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MetricBox(
                        title = "Speaking",
                        value = "${profile.totalSpeakingMinutes} min",
                        subtitle = "Voice practice",
                        icon = Icons.Default.Mic,
                        color = OrangePrimary,
                        modifier = Modifier.weight(1f)
                    )
                    MetricBox(
                        title = "Tutor Calls",
                        value = "${profile.tutorSessionsCount}",
                        subtitle = "Live sessions",
                        icon = Icons.Default.RecordVoiceOver,
                        color = SuccessGreen,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // 7-Day Streak Bar
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
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "7-Day Activity Rhythm",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = TextPrimary
                            )
                            Text(
                                text = "${profile.streak} Day Streak 🔥",
                                fontSize = 12.sp,
                                color = OrangeSecondary,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        val days = listOf("M", "T", "W", "T", "F", "S", "S")
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            days.forEachIndexed { i, d ->
                                val isLearned = i < (profile.streak.coerceAtMost(7))
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Box(
                                        modifier = Modifier
                                            .size(34.dp)
                                            .clip(CircleShape)
                                            .background(if (isLearned) OrangePrimary else DarkSurfaceVariant)
                                            .border(
                                                1.dp,
                                                if (isLearned) OrangeDark else DarkBorder,
                                                CircleShape
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (isLearned) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = d, fontSize = 11.sp, color = TextMuted)
                                }
                            }
                        }
                    }
                }
            }

            // Skill Breakdown
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(DarkSurface)
                        .border(1.dp, DarkBorder, RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Skill Competency Breakdown",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = TextPrimary
                            )
                            CefrBadge(level = profile.cefrLevel)
                        }

                        CompetencyBar(label = "Grammar Accuracy", score = profile.grammarScore)
                        CompetencyBar(label = "Vocabulary Breadth", score = profile.vocabScore)
                        CompetencyBar(label = "Sentence Formation", score = profile.sentenceScore)
                        CompetencyBar(label = "Reading Comprehension", score = profile.readingScore)
                        CompetencyBar(label = "Everyday Colloquial English", score = profile.everydayScore)
                    }
                }
            }

            // Recent Saved Corrections
            item {
                Column {
                    Text(
                        text = "Recent Corrections from AI Tutor",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Review the phrasing tips your AI tutor shared during live calls",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    if (corrections.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(DarkSurface)
                                .border(1.dp, DarkBorder, RoundedCornerShape(12.dp))
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No corrections recorded yet. Talk to the AI Tutor to get real-time feedback!",
                                fontSize = 13.sp,
                                color = TextMuted,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            corrections.take(5).forEach { corr ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(DarkSurface)
                                        .border(1.dp, DarkBorder, RoundedCornerShape(12.dp))
                                        .padding(12.dp)
                                ) {
                                    Column {
                                        Text(
                                            text = "You said: \"${corr.youSaid}\"",
                                            fontSize = 12.sp,
                                            color = ErrorRed
                                        )
                                        Text(
                                            text = "Better: \"${corr.better}\"",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = SuccessGreen
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = "Why: ${corr.why}",
                                            fontSize = 11.sp,
                                            color = TextSecondary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Achievements
            item {
                Column {
                    Text(
                        text = "Achievements & Milestones",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        achievements.forEach { ach ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(DarkSurface)
                                    .border(
                                        1.dp,
                                        if (ach.isUnlocked) OrangePrimary.copy(alpha = 0.4f) else DarkBorder,
                                        RoundedCornerShape(12.dp)
                                    )
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(CircleShape)
                                        .background(if (ach.isUnlocked) OrangeContainer else DarkSurfaceVariant),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = ach.icon, fontSize = 18.sp)
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = ach.title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = if (ach.isUnlocked) TextPrimary else TextMuted
                                    )
                                    Text(
                                        text = ach.description,
                                        fontSize = 11.sp,
                                        color = TextSecondary
                                    )
                                }

                                if (ach.isUnlocked) {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(SuccessGreen.copy(alpha = 0.2f))
                                            .padding(horizontal = 6.dp, vertical = 3.dp)
                                    ) {
                                        Text("Unlocked", fontSize = 10.sp, color = SuccessGreen, fontWeight = FontWeight.Bold)
                                    }
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = null,
                                        tint = DarkBorder,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
private fun MetricBox(
    title: String,
    value: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(DarkSurface)
            .border(1.dp, DarkBorder, RoundedCornerShape(14.dp))
            .padding(14.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = title, fontSize = 11.sp, color = TextMuted, fontWeight = FontWeight.SemiBold)
                Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = value, fontSize = 20.sp, fontWeight = FontWeight.Black, color = TextPrimary)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = subtitle, fontSize = 11.sp, color = TextSecondary)
        }
    }
}

@Composable
private fun CompetencyBar(label: String, score: Int) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, fontSize = 12.sp, color = TextSecondary)
            Text(text = "$score%", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { (score / 100f).coerceIn(0f, 1f) },
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = if (score >= 70) SuccessGreen else if (score >= 45) OrangePrimary else ErrorRed,
            trackColor = DarkSurfaceVariant
        )
    }
}
