// TestRedirect.java
import io.github.JalogTeam.jalog.Jalog;
import java.io.*;
import io.github.JalogTeam.jalog.Database;

public class TestRedirect
{
  static final String id_string =
      "TestRedirect 0.1 by Ari Okkonen & Mikko Levanto 2021-04-27";
  
  static Jalog myJalog = new Jalog();

  static String[] teststringlist = {
    "stringrule(5):-", 
    "  write(\"teststringlist here\"), nl, writeln(\"loppu\")."
  };
  
  static String[] errstringlist = {
    "stringrule(5):-", 
    "  write(\"teststringlist here\"),$ nl, writeln(\"loppu\")."
  };
  
  public static class TestOutput extends Jalog.Output {
    public void print(String s) {
      System.out.print("|" + s);
    }
  }
  
  public static class TestErr extends Jalog.Output {
    public void print(String s) {
      System.out.print("#" + s);
    }
  }
  

  
  public static void main(String args[])
  { 
    int i;
    String type;
    System.out.println(id_string);
    long int_value;
    String string_value;
    double real_value;
    char char_value;
    Jalog.Term in_int, out_int, 
        in_symbol, out_symbol, 
        in_real, out_real,
        in_char, out_char,
        in_string, out_string,
        in_list, out_list,
        in_compound, out_compound,
        in_open, out_open,
        out_comment;
    Jalog.Term[] in_list_content, out_list_content;


// output_redirect

    Jalog.Output teststream = new TestOutput();
    Jalog.Output testerr = new TestErr();
    
    myJalog.setOut(teststream);
    myJalog.setErr(testerr);
    
    System.out.println("output_redirect test");
    
    myJalog.consult_stringlist(teststringlist, "teststringlist");
    out_int = Jalog.open();

    try {
      if (myJalog.call("stringrule", out_int))
      {
        System.out.println("output_redirect_success");
        type = out_int.getType();
        System.out.println("out_int of type " + type);
        if (type == Jalog.INTEGER) {
          int_value = out_int.getIntegerValue();
          System.out.println("out_int = " + int_value);
        } else {
          System.out.println("out_int bad");
        }

      } else {
         System.out.println("output_redirect_fail");
       
      }

    } catch (Jalog.Exit e) {
      System.out.println("Exit " + e);
    }
    System.out.println("Error follows:");
    myJalog.consult_stringlist(errstringlist, "errstringlist");
    
    System.out.println("-End redirect test-");

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
