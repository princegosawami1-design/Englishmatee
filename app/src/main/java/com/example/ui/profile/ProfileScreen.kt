package com.example.ui.profile

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
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.data.models.SavedWord
import com.example.data.models.UserProfile
import com.example.ui.components.AudioPlayButton
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
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun ProfileScreen(
    profile: UserProfile,
    savedWords: List<SavedWord>,
    onRetakePlacement: () -> Unit,
    onDeleteSavedWord: (Int) -> Unit,
    onResetProgress: () -> Unit,
    onSpeak: (String, Boolean) -> Unit
) {
    var showResetDialog by remember { mutableStateOf(false) }
    var slowerAudioPreference by remember { mutableStateOf(false) }

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
                    text = "Profile & Settings ⚙️",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    color = TextPrimary
                )
                Text(
                    text = "Manage your English goals, vocabulary bank, and preferences",
                    fontSize = 13.sp,
                    color = TextSecondary
                )
            }

            // User Identity Card
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(DarkSurface)
                        .border(1.dp, DarkBorder, RoundedCornerShape(20.dp))
                        .padding(18.dp)
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
                                        .size(54.dp)
                                        .clip(CircleShape)
                                        .background(
                                            Brush.linearGradient(
                                                listOf(OrangePrimary, OrangeDark)
                                            )
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(14.dp))
                                Column {
                                    Text(
                                        text = profile.name,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        color = TextPrimary
                                    )
                                    Text(
                                        text = profile.learningGoal,
                                        fontSize = 12.sp,
                                        color = OrangeSecondary
                                    )
                                }
                            }
                            CefrBadge(level = profile.cefrLevel, large = true)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Study Target Details
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            DetailPill(label = "Daily Time", value = profile.dailyStudyTime)
                            DetailPill(label = "Streak", value = "${profile.streak} Days")
                            DetailPill(label = "XP", value = "${profile.xp} XP")
                        }
                    }
                }
            }

            // Retake Assessment Card
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(OrangeContainer)
                        .border(1.dp, OrangePrimary.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.School,
                                contentDescription = null,
                                tint = OrangeSecondary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "CEFR Level Assessment",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = OrangeSecondary
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Current evaluated level: ${profile.cefrLevel} (${profile.overallScore}% score). You can retake the 40-question placement test anytime to measure improvement.",
                            fontSize = 13.sp,
                            color = TextPrimary,
                            lineHeight = 18.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = onRetakePlacement,
                            colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("profile_retake_assessment_btn")
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Retake 40-Question Assessment", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                        }
                    }
                }
            }

            // Saved Vocabulary Library
            item {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Saved Vocabulary Bank (${savedWords.size})",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = TextPrimary
                        )
                        Icon(
                            imageVector = Icons.Default.Bookmark,
                            contentDescription = null,
                            tint = OrangeSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    if (savedWords.isEmpty()) {
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
                                text = "No words saved yet. Bookmark words in the Learn tab to review them here!",
                                fontSize = 13.sp,
                                color = TextMuted,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            savedWords.forEach { item ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(DarkSurface)
                                        .border(1.dp, DarkBorder, RoundedCornerShape(12.dp))
                                        .padding(12.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Text(
                                                    text = item.word,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 15.sp,
                                                    color = TextPrimary
                                                )
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Text(
                                                    text = item.phonetic,
                                                    fontSize = 12.sp,
                                                    color = TextMuted
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(3.dp))
                                            Text(
                                                text = item.definition,
                                                fontSize = 12.sp,
                                                color = TextSecondary,
                                                maxLines = 2
                                            )
                                        }

                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            AudioPlayButton(
                                                textToSpeak = item.word,
                                                onSpeak = onSpeak,
                                                isSlowAllowed = false
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            IconButton(
                                                onClick = { onDeleteSavedWord(item.id) },
                                                modifier = Modifier.size(32.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Delete,
                                                    contentDescription = "Delete",
                                                    tint = TextMuted,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Audio & Playback Preferences
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
                        Text(
                            text = "Audio & Tutor Preferences",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = TextPrimary
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Slower Audio Default",
                                    fontSize = 13.sp,
                                    color = TextPrimary
                                )
                                Text(
                                    text = "Plays voice at 0.72x speed for clearer listening",
                                    fontSize = 11.sp,
                                    color = TextSecondary
                                )
                            }
                            Switch(
                                checked = slowerAudioPreference,
                                onCheckedChange = { slowerAudioPreference = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = OrangePrimary,
                                    checkedTrackColor = OrangeContainer
                                )
                            )
                        }
                    }
                }
            }

            // Reset All Progress (Dangerous action)
            item {
                OutlinedButton(
                    onClick = { showResetDialog = true },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = ErrorRed)
                ) {
                    Icon(Icons.Default.Warning, contentDescription = null, tint = ErrorRed, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Reset All Learning Data", color = ErrorRed, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }

    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text("Reset All Progress?", fontWeight = FontWeight.Bold, color = TextPrimary) },
            text = {
                Text(
                    "This will clear your streak, XP, placement assessment score, and return you to the initial onboarding screen. Are you sure?",
                    color = TextSecondary
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showResetDialog = false
                        onResetProgress()
                    }
                ) {
                    Text("Yes, Reset Everything", color = ErrorRed, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("Cancel", color = TextSecondary)
                }
            },
            containerColor = DarkSurface
        )
    }
}

@Composable
private fun DetailPill(label: String, value: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(DarkSurfaceVariant)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = label, fontSize = 10.sp, color = TextMuted)
            Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        }
    }
}
