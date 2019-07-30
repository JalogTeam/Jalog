// SolveSudoku.java
import io.github.JalogTeam.jalog.Jalog;
import java.io.*;

public class SolveSudoku
{
  static final String id_string="SolveSudoku 0.1 by Ari Okkonen & Mikko Levanto 2019-05-28";
  
  static Jalog myJalog = new Jalog();

  static final int[][] sudoku_problem = {
    { 8, 0, 0, 0, 2, 0, 0, 3, 0},
    { 9, 0, 4, 6, 0, 0, 0, 1, 2},
    { 0, 5, 0, 0, 7, 1, 0, 0, 6},
    { 0, 0, 0, 0, 0, 0, 0, 7, 0},
    { 6, 0, 2, 0, 3, 0, 0, 8, 0},
    { 4, 8, 0, 0, 1, 9, 0, 6, 5},
    { 0, 6, 8, 7, 5, 0, 9, 4, 0},
    { 1, 2, 0, 0, 0, 4, 7, 0, 0},
    { 0, 0, 0, 1, 0, 8, 6, 0, 0}
  };
  



  public static void main(String args[])
  { 
    int[][] sudoku_solution = new int[9][9];
    System.err.println(id_string);
/*
    for(int i=0;i<args.length;i++){
      System.out.println("" + i + ": '" + args[i] + "'");
    }
*/
    
    myJalog.consult_file("D:\\wa\\Jalog\\demo\\sudoku\\sudoku_solver_compnent.pro");
    System.out.println("Problem");
    print_sudoku(sudoku_problem);
    solve_sudoku(sudoku_problem, sudoku_solution);
    System.out.println("Solution");
    print_sudoku(sudoku_solution);
     
     
    myJalog.dispose();
  }
  
  static final String horizontal_line = "-------------------------";
  static final String symbols = " 123456789";
  
  public static void print_sudoku(int[][] sudoku){
    int i, j;
    
    for (i = 0; i < 9; i++) {
      if ((i % 3) == 0) {
        System.out.println(horizontal_line);
      }
      for (j = 0; j < 9; j++) {
        if ((j % 3) == 0) {
          System.out.print("| ");
        }
        System.out.print(symbols.charAt(sudoku[i][j]) + " ");
      }
      System.out.println("|");
    }
    System.out.println(horizontal_line);
  }

  public static void solve_sudoku(int[][] sudoku_problem, 
      int[][] sudoku_solution) {

    int i, j, value;
    Jalog.Term line[] = new Jalog.Term[9];
    Jalog.Term matrix[] = new Jalog.Term[9];
    Jalog.Term board;
    Jalog.Term element;
    
    for (i = 0; i < 9; i++) {
      for (j = 0; j < 9; j++) {
        value = sudoku_problem[i][j];
        if(value == 0) {
          line[j] = Jalog.open();
        } else {
          line[j] = Jalog.integer(sudoku_problem[i][j]);
        }
      }
      matrix[i] = Jalog.list(line);
    }
    board = Jalog.list(matrix);
    try {
      if (myJalog.call("sudoku1", board))
      {
        matrix = board.getElements();
        for (i = 0; i < 9; i++) {
          line = matrix[i].getElements();
          for (j = 0; j < 9; j++) {
            element = line[j];
            if (element.getType() == Jalog.INTEGER) {
              sudoku_solution[i][j] = (int)element.getIntegerValue();
            } else {
              sudoku_solution[i][j] = 0;
            }
          }
        }
      } else { // fail
        for (i = 0; i < 9; i++) {
          for (j = 0; j < 9; j++) {
            sudoku_solution[i][j] = sudoku_problem[i][j];
          }
        }
      }
    } catch (Jalog.Exit e) {
      for (i = 0; i < 9; i++) {
        for (j = 0; j < 9; j++) {
          sudoku_solution[i][j] = 0;
        }
      }
    }
  }
     
/* Kaleva 2581 */
/*
:-  sudoku1([
    [ 8, _, _, _, 2, _, _, 3, _],
    [ 9, _, 4, 6, _, _, _, 1, 2],
    [ _, 5, _, _, 7, 1, _, _, 6],
    [ _, _, _, _, _, _, _, 7, _],
    [ 6, _, 2, _, 3, _, _, 8, _],
    [ 4,  8, _, _, 1, 9, _, 6, 5],
    [ _,  6, 8, 7, 5, _, 9, 4, _],
    [ 1, 2, _, _, _, 4, 7, _, _],
    [ _, _, _, 1, _, 8, 6, _, _]
    ]).
 
*/
  
}
