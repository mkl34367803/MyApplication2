package com.example.myapplication2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculatorActivity extends AppCompatActivity {

    private TextView tvExpression;
    private TextView tvResult;

    private String currentNumber = "";
    private String operator = "";
    private BigDecimal firstOperand = BigDecimal.ZERO;
    private boolean hasCalculated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvExpression = findViewById(R.id.tv_expression);
        tvResult = findViewById(R.id.tv_result);

        setupNumberButtons();
        setupOperatorButtons();
        setupActionButtons();
    }

    private void setupNumberButtons() {
        int[] numberIds = {
                R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4,
                R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9
        };

        for (int id : numberIds) {
            findViewById(id).setOnClickListener(v -> {
                if (hasCalculated) {
                    clear();
                }
                String digit = ((Button) v).getText().toString();
                currentNumber += digit;
                updateDisplay();
            });
        }

        findViewById(R.id.btn_double_zero).setOnClickListener(v -> {
            if (hasCalculated) {
                clear();
            }
            if (!currentNumber.isEmpty() && !currentNumber.equals("0")) {
                currentNumber += "00";
                updateDisplay();
            }
        });

        findViewById(R.id.btn_dot).setOnClickListener(v -> {
            if (hasCalculated) {
                clear();
            }
            if (currentNumber.isEmpty()) {
                currentNumber = "0.";
            } else if (!currentNumber.contains(".")) {
                currentNumber += ".";
            }
            updateDisplay();
        });
    }

    private void setupOperatorButtons() {
        setupOperatorButton(R.id.btn_add, "+");
        setupOperatorButton(R.id.btn_subtract, "−");
        setupOperatorButton(R.id.btn_multiply, "×");
        setupOperatorButton(R.id.btn_divide, "÷");
    }

    private void setupOperatorButton(int buttonId, String op) {
        findViewById(buttonId).setOnClickListener(v -> {
            if (!currentNumber.isEmpty()) {
                if (!operator.isEmpty()) {
                    calculate();
                }
                firstOperand = new BigDecimal(currentNumber);
                currentNumber = "";
                hasCalculated = false;
            } else if (hasCalculated) {
                hasCalculated = false;
            }
            operator = op;
            tvExpression.setText(formatNumber(firstOperand) + " " + operator);
        });
    }

    private void setupActionButtons() {
        findViewById(R.id.btn_clear).setOnClickListener(v -> clear());

        findViewById(R.id.btn_backspace).setOnClickListener(v -> {
            if (hasCalculated) {
                clear();
                return;
            }
            if (!currentNumber.isEmpty()) {
                currentNumber = currentNumber.substring(0, currentNumber.length() - 1);
                updateDisplay();
            }
        });

        findViewById(R.id.btn_percent).setOnClickListener(v -> {
            if (!currentNumber.isEmpty()) {
                BigDecimal value = new BigDecimal(currentNumber);
                value = value.divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);
                currentNumber = value.stripTrailingZeros().toPlainString();
                updateDisplay();
            }
        });

        findViewById(R.id.btn_equals).setOnClickListener(v -> {
            if (!operator.isEmpty() && !currentNumber.isEmpty()) {
                BigDecimal secondOperand = new BigDecimal(currentNumber);
                tvExpression.setText(formatNumber(firstOperand) + " " + operator + " " + formatNumber(secondOperand) + " =");
                calculate();
                hasCalculated = true;
            }
        });
    }

    private void calculate() {
        if (currentNumber.isEmpty()) return;

        BigDecimal secondOperand = new BigDecimal(currentNumber);
        BigDecimal result;

        switch (operator) {
            case "+":
                result = firstOperand.add(secondOperand);
                break;
            case "−":
                result = firstOperand.subtract(secondOperand);
                break;
            case "×":
                result = firstOperand.multiply(secondOperand);
                break;
            case "÷":
                if (secondOperand.compareTo(BigDecimal.ZERO) == 0) {
                    tvResult.setText("错误");
                    currentNumber = "";
                    operator = "";
                    return;
                }
                result = firstOperand.divide(secondOperand, 10, RoundingMode.HALF_UP);
                break;
            default:
                return;
        }

        firstOperand = result;
        currentNumber = result.stripTrailingZeros().toPlainString();
        operator = "";
        tvResult.setText(formatNumber(result));
    }

    private void clear() {
        currentNumber = "";
        operator = "";
        firstOperand = BigDecimal.ZERO;
        hasCalculated = false;
        tvExpression.setText("");
        tvResult.setText("0");
    }

    private void updateDisplay() {
        if (currentNumber.isEmpty()) {
            tvResult.setText("0");
        } else {
            tvResult.setText(currentNumber);
        }
    }

    private String formatNumber(BigDecimal number) {
        String plain = number.stripTrailingZeros().toPlainString();
        if (plain.startsWith("-")) {
            return "−" + formatPositiveNumber(plain.substring(1));
        }
        return formatPositiveNumber(plain);
    }

    private String formatPositiveNumber(String plain) {
        int dotIndex = plain.indexOf('.');
        String intPart = dotIndex >= 0 ? plain.substring(0, dotIndex) : plain;
        String decPart = dotIndex >= 0 ? plain.substring(dotIndex) : "";

        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (int i = intPart.length() - 1; i >= 0; i--) {
            if (count > 0 && count % 3 == 0) {
                sb.insert(0, ',');
            }
            sb.insert(0, intPart.charAt(i));
            count++;
        }
        return sb + decPart;
    }
}
