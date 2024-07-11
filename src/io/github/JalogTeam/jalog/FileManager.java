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
  static public FileInfo current_writedevice = null;
  static private String base_dir = "file:" + 
      (new File("")).getAbsolutePath();
  static private String current_dir = base_dir;
  
//  public static Vector<String> modify_control_list = new Vector<String>();
  
  public static class FileInfo {  // Opened file
    public String symbolic_name = null;
    public String path = null;
    public BufferedReader reader = null;
    public BufferedWriter writer = null;  // vai BufferedWriter
    
    public FileInfo(String newsymbolic_name, String newpath, 
        BufferedReader newreader, BufferedWriter newwriter) {
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
  // In consult: dir = consult_dir, otherwise = dir.
  static public String identify(String name, String dir, 
      boolean consult_use_res) {
    File infile = null;
    String result = name;
    boolean use_res;
    boolean is_absolute = false;
// System.out.println("FileManager.identify name: " + name); 
    if ("".equals(dir)) dir = current_dir; // null;   
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
        if (dir != null) {
          result = dir + "/" + name;
        } else {
          result = "res:" + name;
        }
// System.out.println("FileManager.identify 94 use_res result=" + result);
      } else { // relative file path
        if (dir != null) {
// System.out.println("FileManager.identify 97 dir=" + dir);
          result = dir + File.separatorChar + name;
        } else {
          result = "file:" + name;
        }
// System.out.println("FileManager.identify 102 default result=" + result);
      }
    }
// System.out.println("FileManager.identify path=" + result);
//System.out.println("FileManager.identify 107 name = " + name + "; parent = " +new File(name).getParent());
    return result;
  }

  
  public static void closefile(String symbolic_filename) {
    FileInfo fi = open_files.get(symbolic_filename);
    exit_value = 0;
    if(fi != null) {
      if (fi.reader != null) {
        if (fi == current_readdevice) current_readdevice = null;
        try {
          fi.reader.close();
        } catch (IOException e) {
        }
      }
      if (fi.writer != null) {
        if (fi == current_writedevice) current_writedevice = null;
        try {
          fi.writer.close();
        } catch (IOException e) {
          exit_value = 2001; // Cannot execute a write operation
        }
      }
      open_files.remove(symbolic_filename);
    }
  }
  
  public static void closeAllFiles() {
    for (Enumeration<String> k = open_files.keys() ; k.hasMoreElements() ;) {
         //System.out.println(e.nextElement());
      closefile(k.nextElement());
    }
  }

  public static boolean existfile(String raw_filename) {
    boolean ans;
    exit_value = 0;
    String fileName = identify(raw_filename, "", false);
    if (fileName.startsWith("file:")) {
      if (Permissions.permitted(Permissions.READ, fileName) ||
          Permissions.permitted(Permissions.WRITE, fileName) ||
          Permissions.permitted(Permissions.APPEND, fileName) ||
          Permissions.permitted(Permissions.MODIFY, fileName)) 
      { 
/*
System.out.println("existfile(" + fileName + ") : permitted");
System.out.println("  READ: " + Permissions.permitted(Permissions.READ, fileName));
System.out.println("  WRITE: " + Permissions.permitted(Permissions.WRITE, fileName));
System.out.println("  APPEND: " + Permissions.permitted(Permissions.APPEND, fileName));
System.out.println("  MODIFY: " + Permissions.permitted(Permissions.MODIFY, fileName));
*/
        fileName = fileName.substring(5);

        File f = new File(fileName);
        ans = f.exists() && !f.isDirectory();
      } else {
// System.out.println("existfile(" + raw_filename + "), filename = " + fileName +" : not permitted");
        exit_value = 2002; // impossible to open - illegal request
        ans = false;
      }
    } else {
// System.out.println("existfile(" + raw_filename + ") : resource");
      fileName = fileName.substring(4);
      ans = Jalog.class.getResource(fileName) != null;
    }
    
    return ans;
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
// System.out.println("FileManager.openread 216");
      if (Permissions.permitted(Permissions.READ, fileName)) { 
// System.out.println("FileManager.openread 218 permitted");

        root_type = 1; // file
        name_start_pos = 5;
        
        try {
          
          /*FileInputStream f*/is = new FileInputStream(
              fileName.substring(name_start_pos));
  /*        input = new InputStreamReader(
              fis,  "UTF-8");
  */        
// System.out.println("FileManager.openread 230 " + (is == null ? "is == null" : "is != null"));
        } catch (Exception e) {
// System.out.println("FileManager.openread 232 is=null");
          is = null;
        }
      }
    }
// System.out.println("FileManager.openread 182 is==null " + (is==null?"not ":"") + "found");
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
// System.out.println("FileManager.openread: File not found");
// System.out.println("  symbolic_filename = " + symbolic_filename);
// System.out.println("  raw_filename = " + raw_filename);
// System.out.println("  consult_dirname = " + consult_dirname);
// System.out.println("  fileName = " + fileName);
      exit_value = 2002; // File not found
    }
  }
  
  public static void openwrite(String symbolic_filename, String raw_filename) 
  {
    openwrite(symbolic_filename, raw_filename, false);
  }
  
  public static void openappend(String symbolic_filename, String raw_filename) 
  {
    openwrite(symbolic_filename, raw_filename, true);
  }

  public static void openwrite(String symbolic_filename, String raw_filename, 
      boolean append) 
  {
    exit_value = 0;
    int root_type = 0; // 1-file, 2-resource
    int name_start_pos = 0;  

// System.out.println("FileManager.openwrite raw_filename=" + raw_filename);    
    
    closefile(symbolic_filename); // old attachment closed, if open
    
    File outfile = null;    
//consult_dirname = "file:";    
    Writer output = null;

    String fileName = identify(raw_filename, "", false);
// System.out.println("openwrite fileName=" + fileName);    
    OutputStream os = null;
    if (fileName.startsWith("file:")) {
      if (Permissions.permitted(Permissions.WRITE, fileName) ||
          (append && Permissions.permitted(Permissions.APPEND, fileName))) { 

        root_type = 1; // file
        name_start_pos = 5;
        
        try {
// System.out.println("openwrite append=" + append);    
          
          /*FileInputStream f*/os = new FileOutputStream(
              fileName.substring(name_start_pos), append);
  /*        input = new InputStreamReader(
              fis,  "UTF-8");
  */        
        } catch (Exception e) {
          os = null;
        }
      }
    }
    if (os != null){
      try {
        output = new OutputStreamWriter(os, "UTF-8");
        BufferedWriter buffered_output = 
            new BufferedWriter(output);
        FileManager.FileInfo fi = 
            new FileManager.FileInfo(symbolic_filename, raw_filename, 
            null, buffered_output);
// System.out.println("FileManager.openwrite: symbolic_filename, fi=" + symbolic_filename + ", " + fi);
        open_files.put(symbolic_filename, fi);
// System.out.println("FileManager.openwrite: open_files=" + open_files);
      } catch (UnsupportedEncodingException e) {
        throw new Error(e);
      }
    } else {
// System.out.println("FileManager.openwrite: Impossible to open, open_files=" + open_files);
      exit_value = 2002; // Impossible to open
    }
  }

  public static void deletefile(String raw_filename) {
    String fileName = identify(raw_filename, "", false);
    
    exit_value = 2003; // Default: Impossible to erase
    if (fileName.startsWith("file:") &&
        Permissions.permitted(Permissions.WRITE, fileName)) 
    {
      File myFile = new File(fileName.substring(5)); 
      
      if (myFile.delete()) { 
        exit_value = 0; // Success: Possible to erase
      } 
    }
  } 
  
  public static void set_readdevice(String symbolic_filename) {
// System.out.println("# FileManager.readdevice: open_files=" + open_files);    
    FileInfo fi = open_files.get(symbolic_filename);
    exit_value = 0;
// System.out.println("# FileManager.readdevice: symbolic_filename=\"" + symbolic_filename + "\" fi=" + fi);  
    if((fi != null) && (fi.reader != null)) {
      current_readdevice = fi;
// System.out.println("# FileManager.readdevice: " + current_readdevice.symbolic_name);      
    } else {
      exit_value = 1011; // attempt to assign input device to unopended file
// System.out.println("# FileManager.readdevice: exit=1011");      
      current_readdevice = null;
    }
  }
  
  public static void set_writedevice(String symbolic_filename) {
// System.out.println("# FileManager.readdevice: open_files=" + open_files);    
    exit_value = 0;
    if ((current_writedevice != null) && 
        (current_writedevice.writer != null)) 
    {
      try {
        current_writedevice.writer.flush();
      } catch (Exception e) {
        exit_value = 2001; // cannot execute write operation
      }
    }
    if (symbolic_filename.equals("screen")) {
       current_writedevice = null;
    } else {
      FileInfo fi = open_files.get(symbolic_filename);
// System.out.println("# FileManager.readdevice: symbolic_filename=\"" + symbolic_filename + "\" fi=" + fi);  
      if((fi != null) && (fi.writer != null)) {
        current_writedevice = fi;
// System.out.println("# FileManager.readdevice: " + current_readdevice.symbolic_name);      
      } else {
        exit_value = 1012; // attempt to assign output device to unopended file
// System.out.println("# FileManager.writedevice: exit=1012");      
        current_writedevice = null;
      }
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
  
  public static String get_writedevice() {
    exit_value = 0;
    if (current_writedevice != null) {
// System.out.println("# FileManager.get_readdevice: " + current_readdevice.symbolic_name);      
      return current_writedevice.symbolic_name;
    } else {
// System.out.println("# FileManager.get_readdevice: null");      
      return "screen";
    }
  }
  
  public static String readln() {
    exit_value = 0;
    try {
      return current_readdevice.reader.readLine();
    } catch(Exception e) {
      exit_value = 2006; // cannot execute a read operation
      return null;
    }
  }
  
  public static void write(String line) {
    exit_value = 0;
    try {
      if (current_writedevice == null) {
        Jalog.out.print(line);
      } else {
        current_writedevice.writer.write(line);
      }
    } catch(Exception e) {
      exit_value = 2001; // cannot execute a write operation
    }
  }
   
  public static void nl() {
    exit_value = 0;
    try {
      if (current_writedevice == null) {
        Jalog.out.println();
      } else {
        current_writedevice.writer.newLine();
      }
    } catch(Exception e) {
      exit_value = 2001; // cannot execute a write operation
    }
  }
/*  
  public static void writeq() {
    exit_value = 0;
  }
  
  public static boolean permitted(Vector<String> control_list, String path) {
    int size = control_list.size();
    boolean found = false;
    for (int i = 0; (i < size) && !found; i++) {
      found = path.startsWith(control_list.elementAt(i));
    }
    return found;
  }

  public static void permit_write(String path) {
    String fileName = identify(path, "", false);

    if (fileName.startsWith("file:")) {
      if (write_control_list.indexOf(path) < 0) {
        write_control_list.add(fileName);
      }

      if (append_control_list.indexOf(path) < 0) {
        append_control_list.add(fileName);
      }
    }
  }
    
  public static void permit_read(String path) {
    String fileName = identify(path, "", false);
System.out.println("FileManager.permit_read: " + fileName);
    if (fileName.startsWith("file:") && (read_control_list.indexOf(path) < 0)) {
System.out.println("FileManager.permit_read read_control_list: " + read_control_list);
      read_control_list.add(fileName);
    }
System.out.println("FileManager.permit_read finally read_control_list: " + read_control_list);
  }

  public static void permit_modify(String path) {
    String fileName = identify(path, "", false);

    if (fileName.startsWith("file:")) {
      if (write_control_list.indexOf(path) < 0) {
        write_control_list.add(fileName);
      }

      if (read_control_list.indexOf(path) < 0) {
        read_control_list.add(fileName);
      }

      if (append_control_list.indexOf(path) < 0) {
        append_control_list.add(fileName);
      }
    }
  }
  
  public static void permit_append(String path) {
    String fileName = identify(path, "", false);

    if (fileName.startsWith("file:") && (append_control_list.indexOf(path) < 0)) {
      append_control_list.add(fileName);
    }
  }
*/
>>>>>>> r1.5
}
