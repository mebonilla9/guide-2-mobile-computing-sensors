package dev.manuel.guide2sensors.application.screen

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TemperatureScreen() {

  val ctx = LocalContext.current

  val sensorManager = ctx.getSystemService(Context.SENSOR_SERVICE) as SensorManager

  val ambientSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)

  val sensorStatus = remember { mutableStateOf("") }

  val tag = "thermometerService"

  val ambientSensorListener = object : SensorEventListener {
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
      Log.d(tag, "onAccuracyChanged: any thing detect")
      sensor?.let {
        Log.d(tag, "onAccuracyChanged: any value --> $sensor.name}")
      }
    }

    override fun onSensorChanged(event: SensorEvent?) {
      if (event?.sensor?.type != Sensor.TYPE_AMBIENT_TEMPERATURE) {
        return
      }

      Log.d(tag, "any event has onSensorDetect detect !!!")

      Log.d(tag, event.values?.get(0).toString())
      sensorStatus.value = event.values?.get(0)?.toInt().toString()
    }
  }

  sensorManager.registerListener(
    ambientSensorListener,
    ambientSensor,
    SensorManager.SENSOR_DELAY_NORMAL
  )

  Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
  ) {
    LinearProgressIndicator(
      modifier = Modifier
        .fillMaxWidth()
        .height(40.dp)
        .rotate(-90F)
        .clip(RoundedCornerShape(50)),
      backgroundColor = Color.Gray,
      color = if (convertToFloat(sensorStatus) < 0.3F
      ) {
        Color.Blue
      } else if (convertToFloat(sensorStatus) > 0.3F && convertToFloat(sensorStatus) < 0.6F) {
        Color.Green
      } else {
        Color.Red
      },
      progress = convertToFloat(sensorStatus)
    )
  }
  Box(
    modifier = Modifier
      .fillMaxSize()
      .padding(bottom = 140.dp),
    contentAlignment = Alignment.BottomCenter
  ) {
    Text(
      text = if (sensorStatus.value != "") sensorStatus.value.toInt().toString()+"ºC" else "0ºC",
      style = MaterialTheme.typography.h3.copy(
        fontWeight = FontWeight.Bold
      )
    )
  }
}


fun convertToFloat(number: MutableState<String>): Float {
  if (number.value.isBlank()) {
    return 0F
  }
  return number.value.toFloat() / 100
}

@Preview(showBackground = true)
@Composable
fun PreviewTemperatureScreen() {
  TemperatureScreen()
}