import java.io.*;

public class String_test
{
  static String[] data = {
    "String 1",
    "String 2"
  };
  
  public static void main(String args[])
  {
    int LineNmbr = 0;
    String line;
    try {

      StringArrayReader myReader =
          new StringArrayReader(data);
        
  //    BufferedReader file1 = new BufferedReader(new FileReader(FileName));    
      BufferedReader file1 = new BufferedReader(myReader);    
      System.out.println("Heippa!");

      do {
        
        line = file1.readLine();
        LineNmbr = LineNmbr + 1;
        System.out.println(LineNmbr + ": " + line);
      } while (line != null);
    } catch (Exception e) {
      System.out.println("*** Error: " + e);
    }


  }
}
