package com.aki.calculation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.*;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private String lastNum = null;
    private String num = "";
    private boolean isFinished = false;
    private boolean isNum = false;
    private boolean isSymbol = false;

    @BindView(R.id.last_show)
    TextView lastShow;
    @BindView(R.id.show)
    TextView show;
    @BindView(R.id.clear)
    Button clear;
    @BindView(R.id.back_space)
    Button backSpace;
    @BindView(R.id.divide)
    Button divide;
    @BindView(R.id.times)
    Button times;
    @BindView(R.id.seven)
    Button seven;
    @BindView(R.id.eight)
    Button eight;
    @BindView(R.id.nine)
    Button nine;
    @BindView(R.id.four)
    Button four;
    @BindView(R.id.five)
    Button five;
    @BindView(R.id.six)
    Button six;
    @BindView(R.id.plus)
    Button plus;
    @BindView(R.id.one)
    Button one;
    @BindView(R.id.two)
    Button two;
    @BindView(R.id.three)
    Button three;
    @BindView(R.id.zero)
    Button zero;
    @BindView(R.id.dot)
    Button dot;
    @BindView(R.id.equal)
    Button equal;
    @BindView(R.id.minus)
    Button minus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    private int numCount = 0;
    private void num(int resId) {
        if (isFinished || num.equals("0")) {
            if(isSymbol){
                //********
            }else{
                num = "";
            }
        }
        isFinished = false;
        switch (resId) {
            case R.id.zero:
                num += "0";
                break;
            case R.id.one:
                num += "1";
                break;
            case R.id.two:
                num += "2";
                break;
            case R.id.three:
                num += "3";
                break;
            case R.id.four:
                num += "4";
                break;
            case R.id.five:
                num += "5";
                break;
            case R.id.six:
                num += "6";
                break;
            case R.id.seven:
                num += "7";
                break;
            case R.id.eight:
                num += "8";
                break;
            case R.id.nine:
                num += "9";
                break;
            case R.id.dot:
                if("" != num){

                }
                String unitNum = num.substring(num.length()-numCount);
                if(num.equals("0")){
                    num += "0.";
                }else if(-1 != unitNum.indexOf(".")){
                    //************
                    return;
                } else{
                    num += ".";
                }
                break;
        }
        show.setText(num);
        isSymbol = false;
        operatorCount = 0;
        numCount ++;
    }

    private List<Float> computeNum = new ArrayList<>();
    private List<String> operator = new ArrayList<>();

    private int operatorCount = 0;

    private void operator(int resId) {
        if(num.charAt(num.length()-1) == 'E'){
            num = num.substring(0,num.length()-1);
        }
        if(num.charAt(num.length()-1) == '+'
                || num.charAt(num.length()-1) == '-'
                || num.charAt(num.length()-1) == '×'
                || num.charAt(num.length()-1) == '÷'
                ){
            if(num.charAt(num.length()-1) == '+'
                    || num.charAt(num.length()-2) == '-'
                    || num.charAt(num.length()-2) == '×'
                    || num.charAt(num.length()-2) == '÷'){
                operatorCount = 2;
            }else{
                operatorCount = 1;
            }
            isSymbol = true;
        }else{
            operatorCount = 0;
            isSymbol = false;
        }
        operatorCount ++;
        if(isSymbol&&(resId != R.id.minus) || operatorCount >= 2){
            if(operatorCount == 2 && resId == R.id.minus){
                //*********
            }else{
                return;
            }
        }
        if(m == 2){
            Log.i("Aki","");
        }
        switch (resId) {
            case R.id.plus:
                num += "+";
                break;
            case R.id.minus:
                num += "-";
                break;
            case R.id.times:
                num += "×";
                break;
            case R.id.divide:
                num += "÷";
                break;
        }
        isFinished = false;
        show.setText(num);
        isNum = false;
        isSymbol = true;
        numCount = 0;
    }

    private void result() {
        if(1 == num.length()&&(num.equals("+") || num.equals("-") || num.equals("×") || num.equals("÷"))){
            return;
        }
        if(isSymbol){
            num.substring(0,num.length()-1);
        }
        isFinished = true;
        lastShow();
        int location = -1;
        boolean isOperator = false;
        for(int count = 0;count < num.length();count ++){
            if(num.charAt(count) == '+'
                    || num.charAt(count) == '-'
                    || num.charAt(count) == '×'
                    || num.charAt(count) == '÷'){
                if((count == 0 && num.charAt(count) == '-') ||(isOperator && num.charAt(count) == '-')){
                    continue;
                }
                isOperator = true;
                computeNum.add(Float.parseFloat(num.substring(location+1,count)));
                operator.add(String.valueOf(num.charAt(count)));
                location = count;
            }else{
                isOperator = false;
            }
        }
        if(location<num.length()-1){
            computeNum.add(Float.parseFloat(num.substring(location+1)));
        }

        calculate();

        float number = computeNum.get(0);
        int numberInt = (int) number;
        if(Math.abs(number - numberInt)<0.000001){
            show.setText(numberInt + "");
            num = String.valueOf(numberInt);
        }else{
            show.setText(computeNum.get(0) + "");
            num = computeNum.get(0) + "";
        }


        isSymbol = false;
        isNum = true;
        computeNum.clear();
        operator.clear();
        numCount = 0;
        operatorCount = 0;
    }

    private void lastShow() {
        lastShow.setText(num + "=");
    }

    private void decrease() {
        if(1 != num.length()){
            num = num.substring(0,num.length()-1);
        }else{
            num = "0";
            operatorCount = 0;
        }
        show.setText(num);
    }

    private void clear() {
        num = "0";
        operator.clear();
        computeNum.clear();
        show.setText("0");
        lastShow.setText("");
        operatorCount = 0;
        numCount = 0;
        isSymbol = false;
        isNum = false;
    }

    private int m = 0;
    private void calculate(){
        m ++;
        if(m == 2){
            Log.i("Aki","");
        }
        if(operator.size() == computeNum.size()){
            operator.remove(operator.size()-1);
        }
        firstCalculate();
        secondCalculate();
    }

    private void firstCalculate(){
        if(m == 2){
            Log.i("Aki","");
        }
        for(int count = 0;count < operator.size();count ++){
            if(operator.get(count).equals("×") || operator.get(count).equals("÷")){
                switch (operator.get(count)){
                    case "×":
                        computeNum.set(count,computeNum.get(count)*computeNum.get(count+1));
                        computeNum.remove(count+1);
                        operator.remove(count);
                        break;
                    case "÷":
                        computeNum.set(count,computeNum.get(count)/computeNum.get(count+1));
                        computeNum.remove(count+1);
                        operator.remove(count);
                        break;
                }
                break;
            }
        }
        for(int i = 0;i < operator.size();i ++){
            if(operator.get(i).equals("×") || operator.get(i).equals("÷")){
                firstCalculate();
                break;
            }
        }
    }

    private void secondCalculate(){
        if(m == 2){
            Log.i("Aki","");
        }
        for(String symbol:operator){
            if(symbol.equals("+")){
                computeNum.set(0,computeNum.get(0) + computeNum.get(1));
                computeNum.remove(1);
            }else if(symbol.equals("-")){
                computeNum.set(0,computeNum.get(0) - computeNum.get(1));
                computeNum.remove(1);
            }
        }
    }

    @OnClick({R.id.clear,R.id.minus, R.id.back_space, R.id.divide, R.id.times, R.id.seven, R.id.eight, R.id.nine, R.id.four, R.id.five, R.id.six, R.id.plus, R.id.one, R.id.two, R.id.three, R.id.zero, R.id.dot, R.id.equal})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.clear:
                clear();
                break;
            case R.id.back_space:
                decrease();
                break;
            case R.id.minus:
                operator(R.id.minus);
                break;
            case R.id.divide:
                operator(R.id.divide);
                break;
            case R.id.times:
                operator(R.id.times);
                break;
            case R.id.seven:
                num(R.id.seven);
                break;
            case R.id.eight:
                num(R.id.eight);
                break;
            case R.id.nine:
                num(R.id.nine);
                break;
            case R.id.four:
                num(R.id.four);
                break;
            case R.id.five:
                num(R.id.five);
                break;
            case R.id.six:
                num(R.id.six);
                break;
            case R.id.plus:
                operator(R.id.plus);
                break;
            case R.id.one:
                num(R.id.one);
                break;
            case R.id.two:
                num(R.id.two);
                break;
            case R.id.three:
                num(R.id.three);
                break;
            case R.id.zero:
                num(R.id.zero);
                break;
            case R.id.dot:
                num(R.id.dot);
                break;
            case R.id.equal:
                result();
                break;
        }
    }
}
