package com.example.currency_convert;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText amountInput;
    private Spinner fromCurrency;
    private Spinner toCurrency;
    private TextView resultText;

    private final Map<String, Double> exchangeRates = new HashMap<String, Double>() {{
        put("EURO", 1.0);
        put("US DOLLAR", 1.1);
        put("GB POUND", 0.9);
        put("BAHRAINI DINAR", 0.4);
        put("SAUDI RYAL", 4.1);
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        amountInput = findViewById(R.id.amount_input);
        fromCurrency = findViewById(R.id.from_currency);
        toCurrency = findViewById(R.id.to_currency);
        resultText = findViewById(R.id.result_text);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currencies_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fromCurrency.setAdapter(adapter);
        toCurrency.setAdapter(adapter);

        amountInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                convertCurrency();
            }
        });

        fromCurrency.setOnItemSelectedListener(new CustomItemSelectedListener(this::convertCurrency));
        toCurrency.setOnItemSelectedListener(new CustomItemSelectedListener(this::convertCurrency));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void convertCurrency() {
        String amountStr = amountInput.getText().toString();
        if (amountStr.isEmpty()) {
            resultText.setText("");
            return;
        }

        double amount = Double.parseDouble(amountStr);
        String from = fromCurrency.getSelectedItem().toString();
        String to = toCurrency.getSelectedItem().toString();

        double fromRate = exchangeRates.getOrDefault(from, 1.0);
        double toRate = exchangeRates.getOrDefault(to, 1.0);
        double result = amount * (toRate / fromRate);

        resultText.setText(String.format("%.2f", result));
    }
}