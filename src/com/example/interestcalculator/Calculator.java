package com.example.interestcalculator;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.text.DecimalFormat;

public class Calculator extends JDialog {
    private JPanel contentPane;
    private JButton buttonCalculate;
    private JButton buttonCancel;
    private JTextField tfPrincipalAmount;
    private JTextField tfInterest;
    private JTextField tfInterestAmount;
    private JRadioButton simpleRadioButton;
    private JRadioButton comoundRadioButton;
    private JLabel tfValidationError;
    private JTextField tfTenure;
    private JTextField tfTotalValue;
    private JComboBox cbFrequency;
    private JPanel pCompoundFrequency;
    private ButtonGroup InterestType;

    public Calculator() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonCalculate);

        simpleRadioButton.setSelected(true);

        buttonCalculate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCalculate();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


        cbFrequency.addItem(new ComboItem("Daily","365"));
        cbFrequency.addItem(new ComboItem("Weekly","52"));
        cbFrequency.addItem(new ComboItem("Monthly","12"));
        cbFrequency.addItem(new ComboItem("Semi-Annually","2"));
        cbFrequency.addItem(new ComboItem("Annually","1"));

        comoundRadioButton.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                toggleCompoundFreq();
            }
        });
        simpleRadioButton.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                toggleCompoundFreq();
            }
        });
    }

    private void toggleCompoundFreq() {
        if (comoundRadioButton.isSelected())
            pCompoundFrequency.setVisible(true);
        else
            pCompoundFrequency.setVisible(false);
    }
    private void onCalculate() {
        // add your code here
        tfValidationError.setText("");
        Double principal = 0.0d;
        Float interestRate = 0.0f;
        Integer tenure = 0;
        Double InterestAmount = 0.0d;
        Double TotalValue = 0.0d;
        try {
            principal = Double.parseDouble(tfPrincipalAmount.getText());
            interestRate = Float.parseFloat(tfInterest.getText());
            tenure = Integer.parseInt(tfTenure.getText());
        }
        catch (Exception e) {
            tfValidationError.setText("Invalid Input");
        }
        if (this.simpleRadioButton.isSelected()) {
            //System.out.print(interestRate.toString()+"-"+tenure.toString());
            TotalValue = (double) principal * (1+(interestRate/100/12)*(tenure));
            InterestAmount = (double) TotalValue - principal;
            tfTotalValue.setText(new DecimalFormat("#.00").format(TotalValue).toString());
            tfInterestAmount.setText(new DecimalFormat("#.00").format(InterestAmount).toString());
        }
        else{
            int n = Integer.parseInt(((ComboItem) cbFrequency.getSelectedItem()).getValue());
            int totalYear = tenure/12;
            TotalValue = (double)principal * Math.pow(( (1+(interestRate/100)/n)),(n*totalYear));
            InterestAmount = (double)  TotalValue - principal;
            tfTotalValue.setText(new DecimalFormat("#.00").format(TotalValue).toString());
            tfInterestAmount.setText(new DecimalFormat("#.00").format(InterestAmount).toString());
        }
        //dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        Calculator dialog = new Calculator();
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }



        dialog.setTitle("Interest Calculator");
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
