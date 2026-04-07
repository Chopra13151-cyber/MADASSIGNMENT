package com.example.mad_assg_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));

        Spinner spinnerFrom = findViewById(R.id.spinnerFrom);
        Spinner spinnerTo = findViewById(R.id.spinnerTo);

        String[] currencies = {"USD", "INR", "JPY", "EUR"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        Button btnConvert = findViewById(R.id.btnConvert);
        EditText etAmount = findViewById(R.id.etAmount);
        TextView tvResult = findViewById(R.id.tvResult);

        btnConvert.setOnClickListener(v -> {
            String input = etAmount.getText().toString();
            if (input.isEmpty()) {
                Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show();
                return;
            }

            double amount = Double.parseDouble(input);
            String fromCurr = spinnerFrom.getSelectedItem().toString();
            String toCurr = spinnerTo.getSelectedItem().toString();

            // Conversion Logic (Base: 1 USD)
            // Rates as of 2024 (approx): 1 USD = 83 INR, 150 JPY, 0.92 EUR
            double inUSD;
            switch (fromCurr) {
                case "INR": inUSD = amount / 83.0; break;
                case "JPY": inUSD = amount / 150.0; break;
                case "EUR": inUSD = amount / 0.92; break;
                default: inUSD = amount; // USD
            }

            double result;
            switch (toCurr) {
                case "INR": result = inUSD * 83.0; break;
                case "JPY": result = inUSD * 150.0; break;
                case "EUR": result = inUSD * 0.92; break;
                default: result = inUSD; // USD
            }

            tvResult.setText(String.format("%.2f", result));
        });
    }
}