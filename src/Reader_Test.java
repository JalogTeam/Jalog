// Reader_Test.java
import java.io.*;

public class Reader_Test
{
  static void test1(String FileName)
  {
    Reader.run(FileName);
  }
  public static void main(String args[])
  {
    System.out.println(args.length + " " + args[0]);
    test1(args[0]);
  }
}
