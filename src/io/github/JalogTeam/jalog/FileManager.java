package io.github.JalogTeam.jalog;

import java.io.*;
import java.util.*;

public class FileManager {
/*
  public InputStream getResourceAsStream(String fileName) throws IOException {
// System.out.println("* Jalog.ResourceManager.getResourceAsStream(" + fileName + ")");
    return Jalog.class.getClassLoader().getResourceAsStream(fileName);
  }
*/
  static int exit_value = 0;
  
  public static class FileInfo {  // Opened file
    public String path = null;
    public Reader reader = null;  // vai BufferedReader
    public Writer writer = null;  // vai BufferedWriter
    
    public FileInfo(String newpath, Reader newreader, Writer newwriter) {
      path = newpath;
      reader = newreader;
      writer = newwriter;
    }
  }

  public static Hashtable<String, FileInfo> open_files = 
      new Hashtable<String, FileInfo>(10);
/*
  static {
    try {
      for (int i = 0; i < BuiltIns.list.length; i++) {
        ClassInfo built_in_pred = BuiltIns.list[i];
        built_in_info = new BuiltInInfo();
        built_in_info.make_method = built_in_pred.pred_class.
            getMethod("first_call", Pro_TermData_Compound.class);
        built_in_info.min_arity = built_in_pred.min_arity;
        built_in_info.max_arity = built_in_pred.max_arity;
        
        
        builtIns.put(built_in_pred.name, built_in_info);
      }
    } catch (Exception e) {
    }
  }
*/
  // identify returns absolute path prefixed with 'file:' or 'res:'
  static public String identify(String name, String current_dir, 
      boolean consult_use_res) {
    File infile = null;
    String result = name;
    boolean use_res;
    
    if (name.startsWith("res:")) {
      // Ok
//    } else if (name.startsWith("file:")) {
      // Ok
    } else {
      if (name.startsWith("file:")) {
        use_res = false;
        name = name.substring(5); // remove "file:" from beginning
      } else {
        use_res = consult_use_res; // Consult!
      }
      if((!use_res) && (File.separatorChar != '/')) {
        name = name.replace('/', File.separatorChar);
      }
      infile = new File(name);
      if (infile.isAbsolute()) {
        result = "file:" + name;
      } else if (use_res){
        if (current_dir != null) {
          result = current_dir + "/" + name;
        } else {
          result = "res:" + name;
        }
      } else { // relative file path
        if (current_dir != null) {
          result = current_dir + File.separatorChar + name;
        } else {
          result = "file:" + name;
        }
      }
    }
    return result;
  }

  
  public static void closefile() {
  }

  public static void openread(String symbolic_filename, String raw_fileName) {
    openread(symbolic_filename, raw_filename, "", false);
  }
  
  public static void openread(String symbolic_filename, String raw_fileName,
      String consult_dirname, boolean consult_use_res) {
    exit_value = 0;
    int root_type = 0; // 1-file, 2-resource
    int name_start_pos = 0;  
    
    File infile = null;    
    
    Reader input = null;

    String fileName = identify(raw_fileName, consult_dirname, consult_use_res);
    
    if (fileName.startsWith("res:")) {
      root_type = 2; // resource
      name_start_pos = 4;
      
      try {
// System.out.println("A " + fileName);
        InputStream is = Jalog.getResourceAsStream(fileName.substring(name_start_pos));
// System.out.println("A0 " + (is==null?"is==null":"is/=null"));
        input =
          new InputStreamReader(is, "UTF-8");
      } catch (Exception e) {
      }
      
    } else if (fileName.startsWith("file:")) {
      root_type = 1; // file
      name_start_pos = 5;
      
      try {
// System.out.println("B");
        input = new FileReader(fileName.substring(name_start_pos));
      } catch (Exception e) {
      }
      
    }
    if (input != null){
      FileManager.FileInfo fi = new FileManager.FileInfo(raw_fileName,
         input, null);
      open_files.put(symbolic_filename, fi);
    } else {
      exit_value = 2002; // File not found
    }
  }
  
  public static void openwrite() {
  }
  
  public static void readdevice() {
  }
  
  public static void writedevice() {
  }
  
  public static void readln() {
  }
  
  public static void writeln() {
  }
  
  public static void write() {
  }
  
  public static void writeq() {
  }
}
