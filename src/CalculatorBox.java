
import javax.swing.*;
import java.awt.event.ActionListener;
import java.lang.invoke.CallSite;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;


public class CalculatorBox extends JFrame{
        JButton btnAdd, btnSubtract, btnDivide, btnMultiply, btnClear, btnDelete, btnEquals, btnDot, btnMod, btnNeg;
        JButton[] numBtn;
        JTextField output;
        String previous = "";
        String current = "";
        String operator;


    public CalculatorBox(){
        super("Box Calculator");
        JPanel mainPanel = new JPanel();

        JPanel row1 = new JPanel();
        JPanel row2 = new JPanel();
        JPanel row3 = new JPanel();
        JPanel row4 = new JPanel();
        JPanel row5 = new JPanel();

        output = new JTextField(16);
        output.setEditable(false);
        btnSubtract = new JButton("-");
        btnAdd = new JButton("+");
        btnDivide = new JButton("÷");
        btnMultiply = new JButton("*");
        btnDot = new JButton(".");
        btnEquals = new JButton("=");
        btnClear = new JButton("C");
        btnDelete = new JButton("D");
        btnMod = new JButton("%");
        btnNeg = new JButton("¬");


        NumberBtnHandler numBtnHandler = new NumberBtnHandler();
        OtherBtnHandler otherBtnHandler = new OtherBtnHandler();
        OperatorBtnHandler opBtnHandler = new OperatorBtnHandler();

        numBtn = new JButton[11];
        numBtn[10] = btnDot;

        for(int count = 0; count < numBtn.length -1; count++){
            numBtn[count] = new JButton(String.valueOf(count));
            numBtn[count].setFont(new Font("Monospaced", Font.BOLD, 22));
            numBtn[count].addActionListener(numBtnHandler);
        }




        btnDot.setFont(new Font("Monospaced", Font.BOLD, 22));
        btnEquals.setFont(new Font("Monospaced", Font.BOLD, 22));
        btnAdd.setFont(new Font("Monospaced", Font.BOLD, 22));
        btnSubtract.setFont(new Font("Monospaced", Font.BOLD, 22));
        btnDivide.setFont(new Font("Monospaced", Font.BOLD, 22));
        btnMultiply.setFont(new Font("Monospaced", Font.BOLD, 22));
        btnClear.setFont(new Font("Monospaced", Font.BOLD, 22));
        btnDelete.setFont(new Font("Monospaced", Font.BOLD, 22));
        btnMod.setFont(new Font("Monospaced", Font.BOLD, 22));
        btnNeg.setFont(new Font("Monospaced", Font.BOLD, 22));

        output.setMaximumSize(new Dimension(185,40));
        output.setFont(new Font("Monospaced", Font.BOLD, 27));
        output.setDisabledTextColor(new Color(0,0,0));
        output.setMargin(new Insets(0,5,0,0));
        output.setText("0");

        // Add action listeners to other buttons
        btnDot.addActionListener(numBtnHandler);
        btnDelete.addActionListener(otherBtnHandler);
        btnClear.addActionListener(otherBtnHandler);
        btnEquals.addActionListener(otherBtnHandler);

        // Add action listeners to operation buttons
        btnMultiply.addActionListener(opBtnHandler);
        btnAdd.addActionListener(opBtnHandler);
        btnSubtract.addActionListener(opBtnHandler);
        btnDivide.addActionListener(opBtnHandler);
        btnMod.addActionListener(opBtnHandler);
        btnNeg.addActionListener(opBtnHandler);

        output.setText("0");


        row1.setLayout(new GridLayout(1, 1));
        row2.setLayout(new GridLayout(1, 1));
        row3.setLayout(new GridLayout(1, 1));
        row4.setLayout(new GridLayout(1, 1));
        row5.setLayout(new GridLayout(1, 1));

        //row1.add(Box.createHorizontalGlue());
        row1.add(btnNeg);
        row1.add(btnMod);
        row1.add(btnClear);
        row1.add(btnDelete);
        row2.add(numBtn[7]);
        row2.add(numBtn[8]);
        row2.add(numBtn[9]);
        row2.add(btnMultiply);
        row3.add(numBtn[4]);
        row3.add(numBtn[5]);
        row3.add(numBtn[6]);
        row3.add(btnAdd);
        row4.add(numBtn[1]);
        row4.add(numBtn[2]);
        row4.add(numBtn[3]);
        row4.add(btnSubtract);
        row5.add(btnDot);
        row5.add(numBtn[0]);
        row5.add(btnEquals);
        row5.add(btnDivide);

        mainPanel.setLayout(new GridLayout(6, 5));
        mainPanel.add(output);
        //mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(row1);
        mainPanel.add(row2);
        mainPanel.add(row3);
        mainPanel.add(row4);
        mainPanel.add(row5);

        this.add(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        //this.setSize(205, 280);
        this.setMinimumSize(new Dimension(205,280));

    }

        public void delete() {
            if(current.length() > 0) {
                current = current.substring(0, current.length() - 1);
            }
        }

        public void clear() {
            previous = "";
            current = "";
            operator = null;
        }

        public void updateOutput(){
            output.setText(current);
            if(current.length() == 0){
                output.setText("0");
            }
        }

        public void appendToOutput(String num) {
            if(num.equals(".") && current.contains(".")){
                return;
            }
            current += num;
        }

        public void selectOperator(String newOperator) {
            if(current.isEmpty()){
                operator = newOperator;
                return;
            }

            if(!previous.isEmpty()){
                calculate();
            }

            operator = newOperator;
            previous = current;
            current = "";
        }

        public void calculate() {

            if(previous.length() < 1 || current.length() < 1){
                return;
            }

            double result = 0.0;
            double num1 = Double.parseDouble(previous);
            double num2 = Double.parseDouble(current);

            switch (operator){
                case "*":
                    result = num1 * num2;
                    break;
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "÷":
                    if(num2 != 0) result = num1 / num2;
                    break;
                case "%":
                    result = num1 % num2;
                    break;
                default:
                    break;
            }
            current = String.valueOf(result);
            operator = null;
            previous = "";
            prettyString();
        }

        public void prettyString(){
            if(current.length() > 0){
                String integer = current.split("\\.")[0];
                String decimal = current.split("\\.")[1];
                if(decimal.equals("0")){
                    current = integer;
                }
            }

        }

        private class NumberBtnHandler implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e){
                JButton selectedBtn = (JButton) e.getSource();
                for(JButton btn : numBtn){
                    if (selectedBtn == btn) {
                        appendToOutput(btn.getText());
                        updateOutput();
                    }
                }
            }
        }

        private class OperatorBtnHandler implements  ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton selectedBtn = (JButton) e.getSource();
                if(selectedBtn == btnMultiply){
                    selectOperator(btnMultiply.getText());
                }
                else if(selectedBtn == btnAdd){
                    selectOperator(btnAdd.getText());
                }
                else if(selectedBtn == btnSubtract){
                    selectOperator(btnSubtract.getText());
                }
                else if(selectedBtn == btnDivide){
                    selectOperator(btnDivide.getText());
                }
                else if(selectedBtn == btnMod){
                    selectOperator(btnMod.getText());
                }
                else if(selectedBtn == btnNeg){
                    current = String.valueOf(Integer.parseInt(current) * (-1));
                }
                updateOutput();
            }
        }

        private class OtherBtnHandler implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e){
                JButton selectedBtn = (JButton) e.getSource();
                if(selectedBtn == btnDelete){
                    delete();
                }
                else if(selectedBtn == btnClear){
                    clear();
                }
                else if (selectedBtn == btnEquals){
                    calculate();
                }
                updateOutput();
            }
        }

    public static void main(String[] args){
        new CalculatorBox();
    }


    }