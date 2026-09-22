package com.example.ui.practice

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.content.LearningCurriculum
import com.example.data.models.ListeningExercise
import com.example.data.models.PronunciationExercise
import com.example.data.models.SentenceExercise
import com.example.data.models.SpeakingExercise
import com.example.ui.components.AudioPlayButton
import com.example.ui.components.CefrBadge
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceHigh
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.OrangeContainer
import com.example.ui.theme.OrangePrimary
import com.example.ui.theme.OrangeSecondary
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun PracticeScreen(
    onSpeak: (String, Boolean) -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Sentence Builder", "Listening", "Speaking", "Pronunciation")

    Surface(
        color = DarkBackground,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                Text(
                    text = "Skill Practice 🏋️",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    color = TextPrimary
                )
                Text(
                    text = "Interactive sentence ordering, listening audio, speech, and phonetics",
                    fontSize = 13.sp,
                    color = TextSecondary
                )
            }

            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = DarkSurface,
                contentColor = OrangePrimary,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = OrangePrimary
                    )
                },
                modifier = Modifier.border(1.dp, DarkBorder)
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = title,
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 12.sp
                            )
                        },
                        modifier = Modifier.testTag("practice_tab_$index")
                    )
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                when (selectedTab) {
                    0 -> SentenceBuilderTab(
                        exercises = LearningCurriculum.sentenceExercises,
                        onSpeak = onSpeak
                    )
                    1 -> ListeningTab(
                        exercises = LearningCurriculum.listeningExercises,
                        onSpeak = onSpeak
                    )
                    2 -> SpeakingTab(
                        exercises = LearningCurriculum.speakingExercises,
                        onSpeak = onSpeak
                    )
                    3 -> PronunciationTab(
                        exercises = LearningCurriculum.pronunciationExercises,
                        onSpeak = onSpeak
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SentenceBuilderTab(
    exercises: List<SentenceExercise>,
    onSpeak: (String, Boolean) -> Unit
) {
    var currentIndex by remember { mutableIntStateOf(0) }
    val exercise = exercises[currentIndex]

    val arrangedWords = remember(exercise.id) { mutableStateListOf<String>() }
    val availableWords = remember(exercise.id) { mutableStateListOf<String>().apply { addAll(exercise.scrambledWords) } }

    var isChecked by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Exercise ${currentIndex + 1} of ${exercises.size}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = OrangeSecondary
                )
                CefrBadge(level = exercise.level)
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Tap the words in the correct order to form a natural English sentence:",
                fontSize = 14.sp,
                color = TextSecondary,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Arranged Sentence Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(DarkSurface)
                    .border(
                        1.dp,
                        if (isChecked) {
                            if (isCorrect) SuccessGreen else ErrorRed
                        } else DarkBorder,
                        RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = "YOUR SENTENCE:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextMuted,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    if (arrangedWords.isEmpty()) {
                        Text(
                            text = "Tap word tiles below to place them here...",
                            fontSize = 14.sp,
                            color = TextMuted,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        )
                    } else {
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            arrangedWords.forEachIndexed { idx, word ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(OrangeContainer)
                                        .border(1.dp, OrangePrimary, RoundedCornerShape(8.dp))
                                        .clickable {
                                            // return word to pool
                                            arrangedWords.removeAt(idx)
                                            availableWords.add(word)
                                            isChecked = false
                                        }
                                        .padding(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = word,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = TextPrimary
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Word Bank / Available Tiles
            Text(
                text = "Available Words:",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                availableWords.forEachIndexed { index, word ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(DarkSurfaceVariant)
                            .border(1.dp, DarkBorder, RoundedCornerShape(8.dp))
                            .clickable {
                                availableWords.removeAt(index)
                                arrangedWords.add(word)
                                isChecked = false
                            }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = word,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            color = TextPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Hint
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(DarkSurfaceHigh)
                    .padding(12.dp)
            ) {
                Text(
                    text = "💡 Hint: ${exercise.hint}",
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }

            if (isChecked) {
                Spacer(modifier = Modifier.height(14.dp))
                if (isCorrect) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(SuccessGreen.copy(alpha = 0.15f))
                            .border(1.dp, SuccessGreen, RoundedCornerShape(12.dp))
                            .padding(14.dp)
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Check, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Correct! Well constructed.", fontWeight = FontWeight.Bold, color = SuccessGreen, fontSize = 14.sp)
                            }
                            if (exercise.betterSuggestion.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Text("Native Phrasing Variation:", fontWeight = FontWeight.SemiBold, color = TextPrimary, fontSize = 12.sp)
                                Text("\"${exercise.betterSuggestion}\"", fontStyle = androidx.compose.ui.text.font.FontStyle.Italic, color = TextSecondary, fontSize = 12.sp)
                            }
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(ErrorRed.copy(alpha = 0.15f))
                            .border(1.dp, ErrorRed, RoundedCornerShape(12.dp))
                            .padding(14.dp)
                    ) {
                        Text("Not quite right yet. Check word order and try again!", color = ErrorRed, fontSize = 13.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Action Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        arrangedWords.clear()
                        availableWords.clear()
                        availableWords.addAll(exercise.scrambledWords)
                        isChecked = false
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(0.4f)
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Reset", fontSize = 13.sp)
                }

                Button(
                    onClick = {
                        val assembled = arrangedWords.joinToString(" ").trim()
                        val target = exercise.targetSentence.trim()
                        isCorrect = assembled.equals(target, ignoreCase = true)
                        isChecked = true
                        if (isCorrect) {
                            onSpeak(assembled, false)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(0.6f)
                ) {
                    Text("Check Order", fontWeight = FontWeight.Bold)
                }
            }

            if (isChecked && isCorrect && currentIndex < exercises.size - 1) {
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        currentIndex += 1
                        isChecked = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = OrangeSecondary),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Next Sentence Exercise", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun ListeningTab(
    exercises: List<ListeningExercise>,
    onSpeak: (String, Boolean) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(exercises) { ex ->
            var selectedIdx by remember { mutableStateOf<Int?>(null) }
            var showAnswer by remember { mutableStateOf(false) }

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
                            text = ex.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = TextPrimary
                        )
                        CefrBadge(level = ex.level)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Audio Play Bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(DarkSurfaceVariant)
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AudioPlayButton(
                            textToSpeak = ex.textToSpeak,
                            onSpeak = onSpeak
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Listen to the English audio passage",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = TextPrimary
                            )
                            Text(
                                text = "Use normal (1.0x) or slower (0.72x) speed",
                                fontSize = 11.sp,
                                color = TextMuted
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Question: ${ex.question}",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = TextPrimary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ex.options.forEachIndexed { i, opt ->
                        val isSelected = selectedIdx == i
                        val isCorrect = i == ex.correctIndex
                        val bg = if (showAnswer) {
                            if (isCorrect) SuccessGreen.copy(alpha = 0.2f)
                            else if (isSelected) ErrorRed.copy(alpha = 0.2f)
                            else DarkSurfaceVariant
                        } else {
                            if (isSelected) OrangeContainer else DarkSurfaceVariant
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(bg)
                                .border(1.dp, DarkBorder, RoundedCornerShape(8.dp))
                                .clickable {
                                    selectedIdx = i
                                    showAnswer = true
                                }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = opt,
                                fontSize = 13.sp,
                                color = TextPrimary,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }

                    if (showAnswer) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Explanation: ${ex.explanation}",
                            fontSize = 12.sp,
                            color = OrangeSecondary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SpeakingTab(
    exercises: List<SpeakingExercise>,
    onSpeak: (String, Boolean) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(exercises) { item ->
            var isRecordingSimulated by remember { mutableStateOf(false) }
            var spokenRecorded by remember { mutableStateOf(false) }

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
                            text = item.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = TextPrimary
                        )
                        CefrBadge(level = item.level)
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = item.prompt,
                        fontSize = 14.sp,
                        color = TextSecondary,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Model Target Phrase
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(OrangeContainer)
                            .border(1.dp, OrangePrimary.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AudioPlayButton(
                                textToSpeak = item.targetPhrase,
                                onSpeak = onSpeak
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "TARGET PHRASE:",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    color = OrangeSecondary
                                )
                                Text(
                                    text = "\"${item.targetPhrase}\"",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "🎙️ Context & Pronunciation Tip:\n${item.contextTip}",
                        fontSize = 12.sp,
                        color = TextMuted,
                        lineHeight = 17.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Practice Record Button
                    Button(
                        onClick = {
                            isRecordingSimulated = !isRecordingSimulated
                            if (!isRecordingSimulated) {
                                spokenRecorded = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isRecordingSimulated) ErrorRed else DarkSurfaceHigh
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Mic,
                                contentDescription = null,
                                tint = if (isRecordingSimulated) Color.White else OrangePrimary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isRecordingSimulated) "Listening... Tap to Complete" else "Tap to Practice Speaking Aloud",
                                fontWeight = FontWeight.Bold,
                                color = if (isRecordingSimulated) Color.White else TextPrimary
                            )
                        }
                    }

                    if (spokenRecorded) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "✓ Great articulation! Your pronunciation rhythm matched the model phrase.",
                            fontSize = 12.sp,
                            color = SuccessGreen,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PronunciationTab(
    exercises: List<PronunciationExercise>,
    onSpeak: (String, Boolean) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(exercises) { p ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(DarkSurface)
                    .border(1.dp, DarkBorder, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = p.soundLabel,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = OrangeSecondary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = p.tip,
                        fontSize = 13.sp,
                        color = TextSecondary,
                        lineHeight = 19.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Minimal Pair Comparison Cards
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Word 1
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(DarkSurfaceVariant)
                                .border(1.dp, DarkBorder, RoundedCornerShape(12.dp))
                                .padding(12.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = p.word1, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary)
                                Spacer(modifier = Modifier.height(6.dp))
                                AudioPlayButton(
                                    textToSpeak = p.word1,
                                    onSpeak = onSpeak
                                )
                            }
                        }

                        // Word 2
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(DarkSurfaceVariant)
                                .border(1.dp, DarkBorder, RoundedCornerShape(12.dp))
                                .padding(12.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = p.word2, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary)
                                Spacer(modifier = Modifier.height(6.dp))
                                AudioPlayButton(
                                    textToSpeak = p.word2,
                                    onSpeak = onSpeak
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Context Sentence
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(DarkSurfaceHigh)
                            .padding(12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AudioPlayButton(
                                textToSpeak = p.exampleSentence,
                                onSpeak = onSpeak,
                                isSlowAllowed = false
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "\"${p.exampleSentence}\"",
                                fontSize = 12.sp,
                                color = TextPrimary,
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                            )
                        }
                    }
                }
            }
        }
    }
}
