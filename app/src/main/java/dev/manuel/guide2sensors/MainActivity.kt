package dev.manuel.guide2sensors

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.manuel.guide2sensors.application.screen.TemperatureScreen
import dev.manuel.guide2sensors.ui.theme.Guide2SensorsTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      Guide2SensorsTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
          TemperatureScreen()
        }
      }
    }
  }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  Guide2SensorsTheme {
    TemperatureScreen()
  }
}
