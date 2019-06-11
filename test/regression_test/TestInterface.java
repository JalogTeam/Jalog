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
    String string_value;
    double real_value;
    char char_value;
    Jalog.Term in_int, out_int, 
        in_symbol, out_symbol, 
        in_real, out_real,
        in_char, out_char,
        in_string, out_string,
        out_comment;
    
    // integer

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
      } else {
         System.out.println("test_int_fail");
       
      }
    } catch (Jalog.Exit e) {
      System.out.println("Exit " + e);
    }
    System.out.println();

    // symbol

    in_symbol = Jalog.symbol("test");
    out_symbol = Jalog.open();
    out_comment = Jalog.open();
    
    try {
      if (myJalog.call("test_symbol", in_symbol, out_symbol, out_comment))
      {
        System.out.println("test_symbol_success");
        type = out_symbol.getType();
        System.out.println("out_symbol of type " + type);
        if (type == Jalog.SYMBOL) {
          string_value = out_symbol.getSymbolValue();
          System.out.println("out_symbol = " + string_value);
        } else {
          System.out.println("out_symbol bad");
        }

        print_comment(out_comment);
      } else {
         System.out.println("test_symbol_fail");
       
      }
    } catch (Jalog.Exit e) {
      System.out.println("Exit " + e);
    }
    System.out.println();

    // real

    in_real = Jalog.real(3.142);
    out_real = Jalog.open();
    out_comment = Jalog.open();
    
    try {
      if (myJalog.call("test_real", in_real, out_real, out_comment))
      {
        System.out.println("test_real_success");
        type = out_real.getType();
        System.out.println("out_real of type " + type);
        if (type == Jalog.REAL) {
          real_value = out_real.getRealValue();
          System.out.println("out_real = " + real_value);
        } else {
          System.out.println("out_real bad");
        }

        print_comment(out_comment);
      } else {
         System.out.println("test_real_fail");
       
      }
    } catch (Jalog.Exit e) {
      System.out.println("Exit " + e);
    }
    System.out.println();

    // character

    in_char = Jalog.character('y');
    out_char = Jalog.open();
    out_comment = Jalog.open();
    
    try {
      if (myJalog.call("test_char", in_char, out_char, out_comment))
      {
        System.out.println("test_char_success");
        type = out_char.getType();
        System.out.println("out_char of type " + type);
        if (type == Jalog.CHARACTER) {
          char_value = out_char.getCharacterValue();
          System.out.println("out_char = '" + char_value + "'");
        } else {
          System.out.println("out_char bad");
        }

        print_comment(out_comment);
      } else {
         System.out.println("test_char_fail");
       
      }
    } catch (Jalog.Exit e) {
      System.out.println("Exit " + e);
    }
    System.out.println();

    // string

    in_string = Jalog.string("template");
    out_string = Jalog.open();
    out_comment = Jalog.open();
    
    try {
      if (myJalog.call("test_string", in_string, out_string, out_comment))
      {
        System.out.println("test_string_success");
        type = out_string.getType();
        System.out.println("out_string of type " + type);
        if (type == Jalog.STRING) {
          string_value = out_string.getStringValue();
          System.out.println("out_string = \"" + string_value + "\"");
        } else {
          System.out.println("out_string bad");
        }

        print_comment(out_comment);
      } else {
         System.out.println("test_string_fail");
       
      }
    } catch (Jalog.Exit e) {
      System.out.println("Exit " + e);
    }
    System.out.println();

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
