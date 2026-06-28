package com.math.finalapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {
    private TextView pantalla;
    private double primerValor = Double.NaN;
    private double segundoValor;
    private String operadorActual = "";
    private boolean nuevoNumero = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        pantalla = findViewById(R.id.pantalla);

        int[] botonesIds = {
            R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4,
            R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9, R.id.btn_punto,
            R.id.btn_mas, R.id.btn_menos, R.id.btn_mult, R.id.btn_div, R.id.btn_pot,
            R.id.btn_sin, R.id.btn_cos, R.id.btn_tan, R.id.btn_raiz, R.id.btn_ln, R.id.btn_log,
            R.id.btn_c, R.id.btn_igual
        };

        for (int id : botonesIds) {
            View btn = findViewById(id);
            if (btn != null) {
                btn.setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Button boton = (Button) view;
        String texto = boton.getText().toString();

        if (id == R.id.btn_c) {
            pantalla.setText("0");
            primerValor = Double.NaN;
            operadorActual = "";
            nuevoNumero = true;
        } else if (id == R.id.btn_igual) {
            calcular();
            operadorActual = "";
        } else if (id == R.id.btn_mas || id == R.id.btn_menos || id == R.id.btn_mult || id == R.id.btn_div || id == R.id.btn_pot) {
            try {
                if (!Double.isNaN(primerValor)) {
                    calcular();
                } else {
                    primerValor = Double.parseDouble(pantalla.getText().toString());
                }
                operadorActual = texto;
                nuevoNumero = true;
            } catch (Exception e) {
                pantalla.setText("0");
            }
        } else if (id == R.id.btn_sin || id == R.id.btn_cos || id == R.id.btn_tan || id == R.id.btn_raiz || id == R.id.btn_ln || id == R.id.btn_log) {
            try {
                double valor = Double.parseDouble(pantalla.getText().toString());
                double resultado = 0;
                switch (texto) {
                    case "sin": resultado = Math.sin(Math.toRadians(valor)); break;
                    case "cos": resultado = Math.cos(Math.toRadians(valor)); break;
                    case "tan": resultado = Math.tan(Math.toRadians(valor)); break;
                    case "√":   resultado = Math.sqrt(valor); break;
                    case "ln":  resultado = Math.log(valor); break;
                    case "log": resultado = Math.log10(valor); break;
                }
                pantalla.setText(String.valueOf(resultado));
                primerValor = resultado;
                nuevoNumero = true;
            } catch (Exception e) {
                pantalla.setText("Error");
            }
        } else {
            if (nuevoNumero) {
                pantalla.setText(texto);
                nuevoNumero = false;
            } else {
                pantalla.append(texto);
            }
        }
    }

    private void calcular() {
        try {
            if (!Double.isNaN(primerValor)) {
                segundoValor = Double.parseDouble(pantalla.getText().toString());
                switch (operadorActual) {
                    case "+": primerValor += segundoValor; break;
                    case "-": primerValor -= segundoValor; break;
                    case "*": primerValor *= segundoValor; break;
                    case "/": 
                        if (segundoValor != 0) primerValor /= segundoValor;
                        else pantalla.setText("Error");
                        break;
                    case "^": primerValor = Math.pow(primerValor, segundoValor); break;
                }
                pantalla.setText(String.valueOf(primerValor));
                nuevoNumero = true;
            }
        } catch (Exception e) {
            pantalla.setText("Error");
        }
    }
}
