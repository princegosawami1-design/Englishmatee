package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AppScreen
import com.example.ui.theme.AccentAmber
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkBorderSubtle
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceHigh
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.OrangeContainer
import com.example.ui.theme.OrangeDark
import com.example.ui.theme.OrangePrimary
import com.example.ui.theme.OrangeSecondary
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun EnglishMateTopBar(
    streak: Int,
    xp: Int,
    onRetakePlacement: (() -> Unit)? = null
) {
    Surface(
        color = DarkSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorderSubtle),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // App Identity
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onRetakePlacement?.invoke() }
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            Brush.linearGradient(listOf(OrangePrimary, OrangeDark))
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "EM",
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "EnglishMate",
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        fontSize = 17.sp,
                        letterSpacing = (-0.3).sp
                    )
                    Text(
                        text = "Your English Companion",
                        color = TextMuted,
                        fontSize = 11.sp
                    )
                }
            }

            // Streak & XP Badges
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Streak Pill
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(DarkSurfaceVariant)
                        .border(1.dp, DarkBorder, RoundedCornerShape(20.dp))
                        .padding(horizontal = 9.dp, vertical = 5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocalFireDepartment,
                        contentDescription = "Streak",
                        tint = AccentAmber,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$streak",
                        color = TextPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // XP Pill
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(OrangeContainer)
                        .border(1.dp, OrangePrimary.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                        .padding(horizontal = 9.dp, vertical = 5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "XP",
                        tint = OrangeSecondary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$xp XP",
                        color = OrangeSecondary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun EnglishMateBottomNav(
    currentScreen: AppScreen,
    onNavigate: (AppScreen) -> Unit
) {
    val items = listOf(
        NavigationItem("Home", AppScreen.HOME, Icons.Filled.Home, Icons.Outlined.Home),
        NavigationItem("Learn", AppScreen.LEARN, Icons.Filled.MenuBook, Icons.Outlined.MenuBook),
        NavigationItem("Practice", AppScreen.PRACTICE, Icons.Filled.FitnessCenter, Icons.Outlined.FitnessCenter),
        NavigationItem("Live Tutor", AppScreen.LIVE_TUTOR, Icons.Filled.Chat, Icons.Outlined.Chat),
        NavigationItem("Progress", AppScreen.PROGRESS, Icons.Filled.BarChart, Icons.Outlined.BarChart),
        NavigationItem("Profile", AppScreen.PROFILE, Icons.Filled.Person, Icons.Outlined.Person)
    )

    NavigationBar(
        containerColor = DarkSurface,
        tonalElevation = 8.dp,
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.navigationBars)
            .border(androidx.compose.foundation.BorderStroke(1.dp, DarkBorderSubtle))
    ) {
        items.forEach { item ->
            val isSelected = currentScreen == item.screen
            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(item.screen) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label,
                        modifier = Modifier.size(22.dp)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 10.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = OrangePrimary,
                    selectedTextColor = OrangePrimary,
                    indicatorColor = OrangeContainer,
                    unselectedIconColor = TextMuted,
                    unselectedTextColor = TextMuted
                ),
                modifier = Modifier.testTag("nav_${item.label.lowercase().replace(" ", "_")}")
            )
        }
    }
}

private data class NavigationItem(
    val label: String,
    val screen: AppScreen,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Composable
fun CefrBadge(
    level: String,
    modifier: Modifier = Modifier,
    large: Boolean = false
) {
    val (bgColor, borderColor, desc) = when (level.uppercase()) {
        "A1" -> Triple(Color(0xFF1E3A8A).copy(alpha = 0.4f), Color(0xFF3B82F6), "Beginner")
        "A2" -> Triple(Color(0xFF064E3B).copy(alpha = 0.4f), Color(0xFF10B981), "Elementary")
        "B1" -> Triple(Color(0xFF78350F).copy(alpha = 0.4f), Color(0xFFF59E0B), "Intermediate")
        "B2" -> Triple(Color(0xFF4C1D95).copy(alpha = 0.4f), Color(0xFF8B5CF6), "Upper-Int")
        "C1" -> Triple(Color(0xFF831843).copy(alpha = 0.4f), Color(0xFFEC4899), "Advanced")
        "C2" -> Triple(Color(0xFF3D1F08), OrangePrimary, "Mastery")
        else -> Triple(DarkSurfaceVariant, DarkBorder, "Assessing")
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(if (large) 12.dp else 8.dp))
            .background(bgColor)
            .border(1.dp, borderColor, RoundedCornerShape(if (large) 12.dp else 8.dp))
            .padding(horizontal = if (large) 12.dp else 8.dp, vertical = if (large) 6.dp else 3.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = level.uppercase(),
                fontWeight = FontWeight.Black,
                color = borderColor,
                fontSize = if (large) 16.sp else 12.sp
            )
            if (large) {
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = desc,
                    fontWeight = FontWeight.Medium,
                    color = TextPrimary,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
fun AudioPlayButton(
    textToSpeak: String,
    onSpeak: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    isSlowAllowed: Boolean = true
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        // Normal Speed Play
        IconButton(
            onClick = { onSpeak(textToSpeak, false) },
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(OrangeContainer)
                .border(1.dp, OrangePrimary.copy(alpha = 0.4f), CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.VolumeUp,
                contentDescription = "Listen to English pronunciation",
                tint = OrangeSecondary,
                modifier = Modifier.size(18.dp)
            )
        }

        if (isSlowAllowed) {
            Spacer(modifier = Modifier.width(6.dp))
            // Slow Speed Play
            IconButton(
                onClick = { onSpeak(textToSpeak, true) },
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(DarkSurfaceHigh)
                    .border(1.dp, DarkBorder, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Speed,
                    contentDescription = "Slow speed playback",
                    tint = TextSecondary,
                    modifier = Modifier.size(15.dp)
                )
            }
        }
    }
}
