// Command_Line.java

package io.github.JalogTeam.jalog;

import java.io.*;

public class Command_Line
{
  public static String env_labels[];
  public static String env_values[];
  public static String program_name;
  public static String appl_labels[];
  public static String appl_values[];

  static String label;
  static String value;

  static void parse_argument(String arg)
  {
    int i;

    label = "";
    value = "";
    if(arg.length() > 0) {
// System.out.println("parse_argument: charAt(0)='" + arg.charAt(0) + "'");     
      if(arg.charAt(0) == '-') {
        i = arg.indexOf('=');
// System.out.println("parse_argument: i='" + i + "'");     
        if(i > 0) {
          label = arg.substring(1, i);
          value = arg.substring(i+1, arg.length());
        } else {
          label = arg.substring(1, arg.length());
        }
      } else {
        value = arg;
      }
    }
// System.out.println("parse_argument: '" + arg + "' -> '" + label + "','" + value + "'");     
  }

  public static void set(String args[])
  {
    int n_env_labels = 0;
    int n_appl_labels = 0;
    int i;
    int j;
    boolean env=true;


    for(i=0;i<args.length;i++){
      parse_argument(args[i]);
      if(env){
        if(label.length() > 0){
          n_env_labels ++;
        } else {
          env = false;
        }
      } else {
        n_appl_labels ++;
      }
    }
    env_labels = new String[n_env_labels];
    env_values = new String[n_env_labels];
    appl_labels = new String[n_appl_labels];
    appl_values = new String[n_appl_labels];

    env = true;
    j = 0;

    for(i=0;i<args.length;i++){
      parse_argument(args[i]);
      if(env){
        if(label.length() > 0){
          env_labels[j] = label;
          env_values[j] = value;
          j ++;
        } else {
          env = false;
          j = 0;
          program_name = value;
        }
      } else {
        appl_labels[j] = label;
        appl_values[j] = value;
        j ++;
      }
    }
  }
}
