/* JalogSyntax.java */

import java.util.Locale;

public class JalogSyntax extends SimpleSyntax
{
  public static final String COMMA = "COMMA";
  public static final String SEMICOLON = "SEMICOLON";
  public static final String IFSYM = "IFSYM";
  public static final String POINT = "POINT";
  public static final String EQ = "EQ";
  public static final String STAR = "STAR";
  public static final String MINUS = "MINUS";
  public static final String LPAR = "LPAR";
  public static final String RPAR = "RPAR";
  public static final String NE = "NE";
  public static final String GT = "GT";
  public static final String GE = "GE";
  public static final String LT = "LT";
  public static final String LE = "LE";
  public static final String PLUS = "PLUS";
  public static final String SLASH = "SLASH";
  public static final String INT = "INT";
  public static final String REAL = "REAL";
  public static final String CHAR = "CHAR";
  public static final String LBRAK = "LBRAK";
  public static final String RBRAK = "RBRAK";
  public static final String CUT = "CUT";
  public static final String VBAR = "VBAR";
  public static final String NAME = "NAME";
  public static final String STRING = "STRING";
  public static final String VARIABLE = "VARIABLE";
  public static final String OPEN = "OPEN";
  public static final String COMMENT_MARK = "COMMENT_MARK";

  public static final String AS_ = "AS_";
  public static final String CLAUSES_ = "CLAUSES_";
  public static final String CONSTANTS_ = "CONSTANTS_";
  public static final String DATABASE_ = "DATABASE_";
  public static final String DETERM_ = "DETERM_";
  public static final String DIV_ = "DIV_";
  public static final String DOMAINS_ = "DOMAINS_";
  public static final String ELSEDEF_ = "ELSEDEF_";
  public static final String ENDDEF_ = "ENDDEF_";
  public static final String GLOBAL_ = "GLOBAL_";
  public static final String GOAL_ = "GOAL_";
  public static final String IFDEF_ = "IFDEF_";
  public static final String IFNDEF_ = "IFNDEF_";
  public static final String INCLUDE_ = "INCLUDE_";
  public static final String LANGUAGE_ = "LANGUAGE_";
  public static final String MOD_ = "MOD_";
  public static final String NONDETERM_ = "NONDETERM_";
  public static final String PREDICATES_ = "PREDICATES_";

  public static final String[] keyword = {
    AS_, CLAUSES_, CONSTANTS_, DATABASE_, DETERM_, DIV_, DOMAINS_, ELSEDEF_,
    ENDDEF_, GLOBAL_, GOAL_, IFDEF_, IFNDEF_, INCLUDE_, LANGUAGE_, MOD_,
    NONDETERM_, PREDICATES_ };

  // special characters and range ends
  public static final char LCF = '\u0000'; // first low control character
  public static final char LCL = '\u001F'; // last low control character
  public static final char LVF = '\u0021'; // first low visible character
  public static final char SQ  = '\'';     // single quote
  public static final char EP  = '\\';     // escape sequence prefix
  public static final char LVL = '\u007E'; // last low visible character
  public static final char DEL = '\u007F'; // DEL
  public static final char HCF = '\u0080'; // first upper control character
  public static final char HCL = '\u009F'; // last upper control character
  public static final char HSP = '\u00A0'; // upper space
  public static final char HVF = '\u00A1'; // first high visible character
  public static final char HVL = '\uFFFD'; // last high visible character
  public static final char HAL = '\uFFFF'; // last high character

  private static ScanRule scanTable[][] =
  {
    { // state 0: START of token
      new ScanRule(LCF,' ',NIL,END), // lower control and space
      new ScanRule(DEL,HSP,NIL,END), // upper control and space
      new ScanRule(',',',',COMMA,END),
      new ScanRule(';',';',SEMICOLON,END),
      new ScanRule('.','.',POINT,END),
      new ScanRule('=','=',EQ,END),
      new ScanRule('*','*',STAR,END),
      new ScanRule('-','-',MINUS,END),
      new ScanRule('(','(',LPAR,END),
      new ScanRule(')',')',RPAR,END),
      new ScanRule('+','+',PLUS,END),
      new ScanRule('[','[',LBRAK,END),
      new ScanRule(']',']',RBRAK,END),
      new ScanRule('|','|',VBAR,END),
      new ScanRule('<','<',LT,2),
      new ScanRule('>','>',GT,3),
      new ScanRule('!','!',CUT,4),
      new ScanRule('$','$',ERR,5),
      new ScanRule('0','0',INT,6), // number
      new ScanRule('1','9',INT,7), // decimal number
      new ScanRule('A','Z',NAME,11), // type updated later
      new ScanRule('_','_',OPEN,11),
      new ScanRule('a','z',NAME,11),
      new ScanRule(SQ ,SQ ,ERR,12), // single quote
      new ScanRule('"','"',ERR,16), // double quote
      new ScanRule(':',':',ERR,18),
      new ScanRule('%','%',EOL,END), // end-of-line comment
      new ScanRule('/','/',SLASH,1) },
    { // state 1: after /
      new ScanRule('*','*',COMMENT_MARK,END) },
    { // state 2: after <
      new ScanRule('=','=',LE,END),
      new ScanRule('>','>',NE,END) },
    { // state 3: after >
      new ScanRule('=','=',GE,END),
      new ScanRule('<','<',NE,END) },
    { // state 4: after !
      new ScanRule('=','=',NE,END) },
    { // state 5: after $
      new ScanRule('0','9',INT,5),
      new ScanRule('A','F',INT,5),
      new ScanRule('a','f',INT,5) },
    { // state 6: after 0
      new ScanRule('x','x',ERR,5), // hex number
      new ScanRule('0','9',INT,7), // decimal number with leading zero
      new ScanRule('.','.',ERR,8), // decimal fraction
      new ScanRule('E','E',ERR,9), // zero with exponent part
      new ScanRule('e','e',ERR,9) },
    { // state 7: after digits
      new ScanRule('0','9',INT,7), // more digits
      new ScanRule('.','.',ERR,8), // fraction part
      new ScanRule('E','E',ERR,9), // exponent part
      new ScanRule('e','e',ERR,9) },
    { // state 8: after . in real
      new ScanRule('0','9',REAL,8), // fraction digit
      new ScanRule('E','E',ERR,9), // exponent part
      new ScanRule('e','e',ERR,9) },
    { // state 9: after E in real
      new ScanRule('+','+',ERR,10), // positive exponent
      new ScanRule('-','-',ERR,10), // negative exponent
      new ScanRule('0','9',REAL,10) }, // unsigned eponent
    { // state 10: after E+ in real
      new ScanRule('0','9',REAL,10) }, // digits in exponent
    { // state 11: after A..Z _ a..z
      new ScanRule('0','9',NAME,11),
      new ScanRule('A','Z',NAME,11),
      new ScanRule('_','_',NAME,11),
      new ScanRule('a','z',NAME,11) },
    { // state 12: after '
      new ScanRule(EP ,EP ,ERR,13), // escape sequence prefix
      new ScanRule(' ',LVL,ERR,15), // lower space and visible
      new ScanRule(HSP,HVL,ERR,15) }, // upper space and visible
    { // state 13: after ' EP
      new ScanRule(EP ,EP ,ERR,15),
      new ScanRule(SQ ,SQ ,ERR,15),
      new ScanRule(' ',LVL,ERR,14) },
    { // state 14: after ' EP ...
      new ScanRule(EP ,EP ,ERR,END), // another EP not allowed
      new ScanRule(SQ ,SQ ,CHAR,END), // closing '
      new ScanRule(' ',LVL,ERR,14) },
    { // state 15: after 'c
      new ScanRule(SQ ,SQ ,CHAR,END) },
    { // state 16: after "
      new ScanRule('"','"',STRING,END), // end of string
      new ScanRule(EP ,EP ,ERR,17), // escape sequence prefix
      new ScanRule(' ',LVL,ERR,16), // lower space and visible
      new ScanRule(HSP,HVL,ERR,16) }, // upper space and visible
    { // state 17: after EP
      new ScanRule(' ',LVL,ERR,16),
      new ScanRule(HSP,HVL,ERR,16) },
    { // state 18: after :
      new ScanRule('-','-',IFSYM,END) },
  };

  public ScanRule getScanRule(int state, char c) {
    if (state >= 0 && state < scanTable.length) {
      ScanRule[] stateTable = scanTable[state];
      int stateLen = stateTable.length;
      for (int i = 0; i < stateLen; i++) {
        ScanRule rule = stateTable[i];
        if (c >= rule.fst && c <= rule.lst) {
          return rule;
        }
      }
    }
    return null;
  }

  public static String COMMENT_END_MARK = "*/";

  public static String unquote(String img) {
    String ans = null;
    if (img != null) {
      int n = img.length() - 1;
      if (img.charAt(0) == img.charAt(n)) {
        StringBuilder buf = new StringBuilder(img.length());
        int p = 1;
        while (p < n) {
          int c = img.charAt(p);
          p++;
          if (c == '\\' && p < n) {
            c = img.charAt(p);
            p++;
            switch (c) {
              case 'a': {
                c = '\u0007';
              } break;
              case 'b': {
                c = '\u0008';
              } break;
              case 't': {
                c = '\t';
              } break;
              case 'n': {
                c = '\n';
              } break;
              case 'v': {
                c = '\u000B';
              } break;
              case 'f': {
                c = '\f';
              } break;
              case 'r': {
                c = '\r';
              } break;
              case 'e': {
                c = '\u001B';
              } break;
              case 'x':
              case 'u':
              case 'U': {
                c = 0;
                int end = p + (c == 'x' ? 2 : c == 'u' ? 4 : 8);
                if (end > n) end = n;
                while (p < end) {
                  char d = img.charAt(p);
                  p++;
                  if (d >= '0' && d <= '9') {
                    c = (c << 4) + (d - '0');
                  } else if (d >= 'A' && d <= 'F') {
                    c = (c << 4) + (d - 'A' + 10);
                  } else if (d >= 'a' && d <= 'f') {
                    c = (c << 4) + (d - 'a' + 10);
                  } else {
                    p--;
                    end = p;
                  }
                }
              } break;
              default: {
                if (c >= '0' && c <= '9') {
                  c = c - '0';
                  char d = img.charAt(p);
                  if (d >= '0' && d <= '9') {
                    p++;
                    c = 10 * c + (d - '0');
                    d = img.charAt(p);
                    if (d >= '0' && d <= '9') {
                      p++;
                      c = 10 * c + (d - '0');
                    }
                  }
                }
              } break;
            }
          }
          buf.appendCodePoint(c);
        }
        ans = buf.toString();
      }
    }
    return ans;
  }

  public static String quote(String val, char quote) {
    String ans;
    if (val == null) {
      ans = "null";
    } else {
      StringBuilder buf = new StringBuilder(val.length()+10);
      buf.append(quote);
      int n = val.length();
      for (int i = 0; i < n; i++) {
        int c = val.codePointAt(i);
        if (c > 0xFFFF) {
          i++;
          buf.append(String.format((Locale)null,"\\U%08X", c));
        } else if (c == '\b') {
          buf.append("\\b");
        } else if (c == '\t') {
          buf.append("\\t");
        } else if (c == '\n') {
          buf.append("\\n");
        } else if (c == '\f') {
          buf.append("\\f");
        } else if (c == '\r') {
          buf.append("\\r");
        } else if (c == '\\') {
          buf.append("\\\\");
        } else if (c == quote) {
          buf.append("\\").append(quote);
        } else if (c == '?') {
          buf.append("\\?");
        } else if (c > 0x1F && c < 0x7F) {
          buf.append((char)c);
        } else {
          buf.append(String.format((Locale)null,"\\u%04X",c));
        }
      }
      buf.append(quote);
      ans = buf.toString();
    }
    return ans;
  }

  public static String quote(String val) {
    return quote(val, '"');
  }

  public static String quote(int val, char quote) {
    int[] vals = { val };
    return quote(new String(vals, 0, 1), quote);
  }

  public static String quote(int val) {
    return quote(val, '\'');
  }

  public static long parseInt(String img) {
    long ans;
    if (img.startsWith("'")) {
      String s = unquote(img);
      ans = s.codePointAt(0);
    } else if (img.startsWith("$")) {
      ans = Long.parseLong(img.substring(1), 16);
    } else if (img.startsWith("-$")) {
      ans = Long.parseLong("-"+img.substring(2), 16);
    } else if (img.startsWith("0x") || img.startsWith("0X")) {
      ans = Long.parseLong(img.substring(2), 16);
    } else if (img.startsWith("-0x") || img.startsWith("-0X")) {
      ans = Long.parseLong("-"+img.substring(3), 16);
    } else {
      ans = Long.parseLong(img);
    }
    return ans;
  }

  public static double parseReal(String img) {
    String s;
    if (img.startsWith("$")) {
      s = "0x"+img.substring(1)+"p0";
    } else if (img.startsWith("-$")) {
      s = "-0x"+img.substring(2)+"p0";
    } else if ( img.startsWith("0x") || img.startsWith("0X")
             || img.startsWith("-0x") || img.startsWith("-0X") )
    {
      s = img + "p0";
    } else {
      s = img;
    }
    return Double.parseDouble(s);
  }

  public static final String BGN_CLAUSE = "bgn_clause";
  public static final String END_CLAUSE = "end_clause";
  public static final String BGN_BODY = "bgn_body";
  public static final String END_BODY = "end_body";
  public static final String BGN_ITEM = "bgn_item";
  public static final String END_ITEM = "end_item";
  public static final String BGN_STRING = "bgn_string";
  public static final String MORE_STRING = "more_string";
  public static final String END_STRING = "end_string";
  public static final String BGN_BINOP = "bgn_binop";
  public static final String END_BINOP = "end_binop";
  public static final String BGN_UNOP = "bgn_unop";
  public static final String END_UNOP = "end_unop";
  public static final String FUNCTOR = "functor";
  public static final String SYM = "sym";
  public static final String BGN_STRUCT = "bgn_struct";
  public static final String END_STRUCT = "end_struct";
  public static final String BGN_ARG = "bgn_arg";
  public static final String END_ARG = "end_arg";
  public static final String EMPTY_LIST = "empty_list";
  public static final String BGN_LIST = "bgn_list";
  public static final String END_LIST = "end_list";
  public static final String BGN_ELEM = "bgn_elem";
  public static final String END_ELEM = "end_elem";
  public static final String BGN_TAIL = "bgn_tail";
  public static final String END_TAIL = "end_tail";
  // in addition, token types can be used as actions

  public static int CLAUSE = 1;
  public static int CLAUSES = 2;
  public static int FACT = 3;
  public static int FACTS = 4;
  public static int TERM = 5;
  public static int EXPR = 6;
  public static int TOKENS = 7;
  public static int SPACE = 8;

  private static ParseRule parseTable[] = {

// CLAUSE = expr0 point

    new ParseRule(CLAUSE,ANY,BGN_CLAUSE,910,END_CLAUSE,END),
    new ParseRule(910,ANY,NIL,1000,NIL,990),

// CLAUSES = EOF
//         = CLAUSES CLAUSE

    new ParseRule(CLAUSES,EOF,NIL,NONE,NIL,END),
    new ParseRule(CLAUSES,ANY,NIL,CLAUSE,NIL,CLAUSES),

// FACT = expr8 point

    new ParseRule(FACT,ANY,BGN_CLAUSE,930,END_CLAUSE,END),
    new ParseRule(930,ANY,NIL,1800,NIL,990),

// FACTS = EOF
//       = FACTS FACT

    new ParseRule(FACTS,EOF,NIL,NONE,NIL,END),
    new ParseRule(FACTS,ANY,NIL,FACT,NIL,FACTS),

// TERM = expr6

    new ParseRule(TERM,ANY,NIL,1600,NIL,END),

// EXPR = expr3

    new ParseRule(EXPR,ANY,NIL,1300,NIL,END),

// TOKENS = EOF
//        = TOKENS token

    new ParseRule(TOKENS,EOF,USE_TOKEN,NONE,NIL,END),
    new ParseRule(TOKENS,ANY,USE_TOKEN,NONE,NIL,TOKENS),

// SPACE =

    new ParseRule(SPACE,ANY,NIL,NONE,NIL,END),

// point = POINT

    new ParseRule(990,POINT,NIL,NONE,NIL,END),

// expr0 = IFSYM expr1
//       = expr3
//       = expr3 IFSYM expr1

    new ParseRule(1000,IFSYM,BGN_UNOP,1100,END_UNOP,END),
    new ParseRule(1000,ANY,NIL,1300,NIL,1001),

    new ParseRule(1001,IFSYM,BGN_BINOP,1100,END_BINOP,END),
    new ParseRule(1001,ANY,NIL,NONE,NIL,END),

// expr1 = expr2
//       = expr1 SEMICOLON expr2

    new ParseRule(1100,ANY,BGN_BODY,1200,END_BODY,1101),

    new ParseRule(1101,SEMICOLON,BGN_BODY,1200,END_BODY,1101),
    new ParseRule(1101,ANY,NIL,NONE,NIL,END),

// expr2 = expr3
//       = expr2 COMMA expr3

    new ParseRule(1200,ANY,BGN_ITEM,1300,END_ITEM,1201),

    new ParseRule(1201,COMMA,BGN_ITEM,1300,END_ITEM,1201),
    new ParseRule(1201,ANY,NIL,NONE,NIL,END),

// expr3 = expr4
//       = expr4 EQ expr4
//       = expr4 NE expr4
//       = expr4 GT expr4
//       = expr4 GE expr4
//       = expr4 LT expr4
//       = expr4 LE expr4

    new ParseRule(1300,ANY,NIL,1400,NIL,1301),

    new ParseRule(1301,EQ,BGN_BINOP,1400,END_BINOP,END),
    new ParseRule(1301,NE,BGN_BINOP,1400,END_BINOP,END),
    new ParseRule(1301,GT,BGN_BINOP,1400,END_BINOP,END),
    new ParseRule(1301,GE,BGN_BINOP,1400,END_BINOP,END),
    new ParseRule(1301,LT,BGN_BINOP,1400,END_BINOP,END),
    new ParseRule(1301,LE,BGN_BINOP,1400,END_BINOP,END),
    new ParseRule(1301,ANY,NIL,NONE,NIL,END),

// expr4 = PLUS expr5
//       = MINUS expr5
//       = expr5
//       = expr4 PLUS expr5
//       = expr4 MINUS expr5

    new ParseRule(1400,PLUS,BGN_UNOP,1500,BGN_UNOP,1401),
    new ParseRule(1400,MINUS,BGN_UNOP,1500,END_UNOP,1401),
    new ParseRule(1400,ANY,NIL,1500,NIL,1401),

    new ParseRule(1401,PLUS,BGN_BINOP,1500,END_BINOP,1401),
    new ParseRule(1401,MINUS,BGN_BINOP,1500,END_BINOP,1401),
    new ParseRule(1401,ANY,NIL,NONE,NIL,END),


// expr5 = expr6
//       = expr5 STAR expr6
//       = expr5 SLASH expr6
//       = expr5 DIV_ expr6
//       = expr5 MOD_ expr6

    new ParseRule(1500,NIL,NIL,1600,NIL,1501),

    new ParseRule(1501,STAR,BGN_BINOP,1600,END_BINOP,1501),
    new ParseRule(1501,SLASH,BGN_BINOP,1600,END_BINOP,1501),
    new ParseRule(1501,DIV_,BGN_BINOP,1600,END_BINOP,1501),
    new ParseRule(1501,MOD_,BGN_BINOP,1600,END_BINOP,1501),
    new ParseRule(1501,ANY,NIL,NONE,NIL,END),

// expr6 = VARNAME
//       = OPEN
//       = INT
//       = REAL
//       = CHAR
//       = CUT
//       = LPAR expr0 RPAR
//       = string
//       = expr7
// string = STRING
//        = string STRING

    new ParseRule(1600,VARIABLE,VARIABLE,NONE,NIL,END),
    new ParseRule(1600,OPEN,OPEN,NONE,NIL,END),
    new ParseRule(1600,INT,INT,NONE,NIL,END),
    new ParseRule(1600,REAL,REAL,NONE,NIL,END),
    new ParseRule(1600,CHAR,CHAR,NONE,NIL,END),
    new ParseRule(1600,CUT,CUT,NONE,NIL,END),
    new ParseRule(1600,LPAR,NIL,1000,NIL,1601),
    new ParseRule(1600,STRING,BGN_STRING,1610,END_STRING,END),
    new ParseRule(1600,ANY,NIL,NONE,NIL,1700),

    new ParseRule(1601,RPAR,NIL,NONE,NIL,END),

    new ParseRule(1610,STRING,MORE_STRING,NONE,NIL,1610),
    new ParseRule(1610,ANY,NIL,NONE,NIL,END),

// expr7 = LBRAK RBRAK
//       = LBRAK elemlist | expr3 RBRAK
//       = LBRAK elemlist RBRAK
//       = expr8
// elemlist = expr3
//          = elemlist COMMA expr3

    new ParseRule(1700,LBRAK,NIL,NONE,NIL,1701),
    new ParseRule(1700,ANY,NIL,NONE,NIL,1800),

    new ParseRule(1701,RBRAK,EMPTY_LIST,NONE,NIL,END),
    new ParseRule(1701,ANY,BGN_LIST,1702,END_LIST,END),

    new ParseRule(1702,ANY,BGN_ELEM,1300,END_ELEM,1703),

    new ParseRule(1703,COMMA,BGN_ELEM,1300,END_ELEM,1703),
    new ParseRule(1703,ANY,BGN_TAIL,1704,END_TAIL,END),

    new ParseRule(1704,VBAR,NIL,1300,NIL,1705),
    new ParseRule(1704,RBRAK,EMPTY_LIST,NONE,NIL,END),

    new ParseRule(1705,RBRAK,NIL,NONE,NIL,END),

// expr8 = NAME LPAR RPAR
//       = NAME LPAR arglist RPAR
//       = NAME
// arglist = expr0
//         = arglist COMMA expr0

    new ParseRule(1800,NAME,FUNCTOR,NONE,NIL,1801),

    new ParseRule(1801,LPAR,BGN_STRUCT,1802,END_STRUCT,END),
    new ParseRule(1801,ANY,SYM,NONE,NIL,END),

    new ParseRule(1802,RPAR,NIL,NONE,NIL,END),
    new ParseRule(1802,ANY,NIL,1810,NIL,1803),

    new ParseRule(1803,RPAR,NIL,NONE,NIL,END),

    new ParseRule(1810,ANY,BGN_ARG,1300,END_ARG,1811),

    new ParseRule(1811,COMMA,BGN_ARG,1300,END_ARG,1811),
    new ParseRule(1811,ANY,NIL,NONE,NIL,END),

  };

  public ParseRule getParseRule(int state, String tokenType) {
    ParseRule rule;
    int n = parseTable.length;
    for (int i = 0; i < n; i++) {
      rule = parseTable[i];
      if (rule.state == state) {
        if (rule.tokenType == tokenType || rule.tokenType == ANY) {
          return rule;
        }
      }
    }
    return null;
  }

}
