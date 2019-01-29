// Parser_Test.java
import java.io.*;

public class Parser_Test
{
  static void test1(String S)
  {
    Parser Pr1 = new Parser();
    Pro_Term T;
    System.out.println("\nLine: " + S);
    Pr1.SetString(S);
    do
    {
//      T = Pr1.NextTerm();
      T = Pr1.NextClause();
      System.out.println("Term: " + T);
    } while(T != null);
  }
  public static void main(String args[])
  {
    String temp = new String("\\abc\"xyz");
    String temp2;
    test1(":- print(\"jee\").");
    test1("appendX([],X,X).");
    test1("appendX([H|T],X,[H|T2]) :- appendX(T, X, T2).");

//test1("funktori(256,'x',f(3),\"heip\\\\pa\",[],[X],[15|_],_B,3.141,More,\"\\\"\")");
//    test1("256");
//    test1("256+14*(23- 15)/39+ 7 div 8 - 3 mod 6");
/*
    temp2 = temp.replaceAll("\\\\","\\\\\\\\");
    temp2 = temp2.replaceAll("\\\"","\\\\\\\"");
    System.out.println(temp);
    System.out.println(temp2);
*/
  }
}
