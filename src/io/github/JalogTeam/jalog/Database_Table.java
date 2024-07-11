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

  boolean retract(Pro_Term filter, Pro_TrailMark Mark)
  {
//    return null;
    boolean status;
    Pro_Term unified_clause;
    DB_Cursor prev_item;
    
    if(dynamic) {
      prev_item = new DB_Cursor();
      Pred.trail.mark(Mark);
      unified_clause = Database.fetch(this, prev_item, filter, Mark, true);

      if(unified_clause != null) {
        facts.remove(prev_item.current_item);
        status = true;
      } else {
        status = false;
      }
    } else { // not dynamic
      status = false;
    }

    return status;

  }


}
