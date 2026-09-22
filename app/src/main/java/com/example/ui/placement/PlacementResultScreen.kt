package com.example.ui.placement

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
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
import com.example.data.models.AssessmentResult
import com.example.ui.components.CefrBadge
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
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
fun PlacementResultScreen(
    result: AssessmentResult,
    onStartPersonalizedPlan: () -> Unit,
    onRetakeAssessment: () -> Unit
) {
    val cefrSummary = when (result.estimatedCefrLevel.uppercase()) {
        "A1" -> "Breakthrough Beginner: You can recognize familiar basic phrases and communicate simply."
        "A2" -> "Waystage Elementary: You understand simple sentences and describe everyday routines."
        "B1" -> "Threshold Intermediate: You express thoughts comfortably and handle travel situations."
        "B2" -> "Vantage Upper-Intermediate: You speak with natural spontaneity and comprehend complex texts."
        "C1" -> "Effective Operational Proficiency: You express ideas fluently with nuanced vocabulary."
        "C2" -> "Mastery: You possess effortless comprehension and native-level articulation."
        else -> "Intermediate proficiency."
    }

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
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Text(
                text = "Assessment Results 🎓",
                fontWeight = FontWeight.Black,
                fontSize = 26.sp,
                color = TextPrimary
            )
            Text(
                text = "Based on your 40-question performance across all 5 skill areas.",
                color = TextSecondary,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Main Level Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFF2E1A0A), DarkSurface)
                        )
                    )
                    .border(1.5.dp, OrangePrimary.copy(alpha = 0.6f), RoundedCornerShape(20.dp))
                    .padding(22.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "YOUR ESTIMATED CEFR LEVEL",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = OrangeSecondary,
                        letterSpacing = 1.2.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = result.estimatedCefrLevel,
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Black,
                            color = OrangePrimary
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = "Overall Score",
                                fontSize = 12.sp,
                                color = TextMuted
                            )
                            Text(
                                text = "${result.overallScore}%",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = cefrSummary,
                        fontSize = 13.sp,
                        color = TextSecondary,
                        lineHeight = 19.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Strongest & Weakest Callouts
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Strongest
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .background(DarkSurface)
                        .border(1.dp, SuccessGreen.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
                        .padding(14.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.TrendingUp,
                                contentDescription = null,
                                tint = SuccessGreen,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Strongest",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SuccessGreen
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = result.strongestSkill,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = TextPrimary
                        )
                    }
                }

                // Weakest
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .background(DarkSurface)
                        .border(1.dp, OrangeSecondary.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
                        .padding(14.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.TrendingDown,
                                contentDescription = null,
                                tint = OrangeSecondary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Weakest Area",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = OrangeSecondary
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = result.weakestSkill,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = TextPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Skill Breakdown
            Text(
                text = "Skill-wise Performance",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(10.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(DarkSurface)
                    .border(1.dp, DarkBorder, RoundedCornerShape(16.dp))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                SkillProgressRow(skill = "Grammar", score = result.grammarScore)
                SkillProgressRow(skill = "Vocabulary", score = result.vocabularyScore)
                SkillProgressRow(skill = "Sentence Formation", score = result.sentenceScore)
                SkillProgressRow(skill = "Reading", score = result.readingScore)
                SkillProgressRow(skill = "Everyday English", score = result.everydayScore)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Action CTAs
            Button(
                onClick = onStartPersonalizedPlan,
                colors = ButtonDefaults.buttonColors(
                    containerColor = OrangePrimary,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("start_personalized_plan_btn")
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Start Personalized Daily Plan",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                onClick = onRetakeAssessment,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = TextSecondary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Retake Assessment",
                        fontWeight = FontWeight.Medium,
                        color = TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SkillProgressRow(skill: String, score: Int) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = skill,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary
            )
            Text(
                text = "$score%",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = if (score >= 70) SuccessGreen else if (score >= 45) OrangeSecondary else ErrorRed
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        LinearProgressIndicator(
            progress = { (score / 100f).coerceIn(0f, 1f) },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = if (score >= 70) SuccessGreen else if (score >= 45) OrangePrimary else ErrorRed,
            trackColor = DarkSurfaceVariant
        )
    }
}
