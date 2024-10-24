package dk.easv.extenedcalculatorwoarchitecture;

import dk.easv.bll.Calculator;
import dk.easv.bll.MemoryLogic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.print.DocFlavor;
import javax.swing.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HelloController {

    @FXML
    private TextField txtResult;

    @FXML
    private Label lblOperatorStr;

    @FXML
    private Label lblCalculationStr;

    @FXML
    private ListView lstHistory;

    private Calculator CalculatorLogic = new Calculator();
    private MemoryLogic MemoryEngine = new MemoryLogic();

    DecimalFormat df = new DecimalFormat("#,###.##");


    @FXML
    protected void initialize()
    {
        lblOperatorStr.setText("");
        lblCalculationStr.setText("");

        reloadHistory();

    }

    public void reloadHistory()
    {
        lstHistory.getItems().clear();
        List<String> historyList = MemoryEngine.loadMemory(true);
        for(int i = 0; i<=historyList.size()-1; i=i+1)
        {
            lstHistory.getItems().add(historyList.toArray()[i]);
        }
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
        MemoryEngine.addToMemory(calculationString);
        reloadHistory();
        return calculationString;
    }

    public void onBtnClearHistory(ActionEvent actionEvent)
    {
        Optional<ButtonType> message = showMessageBox(Alert.AlertType.CONFIRMATION, "Are you sure?", "This will clear your history", "This will clear your history. If you press Yes, it will be cleared and cannot be recalled.");
        if(message.get() == ButtonType.OK)
        {
            if (this.MemoryEngine.clearMemory())
            {
                reloadHistory();
            }
        }
    }

    private Optional<ButtonType> showMessageBox(Alert.AlertType alertType, String title, String headerText, String messageText)
    {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(messageText);
        return alert.showAndWait();
    }
}