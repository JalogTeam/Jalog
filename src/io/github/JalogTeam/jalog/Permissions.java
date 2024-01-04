package io.github.JalogTeam.jalog;

import java.io.*;
import java.util.*;

public class Permissions {

  static final int READ = 1;
  static final int WRITE = 2;
  static final int MODIFY = 3; // = READ & WRITE
  static final int APPEND = 4;

  private static Vector<String> read_control_list = new Vector<String>();
  private static Vector<String> write_control_list = new Vector<String>();
  private static Vector<String> append_control_list = new Vector<String>();


  public static boolean permitted(int permission, String name) {
    boolean ans = true;
    
    if (name.startsWith("file:")) {
      if ((permission & READ) != 0) {
        ans = has_permission(read_control_list, name);
      }        
      
      if (ans && (permission & WRITE) != 0) {
        ans = has_permission(write_control_list, name);
      }     
      
      if (ans && (permission == APPEND)) {
        ans = has_permission(append_control_list, name);
      }        
    } else if (name.startsWith("res:")) {
      ans = (permission == READ);
    }
    return ans;
  }
    


  private static boolean has_permission(Vector<String> control_list, String path) {
    int size = control_list.size();
    boolean found = false;
    for (int i = 0; (i < size) && !found; i++) {
      found = path.startsWith(control_list.elementAt(i));
    }
    return found;
  }
  
// Requires normalized name
  public static void permit(int permission, String name) {
    if (name.startsWith("file:")) {
      if ((permission & READ) != 0) {
        set_permission(read_control_list, name);
      }        
      if ((permission & WRITE) != 0) {
        set_permission(write_control_list, name);
      }        
      if (permission == APPEND) {
        set_permission(append_control_list, name);
      }        
    }
  }

  private static void set_permission(Vector<String> control_list, String name) {
// System.out.println("> Permissions.set_permission(" + control_list + ", "+ name + ")");
    if (control_list.indexOf(name) < 0) {
      control_list.add(name);
    }
// System.out.println("< Permissions.set_permission(" + control_list + ")");
  }
  
  public static void permit_parent(int permission, String name) {
    if (name.startsWith("file:")) {
       permit(permission, new File(name.substring(5)).getParent());
    }
  }
/*    
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
}