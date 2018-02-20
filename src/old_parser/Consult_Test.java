// Consult_Test.java
import java.io.*;

public class Consult_Test
{
  static void test1(String FileName)
  {
    Consult.run(FileName);
  }
  public static void main(String args[])
  {
    System.out.println(args.length + " " + args[0]);
    test1(args[0]);
  }
}
