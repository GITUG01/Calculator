package com.example.myapplication;

import androidx.annotation.IntRange;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;

import java.awt.font.TextAttribute;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    final static String NUMBERS = "0123456789";
    final static String COMMONS = ",*÷+-";

    Stack numbers = new Stack();

    TextView tv0;
    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    TextView tv5;
    TextView tv6;
    TextView tv7;
    TextView tv8;
    TextView tv9;
    TextView tvC;
    TextView x;
    TextView del;
    TextView delitel;
    TextView plus;
    TextView minus;
    TextView common;
    TextView result;
    EditText editText;

    static Stack<Integer> stackNumbers = new Stack();
    static Stack stackDelimeters = new Stack();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.text_place);

        result = findViewById(R.id.txt_result);
        x = findViewById(R.id.txt_X);
        common = findViewById(R.id.txt_common);
        delitel = findViewById(R.id.txt_delitel);
        plus = findViewById(R.id.txt_plus);
        minus = findViewById(R.id.txt_minus);
        del = findViewById(R.id.del);
        tvC = findViewById(R.id.cancel);
        tv0 = findViewById(R.id.txt_0);
        tv1 = findViewById(R.id.txt_1);
        tv2 = findViewById(R.id.txt_2);
        tv3 = findViewById(R.id.txt_3);
        tv4 = findViewById(R.id.txt_4);
        tv5 = findViewById(R.id.txt_5);
        tv6 = findViewById(R.id.txt_6);
        tv7 = findViewById(R.id.txt_7);
        tv8 = findViewById(R.id.txt_8);
        tv9 = findViewById(R.id.txt_9);

        tv0.setOnClickListener(view -> editText.append(tv0.getText().toString()));
        tv1.setOnClickListener(view -> editText.append(tv1.getText().toString()));
        tv2.setOnClickListener(view -> editText.append(tv2.getText().toString()));
        tv3.setOnClickListener(view -> editText.append(tv3.getText().toString()));
        tv4.setOnClickListener(view -> editText.append(tv4.getText().toString()));
        tv5.setOnClickListener(view -> editText.append(tv5.getText().toString()));
        tv6.setOnClickListener(view -> editText.append(tv6.getText().toString()));
        tv7.setOnClickListener(view -> editText.append(tv7.getText().toString()));
        tv8.setOnClickListener(view -> editText.append(tv8.getText().toString()));
        tv9.setOnClickListener(view -> editText.append(tv9.getText().toString()));
        result.setOnClickListener(view -> editText.setText(RPNtoAnswer(toRPN(editText.getText().toString()))));
        tvC.setOnClickListener(view -> editText.setText(""));
        x.setOnClickListener(view -> editText.append(x.getText().toString()));
        plus.setOnClickListener(view -> editText.append(plus.getText().toString()));
        minus.setOnClickListener(view -> editText.append(minus.getText().toString()));
        common.setOnClickListener(view -> editText.append(common.getText().toString()));
        delitel.setOnClickListener(view -> editText.append(delitel.getText().toString()));
        del.setOnClickListener(view -> editText.setText(removeLastElement(editText.getText().toString())));


        if (editText.getText().toString().length() == 14) {
            editText.setTextSize(40);
        }

    }

    public static String removeLastElement(String s) {
        return (s == null || s.length() == 0) ?
                null : s.substring(0, s.length() - 1);
    }

    public static String toRPN(String expr) {
        StringBuilder current = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        int priority;
        for (int i = 0; i < expr.length(); i++) {
            priority = getP(expr.charAt(i));

            if (priority == 0) current.append(expr.charAt(i));
            if (priority == 1) stack.push(expr.charAt(i));

            if (priority > 1) {
                current.append(" ");

                while (!stack.empty()) {
                    if (getP(stack.peek()) >= priority) current.append(stack.pop());
                    else break;
                }
                stack.push(expr.charAt(i));
            }

            if (priority == -1) {
                current.append(" ");
                while (getP(stack.peek()) != 1) current.append(stack.pop());
                stack.pop();
            }
        }

        while (!stack.empty()) current.append(stack.pop());
        return current.toString();
    }

    public static String RPNtoAnswer(String rpn) {
        String operand = "";
        Stack<Double> stack = new Stack<>();

        for (int i = 0; i < rpn.length(); i++) {
            if (rpn.charAt(i) == ' ') continue;

            if (getP(rpn.charAt(i)) == 0) {
                while (rpn.charAt(i) != ' ' && getP(rpn.charAt(i)) == 0) {
                    operand += rpn.charAt(i++);
                    if (i == rpn.length()) break;

                    stack.push(Double.parseDouble(operand));
                    operand = "";
                }

                if (getP(rpn.charAt(i)) > 1){
                    double a = stack.pop(), b = stack.pop();

                    if (rpn.charAt(i) == '+') stack.push(b + a);
                    if (rpn.charAt(i) == '-') stack.push(b - a);
                    if (rpn.charAt(i) == 'x') stack.push(b * a);
                    if (rpn.charAt(i) == '÷') stack.push(b / a);


                }

            }
        }

        return stack.pop().toString();
    }

    public static int getP(char token) {
        if (token == 'x' || token == '÷') return 3;
        else if (token == '+' || token == '-') return 2;
        else if (token == '(') return 1;
        else if (token == ')') return -1;
        else return 0;
    }
}