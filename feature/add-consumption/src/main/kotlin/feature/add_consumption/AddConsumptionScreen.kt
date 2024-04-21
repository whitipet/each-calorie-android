package feature.add_consumption

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
internal fun AddConsumptionScreen() {
	Scaffold { padding ->
		Box(
			modifier = Modifier
				.fillMaxSize()
				.padding(padding),
			contentAlignment = Alignment.Center
		) {
			Text(
				text = "AddConsumptionScreen",
				style = MaterialTheme.typography.headlineLarge
			)
		}

	}
}

@Preview
@Composable
private fun StatsScreenPreview() = Theme {
	AddConsumptionScreen()
}