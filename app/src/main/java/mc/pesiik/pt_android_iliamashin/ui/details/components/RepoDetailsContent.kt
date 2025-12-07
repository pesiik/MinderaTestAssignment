package mc.pesiik.pt_android_iliamashin.ui.details.components

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toJavaZoneId
import mc.pesiik.pt_android_iliamashin.R
import mc.pesiik.pt_android_iliamashin.ui.theme.Purple40
import mc.pesiik.pt_android_iliamashin.view.RepoDetailUiState
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale


@Composable
internal fun RepoDetailsContent(
    state: RepoDetailUiState,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(color = Purple40)
            .safeDrawingPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            RepoDescription(description = state.description)
            RepoStatistics(state = state)
            RepoLastUpdated(lastUpdated = state.lastUpdated)
        }
    }
}

@Composable
private fun RepoDescription(description: String?) {
    description?.let {
        Text(
            text = it,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun RepoStatistics(state: RepoDetailUiState) {
    RepoStatRow(
        image = Icons.Default.Star,
        stringId = R.string.repo_details_stars,
        count = state.starsCount
    )
    RepoStatRow(
        image = Icons.Default.Share,
        stringId = R.string.repo_details_forks,
        count = state.forksCount
    )
    RepoStatRow(
        image = Icons.Default.Face,
        stringId = R.string.repo_details_watchers,
        count = state.subscribersCount
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun RepoLastUpdated(lastUpdated: String) {
    Text(
        text = stringResource(
            id = R.string.repo_details_published_at_label,
            formatArgs = arrayOf(dateFormatted(lastUpdated))
        ),
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
private fun RepoStatRow(
    image: ImageVector,
    stringId: Int,
    count: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            imageVector = image,
            contentDescription = image.name,
            colorFilter = ColorFilter.tint(
                color = Purple40
            )
        )
        Text(
            text = stringResource(id = stringId),
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = count.toString(),
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@Composable
private fun dateFormatted(publishDateString: String): String {
    if (publishDateString.isEmpty()) return ""
    val publishDate = Instant.parse(publishDateString)
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        DateTimeFormatter
            .ofLocalizedDate(FormatStyle.MEDIUM)
            .withLocale(Locale.getDefault())
            .withZone(LocalTimeZone.current.toJavaZoneId())
            .format(publishDate.toJavaInstant())
    } else {
        publishDate.toString()
    }
}

private val LocalTimeZone = compositionLocalOf { TimeZone.currentSystemDefault() }