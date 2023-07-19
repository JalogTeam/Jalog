/* VirtualString.java */

package io.github.JalogTeam.parser;

public interface VirtualString
//public abstract class VirtualString
{
//  public abstract boolean isNull();

  public abstract char charAt(long index);
  
  public abstract long length();
  
  public abstract String fragment(long start, long end);
  
  public abstract String toString();
  
  public abstract long indexOf(String str, long fromIndex);
}
