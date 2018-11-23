import java.io.*;
/*
  NextToken() returns Token.NILL when EOL is encountered. At this point,
  feed in the next line using SetString.

*/
public class Scanner
{
  public String s; // string to be scanned
  
  protected int start; // start position, 0 = beginning
//  protected int len;
  
  private int state;
  
  private static final int hex_init_action = 1;
  private static final int int_init_action = 2;
  private static final int name_init_action = 3;
  private static final int hex_action = 4;
  private static final int int_action = 5;
  private static final int char_action = 6;
  private static final int name_action = 7;
  private static final int string_action = 8;
  private static final int real_action = 9;
  private static final int char_numeric_code_action = 10;
  private static final int char_control_code_action = 11;
  private static final int string_numeric_code_action = 12;
  private static final int string_control_code_action = 13;
  private static final int backspace_action = 14;
//  private static final int _action = ;
  
    // state 1: end of token
  private ScanTableEntry ScanTable[] = {
    // state 0: start of token
    new ScanTableEntry(0,'\0',' ',Token.NILL,0,0),
    new ScanTableEntry(0,',',',',Token.COMMA,1,0),
    new ScanTableEntry(0,';',';',Token.SEMICOLON,1,0),
    new ScanTableEntry(0,'.','.',Token.POINT,1,0),
    new ScanTableEntry(0,'=','=',Token.EQ,1,name_init_action),
    new ScanTableEntry(0,'*','*',Token.STAR,1,name_init_action),
    new ScanTableEntry(0,'-','-',Token.MINUS,1,name_init_action),
    new ScanTableEntry(0,'(','(',Token.LPAR,1,0),
    new ScanTableEntry(0,')',')',Token.RPAR,1,0),
    new ScanTableEntry(0,'+','+',Token.PLUS,1,name_init_action),
    new ScanTableEntry(0,'/','/',Token.SLASH,20,name_init_action),
    new ScanTableEntry(0,'[','[',Token.LBRAK,1,0),
    new ScanTableEntry(0,']',']',Token.RBRAK,1,0),
    new ScanTableEntry(0,'|','|',Token.VBAR,1,0),
    new ScanTableEntry(0,'<','<',Token.LT,2,name_init_action),
    new ScanTableEntry(0,'>','>',Token.GT,3,name_init_action),
    new ScanTableEntry(0,'!','!',Token.CUT,4,name_init_action),
    new ScanTableEntry(0,'$','$',Token.ERROR,5,hex_init_action),
    new ScanTableEntry(0,'0','9',Token.INT,6,int_init_action),
    new ScanTableEntry(0,'\'','\'',Token.ERROR,7,0),
    new ScanTableEntry(0,'A','Z',Token.VARNAME,17,name_init_action),
    new ScanTableEntry(0,'_','_',Token.VARNAME,17,name_init_action),
    new ScanTableEntry(0,'a','z',Token.NAME,8,name_init_action),
    new ScanTableEntry(0,'"','"',Token.ERROR,9,0),
    new ScanTableEntry(0,'%','%',Token.NILL,1,0),
    new ScanTableEntry(0,':',':',Token.ERROR,18,name_init_action),
//    new ScanTableEntry(0,'','',Token.,7,0),
    // state 2: after <
    new ScanTableEntry(2,'=','=',Token.LE,1,name_action),
    new ScanTableEntry(2,'>','>',Token.NE,1,name_action),
    // state 3: after >
    new ScanTableEntry(3,'=','=',Token.GE,1,name_action),
    // state 4: after !
    new ScanTableEntry(4,'=','=',Token.NE,1,name_action),
    // state 5: after $
    new ScanTableEntry(5,'0','9',Token.INT,5,hex_action),
    new ScanTableEntry(5,'A','F',Token.INT,5,hex_action),
    new ScanTableEntry(5,'a','f',Token.INT,5,hex_action),
    // state 6: after 0..9
    new ScanTableEntry(6,'0','9',Token.INT,6,int_action),
    new ScanTableEntry(6,'.','.',Token.REAL,10,0),
    // state 7: after '
    new ScanTableEntry(7,'\\','\\',Token.ERROR,11,0),
    new ScanTableEntry(7,' ','\255',Token.ERROR,13,char_action),
    // state 8: after a..z
    new ScanTableEntry(8,'0','9',Token.NAME,8,name_action),
    new ScanTableEntry(8,'A','Z',Token.NAME,8,name_action),
    new ScanTableEntry(8,'_','_',Token.NAME,8,name_action),
    new ScanTableEntry(8,'a','z',Token.NAME,8,name_action),
    // state 9: after "
    new ScanTableEntry(9,'"','"',Token.STRING,1,0),
    new ScanTableEntry(9,'\\','\\',Token.ERROR,14,0),
    new ScanTableEntry(9,' ','\255',Token.ERROR,9,string_action),
    // state 10: real number
    new ScanTableEntry(10,'0','9',Token.REAL,19,real_action),
    new ScanTableEntry(10,' ','\255',Token.INT,1,backspace_action),
    // state 11: escaped character
    new ScanTableEntry(11,'0','9',Token.ERROR,12,char_numeric_code_action),
    new ScanTableEntry(11,'n','n',Token.ERROR,13,char_control_code_action),
    new ScanTableEntry(11,'r','r',Token.ERROR,13,char_control_code_action),
    new ScanTableEntry(11,'t','t',Token.ERROR,13,char_control_code_action),
    new ScanTableEntry(11,' ','\255',Token.ERROR,13,char_action),
    // state 12: numeric character code continues
    new ScanTableEntry(12,'0','9',Token.ERROR,12,char_numeric_code_action),
    new ScanTableEntry(12,'\'','\'',Token.CHAR,1,0),
    // state 13: character seen
    new ScanTableEntry(13,'\'','\'',Token.CHAR,1,0),
    // state 14: escaped character
    new ScanTableEntry(14,'0','9',Token.ERROR,15,string_numeric_code_action),
    new ScanTableEntry(14,'n','n',Token.ERROR,9,string_control_code_action),
    new ScanTableEntry(14,'r','r',Token.ERROR,9,string_control_code_action),
    new ScanTableEntry(14,'t','t',Token.ERROR,9,string_control_code_action),
    new ScanTableEntry(14,' ','\255',Token.ERROR,9,string_action),
    // state 15: numeric character code continues
    new ScanTableEntry(15,'0','9',Token.ERROR,16,string_numeric_code_action),
    new ScanTableEntry(15,'"','"',Token.STRING,1,0),
    new ScanTableEntry(15,' ','\255',Token.ERROR,9,string_action),
    // state 16: numeric character code continues
    new ScanTableEntry(16,'0','9',Token.ERROR,9,string_numeric_code_action),
    new ScanTableEntry(16,'"','"',Token.STRING,1,0),
    new ScanTableEntry(16,' ','\255',Token.ERROR,9,string_action),
    // state 17: after A..Z _
    new ScanTableEntry(17,'0','9',Token.VARNAME,17,name_action),
    new ScanTableEntry(17,'A','Z',Token.VARNAME,17,name_action),
    new ScanTableEntry(17,'_','_',Token.VARNAME,17,name_action),
    new ScanTableEntry(17,'a','z',Token.VARNAME,17,name_action),
    // state 18: after :
    new ScanTableEntry(18,'-','-',Token.IFSYM,1,name_action),
    // state 19: more decimal digits
    new ScanTableEntry(19,'0','9',Token.REAL,19,real_action),
    // state 20..: long comment
    new ScanTableEntry(20,'*','*',Token.NILL,21,0),
    new ScanTableEntry(21,'*','*',Token.NILL,22,0),
    new ScanTableEntry(21,'\0','\255',Token.NILL,21,0),
    new ScanTableEntry(22,'/','/',Token.NILL,0,0),
    new ScanTableEntry(22,'*','*',Token.NILL,22,0),
    new ScanTableEntry(22,'\0','\255',Token.NILL,21,0),
    // EOF
    new ScanTableEntry(-1,'\0','\255',Token.EOF,1,0),
    new ScanTableEntry(-2,'\0','\255',Token.ERROR,-1,0),
  };

  Scanner() {
    state = 0;
  } 

  public void SetString(String str)
  {
    s = str + " ";
//System.out.println("- Line:" + s);
    start = 0;
//    len = 0;
  }

  public void SetEOF()
  {
    s = "  ";
    start = 0;

    if(state == 0)
    {
      state = -1;
    }
    else
    {
      state = -2; // ERROR
    }
  }
  
  public void skipWhitespace()
  {
    char c;
    int stringLength = s.length();
    boolean done = false;

    while ((! done) && start < stringLength)
    {
      c = s.charAt(start);
      done = c > ' ';
      if (!done) start ++;
    }

  }
  
  public Token NextToken()
  {
    char c;
    boolean found;
    int pos;
    int stringLength = s.length();
    Token ans = new Token();
    double divisor = 1.0;
    

//    skipWhitespace();
    pos = start;
    // state is not cleared in order to handle multi-line comments
//    state = 0; 
    ans.col = pos;
    
/*
    while (state != 1 && pos <= stringLength)
    {
      if(pos < stringLength) {
        c = s.charAt(pos);
      } else {
        c = ' ';
      }
*/
    while (state != 1 && pos < stringLength)
    {
      c = s.charAt(pos);
      pos += 1;
// System.out.println("- " + state + ", '" + c + "', " + pos + ": \"" + ans.sValue + "\"");
      found = false;
      for (int i = 0; i < ScanTable.length && !found; i++)
      {
        if(state == ScanTable[i].state)
        {
          if (c >= ScanTable[i].fst && c <= ScanTable[i].lst)
          {
            found = true;
            ans.tokenType = ScanTable[i].tokenType;
            state = ScanTable[i].nextState;
            switch (ScanTable[i].action)
            {
              case hex_init_action: 
                {
                } break;
              case int_init_action: 
                {
                  ans.iValue = c-'0';
                  ans.rValue = ans.iValue;
                } break;
              case name_init_action: 
                {
                  ans.sValue = "" + c;
                } break;
              case hex_action: 
                {
              
                } break;
              case int_action: 
                {
                  ans.iValue = ans.iValue*10 + (c-'0');
                  ans.rValue = ans.rValue*10 + (c-'0');
                } break;
              case char_action: 
                {
                  ans.sValue += c;
                  ans.iValue = c;
                } break;
              case name_action: 
                {
                  ans.sValue += c;
                } break;
              case string_action: 
                {
                  ans.sValue += c;
                } break;
              case real_action: 
                {
                  ans.rValue = ans.rValue*10 + (c-'0');
                  divisor *= 10.0;
              
                } break;
              case char_numeric_code_action: 
                {
              
                } break;
              case char_control_code_action: 
                {
              
                } break;
              case string_numeric_code_action: 
                {
              
                } break;
              case string_control_code_action: 
                {
              
                } break;
              case backspace_action: 
                {
                  pos = pos - 2;
                } break;
              default: { }
            }
          }
        }
      }
      if(!found)
      {
        state = 1;
        pos -= 1;
      }
    }
    ans.rValue /= divisor;
    
    if(pos == start && ans.tokenType != Token.NILL)
    {
      pos += 1;
      ans.tokenType = Token.ERROR;
    }
    start = pos;
    ans.len = pos - ans.col;

    if (ans.tokenType == Token.NAME)
    {
      String name = ans.sValue.toLowerCase();
      if (name.equals("as")) ans.tokenType = Token.AS_;
      else if (name.equals("clauses")) ans.tokenType = Token.CLAUSES_;
      else if (name.equals("constants") ) ans.tokenType = Token.CONSTANTS_;
      else if (name.equals("database") ) ans.tokenType = Token.DATABASE_;
      else if (name.equals("determ") ) ans.tokenType = Token.DETERM_;
      else if (name.equals("div") ) ans.tokenType = Token.DIV_;
      else if (name.equals("domains") ) ans.tokenType = Token.DOMAINS_;
      else if (name.equals("elsedef") ) ans.tokenType = Token.ELSEDEF_;
      else if (name.equals("enddef") ) ans.tokenType = Token.ENDDEF_;
      else if (name.equals("global") ) ans.tokenType = Token.GLOBAL_;
      else if (name.equals("goal") ) ans.tokenType = Token.GOAL_;
      else if (name.equals("ifdef") ) ans.tokenType = Token.IFDEF_;
      else if (name.equals("ifndef") ) ans.tokenType = Token.IFNDEF_;
      else if (name.equals("include") ) ans.tokenType = Token.INCLUDE_;
      else if (name.equals("language") ) ans.tokenType = Token.LANGUAGE_;
      else if (name.equals("mod") ) ans.tokenType = Token.MOD_;
      else if (name.equals("nondeterm") ) ans.tokenType = Token.NONDETERM_;
      else if (name.equals("predicates") ) ans.tokenType = Token.PREDICATES_;
      
    }

//      System.out.println("-" + ans);

    if(state == 1)
    {
      state = 0;
    }
    return ans;
  }
  
}
