package dk.easv.extenedcalculatorwoarchitecture;

import dk.easv.bll.Calculator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.print.DocFlavor;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class HelloController {

    @FXML
    private TextField txtResult;

    @FXML
    private Label lblOperatorStr;

    @FXML
    private Label lblCalculationStr;

    private Calculator CalculatorLogic = new Calculator();

    DecimalFormat df = new DecimalFormat("#,###.##");


    @FXML
    protected void initialize()
    {
        lblOperatorStr.setText("");
        lblCalculationStr.setText("");
    }

    public void onClearAction(ActionEvent actionEvent)
    {
        CalculatorLogic.clear();
        lblOperatorStr.setText("");
        txtResult.setText("0");
        lblCalculationStr.setText("");
    }

    public void onPlusMinusAction(ActionEvent actionEvent)
    {
        double value = Double.parseDouble(txtResult.getText());
        if(value >= 0)
        {
            value = -value;
        }
        else
        {
            value = Math.abs(value);
        }
        txtResult.setText(df.format(value));
        lblOperatorStr.setText("±");
    }

    public void onPercentageAction(ActionEvent actionEvent)
    {
        operatorAction("%");
    }

    public void onDivideAction(ActionEvent actionEvent) {
        operatorAction("/");
    }

    public void onSevenAction(ActionEvent actionEvent)
    {
        setResultDisplay("7");
    }

    public void onEightAction(ActionEvent actionEvent) {
        setResultDisplay("8");
    }

    public void onNineAction(ActionEvent actionEvent)
    {
        setResultDisplay("9");
    }

    public void onMultiplyAction(ActionEvent actionEvent)
    {
        operatorAction("*");
    }

    public void onFourAction(ActionEvent actionEvent)
    {
        setResultDisplay("4");
    }

    public void onFiveAction(ActionEvent actionEvent)
    {
        setResultDisplay("5");
    }

    public void onSixAction(ActionEvent actionEvent)
    {
        setResultDisplay("6");
    }

    public void onMinusAction(ActionEvent actionEvent)
    {
        if(txtResult.getText().equals("0") || txtResult.getText().trim().equals(""))
        {
            setResultDisplay("-");
        }
        else
        {
            operatorAction("-");
        }
    }

    public void onOneAction(ActionEvent actionEvent)
    {
        setResultDisplay("1");
    }

    public void onTwoAction(ActionEvent actionEvent)
    {
        setResultDisplay("2");
    }

    public void onThreeAction(ActionEvent actionEvent)
    {
        setResultDisplay("3");
    }

    public void onPlusAction(ActionEvent actionEvent)
    {
        operatorAction("+");
    }

    public void onZeroAction(ActionEvent actionEvent)
    {
        setResultDisplay("0");
    }

    public void onCommaAction(ActionEvent actionEvent)
    {
        setResultDisplay(",");
    }

    public void onEqualAction(ActionEvent actionEvent) throws Exception {
        CalculatorLogic.addNumber(Double.parseDouble(txtResult.getText()));
        lblCalculationStr.setText(generateCalculationString());
        double result = CalculatorLogic.calculateSequence();

        String dfResult = df.format(result);

        txtResult.setText(dfResult);

        CalculatorLogic.clear();
        lblOperatorStr.setText("=");

    }

    private void setResultDisplay(String value)
    {
        if(txtResult.getText().equals("0"))
        {
            txtResult.setText(value);
        }
        else
        {
            txtResult.setText(txtResult.getText() + value);
        }
    }

    public void operatorAction(String operator)
    {
        double number = Double.parseDouble(txtResult.getText().replace(',', '.'));
        lblOperatorStr.setText(operator == "*" ? "X" : operator);
        txtResult.clear();
        double result = CalculatorLogic.operatorAction(operator, number);
        if(!Double.isNaN(result))
        {
            txtResult.setText(String.valueOf(result));
        }
    }

    private String generateCalculationString()
    {
        String calculationString = new String();
        ArrayList<Double> numbers = CalculatorLogic.getNumbers();
        ArrayList<String> operators = CalculatorLogic.getOperators();

        for(int i = 0; i<=numbers.size()-1; i++)
        {
            if(operators.size() > i)
            {
                calculationString = calculationString.concat(df.format(numbers.get(i))).concat(operators.get(i));
            }
            else
            {
                calculationString = calculationString.concat(df.format(numbers.get(i)));
            }
        }
        return calculationString;
    }
}