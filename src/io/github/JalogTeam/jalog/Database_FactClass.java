// Database_FactClass.java

package io.github.JalogTeam.jalog;

class Database_FactClass // Name/Arity
{
  DB_Cursor last_cursor;
  Chain facts;
  boolean dynamic = false;

  Database_FactClass()
  {
    last_cursor = null;
    facts = new Chain();
  }
}

