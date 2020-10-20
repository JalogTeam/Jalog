import java.io.*;

public class StringArrayReader extends Reader {
  private String[] dataArray;
  private int line, col;


  public StringArrayReader(String[] dataArray) {
    this.dataArray = dataArray;
    this.line = 0;
    this.col = 0;
  }
  
  public void close() {
    this.dataArray = null;
  }

  public int read(char[] cbuf, int off, int len)
      throws IOException
  {
    int di = 0;
    int di_end = off + len;
    
    if (dataArray == null) throw new IOException();
    
    if (line < dataArray.length) {
      for (di = off; (di < di_end) && (line < dataArray.length); di++) {
        if (col < dataArray[line].length()) {
          cbuf[di] = dataArray[line].charAt(col);
          col++;
        } else {
          cbuf[di] = '\n';
          col = 0;
          line ++;
        }
      }
      return di - off;
    } else {
      return -1;
    }
  }
}