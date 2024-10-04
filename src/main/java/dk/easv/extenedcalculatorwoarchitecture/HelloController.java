package dk.easv.extenedcalculatorwoarchitecture;

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

    private ArrayList<Double> numbers = new ArrayList<Double>();

    private ArrayList<String> operators = new ArrayList<String>();

    DecimalFormat df = new DecimalFormat("#,###.##");


    @FXML
    protected void initialize()
    {
        lblOperatorStr.setText("");
        lblCalculationStr.setText("");
    }

    public void onClearAction(ActionEvent actionEvent)
    {
        numbers.clear();
        operators.clear();
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

    public void onEqualAction(ActionEvent actionEvent)
    {
        numbers.add(Double.parseDouble(txtResult.getText()));
        lblCalculationStr.setText(generateCalculationString());
        double result = calculateSequence();

        String dfResult = df.format(result);

        txtResult.setText(dfResult);

        numbers.clear();
        operators.clear();
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

    private void operatorAction(String operator)
    {
        if(operator != "%")
        {
            double number = Double.parseDouble(txtResult.getText().replace(',', '.'));
            operators.add(operator);
            lblOperatorStr.setText(operator == "*" ? "X" : operator);
            numbers.add(number);
            txtResult.clear();
        }
        else
        {
            String lastOperator = operators.getLast();
            int percentageNumber = Integer.parseInt(txtResult.getText());

            if(lastOperator.equals("-"))
            {
                double calculation = calculate("/", percentageNumber, 100);
                numbers.add(calculation*10);
                operators.set(operators.size()-1, "-");
                lblOperatorStr.setText("-");
                txtResult.setText(String.valueOf(calculation));
            }
            else
            {
                txtResult.setText(String.valueOf(
                        (calculate("+",
                                calculate("/", percentageNumber, 100), 1)
                        )
                ));
                operators.set(operators.size()-1, "*");
                lblOperatorStr.setText("X");
            }
        }
    }

    private double calculate(String operator, double value1, double value2)
    {
        switch (operator)
        {
            case "+":
                return value1 + value2;

            case "-":
                return value1 - value2;

            case "*":
                return value1 * value2;

            case "/":
                return value1 / value2;

            default:
                return -990.990;
        }
    }

    private double calculateSequence()
    {
        // Check if the numbers list are 1 size bigger than the operators list.
        if (numbers.size() != operators.size() + 1)
        {
            txtResult.setText("Invalid input: number of operators should be one less than the number of numbers");
        }

        double result = numbers.get(0);

        //Checks of the multiply or division operation exists.
        if((operators.contains("*") || operators.contains("/")) && numbers.size()>=3)
        {
            // Run through all operators.
            for(int i = 0; i<= operators.size()-1; i++)
            {
                // if either multiplicity or division; multiply or divide number at index i with i+1 and place the calculation at index i and remove index i+1.
                if(operators.get(i) == "*")
                {
                    numbers.set(i, numbers.get(i) * numbers.get(i+1));
                    numbers.remove(i+1);
                    operators.remove(i);
                    i = i -1; // Reduction in the i variable, because we have removed an element, to ensure all elements will be traversed.
                }
                else if(operators.get(i) == "/")
                {
                    numbers.set(i, numbers.get(i) / numbers.get(i+1));
                    numbers.remove(i+1);
                    operators.remove(i);
                    i = i -1; // Reduction in the i variable, because we have removed an element, to ensure all elements will be traversed.
                }
            }
        }
        // Now run through the new set of numbers and do addition and minus operation.
        for (int i = 0; i < operators.size(); i++)
        {
            result = calculate(operators.get(i), numbers.get(i), numbers.get(i + 1));
        }

        return result;
    }

    private String generateCalculationString()
    {
        String calculationString = new String();
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