package com.github.leuludyha.ibdb.presentation.screen.maps

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.github.leuludyha.ibdb.ui.theme.IBDBTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

class GoogleMapsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IBDBTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GoogleMaps()
                }
            }
        }
    }
}

@Composable
fun GoogleMaps() {
    val epfl = LatLng(46.520536, 6.568318)
    val epflLimits = listOf(
        LatLng(46.522259, 6.563326),
        LatLng(46.515126, 6.560001),
        LatLng(46.518263, 6.572170),
        LatLng(46.521600, 6.571801),
        LatLng(46.522259, 6.563326)
    )
    val showEpflLimits = remember { mutableStateOf(false) }
    val satellite = LatLng(46.520544, 6.567825)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(epfl, 15f)
    }
    val context = LocalContext.current

    GoogleMap(
        modifier = Modifier
            .fillMaxSize()
            .testTag("GoogleMap"),
        cameraPositionState = cameraPositionState
    ) {
        MarkerInfoWindowContent(
            tag = "test_tag",
            state = MarkerState(position = epfl),
            onInfoWindowClick = {
                showEpflLimits.value = true
            },
            onInfoWindowClose = {
                showEpflLimits.value = false
            },
            content = {
                Column(
                    modifier = Modifier.testTag("info_window_epfl"),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "EPFL", fontWeight = FontWeight.Bold)
                    Text(text = stringResource(id = com.github.leuludyha.ibdb.R.string.title_activity_google_maps))
                    Text(text = "Click to see its limits!")
                }
            }
        )
        Marker(
            state = MarkerState(position = satellite),
            title = "Satellite",
            snippet = "Cool place!",
            onInfoWindowClick = {
                Toast.makeText(context, "Coor: $satellite", Toast.LENGTH_SHORT).show()
            }
        )
        Polygon(
            points = epflLimits,
            visible = showEpflLimits.value,
            strokeColor = Color.Green,
            strokeWidth = 6f,
            fillColor = Color.Green.copy(0.3f)
        )

    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    IBDBTheme {
        GoogleMaps()
    }
}
