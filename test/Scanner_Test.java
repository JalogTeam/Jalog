// Scanner_Test.java
import java.io.*;

public class Scanner_Test
{
  static void test1(String S)
  {
    Scanner Sc1 = new Scanner();
    Token T;
    System.out.println(S);
    Sc1.SetString(S);
    do
    {
      T = Sc1.NextToken();
      System.out.println("Tok: " + T);
    } while(T.tokenType != T.ERROR && T.tokenType != T.NILL);
  }

  static void test2(String S[])
  {
    Scanner Sc1 = new Scanner();
    Token T;
    int i;
    for(i = 0; i < S.length; i++)
    {
      System.out.println(S[i]);
      Sc1.SetString(S[i]);
      do
      {
        T = Sc1.NextToken();
        System.out.println("Tok: " + T);
      } while(T.tokenType != T.ERROR && T.tokenType != T.NILL);
    }
    System.out.println("<EOF>");
    Sc1.SetEOF();
    do
    {
      T = Sc1.NextToken();
      System.out.println("Tok: " + T);
    } while(T.tokenType != T.ERROR && T.tokenType != T.NILL);
  }




  public static void main(String args[])
  {
//    test1("funktori(256,'x',f(3),\"heippa\",[],[x],[15|_],_B,3.141,More,\"\\\"\")");
//    test1(":- I=1.1.");
      String t2[] = {":- ","","I=1/1. /* sdkj sdf */ 15 /*"," asdf*lskd ","*/ a(X) :- a(X)."};

      test2(t2);

      String t3[] = {"/* avoin kommentti "};

      test2(t3);
  }
}
