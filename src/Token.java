public class Token
{
  int tokenType = NILL;
  String sValue = "";
  long iValue = 0;
  double rValue = 0.0;
//  double fCoeff = 0.0; // coefficient for .nnn
  
  int col = 0;
  int len = 0;
  
  static String typename[] = new String[101];
  
  static final int COMMA = 1; static {typename[1]="COMMA";}
  static final int SEMICOLON = 2; static {typename[2]="SEMICOLON";}
  static final int CONSTANTS_ = 3; static {typename[3]="CONSTANTS_";}
  static final int DOMAINS_ = 4; static {typename[4]="DOMAINS_";}
  static final int DATABASE_ = 5; static {typename[5]="DATABASE_";}
  static final int PREDICATES_ = 6; static {typename[6]="PREDICATES_";}
  static final int CLAUSES_ = 7; static {typename[7]="CLAUSES_";}
  static final int INCLUDE_ = 8; static {typename[8]="INCLUDE_";}
  static final int S = 9; static {typename[9]="S";}
  static final int GOAL_ = 10; static {typename[10]="GOAL_";}
  static final int POINT = 11; static {typename[11]="POINT";}
  static final int INLINE = 12; static {typename[12]="INLINE";}
  static final int IFDEF_ = 13; static {typename[13]="IFDEF_";}
  static final int IFNDEF_ = 14; static {typename[14]="IFNDEF_";}
  static final int ELSEDEF_ = 15; static {typename[15]="ELSEDEF_";}
  static final int ENDDEF_ = 16; static {typename[16]="ENDDEF_";}
  static final int EQ = 17; static {typename[17]="EQ";}
  static final int GLOBAL_ = 18; static {typename[18]="GLOBAL_";}
  static final int STAR = 19; static {typename[19]="STAR";}
  static final int MINUS = 20; static {typename[20]="MINUS";}
  static final int DETERM_ = 21; static {typename[21]="DETERM_";}
  static final int NONDETERM_ = 22; static {typename[22]="NONDETERM_";}
  static final int LPAR = 23; static {typename[23]="LPAR";}
  static final int RPAR = 24; static {typename[24]="RPAR";}
  static final int LANGUAGE_ = 25; static {typename[25]="LANGUAGE_";}
  static final int AS_ = 26; static {typename[26]="AS_";}
  static final int IFSYM = 27; static {typename[27]="IFSYM";}
  static final int NE = 28; static {typename[28]="NE";}
  static final int GT = 29; static {typename[29]="GT";}
  static final int GE = 30; static {typename[30]="GE";}
  static final int LT = 31; static {typename[31]="LT";}
  static final int LE = 32; static {typename[32]="LE";}
  static final int PLUS = 33; static {typename[33]="PLUS";}
  static final int SLASH = 34; static {typename[34]="SLASH";}
  static final int DIV_ = 35; static {typename[35]="DIV_";}
  static final int MOD_ = 36; static {typename[36]="MOD_";}
  static final int INT = 37; static {typename[37]="INT";}
  static final int REAL = 38; static {typename[38]="REAL";}
  static final int CHAR = 39; static {typename[39]="CHAR";}
  static final int LBRAK = 40; static {typename[40]="LBRAK";}
  static final int RBRAK = 41; static {typename[41]="RBRAK";}
  static final int CUT = 42; static {typename[42]="CUT";}
  static final int VBAR = 43; static {typename[43]="VBAR";}
  static final int NAME = 44; static {typename[44]="NAME";}
  static final int STRING = 45; static {typename[45]="STRING";}
  static final int VARNAME = 46; static {typename[46]="VARNAME";}
  static final int EOF = 99; static {typename[99]="EOF";}
  static final int ERROR = 100; static {typename[100]="ERROR";}
  static final int NILL = 0; static {typename[0]="NILL";}
  
  public String toString()
  {
    String ans = "(";
    String typeStr;
    
    if( tokenType >= 0 && tokenType < typename.length )
    {
      ans += typename[tokenType];
    }
    else
    {
      ans += tokenType;
    }
    ans += ",\"" + sValue + "\"," + iValue + "," + rValue/* + "," + fCoeff*/ + ";" + 
        col + "," + len + ")";
    
    return ans;
  }
}


