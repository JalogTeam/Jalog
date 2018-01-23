// Debug_times.java
import java.io.*;

public class Debug_times
{
  static long times[] = new long[100];
  static int name_stack[] = new int[100];
  static int name_depth = 0;
  static int current_name;
  static long current_start;

  static
  {
    int i;

    for(i = 0; i < times.length; i++)
    {
      times[i] = 0;
    }

    current_start = System.currentTimeMillis();
    current_name = 0;
  }
  
  static void start(int new_name)
  {
    long now = System.currentTimeMillis();
    times[current_name] += now - current_start;
    current_start = now;
    current_name = new_name;

  }

  static void enter(int new_name)
  {
/*
    name_stack[name_depth] = current_name;
    name_depth ++;
    start(new_name);
*/
  }

  static void leave(int old_name)
  {
/*
    if(old_name != current_name)
    {
      throw new Error("Unsymmetric leave(" + old_name + "), should be " + current_name);
    }
    else
    {
      name_depth --;
      start(name_stack[name_depth]);
    }
*/
  }

  static void report()
  {
    int i;

    start(0); 
    long total = 0;
    for(i = 1; i < times.length; i++)
    {
      total += times[i];
    }
    for(i = 1; i < times.length; i++)
    {
      if(times[i] > 0)
      {
        System.out.println("" + i + ": " + times[i] + " ms, " + 100.0 * times[i] / total + " %");
      }
    }
  }
}

