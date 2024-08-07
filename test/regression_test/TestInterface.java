// TestInterface.java
import io.github.JalogTeam.jalog.Jalog;
import java.io.*;
import io.github.JalogTeam.jalog.Database;

public class TestInterface
{
  static final String id_string="TestInterface 0.2 by Ari Okkonen & Mikko Levanto 2019-06-25";
  
  static Jalog myJalog = new Jalog();

  static String[] teststringlist1 = {
    "stringrule(X):-", 
    "  X=11.",
    "stringdata(5)."
  };
  
  static String[] teststringlist2 = {
    "stringrule(X):-", 
    "  X=9."
  };
  
  public static void main(String args[])
  { 
    int i;
    String type;
    System.out.println(id_string);
    myJalog.permit_access(Jalog.permission.READ, "");       // # TEST
//        "test_java_if.pro");                              // # TEST
    myJalog.consult_file("test_java_if.pro");
    long int_value;
    String string_value;
    double real_value;
    char char_value;
    Jalog.Term in_int, out_int, in_start, in_len,
        in_symbol, out_symbol, 
        in_real, out_real,
        in_char, out_char,
        in_string, in_string2, out_string,
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
    in_string2 = Jalog.string(" more");
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

    in_start = Jalog.integer(2);
    in_len = Jalog.integer(5);
    out_string = Jalog.open();
    try {
      if (myJalog.call("test_string_substring", in_string, in_start, in_len,
          out_string))
      {
        System.out.println("test_string_substring_success");
        type = out_string.getType();
        System.out.println("out_string of type " + type);
        if (type == Jalog.STRING) {
          string_value = out_string.getStringValue();
          System.out.println("out_string = \"" + string_value + "\"");
        } else {
          System.out.println("out_string bad");
        }

      } else {
         System.out.println("test_string_substring_fail");
       
      }
    } catch (Jalog.Exit e) {
      System.out.println("Exit " + e);
    }
    System.out.println();

    out_string = Jalog.open();
    try {
      if (myJalog.call("test_string_concat", in_string, in_string2, out_string))
      {
        System.out.println("test_string_concat_success");
        type = out_string.getType();
        System.out.println("out_string of type " + type);
        if (type == Jalog.STRING) {
          string_value = out_string.getStringValue();
          System.out.println("out_string = \"" + string_value + "\"");
        } else {
          System.out.println("out_string bad");
        }

      } else {
         System.out.println("test_string_concat_fail");
       
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

// Command line arguments

    System.out.println("Command line arguments test");

    myJalog.set_comline_arg("label", "value");   
    myJalog.set_comline_arg("Label", "Value");   
    myJalog.set_comline_arg("switch", "");   
    myJalog.set_comline_arg("", "argument");   
    try {
      if (myJalog.call("test_command_line_arguments"))
      {
        System.out.println("test_command_line_arguments success");
      } else {
        System.out.println("test_command_line_arguments fail");
       
      }
    } catch (Jalog.Exit e) {
      System.out.println("Exit " + e.status);
    }
    System.out.println();

// consult_stringlist

    System.out.println("consult_stringlist test");
    
    myJalog.consult_stringlist(teststringlist2, "teststringlist2");
    out_int = Jalog.open();
    try {
      if (myJalog.call("stringrule", out_int))
      {
        System.out.println("consult_stringlist_success");
        type = out_int.getType();
        System.out.println("out_int of type " + type);
        if (type == Jalog.INTEGER) {
          int_value = out_int.getIntegerValue();
          System.out.println("out_int = " + int_value);
        } else {
          System.out.println("out_int bad");
        }

      } else {
         System.out.println("consult_stringlist_fail");
       
      }

    } catch (Jalog.Exit e) {
      System.out.println("Exit " + e);
    }
    
    System.out.println();


// consult_data_file

    System.out.println("consult_data_file test");
    
    String[] filter = {"data/1"};

    myJalog.make_dynamic("data/1");
    myJalog.consult_data_file("testdata_consult_data_1.pro", filter);
    out_int = Jalog.open();
    try {
      if (myJalog.call("data", out_int))
      {
        System.out.println("consult_data_file_success");
        type = out_int.getType();
        System.out.println("out_int of type " + type);
        if (type == Jalog.INTEGER) {
          int_value = out_int.getIntegerValue();
          System.out.println("out_int = " + int_value);
        } else {
          System.out.println("out_int bad");
        }

      } else {
         System.out.println("consult_data_file_fail");
       
      }

    } catch (Jalog.Exit e) {
      System.out.println("Exit " + e);
    }
    
    System.out.println();

// consult_data_stringlist

    System.out.println("consult_data_stringlist test");
    
    String[] filter2 = {"stringdata/1"};

    myJalog.make_dynamic("stringdata/1");
    myJalog.consult_data_stringlist(teststringlist1, filter2, "teststringlist1");
    out_int = Jalog.open();
    try {
      if (myJalog.call("stringdata", out_int))
      {
        System.out.println("consult_data_stringlist_success");
        type = out_int.getType();
        System.out.println("out_int of type " + type);
        if (type == Jalog.INTEGER) {
          int_value = out_int.getIntegerValue();
          System.out.println("out_int = " + int_value);
        } else {
          System.out.println("out_int bad");
        }

      } else {
         System.out.println("consult_data_stringlist_fail");
       
      }

    } catch (Jalog.Exit e) {
      System.out.println("Exit " + e);
    }
    
    System.out.println();

// set_consult_dir

    System.out.println("set_consult_dir test");
    
    // String filter = {"data/1"}; // Must be previously

    String init_consult_dir = myJalog.get_consult_dir();
    System.out.println("get_consult_dir 1: " + init_consult_dir);
    
    myJalog.set_consult_dir("test_dir");
    System.out.println("get_consult_dir 2: " + myJalog.get_consult_dir());
    
    myJalog.set_consult_dir(init_consult_dir);
    System.out.println("get_consult_dir 3: " + myJalog.get_consult_dir());
    
   
    System.out.println();

// Exit

    System.out.println("Exit test");
    
    try {
      if (myJalog.call("test_exit"))
      {
        System.out.println("test_exit_success ERROR");
      } else {
        System.out.println("test_exit_fail ERROR");
       
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
