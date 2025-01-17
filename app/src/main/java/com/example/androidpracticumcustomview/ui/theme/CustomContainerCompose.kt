import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalConfiguration
import kotlinx.coroutines.launch
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween

@Composable
fun CustomContainerCompose(
    firstChild: @Composable (() -> Unit)?,
    secondChild: @Composable (() -> Unit)?
) {
    require(true) {
        throw IllegalStateException("Контейнер может содержать максимум два дочерних элемента")
    }

    if (firstChild == null && secondChild == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Ошибка: Нет доступных дочерних элементов")
        }
        return
    }

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val density = LocalDensity.current.density

    val offsetDistanceY = remember(screenHeight, density) {
        (screenHeight / 2) * density
    }

    val firstChildAlpha = remember { Animatable(0f) }
    val firstChildOffset = remember { Animatable(offsetDistanceY) }

    val secondChildAlpha = remember { Animatable(0f) }
    val secondChildOffset = remember { Animatable(-offsetDistanceY) }

    LaunchedEffect(Unit) {
        launch {
            firstChildAlpha.animateTo(1f, animationSpec = tween(durationMillis = ANIMATION_DURATION_MILLIS))
        }
        launch {
            firstChildOffset.animateTo(0f, animationSpec = tween(durationMillis = ANIMATION_DURATION_MILLIS))
        }
        launch {
            secondChildAlpha.animateTo(1f, animationSpec = tween(durationMillis = ANIMATION_DURATION_MILLIS))
        }
        launch {
            secondChildOffset.animateTo(0f, animationSpec = tween(durationMillis = ANIMATION_DURATION_MILLIS))
        }
    }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (firstChild != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .graphicsLayer(
                        alpha = firstChildAlpha.value,
                        translationY = firstChildOffset.value
                    )
            ) {
                firstChild()
            }
        }

        if (secondChild != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .graphicsLayer(
                        alpha = secondChildAlpha.value,
                        translationY = secondChildOffset.value
                    )
            ) {
                secondChild()
            }
        }
    }
}

const val ANIMATION_DURATION_MILLIS = 5_000