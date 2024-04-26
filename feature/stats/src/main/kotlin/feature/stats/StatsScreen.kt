package feature.stats

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import project.ui.theme.Theme

@Composable
internal fun StatsScreen() {
	Scaffold { padding ->
		Box(
			modifier = Modifier
				.fillMaxSize()
				.padding(padding),
			contentAlignment = Alignment.Center
		) {
			Text(
				text = "StatsScreen",
				style = MaterialTheme.typography.headlineLarge
			)
		}
	}
}

@Preview
@Composable
private fun StatsScreenPreview() = Theme {
	StatsScreen()
}