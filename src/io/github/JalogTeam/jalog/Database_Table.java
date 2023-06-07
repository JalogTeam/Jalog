// Database_Table.java

package io.github.JalogTeam.jalog;

class Database_Table // Name/Arity
{
  DB_Cursor last_cursor;
  Chain facts;
  boolean dynamic = false;

  Database_Table()
  {
    last_cursor = null;
    facts = new Chain();
  }
}

