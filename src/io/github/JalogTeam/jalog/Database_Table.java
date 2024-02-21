// Database_Table.java

package io.github.JalogTeam.jalog;

class Database_Table // Name/Arity
{
  DB_Cursor last_cursor;
  Chain facts;
  boolean dynamic = false;
  String databaseName = null;
  boolean has_rules = false;

  Database_Table()
  {
    last_cursor = null;
    facts = new Chain();
  }

  void setName(String databaseName) {
    if (this.databaseName == null) {
      this.databaseName = databaseName;
    } else {
      if (databaseName != this.databaseName){
        // error
      }
    }
  }
  
  boolean checkName(String databaseName) {
    boolean result;
    
    result = (this.databaseName.equals(databaseName));
    
    return result;
  }


}
