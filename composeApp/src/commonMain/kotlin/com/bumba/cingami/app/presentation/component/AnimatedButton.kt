package com.bumba.cingami.app.presentation.component

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedButton(
    text: String,
    isLoading: Boolean,
    containerColor: Color = Color.White,
    contentColor: Color = Color.Blue,
    progressIndicatorColor: Color = MaterialTheme.colorScheme.primary,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val contentAlpha by animateFloatAsState(targetValue = if (isLoading) 0f else 1f)
    val loadingAlpha by animateFloatAsState(targetValue = if (isLoading) 1f else 0f)
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            LoadingIndicator(
                isAnimating = isLoading,
                animationType = AnimationType.Bounce,
                color = progressIndicatorColor,
                modifier = Modifier.graphicsLayer { alpha = loadingAlpha },
            )
            Box(
                modifier = Modifier
                    .graphicsLayer { alpha = contentAlpha }
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun LoadingIndicator(
    isAnimating: Boolean,
    color: Color,
    animationType: AnimationType,
    modifier: Modifier = Modifier
) {
    val animatedValues = List(INDICATOR_SIZE) { index ->
        var animatedValue by remember(
            key1 = isAnimating,
            key2 = animationType
        ) { mutableStateOf(0f) }
        LaunchedEffect(key1 = isAnimating, key2 = animationType) {
            if (isAnimating) {
                animate(
                    initialValue = animationType.initialValue,
                    targetValue = animationType.targetValue,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = animationType.animationDuration),
                        repeatMode = RepeatMode.Reverse,
                        initialStartOffset = StartOffset(animationType.animationDelay * index),
                    ),
                ) { value, _ -> animatedValue = value }
            }
        }
        animatedValue
    }
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        animatedValues.forEach { animatedValue ->
            LoadingDot(
                color = color,
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .width(INDICATOR_SIZE.dp)
                    .aspectRatio(1f)
                    .then(
                        when (animationType) {
                            AnimationType.Bounce -> Modifier.offset(y = animatedValue.dp)
                            AnimationType.Fade -> Modifier.graphicsLayer { alpha = animatedValue }
                        }
                    )
            )
        }
    }
}

@Composable
private fun LoadingDot(
    color: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(shape = CircleShape)
            .background(color = color)
    )
}

private const val INDICATOR_SIZE = 5
private const val NUM_INDICATORS = 3

private enum class AnimationType { Bounce, Fade }

private val AnimationType.animationDuration: Int
    get() = when (this) {
        AnimationType.Bounce -> 300
        AnimationType.Fade -> 600
    }

private val AnimationType.animationDelay: Int
    get() = animationDuration / NUM_INDICATORS

private val AnimationType.initialValue: Float
    get() = when (this) {
        AnimationType.Bounce -> INDICATOR_SIZE / 2f
        AnimationType.Fade -> 1f
    }

private val AnimationType.targetValue: Float
    get() = when (this) {
        AnimationType.Bounce -> -INDICATOR_SIZE / 2f
        AnimationType.Fade -> .2f
    }