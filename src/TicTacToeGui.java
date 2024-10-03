import javax.swing.*;

import java.awt.*;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.awt.event.WindowAdapter;

import java.awt.event.WindowEvent;



public class TicTacToeGui extends JFrame implements ActionListener {



    private int xScore, oScore, moveCounter;

    private boolean isPlayerOne;

    private JLabel turnLabel, scoreLabel, resultLabel;

    private JButton[][] board;

    private JDialog resultDialog;





    public TicTacToeGui() {

        super("Jogo Da Velha");

        setSize(CommonConstants.FRAME_SIZE);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setResizable(false);

        setLocationRelativeTo(null);

        setLayout(null);

        getContentPane().setBackground(CommonConstants.BACKGROUND_COLOR);



        createResultDialog();

        board = new JButton[3][3];



        isPlayerOne = true;







        addGuiComponent();





    }

    private void addGuiComponent() {



        JLabel barLabel = new JLabel();

        barLabel.setOpaque(true);

        barLabel.setBackground(CommonConstants.BAR_COLOR);

        barLabel.setBounds(0, 0, CommonConstants.FRAME_SIZE.width, 25);



        turnLabel = new JLabel(CommonConstants.X_LABEL);

        turnLabel.setHorizontalAlignment(SwingConstants.CENTER);

        turnLabel.setFont(new Font("Dialog", Font.PLAIN, 40));

        turnLabel.setPreferredSize(new Dimension(100,turnLabel.getPreferredSize().height));

        turnLabel.setOpaque(true);

        turnLabel.setBackground(CommonConstants.X_COLOR);

        turnLabel.setForeground(CommonConstants.BOARD_COLOR);

        turnLabel.setBounds(

                (CommonConstants.FRAME_SIZE.width - turnLabel.getPreferredSize().width) / 2,

                0,

                turnLabel.getPreferredSize().width,

                turnLabel.getPreferredSize().height

        );



        scoreLabel = new JLabel(CommonConstants.SCORE_LABEL);

        scoreLabel.setFont(new Font("Dialog", Font.PLAIN, 40));

        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        scoreLabel.setForeground(CommonConstants.BOARD_COLOR);

        scoreLabel.setBounds(

                0,

                turnLabel.getY() + turnLabel.getPreferredSize().height + 25,

                CommonConstants.FRAME_SIZE.width,

                scoreLabel.getPreferredSize().height

        );



        GridLayout gridLayout = new GridLayout(3, 3);

        JPanel boardPanel = new JPanel(gridLayout);

        boardPanel.setBounds(

                0,

                scoreLabel.getY() + scoreLabel.getPreferredSize().height + 35,

                CommonConstants.BOARD_SIZE.width,

                CommonConstants.BOARD_SIZE.height

        );



        for(int i = 0; i < board.length; i++ ) {

            for(int j = 0; j < board[i].length; j++) {

                JButton button = new JButton();

                button.setFont(new Font("Dialog", Font.PLAIN, 180));

                button.setPreferredSize(CommonConstants.BUTTON_SIZE);

                button.setBackground(CommonConstants.BACKGROUND_COLOR);

                button.addActionListener(this);

                button.setBorder(BorderFactory.createLineBorder(CommonConstants.BOARD_COLOR));



                board[i][j] = button;

                boardPanel.add(board[i][j]);

            }

        }



        JButton resetButton = new JButton("Resetar");

        resetButton.setFont(new Font("Dialog", Font.PLAIN, 24));

        resetButton.addActionListener(this);

        resetButton.setBackground(CommonConstants.BOARD_COLOR);

        resetButton.setBounds(

                (CommonConstants.FRAME_SIZE.width - resetButton.getPreferredSize().width) /2,

                CommonConstants.FRAME_SIZE.height - 100,

                resetButton.getPreferredSize().width,

                resetButton.getPreferredSize().height

        );















        getContentPane().add(turnLabel);
        getContentPane().add(barLabel);
        getContentPane().add(scoreLabel);
        getContentPane().add(boardPanel);
        getContentPane().add(resetButton);









    }



    private void createResultDialog() {

        resultDialog = new JDialog();

        resultDialog.getContentPane().setBackground(CommonConstants.BACKGROUND_COLOR);

        resultDialog.setResizable(false);

        resultDialog.setTitle("Resultado");

        resultDialog.setSize(CommonConstants.RESULT_DIALOG_SIZE);

        resultDialog.setLocationRelativeTo(this);

        resultDialog.setModal(true);

        resultDialog.setLayout(new GridLayout(2,1));

        resultDialog.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {

                resetGame();

            }

        });



        resultLabel = new JLabel();

        resultLabel.setFont(new Font("Dialog", Font.BOLD, 18));

        resultLabel.setForeground(CommonConstants.BOARD_COLOR);

        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);




        JButton restartButton = new JButton("Jogar Novamente");
        restartButton.setBackground(CommonConstants.BOARD_COLOR);
        restartButton.addActionListener(this);

        resultDialog.add(resultLabel);
        resultDialog.add(restartButton);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.equals("Resetar") || command.equals("Jogar Novamente")){

            resetGame();


            if(command.equals("Resetar"))
                xScore = oScore = 0;

            if(command.equals("Jogar Novamente"))
                resultDialog.setVisible(false);

        }else{

            JButton button = (JButton) e.getSource();
            if(button.getText().equals("")){
                moveCounter++;


                if(isPlayerOne){

                    button.setText(CommonConstants.X_LABEL);
                    button.setForeground(CommonConstants.X_COLOR);


                    turnLabel.setText(CommonConstants.O_LABEL);
                    turnLabel.setBackground(CommonConstants.O_COLOR);


                    isPlayerOne = false;
                }else {

                    button.setText(CommonConstants.O_LABEL);
                    button.setForeground(CommonConstants.O_COLOR);


                    turnLabel.setText(CommonConstants.X_LABEL);
                    turnLabel.setBackground(CommonConstants.X_COLOR);


                    isPlayerOne = true;
                }


                if(isPlayerOne){

                    checkOWin();
                }{

                    checkXWin();
                }


                checkDraw();


                scoreLabel.setText("X: " + xScore + " | O: " + oScore);
            }

            repaint();
            revalidate();
        }
    }



    private void checkXWin() {

        String result = "X venceu!";



        for(int row = 0; row < board.length; row++) {

            if(board[row][0].getText().equals("X") && board[row][1].getText().equals("X") && board[row][2].getText().equals("X")){

                resultLabel.setText(result);



                resultDialog.setVisible(true);



                xScore++;



            }

        }



        for(int col = 0; col < board.length; col++) {

            if(board[0][col].getText().equals("X") && board[1][col].getText().equals("X") && board[2][col].getText().equals("X")){

                resultLabel.setText(result);



                resultDialog.setVisible(true);



                xScore++;



            }

        }



        if(board[0][0].getText().equals("X") && board[1][1].getText().equals("X") && board[2][2].getText().equals("X")){

            resultLabel.setText(result);



            resultDialog.setVisible(true);



            xScore++;



        }



        if(board[2][0].getText().equals("X") && board[1][1].getText().equals("X") && board[0][2].getText().equals("X")){

            resultLabel.setText(result);



            resultDialog.setVisible(true);



            xScore++;



        }

    }



    private void checkOWin() {

        String result = "O venceu!";



        for(int row = 0; row < board.length; row++) {

            if(board[row][0].getText().equals("O") && board[row][1].getText().equals("O") && board[row][2].getText().equals("O")){

                resultLabel.setText(result);



                resultDialog.setVisible(true);



                oScore++;



            }

        }



        for(int col = 0; col < board.length; col++) {

            if(board[0][col].getText().equals("O") && board[1][col].getText().equals("O") && board[2][col].getText().equals("O")){

                resultLabel.setText(result);



                resultDialog.setVisible(true);



                oScore++;



            }

        }



        if(board[0][0].getText().equals("O") && board[1][1].getText().equals("O") && board[2][2].getText().equals("O")){

            resultLabel.setText(result);



            resultDialog.setVisible(true);



            oScore++;



        }



        if(board[2][0].getText().equals("O") && board[1][1].getText().equals("O") && board[0][2].getText().equals("O")){

            resultLabel.setText(result);



            resultDialog.setVisible(true);



            oScore++;



        }

    }



    private void checkDraw(){

        if(moveCounter >= 9){

            resultLabel.setText("Empate!");

            resultDialog.setVisible(true);

        }

    }



    private void resetGame(){



        isPlayerOne = true;

        turnLabel.setText(CommonConstants.X_LABEL);

        turnLabel.setBackground(CommonConstants.X_COLOR);





        scoreLabel.setText(CommonConstants.SCORE_LABEL);





        moveCounter = 0;





        for(int i = 0; i < board.length; i++){

            for(int j = 0; j < board[i].length; j++){

                board[i][j].setText("");

            }

        }

    }

}