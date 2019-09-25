/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokusolverv3;

/**
 *
 * @author Anusha
 */

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;
import javafx.scene.layout.Border;
import javax.swing.*;

public class SudokuSolverV3 extends JFrame{
  
    static int csvFlag = 0;
    static int boardFlag = 0;
    static Possibilities[] p = new Possibilities[81];
    static JTextField[][] jt = new JTextField[9][9];
    static int[][] board = new int[][]
        {
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0}
        };
    
    static int countRecursive = 0;
    
    SudokuSolverV3()
    {
        JFrame f = new JFrame("Sudoku Solver");
        f.setLayout(new BorderLayout());
        
        //**************************************************   radiobutton panel  ********************************
        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout());
        
        JRadioButton rb1 = new JRadioButton("CSV");
        JRadioButton rb2 = new JRadioButton("Board");
        
        p1.add(rb1);
        p1.add(rb2);
        
        //***********************************************     top layout    **************************************
        JPanel topLayout = new JPanel();
        topLayout.setLayout(new FlowLayout());
        
        JButton Solve_JButton = new JButton("Solve");
        JTextField path = new JTextField(30);
        JLabel label = new JLabel("Enter File Path: ");
        
        topLayout.add(label);
        topLayout.add(path);
        topLayout.add(Solve_JButton);
        
        //**************************************************    grid    *********************************************
        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(9,9));
        
        javax.swing.border.Border borderBlue = BorderFactory.createLineBorder(Color.CYAN, 5);
        javax.swing.border.Border borderYellow = BorderFactory.createLineBorder(Color.GRAY, 5);
        Font font1 = new Font("SansSerif", Font.BOLD, 30);
        
        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                jt[i][j] = new JTextField(5);
                jt[i][j].setText("");
                jt[i][j].setFont(font1);
                jt[i][j].setHorizontalAlignment(JTextField.CENTER);

                
                //Colouring of the different subgrids
                if((i%9 == 0 || i%9 == 1 || i%9 == 2 || i%9 == 6 || i%9 == 7 || i%9 == 8) && (j%9 == 0 || j%9 == 1 || j%9 == 2 || j%9 == 6 || j%9 == 7 || j%9 == 8))
                {
                    jt[i][j].setBorder(borderBlue);
                }
                else if((i%9 == 3 || i%9 == 4 || i%9 == 5) && (j%9 == 3 || j%9 == 4 || j%9 == 5))
                {
                    jt[i][j].setBorder(borderBlue);
                }
                else
                {
                    jt[i][j].setBorder(borderYellow);
                }
                //add the textField to the panel
                grid.add(jt[i][j]);
            }
        }
        
        JPanel bigPanel = new JPanel();
        bigPanel.setLayout(new GridLayout(2,1));
        bigPanel.add(p1);
        bigPanel.add(topLayout);
        
        //**********************************************     Add to Frame    ********************************************
        f.add(bigPanel, BorderLayout.NORTH);
        f.add(grid, BorderLayout.CENTER);
        
        //********************************************** Necessary Things for Frames ************************************
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(700, 700);
        f.setVisible(true);
        
        //************************************************** actionListeners ********************************************
        rb1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == 1)
                {
                    csvFlag = 1;
                }
                else
                {
                    csvFlag = 0;
                }
            }
        });
        
        rb2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == 1)
                {
                    boardFlag = 1;
                }
                else
                {
                    boardFlag = 0;
                }
            }
        });
        
        Solve_JButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(csvFlag == 1)
                {
                    String[] csv_values = new String[81];
                    String csv_filepath = path.getText();
        
                    File file = new File(csv_filepath);
                    String row;
                    try (BufferedReader csvReader = new BufferedReader(new FileReader(csv_filepath))) {
                        while ((row = csvReader.readLine()) != null) {
                            csv_values = row.split(",");
                        }
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(SudokuSolverV3.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(SudokuSolverV3.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    int z = 0;
                    int i, j;
                    for(i = 0; i < 9; i++)
                    {
                        for(j = 0; j < 9; j++)
                        {
                            board[i][j] = Integer.parseInt(csv_values[z]);
                            z = z + 1;
                            if(board[i][j] != 0)
                            {
                                jt[i][j].setText("" + board[i][j]);
                                jt[i][j].setForeground(Color.red);
                            }
                        }
                    }
                }
                else if(boardFlag == 1)
                {    
                    for(int i = 0; i < 9; i++)
                    {
                        for(int j = 0; j < 9; j++)
                        {
                            if (jt[i][j].getText().equals(""))
                                board[i][j] = 0;
                            else
                            {
                                board[i][j] = Integer.parseInt(jt[i][j].getText());
                                jt[i][j].setForeground(Color.red);
                            }
                            
                        }
                    }
                }
               print(board);
               long startTime = System.currentTimeMillis();
               SolveSudoku_Full();
               long endTime = System.currentTimeMillis();
               
               System.out.println("Time taken in milli seconds: " + (endTime - startTime)); 
            }
        });
        
    }
        
    public static void main(String[] args) throws FileNotFoundException, IOException {

        SudokuSolverV3 s1 = new SudokuSolverV3();
    }
    
    public static void SolveSudoku_Full()
    {       
        int i, j;
        int rowNum, colNum;
        int SumOfFlags_n = 0;
        int SumOfFlags_n_1 = 0;
        int continueLoop = 1;
        int countIterations = 0;
        while(continueLoop == 1)
        {
            countIterations = countIterations + 1;
            for(i = 0; i < 81; i++)
            {
                p[i] = new Possibilities();
                
                //to get the row and column number on the grid.
                rowNum = (int)(i/9);
                colNum = (int)(i%9);

                p[i].setRowCol(rowNum, colNum);

                if(board[rowNum][colNum] != 0)
                {
                    // if board position is not zero, it already has an element in it.
                    // so then you put that number in the array of possible numbers and number of possibilities = 1
                    // flag variable is set to 1
                    
                    p[i].AddIntoPossibilityArray(board[rowNum][colNum]);
                    p[i].setFlag(1);
                    
                }

                else
                {
                    for(j = 1; j <= 9; j++)
                    {
                        if(NotInRow(board, j, rowNum) && NotInCol(board, j, colNum) && NotInSubGrid(board, j, rowNum, colNum))
                        {
                            //if the number is not in the row, column or the subgrid, then it is added to the array of possible numbers for that box.
                            p[i].AddIntoPossibilityArray(j);
                        }
                    }
                }


                if(p[i].numberOfPossibilities == 1)
                {
                    // if by chance the number of possibilities is 1, ie, there is only one possible number that fits in a box, then you fix it
                    // by adding it to the board.
                    
                    board[rowNum][colNum] = p[i].possibilityArray[0];
                    jt[rowNum][colNum].setText("" + board[rowNum][colNum]);
                    p[i].setFlag(1);
                    print(board);
                }
                
                SumOfFlags_n = SumOfFlags_n + p[i].flag;
                
                
                if((rowNum%3 == 2) && (colNum%3 == 2))
                {
                    //if rowNum and colNum give a remainder of 2 when divided by 3, that means a subgrid is done filling its possibilities array.
                    int k, t, u, number, w;
                    int indexToCheck;
                    
                    //this array will keep track of the counts of each number in all the possibilities arrays of a subgrid.
                    int[] countOfPossibleNumbersInSubGrid = new int[] {0,0,0,0,0,0,0,0,0};
                    
                    //finding the index of the subgrid in which the element board[rowNum][colNum] is in.
                    int m = (int)(rowNum/3);
                    int n = (int)(colNum/3);

                    m = m*3;
                    n = n*3;

                    //traversing the subgrid to count the numbers in all subgrids together.
                    for(k = m; k < m+3; k++)
                    {
                        for(t = n; t < n+3; t++)
                        {
                            indexToCheck = k*9 + t;
                            if(p[indexToCheck].flag == 1)
                            {
                                number = p[indexToCheck].possibilityArray[0] - 1;
                                countOfPossibleNumbersInSubGrid[number] = 0;
                            }
                            if(p[indexToCheck].flag == 0)
                            {
                                // only checks if flag = 0. if flag = 1, that means the number is fixed in that position.
                                for(u = 0; u < p[indexToCheck].numberOfPossibilities; u++)
                                {
                                    //get number from possibilitiesArray, and increase the count of number-1 in countOfPossibleNumbersInSubGrid Array by 1.
                                    number = p[indexToCheck].possibilityArray[u];
                                    number = number - 1;
                                    countOfPossibleNumbersInSubGrid[number] = countOfPossibleNumbersInSubGrid[number] + 1;
                                }
                                
                            }
                            
                        }
                    }
                    
                    for(w = 0; w < 9; w++)
                    {
                        
                        if(countOfPossibleNumbersInSubGrid[w] == 1)
                        {   
                            int flag1 = 0;
                            
                            for(k = m; k < m+3; k++)
                            {
                                for(t = n; t < n+3; t++)
                                {
                                    indexToCheck = k*9 + t;
                                    if(p[indexToCheck].flag == 0)
                                    {
                                        for(u = 0; u < p[indexToCheck].numberOfPossibilities; u++)
                                        {
                                            if(w+1 == p[indexToCheck].possibilityArray[u])
                                            {
                                                flag1 = 1;
                                                p[indexToCheck].numberOfPossibilities = 1;
                                                p[indexToCheck].setFlag(1);
                                                p[indexToCheck].setRowCol(indexToCheck/9, indexToCheck%9);
                                                
                                                board[indexToCheck/9][indexToCheck%9] = w+1;
                                                jt[indexToCheck/9][indexToCheck%9].setText("" + board[indexToCheck/9][indexToCheck%9]);
                                                p[indexToCheck].possibilityArray[0] = w+1;
                                                
                                                print(board);
                                                break;
                                            }
                                        }
                                        if(flag1 == 1)
                                        {
                                            break;
                                        }

                                    }

                                }
                                
                                if(flag1 == 1)
                                {
                                    SumOfFlags_n = SumOfFlags_n + 1;
                                    break;
                                }
                            }
                        }
                    }
                    
                }
                
                
            }   // end of for loop
            
            
            if(SumOfFlags_n == SumOfFlags_n_1)
            {
                continueLoop = 0;
            }
            else
            {
                SumOfFlags_n_1 = SumOfFlags_n;
                SumOfFlags_n = 0;
                continueLoop = 1;
            }
            
        }
        
        System.out.println("Iterations of non-recursive algorithm: " + countIterations);
        
        System.out.println("Start of the Backtracking Algorithm:");
        if (solveSudoku_Recursion(board)) 
        { 
            print(board);
            System.out.println("Iterations of non-recursive algorithm: " + countRecursive);
        }  
        else
        { 
            System.out.println("No solution"); 
        }
    }
    
    public static void print(int[][] board) 
    { 
        //print board 
        
        System.out.println("*****************************");
        System.out.println();
        
        for (int r = 0; r < 9; r++) 
        { 
            for (int d = 0; d < 9; d++) 
            { 
                System.out.print(board[r][d]); 
                System.out.print(" "); 
            } 
            System.out.print("\n"); 
        } 
        
        System.out.println();
    }
    
    public static boolean NotInRow(int[][] gridToSolve, int number, int rowNum)
    {
        //Function to check if a number is not in the row.
        //If number is NOT in row, it returns TRUE
        //else it returns TRUE
        int j;
        for(j = 0; j < 9; j++)
        {
            if(gridToSolve[rowNum][j] == number)
                return false;
        }
        
        return true;
    }
    
    public static boolean NotInCol(int[][] gridToSolve, int number, int colNum)
    {
        //Function to check if a number is not in the column.
        //If number is NOT in Column, it returns TRUE
        //else it returns TRUE
        int i;
        for(i = 0; i < 9; i++)
        {
            if(gridToSolve[i][colNum] == number)
                return false;
        }
        
        return true;
    }
    
    public static boolean NotInSubGrid(int[][] gridToSolve, int number, int row, int col)
    {
        //Function to check if a number is not in the sub grid.
        //If number is NOT in subgrid, it returns TRUE
        //else it returns TRUE
        int i, j;
        int m, n;
        
        m = (int)(row/3);
        n = (int)(col/3);
        
        m = m*3;
        n = n*3;
        
        for(i = m; i < m+3; i++)
        {
            for(j = n; j < n+3; j++)
            {
                if(gridToSolve[i][j] == number)
                    return false;
            }
        }
        
        return true;
    } 

    public static boolean solveSudoku_Recursion(int[][] board)  
    { 
        //recursive function to solve the remaining sudoku using
        //backtracking and the possibilities we found in the previous section.
        int row = -1; 
        int col = -1; 
        int EmptySpaceLeft = 0; 
        for (int i = 0; i < 9; i++) 
        { 
            for (int j = 0; j < 9; j++)  
            { 
                if (board[i][j] == 0)  
                { 
                    row = i; 
                    col = j; 

                    //we still have some empty cells in the sudoku to be filled.
                    EmptySpaceLeft = 1;  
                    break; 
                } 
            } 
            if (EmptySpaceLeft == 1) 
            { 
                break; 
            } 
        } 

        //no empty space left, sudoku is filled, so return true value to main.
        if (EmptySpaceLeft == 0)  
        { 
            return true; 
        } 

        int num;
        int indexToCheck = row*9 + col;
        
        //backtracking from the array of possivilities created in the previous step.
        for (int i = 0; i < p[indexToCheck].numberOfPossibilities; i++) 
        { 
            num = p[indexToCheck].possibilityArray[i];
            
            if(NotInRow(board, num, row) && NotInCol(board, num, col) && NotInSubGrid(board, num, row, col))
            { 
                board[row][col] = num; 
                jt[row][col].setText("" + board[row][col]);
                if (solveSudoku_Recursion(board))  
                {
                    //if sudoku works out with num value, then return true value.
                    countRecursive = countRecursive + 1;
                    return true; 
                }  
                else
                { 
                    //if sudoku DOESN'T works out with num value, then return false.
                    //replace it with 0
                    board[row][col] = 0;
                    jt[row][col].setText("0");
                } 
            } 
        } 
        return false;
    }
}