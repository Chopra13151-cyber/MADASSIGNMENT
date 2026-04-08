package com.example.mad_assg_3;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelSensor, lightSensor, proxSensor;
    private TextView tvAccel, tvLight, tvProx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvAccel = findViewById(R.id.tvAccel);
        tvLight = findViewById(R.id.tvLight);
        tvProx = findViewById(R.id.tvProx);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Initialize Sensors
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        proxSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        // Check if sensors exist on device
        if (accelSensor == null) Toast.makeText(this, "No Accelerometer found", Toast.LENGTH_SHORT).show();
        if (lightSensor == null) Toast.makeText(this, "No Light Sensor found", Toast.LENGTH_SHORT).show();
        if (proxSensor == null) Toast.makeText(this, "No Proximity Sensor found", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int type = event.sensor.getType();

        if (type == Sensor.TYPE_ACCELEROMETER) {
            tvAccel.setText(String.format("X: %.2f | Y: %.2f | Z: %.2f", event.values[0], event.values[1], event.values[2]));
        } else if (type == Sensor.TYPE_LIGHT) {
            tvLight.setText(event.values[0] + " lx");
        } else if (type == Sensor.TYPE_PROXIMITY) {
            tvProx.setText(event.values[0] + " cm");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not needed for this task
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register listeners to start receiving data
        if (accelSensor != null) sensorManager.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL);
        if (lightSensor != null) sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        if (proxSensor != null) sensorManager.registerListener(this, proxSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister to save battery
        sensorManager.unregisterListener(this);
    }
}