package dk.easv.bll;

import java.util.ArrayList;

public class Calculator
{
    private ArrayList<Double> Numbers = new ArrayList<Double>();
    private ArrayList<String> Operators = new ArrayList<String>();
    private Operations CurrentOperation = Operations.NAN;

    public void setOperation(String operation)
    {
        switch(operation)
        {
            case "+":
                CurrentOperation = Operations.ADDITION;
                Operators.add(operation);
                break;
            case "-":
                CurrentOperation = Operations.SUBTRACTION;
                break;
            case "*":
                CurrentOperation = Operations.MULTIPLICATION;
                break;
            case "/":
                CurrentOperation = Operations.DIVISION;
                break;
            default:
                CurrentOperation = Operations.NAN;
        }
    }

    public void addNumber(double number)
    {
        this.Numbers.add(number);
    }

    public void clear()
    {
        this.Numbers.clear();
        this.Operators.clear();
        CurrentOperation = Operations.NAN;
    }


    public double operatorAction(String operator, double number)
    {
        if(operator != "%")
        {
            Operators.add(operator);
            Numbers.add(number);
            return Double.NaN;
        }
        else
        {
            String lastOperator = Operators.getLast();

            if(lastOperator.equals("-"))
            {
                double calculation = calculate("/", number, 100);
                Numbers.add(calculation*10);
                Operators.set(Operators.size()-1, "-");
                //lblOperatorStr.setText("-");
                return calculation;
            }
            else
            {
                double calculation = calculate("+",
                                calculate("/", number, 100), 1);
                Operators.set(Operators.size()-1, "*");
                return calculation;
                //lblOperatorStr.setText("X");
            }
        }
    }

    public double calculate(String operator, double value1, double value2)
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

    public double calculateSequence() throws Exception {
        // Check if the numbers list are 1 size bigger than the operators list.
        if (Numbers.size() != Operators.size() + 1)
        {
            throw new Exception("Numbers of oprators should be less than the number of numbers");
        }

        double result = Numbers.get(0);

        //Checks of the multiply or division operation exists.
        if((Operators.contains("*") || Operators.contains("/")) && Numbers.size()>=3)
        {
            // Run through all operators.
            for(int i = 0; i<= Operators.size()-1; i++)
            {
                // if either multiplicity or division; multiply or divide number at index i with i+1 and place the calculation at index i and remove index i+1.
                if(Operators.get(i) == "*")
                {
                    Numbers.set(i, Numbers.get(i) * Numbers.get(i+1));
                    Numbers.remove(i+1);
                    Operators.remove(i);
                    i = i -1; // Reduction in the i variable, because we have removed an element, to ensure all elements will be traversed.
                }
                else if(Operators.get(i) == "/")
                {
                    Numbers.set(i, Numbers.get(i) / Numbers.get(i+1));
                    Numbers.remove(i+1);
                    Operators.remove(i);
                    i = i -1; // Reduction in the i variable, because we have removed an element, to ensure all elements will be traversed.
                }
            }
        }
        // Now run through the new set of numbers and do addition and minus operation.
        for (int i = 0; i < Operators.size(); i++)
        {
            result = calculate(Operators.get(i), Numbers.get(i), Numbers.get(i + 1));
        }

        return result;
    }

    public ArrayList<Double> getNumbers()
    {
        return Numbers;
    }

    public ArrayList<String> getOperators()
    {
        return Operators;
    }
}
