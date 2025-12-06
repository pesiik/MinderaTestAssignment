package mc.pesiik.pt_android_iliamashin.ui.list.components

import androidx.compose.foundation.Canvas
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import mc.pesiik.pt_android_iliamashin.R
import mc.pesiik.pt_android_iliamashin.ui.theme.Typography
import mc.pesiik.pt_android_iliamashin.view.RepoUiModel

@Composable
fun RepoCard(
    repo: RepoUiModel,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .aspectRatio(1f)
            .clickable { onClick(repo.id) },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            OwnerBlock(
                ownerLogin = repo.ownerLogin,
                ownerAvatarUrl = repo.ownerAvatarUrl
            )

            Text(
                text = repo.name,
                style = Typography.titleMedium,
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
            style = Typography.bodyMedium
        )
    }
}

@Composable
private fun Description(description: String?) {
    if (!description.isNullOrBlank()) {
        Text(
            text = description,
            style = Typography.bodyMedium,
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
            style = Typography.bodySmall
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
                style = Typography.bodySmall,
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

private fun generateLanguageColor(language: String): Color {
    val hash = language.hashCode()
    val r = (hash and 0xFF0000) shr 16
    val g = (hash and 0x00FF00) shr 8
    val b = (hash and 0x0000FF)
    val colorString = String.format("#%02X%02X%02X", r, g, b)
    return Color(colorString.toColorInt())
}