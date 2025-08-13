package com.korzik.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.korzik.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                Scaffold { innerPadding ->
                    ArtSpaceLayout(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun PictureCard(
    @DrawableRes image: Int,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 2.dp
    ) {
        Surface(
            modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(8.dp)),
            shape = RoundedCornerShape(8.dp),
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = contentDescription,
                modifier = Modifier
                    .height(400.dp)
                    .width(400.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun InfoCard(
    title: String,
    author: String,
    year: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(8.dp),
        tonalElevation = 2.dp,
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            LabelText(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontSize = 22.sp,
                fontWeight = FontWeight.Normal,
                modifier = modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            LabelText(
                text = "$author ($year)",
                style = MaterialTheme.typography.bodySmall,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun LabelText(
    text: String,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign = TextAlign.Center,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = fontSize,
        fontWeight = fontWeight,
        textAlign = textAlign,
        style = style,
        modifier = modifier
    )
}

@Composable
fun NavigationButton(
    @StringRes text: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(text = stringResource(text))
    }
}

@Composable
fun NavigationRow(
    onPrev: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        NavigationButton(onClick = onPrev, text = R.string.previous)
        Spacer(modifier = Modifier.width(16.dp))
        NavigationButton(onClick = onNext, text = R.string.next)
    }
}

@Composable
fun ArtSpaceLayout(modifier: Modifier = Modifier) {
    var pictureIndex by rememberSaveable { mutableIntStateOf(0) }

    val imageResources = listOf(
        R.drawable.image_1,
        R.drawable.image_2,
        R.drawable.image_3,
        R.drawable.image_4,
        R.drawable.image_5
    )

    val artworkLabels = listOf(
        "Девушка с жемчужной сережкой",
        "Звездная ночь",
        "Черный квадрат",
        "Девочка с персиками",
        "Постоянство памяти"
    )

    val artistLabels = listOf(
        "Ян Вермеер",
        "Винсент Ван Гог",
        "Казимир Малевич",
        "Валентин Серов",
        "Сальвадор Дали"
    )

    val yearLabels = listOf(
        "1665",
        "1889",
        "1915",
        "1899",
        "1931"
    )

    Surface {
        Column(
            modifier = modifier.padding(16.dp).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            PictureCard(
                image = imageResources[pictureIndex],
                contentDescription = "${artworkLabels[pictureIndex]} — ${artistLabels[pictureIndex]}",
                modifier = Modifier.width(400.dp)
            )
            InfoCard(
                title = artworkLabels[pictureIndex],
                author = artistLabels[pictureIndex],
                year = yearLabels[pictureIndex],
                modifier = Modifier.width(400.dp)
            )
            NavigationRow(
                onPrev = { if (pictureIndex > 0) pictureIndex-- },
                onNext = { if (pictureIndex < imageResources.lastIndex) pictureIndex++ }
            )
        }
    }
}

@Preview(
    showBackground = true, showSystemUi = true, locale = "ru",
    device = "spec:width=411dp,height=891dp"
)
@Composable
fun Preview() {
    ArtSpaceTheme {
        Scaffold { innerPadding ->
            ArtSpaceLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        }
    }
}
