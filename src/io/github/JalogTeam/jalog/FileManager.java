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
  public static int exit_value = 0;
  static public FileInfo current_readdevice = null;
  
  public static class FileInfo {  // Opened file
    public String symbolic_name = null;
    public String path = null;
    public BufferedReader reader = null;
    public Writer writer = null;  // vai BufferedWriter
    
    public FileInfo(String newsymbolic_name, String newpath, 
        BufferedReader newreader, Writer newwriter) {
      symbolic_name = newsymbolic_name;
      path = newpath;
      reader = newreader;
      writer = newwriter;
    }
  }

  public static Hashtable<String, FileInfo> open_files = 
      new Hashtable<String, FileInfo>(10); // Key = symbolic_file_name
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
  // In consult: current_dir = consult_dir, otherwise = current_dir.
  static public String identify(String name, String current_dir, 
      boolean consult_use_res) {
    File infile = null;
    String result = name;
    boolean use_res;
    boolean is_absolute = false;
// System.out.println("FileManager.identify name: " + name);    
    exit_value = 0;
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
      is_absolute = name.startsWith("/"); 
      if((!use_res) && (File.separatorChar != '/')) {
        name = name.replace('/', File.separatorChar);
      }
// System.out.println("FileManager.identify 74 name=" + name);
      if (!is_absolute) is_absolute = (new File(name)).isAbsolute();
// System.out.println("FileManager.identify 76 is_absolute=" + is_absolute);
      if (is_absolute) {
        result = "file:" + name;
// System.out.println("FileManager.identify 78 absolute result=" + result);
      } else if (use_res){
        if (current_dir != null) {
          result = current_dir + "/" + name;
        } else {
          result = "res:" + name;
        }
// System.out.println("FileManager.identify 85 use_res result=" + result);
      } else { // relative file path
        if (current_dir != null) {
          result = current_dir + File.separatorChar + name;
        } else {
          result = "file:" + name;
        }
// System.out.println("FileManager.identify 92 default result=" + result);
      }
    }
// System.out.println("FileManager.identify path=" + result);
    return result;
  }

  
  public static void closefile(String symbolic_filename) {
    FileInfo fi = open_files.get(symbolic_filename);
    exit_value = 0;
    if(fi != null) {
      if (fi.reader != null) {
        try {
          fi.reader.close();
        } catch (IOException e) {
        }
      }
      if (fi.writer != null) {
      }
      open_files.remove(symbolic_filename);
    }
  }

  public static void openread(String symbolic_filename, String raw_filename) {
    exit_value = 0;
    openread(symbolic_filename, raw_filename, "", false);
  }
  
  public static void openread(String symbolic_filename, String raw_filename,
      String consult_dirname, boolean consult_use_res) {
    exit_value = 0;
    int root_type = 0; // 1-file, 2-resource
    int name_start_pos = 0;  

// System.out.println("FileManager.openread consult_dirname=" + consult_dirname);    
    
    closefile(symbolic_filename); // old attachment closed, if open
    
    File infile = null;    
//consult_dirname = "file:";    
    Reader input = null;

    String fileName = identify(raw_filename, consult_dirname, consult_use_res);
// System.out.println("openread fileName=" + fileName);    
    InputStream is = null;
    if (fileName.startsWith("res:")) {
      root_type = 2; // resource
      name_start_pos = 4;
      
      try {
// System.out.println("A " + fileName);
        /*InputStream */is = Jalog.getResourceAsStream(fileName.substring(name_start_pos));
// System.out.println("A0 " + (is==null?"is==null":"is/=null"));
/*        input =
          new InputStreamReader(is, "UTF-8");
 */
      } catch (Exception e) {
        is = null;
      }
      
    } else if (fileName.startsWith("file:")) {
      root_type = 1; // file
      name_start_pos = 5;
      
      try {
        
        /*FileInputStream f*/is = new FileInputStream(
            fileName.substring(name_start_pos));
/*        input = new InputStreamReader(
            fis,  "UTF-8");
*/        
      } catch (Exception e) {
        is = null;
      }
      
    }
    if (is != null){
      try {
        input = new InputStreamReader(is, "UTF-8");
        BufferedReader buffered_input = 
            new BufferedReader(input);
        FileManager.FileInfo fi = 
            new FileManager.FileInfo(symbolic_filename, raw_filename, 
            buffered_input, null);
// System.out.println("FileManager.openread: symbolic_filename, fi=" + symbolic_filename + ", " + fi);
        open_files.put(symbolic_filename, fi);
// System.out.println("FileManager.openread: open_files=" + open_files);
      } catch (UnsupportedEncodingException e) {
        throw new Error(e);
      }
    } else {
      exit_value = 2002; // File not found
    }
  }
  
  public static void openwrite() {
    exit_value = 0;
  }
  
  public static void readdevice(String symbolic_filename) {
// System.out.println("# FileManager.readdevice: open_files=" + open_files);    
    FileInfo fi = open_files.get(symbolic_filename);
    exit_value = 0;
// System.out.println("# FileManager.readdevice: symbolic_filename=\"" + symbolic_filename + "\" fi=" + fi);  
    if((fi != null) && (fi.reader != null)) {
      current_readdevice = open_files.get(symbolic_filename);
// System.out.println("# FileManager.readdevice: " + current_readdevice.symbolic_name);      
    } else {
      exit_value = 1011; // attempt to assign input device to unopended file
// System.out.println("# FileManager.readdevice: exit=1011");      
      current_readdevice = null;
    }
  }
  
  public static String get_readdevice() {
    exit_value = 0;
    if (current_readdevice != null) {
// System.out.println("# FileManager.get_readdevice: " + current_readdevice.symbolic_name);      
      return current_readdevice.symbolic_name;
    } else {
// System.out.println("# FileManager.get_readdevice: null");      
      return "null";
    }
  }
  
  public static void writedevice() {
    exit_value = 0;
  }
  
  public static String readln() {
    exit_value = 0;
    try {
      return current_readdevice.reader.readLine();
    } catch(Exception e) {
      exit_value = 1018;
      return null;
    }
  }
  
  public static void writeln() {
    exit_value = 0;
  }
  
  public static void write() {
    exit_value = 0;
  }
  
  public static void writeq() {
    exit_value = 0;
  }
}
