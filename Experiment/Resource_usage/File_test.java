import java.io.*;

public class File_test
{
  public static void main(String args[])
  {
    int LineNmbr = 0;
    String line;
    try {
      InputStream is = new FileInputStream("Data.txt");

      InputStreamReader streamReader =
          new InputStreamReader(is, "UTF-8");
        
  //    BufferedReader file1 = new BufferedReader(new FileReader(FileName));    
      BufferedReader file1 = new BufferedReader(streamReader);    
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
