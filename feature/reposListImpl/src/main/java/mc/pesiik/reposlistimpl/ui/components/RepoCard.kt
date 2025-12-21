package mc.pesiik.reposlistimpl.ui.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import mc.pesiik.reposlistimpl.R
import mc.pesiik.reposlistimpl.view.RepoUiModel

@Composable
internal fun RepoCard(
    repo: RepoUiModel,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable {
                if (!repo.isShimmer) onClick(repo.id)
            },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .run {
                    if (repo.isShimmer) {
                        shimmerEffect()
                    } else {
                        this
                    }
                }
                .padding(16.dp)
        ) {
            OwnerBlock(
                ownerLogin = repo.ownerLogin,
                ownerAvatarUrl = repo.ownerAvatarUrl
            )

            Text(
                text = repo.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Description(repo.description)

            Spacer(modifier = Modifier.weight(1f))

            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StartsBlock(repo.starCount)

                LanguageBlock(repo.language)
            }
        }
    }
}

@Composable
private fun OwnerBlock(
    ownerLogin: String,
    ownerAvatarUrl: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 8.dp)
    ) {
        AsyncImage(
            model = ownerAvatarUrl,
            contentDescription = stringResource(R.string.repo_list_owner_avatar),
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = ownerLogin,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun Description(description: String?) {
    if (!description.isNullOrBlank()) {
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}

@Composable
private fun StartsBlock(starCount: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Default.Star,
            tint = Color.Yellow,
            contentDescription = stringResource(R.string.repo_list_stars),
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = formatStarCount(starCount),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun LanguageBlock(language: String?) {
    if (!language.isNullOrBlank()) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Canvas(modifier = Modifier.size(12.dp)) {
                drawCircle(
                    color = generateLanguageColor(language)
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = language,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun formatStarCount(count: Int): String {
    return when {
        count >= 1000 -> stringResource(R.string.repo_list_formatted_stars, count / 1000.0)
        else -> count.toString()
    }
}

private fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition()
    val startOffsetX = transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1000)
        )
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                MaterialTheme.colorScheme.surfaceVariant,
                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f),
                MaterialTheme.colorScheme.surfaceVariant,
            ),
            start = androidx.compose.ui.geometry.Offset(
                x = startOffsetX.value,
                y = 0f
            ),
            end = androidx.compose.ui.geometry.Offset(
                x = startOffsetX.value + size.width.toFloat(),
                y = size.height.toFloat()
            )
        )
    ).onGloballyPositioned {
        size = it.size
    }
}

private fun generateLanguageColor(language: String): Color {
    val hash = language.hashCode()
    val r = (hash and 0xFF0000) shr 16
    val g = (hash and 0x00FF00) shr 8
    val b = (hash and 0x0000FF)
    val colorString = String.format("#%02X%02X%02X", r, g, b)
    return Color(colorString.toColorInt())
}