package project.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun Theme(
	darkTheme: Boolean = isSystemInDarkTheme(),
	content: @Composable () -> Unit,
) = MaterialTheme(
	colorScheme = when {
		Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
			with(LocalContext.current) {
				if (darkTheme) dynamicDarkColorScheme(this)
				else dynamicLightColorScheme(this)
			}
		}
		darkTheme -> darkColorScheme()
		else -> lightColorScheme()
	},
	content = content
)