// Chain.java

package io.github.JalogTeam.jalog;

import java.io.*;

public class Chain
{
  public Chain_Item first;
  public Chain_Item last;

  public void addFirst(Chain_Item item)
  {
    if(first == null) {  // now empty
      last = item;
    } else {
      first.prev = item;
    }
    item.prev = null;
    item.next = first;
    first = item;
    item.deleted = false;
  }

  public void addLast(Chain_Item item)
  {
    if(last == null) {
      first = item;
    } else {
      last.next = item;
    }
    item.next = null;
    item.prev = last;
    last = item;
    item.deleted = false;
  }

  public void remove(Chain_Item item)
  {
    if(item.prev == null) {
      first = item.next;
    } else {
      item.prev.next = item.next;
    }
    if(item.next == null) {
      last = item.prev;
    } else {
      item.next.prev = item.prev;
    }
    item.deleted = true;
  }

}

