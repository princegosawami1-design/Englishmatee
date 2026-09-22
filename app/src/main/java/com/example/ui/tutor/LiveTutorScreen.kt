package com.example.ui.tutor

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.VideocamOff
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.models.ChatMessage
import com.example.data.models.TutorSessionSummary
import com.example.ui.components.AudioPlayButton
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
fun LiveTutorScreen(
    isActive: Boolean,
    isMicMuted: Boolean,
    isCameraOn: Boolean,
    isSpeakerOn: Boolean,
    selectedMode: String,
    chatMessages: List<ChatMessage>,
    sessionDurationSeconds: Int,
    isTutorResponding: Boolean,
    tutorSummary: TutorSessionSummary?,
    showSummaryDialog: Boolean,
    onStartSession: (String) -> Unit,
    onEndSession: () -> Unit,
    onToggleMic: () -> Unit,
    onToggleCamera: () -> Unit,
    onToggleSpeaker: () -> Unit,
    onSelectMode: (String) -> Unit,
    onSendMessage: (String) -> Unit,
    onSpeak: (String, Boolean) -> Unit,
    onDismissSummary: () -> Unit
) {
    var textInput by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    LaunchedEffect(chatMessages.size) {
        if (chatMessages.isNotEmpty()) {
            listState.animateScrollToItem(chatMessages.size - 1)
        }
    }

    val availableModes = listOf(
        "Casual Conversation",
        "Job Interview Practice",
        "Travel English",
        "Restaurant Conversation",
        "Speaking Confidence",
        "Grammar Correction",
        "School/College Conversation",
        "Shopping Conversation",
        "Free Talk"
    )

    Surface(
        color = DarkBackground,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkSurface)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
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
                    Column {
                        Text(
                            text = "EnglishMate AI Tutor",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = TextPrimary
                        )
                        Text(
                            text = if (isActive) "Active Call • $selectedMode" else "Select Scenario",
                            fontSize = 11.sp,
                            color = OrangeSecondary
                        )
                    }
                }

                if (isActive) {
                    val mins = sessionDurationSeconds / 60
                    val secs = sessionDurationSeconds % 60
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(ErrorRed.copy(alpha = 0.2f))
                            .border(1.dp, ErrorRed, RoundedCornerShape(12.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(ErrorRed)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = String.format("%02d:%02d", mins, secs),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // Mode Selector Carousel (Available before or during call)
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkSurfaceVariant)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(availableModes) { mode ->
                    val isSelected = selectedMode == mode
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) OrangePrimary else DarkSurface)
                            .border(1.dp, if (isSelected) OrangePrimary else DarkBorder, RoundedCornerShape(20.dp))
                            .clickable {
                                onSelectMode(mode)
                                if (isActive) {
                                    onStartSession(mode)
                                }
                            }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = mode,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) Color.White else TextSecondary
                        )
                    }
                }
            }

            if (!isActive) {
                // Call Start Screen
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    listOf(OrangePrimary, DarkSurfaceVariant)
                                )
                            )
                            .border(3.dp, OrangePrimary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.School,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(60.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Real-time AI English Conversation",
                        fontWeight = FontWeight.Black,
                        fontSize = 20.sp,
                        color = TextPrimary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Practice speaking naturally with a patient, friendly English tutor. Get immediate constructive corrections for grammar and pronunciation.",
                        fontSize = 14.sp,
                        color = TextSecondary,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { onStartSession(selectedMode) },
                        colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .height(54.dp)
                            .testTag("start_live_tutor_btn")
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("Start Live Tutor Session", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    }
                }
            } else {
                // Active Call Video Area & Transcript
                Column(modifier = Modifier.weight(1f)) {
                    // Video Feeds Box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .background(Color.Black)
                            .padding(12.dp)
                    ) {
                        // AI Tutor Main Avatar
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(16.dp))
                                .background(
                                    Brush.linearGradient(
                                        listOf(Color(0xFF2B1405), Color(0xFF140A02))
                                    )
                                )
                                .border(1.dp, OrangePrimary.copy(alpha = 0.5f), RoundedCornerShape(16.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                val infiniteTransition = rememberInfiniteTransition(label = "pulse")
                                val scale by infiniteTransition.animateFloat(
                                    initialValue = 1.0f,
                                    targetValue = 1.15f,
                                    animationSpec = infiniteRepeatable(
                                        animation = tween(800, easing = FastOutSlowInEasing),
                                        repeatMode = RepeatMode.Reverse
                                    ),
                                    label = "scale"
                                )

                                Box(
                                    modifier = Modifier
                                        .size(68.dp)
                                        .scale(if (isTutorResponding) scale else 1f)
                                        .clip(CircleShape)
                                        .background(OrangeContainer)
                                        .border(2.dp, OrangePrimary, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.School,
                                        contentDescription = null,
                                        tint = OrangeSecondary,
                                        modifier = Modifier.size(36.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = if (isTutorResponding) "AI Tutor is speaking..." else "AI Tutor (Listening)",
                                    fontSize = 12.sp,
                                    color = if (isTutorResponding) OrangeSecondary else TextMuted,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        // User Video Pip
                        Box(
                            modifier = Modifier
                                .size(70.dp, 95.dp)
                                .align(Alignment.BottomEnd)
                                .clip(RoundedCornerShape(12.dp))
                                .background(DarkSurfaceVariant)
                                .border(1.dp, DarkBorder, RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isCameraOn) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(Icons.Default.Videocam, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(22.dp))
                                    Text("Camera On", fontSize = 9.sp, color = TextSecondary)
                                }
                            } else {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(Icons.Default.Person, contentDescription = null, tint = TextMuted, modifier = Modifier.size(24.dp))
                                    Text("You", fontSize = 10.sp, color = TextMuted)
                                }
                            }
                        }
                    }

                    // Conversation Transcript
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        item { Spacer(modifier = Modifier.height(6.dp)) }

                        items(chatMessages) { msg ->
                            val isUser = msg.sender == "user"

                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.85f)
                                        .clip(
                                            RoundedCornerShape(
                                                topStart = 14.dp,
                                                topEnd = 14.dp,
                                                bottomStart = if (isUser) 14.dp else 2.dp,
                                                bottomEnd = if (isUser) 2.dp else 14.dp
                                            )
                                        )
                                        .background(if (isUser) OrangePrimary else DarkSurface)
                                        .border(
                                            1.dp,
                                            if (isUser) OrangeDark else DarkBorder,
                                            RoundedCornerShape(14.dp)
                                        )
                                        .padding(12.dp)
                                ) {
                                    Column {
                                        Text(
                                            text = msg.text,
                                            fontSize = 14.sp,
                                            color = if (isUser) Color.White else TextPrimary,
                                            lineHeight = 20.sp
                                        )

                                        if (!isUser) {
                                            Spacer(modifier = Modifier.height(6.dp))
                                            AudioPlayButton(
                                                textToSpeak = msg.text,
                                                onSpeak = onSpeak
                                            )
                                        }
                                    }
                                }

                                // Constructive Correction Pill
                                msg.correction?.let { corr ->
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(0.88f)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(DarkSurfaceVariant)
                                            .border(1.dp, OrangeSecondary.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
                                            .padding(10.dp)
                                    ) {
                                        Column {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Icon(
                                                    imageVector = Icons.Default.AutoAwesome,
                                                    contentDescription = null,
                                                    tint = OrangeSecondary,
                                                    modifier = Modifier.size(14.dp)
                                                )
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text(
                                                    text = "Constructive Tip",
                                                    fontSize = 11.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = OrangeSecondary
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(
                                                text = "You said: \"${corr.youSaid}\"",
                                                fontSize = 12.sp,
                                                color = ErrorRed
                                            )
                                            Text(
                                                text = "Better: \"${corr.better}\"",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = SuccessGreen
                                            )
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

                        if (isTutorResponding) {
                            item {
                                Row(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(DarkSurface)
                                        .padding(horizontal = 12.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    CircularProgressIndicator(
                                        color = OrangePrimary,
                                        modifier = Modifier.size(16.dp),
                                        strokeWidth = 2.dp
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Tutor is thinking...",
                                        fontSize = 12.sp,
                                        color = TextSecondary
                                    )
                                }
                            }
                        }

                        item { Spacer(modifier = Modifier.height(8.dp)) }
                    }

                    // Call Controls Bar (Mic, Camera, Speaker, End Call)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(DarkSurface)
                            .border(1.dp, DarkBorder)
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Mic
                        IconButton(
                            onClick = onToggleMic,
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(if (isMicMuted) ErrorRed.copy(alpha = 0.2f) else DarkSurfaceHigh)
                                .border(1.dp, if (isMicMuted) ErrorRed else DarkBorder, CircleShape)
                        ) {
                            Icon(
                                imageVector = if (isMicMuted) Icons.Default.MicOff else Icons.Default.Mic,
                                contentDescription = "Toggle Mic",
                                tint = if (isMicMuted) ErrorRed else TextPrimary
                            )
                        }

                        // Camera
                        IconButton(
                            onClick = onToggleCamera,
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(if (isCameraOn) SuccessGreen.copy(alpha = 0.2f) else DarkSurfaceHigh)
                                .border(1.dp, if (isCameraOn) SuccessGreen else DarkBorder, CircleShape)
                        ) {
                            Icon(
                                imageVector = if (isCameraOn) Icons.Default.Videocam else Icons.Default.VideocamOff,
                                contentDescription = "Toggle Camera",
                                tint = if (isCameraOn) SuccessGreen else TextPrimary
                            )
                        }

                        // Speaker
                        IconButton(
                            onClick = onToggleSpeaker,
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(if (isSpeakerOn) OrangeContainer else DarkSurfaceHigh)
                                .border(1.dp, if (isSpeakerOn) OrangePrimary else DarkBorder, CircleShape)
                        ) {
                            Icon(
                                imageVector = if (isSpeakerOn) Icons.Default.VolumeUp else Icons.Default.VolumeOff,
                                contentDescription = "Toggle Speaker",
                                tint = if (isSpeakerOn) OrangeSecondary else TextMuted
                            )
                        }

                        // End Call
                        IconButton(
                            onClick = onEndSession,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(ErrorRed)
                                .testTag("tutor_end_call_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.CallEnd,
                                contentDescription = "End Call",
                                tint = Color.White
                            )
                        }
                    }

                    // Bottom Text/Speech Input Bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(DarkBackground)
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = textInput,
                            onValueChange = { textInput = it },
                            placeholder = { Text("Speak or type to tutor...", fontSize = 13.sp, color = TextMuted) },
                            singleLine = true,
                            shape = RoundedCornerShape(24.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = OrangePrimary,
                                unfocusedBorderColor = DarkBorder,
                                focusedContainerColor = DarkSurface,
                                unfocusedContainerColor = DarkSurface,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("tutor_message_input")
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        IconButton(
                            onClick = {
                                if (textInput.isNotBlank()) {
                                    onSendMessage(textInput)
                                    textInput = ""
                                }
                            },
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(OrangePrimary)
                                .testTag("tutor_send_btn")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = "Send",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    // Session Summary Modal
    if (showSummaryDialog && tutorSummary != null) {
        Dialog(onDismissRequest = onDismissSummary) {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = DarkSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            ) {
                LazyColumn(modifier = Modifier.padding(20.dp)) {
                    item {
                        Text(
                            text = "Session Summary 🎉",
                            fontWeight = FontWeight.Black,
                            fontSize = 20.sp,
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Great conversational practice session with AI Tutor!",
                            fontSize = 13.sp,
                            color = TextSecondary
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // XP Badge
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(OrangeContainer)
                                .border(1.dp, OrangePrimary, RoundedCornerShape(12.dp))
                                .padding(14.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = OrangeSecondary, modifier = Modifier.size(24.dp))
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "+${tutorSummary.xpAwarded} XP Earned!",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = OrangeSecondary
                                    )
                                    Text(
                                        text = "Duration: ${tutorSummary.durationMinutes} mins • Turns: ${tutorSummary.turns}",
                                        fontSize = 12.sp,
                                        color = TextPrimary
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Constructive Corrections
                        if (tutorSummary.corrections.isNotEmpty()) {
                            Text(
                                text = "Constructive Corrections to Review:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            tutorSummary.corrections.forEach { corr ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(DarkSurfaceVariant)
                                        .padding(10.dp)
                                ) {
                                    Column {
                                        Text("You said: \"${corr.youSaid}\"", fontSize = 12.sp, color = ErrorRed)
                                        Text("Better: \"${corr.better}\"", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SuccessGreen)
                                        Text("Why: ${corr.why}", fontSize = 11.sp, color = TextSecondary)
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                        }

                        // Confidence Feedback
                        Text(
                            text = "Teacher Feedback:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = tutorSummary.confidenceFeedback,
                            fontSize = 13.sp,
                            color = TextSecondary,
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Suggested Next Step:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = OrangeSecondary
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = tutorSummary.suggestedNext,
                            fontSize = 12.sp,
                            color = TextSecondary
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = onDismissSummary,
                            colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Done & Save Progress", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
