// TestInterface.java
import java.io.*;

public class TestInterface
{
  static final String id_string="TestInterface 0.1 by Ari Okkonen & Mikko Levanto 2019-06-11";
  
  static Jalog myJalog = new Jalog();


  public static void main(String args[])
  { 
    String type;
    System.out.println(id_string);
    myJalog.consult_file("test_java_if.pro");
    long int_value;
    Jalog.Term in_int, out_int, out_comment;
    

    in_int = Jalog.integer(7);
    out_int = Jalog.open();
    out_comment = Jalog.open();
    
    try {
      if (myJalog.call("test_int", in_int, out_int, out_comment))
      {
        System.out.println("test_int_success");
        type = out_int.getType();
        System.out.println("out_int of type " + type);
        if (type == Jalog.INTEGER) {
          int_value = out_int.getIntegerValue();
          System.out.println("out_int = " + int_value);
        } else {
          System.out.println("out_int bad");
        }

        print_comment(out_comment);
      }
    } catch (Jalog.Exit e) {
      System.out.println("Exit " + e);
    }
  }
  
  public static void print_comment(Jalog.Term out_comment) {
    String type;

    type = out_comment.getType();
    System.out.println("out_comment of type " + type);
    if (type == Jalog.STRING) {
      System.out.println("out_comment = " + out_comment.getStringValue());
    } else {
      System.out.println("out_comment bad");
    }

  }
}
