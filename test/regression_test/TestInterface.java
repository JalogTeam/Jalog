// TestInterface.java
import java.io.*;

public class TestInterface
{
  static final String id_string="TestInterface 0.2 by Ari Okkonen & Mikko Levanto 2019-06-25";
  
  static Jalog myJalog = new Jalog();


  public static void main(String args[])
  { 
    int i;
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
        in_list, out_list,
        in_compound, out_compound,
        in_open, out_open,
        out_comment;
    Jalog.Term[] in_list_content, out_list_content;

    // integer

    System.out.println("integer test");
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

    System.out.println("symbol test");
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

    System.out.println("real test");
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

    System.out.println("character test");
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

    System.out.println("string test");
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

    // list

    System.out.println("list test");
    in_list_content = new Jalog.Term[2];
    in_list_content[0] = Jalog.integer(3);
    in_list_content[1] = Jalog.integer(5);
    in_list = Jalog.list(in_list_content);
    out_list = Jalog.open();
    out_comment = Jalog.open();
    
    try {
      if (myJalog.call("test_list", in_list, out_list, out_comment))
      {
        System.out.println("test_list_success");
        type = out_list.getType();
        System.out.println("out_list of type " + type);
        if (type == Jalog.LIST) {
          out_list_content = out_list.getElements();
          for (i = 0; i < out_list_content.length; i++) {

            out_int = out_list_content[i];
            type = out_int.getType();
            System.out.println("  out_list[" + i + "] of type " + type);
            if (type == Jalog.INTEGER) {
              int_value = out_int.getIntegerValue();
              System.out.println("  out_list[" + i + "] = " + int_value);
            } else {
              System.out.println("  out_list[" + i + "] bad");
            }
          }
        } else {
          System.out.println("out_list bad");
        }

        print_comment(out_comment);
      } else {
         System.out.println("test_list_fail");
       
      }
    } catch (Jalog.Exit e) {
      System.out.println("Exit " + e);
    }
    System.out.println();

    // compound

    System.out.println("compound test");
    in_list_content = new Jalog.Term[2];
    in_list_content[0] = Jalog.integer(3);
    in_list_content[1] = Jalog.integer(5);
    in_compound = Jalog.compound("a", in_list_content);
    out_compound = Jalog.open();
    out_comment = Jalog.open();
    
    try {
      if (myJalog.call("test_compound", in_compound, out_compound, out_comment))
      {
        System.out.println("test_compound_success");
        type = out_compound.getType();
        System.out.println("out_compound of type " + type);
        if (type == Jalog.COMPOUND) {
          System.out.println("  out_compound funcgtor: " + 
              out_compound.getFunctor());
          out_list_content = out_compound.getSubTerms();
          for (i = 0; i < out_list_content.length; i++) {

            out_int = out_list_content[i];
            type = out_int.getType();
            System.out.println("  out_compound[" + i + "] of type " + type);
            if (type == Jalog.INTEGER) {
              int_value = out_int.getIntegerValue();
              System.out.println("  out_compound[" + i + "] = " + int_value);
            } else {
              System.out.println("  out_compound[" + i + "] bad");
            }
          }
        } else {
          System.out.println("out_compound bad");
        }

        print_comment(out_comment);
      } else {
         System.out.println("test_compound_fail");
       
      }
    } catch (Jalog.Exit e) {
      System.out.println("Exit " + e);
    }
    System.out.println();

    // open

    System.out.println("open test");
    in_open = Jalog.open();
    out_open = Jalog.open();
    out_comment = Jalog.open();
    
    try {
      if (myJalog.call("test_open", in_open, out_open, out_comment))
      {
        System.out.println("test_open_success");
        type = out_open.getType();
        System.out.println("out_open of type " + type);
        if (type == Jalog.OPEN) {
          System.out.println("out_open good");
        } else {
          System.out.println("out_open bad");
        }

        print_comment(out_comment);
      } else {
         System.out.println("test_open_fail");
       
      }
    } catch (Jalog.Exit e) {
      System.out.println("Exit " + e);
    }
    System.out.println();

    // Exit

    System.out.println("Exit test");
    
    try {
      if (myJalog.call("test_exit"))
      {
        System.out.println("test_open_success ERROR");
      } else {
        System.out.println("test_open_fail ERROR");
       
      }
    } catch (Jalog.Exit e) {
      System.out.println("Expected Exit " + e.status);
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
