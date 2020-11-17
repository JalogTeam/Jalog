// Test_1.java
import io.github.JalogTeam.jalog.Jalog;
import java.io.*;

public class Test_1
{
  static final String id_string="Test_1 0.1 by Ari Okkonen & Mikko Levanto 2020-11-17";
  
  static Jalog myJalog = new Jalog();

  static final String[] program_1 = {
    ":- write(\"Heippa\").",
  };


  public static void main(String args[])
  { 
    System.out.println("Ennen");
    myJalog.consult_stringlist(program_1, 'c', "program_1");
    System.out.println("Jälkeen");
     
    myJalog.dispose();
  }
  
  
}
