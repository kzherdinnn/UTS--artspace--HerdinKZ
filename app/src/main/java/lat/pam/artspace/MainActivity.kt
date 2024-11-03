package lat.pam.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.*
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch


data class ArtPiece(
    val title: String,
    val artist: String,
    val year: String,
    val imageResourceId: Int
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtSpaceApp(
                        artPieces = listOf(
                            ArtPiece("Starry Night", "Vincent van Gogh", "1889", R.drawable.starry_night),
                            ArtPiece("The Persistence of Memory", "Salvador Dalí", "1931", R.drawable.persistence_of_memory),
                            ArtPiece("The Scream", "Edvard Munch", "1893", R.drawable.the_scream),
                            ArtPiece("Girl with a Pearl Earring", "Johannes Vermeer", "1665", R.drawable.girl_with_pearl_earring)
                        )
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ArtSpaceApp(artPieces: List<ArtPiece>) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope() // Tambahkan ini

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            count = artPieces.size,
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            ArtSpaceScreen(artPiece = artPieces[page])
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Button(onClick = {
                if (pagerState.currentPage > 0) {
                    coroutineScope.launch { // Panggil animateScrollToPage dalam coroutine
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                }
            }) {
                Text("Previous")
            }
            Button(onClick = {
                if (pagerState.currentPage < artPieces.size - 1) {
                    coroutineScope.launch { // Panggil animateScrollToPage dalam coroutine
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            }) {
                Text("Next")
            }
        }
    }
}

@Composable
fun ArtSpaceScreen(artPiece: ArtPiece, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = artPiece.imageResourceId),
            contentDescription = artPiece.title,
            modifier = Modifier.size(300.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = artPiece.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(text = artPiece.artist, fontSize = 16.sp, fontStyle = FontStyle.Italic)
        Text(text = artPiece.year, fontSize = 14.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewArtSpaceApp() {
    ArtSpaceTheme {
        ArtSpaceApp(
            artPieces = listOf(
                ArtPiece("Starry Night", "Vincent van Gogh", "1889", R.drawable.starry_night)
            )
        )
    }
}

@Composable
fun ArtSpaceTheme(content: @Composable () -> Unit) {
    MaterialTheme {
        content()
    }
}
