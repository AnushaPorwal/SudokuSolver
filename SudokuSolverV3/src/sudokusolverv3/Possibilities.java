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
class Possibilities {
    int row, col;
    int[] possibilityArray = new int[9];
    int numberOfPossibilities = 0;
    int flag = 0;

    //public static void main(String[] args){}

    public void setRowCol(int r,int c)
    {
        row = r;
        col = c;
    }

    public void setFlag(int f)
    {
        flag = f;
    }

    public void AddIntoPossibilityArray(int num)
    {
        possibilityArray[numberOfPossibilities] = num;
        numberOfPossibilities = numberOfPossibilities + 1;
    }
}
