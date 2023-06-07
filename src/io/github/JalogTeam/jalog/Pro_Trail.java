/* Pro_Trail.java */

package io.github.JalogTeam.jalog;

import java.util.Vector;

public class Pro_Trail
{
  public static int maxnum = 0;
  public static int currentnum = 0;

  Pro_Trail_Item top_item;
  
  public void mark(Pro_TrailMark Mark)// = pBack.top;
  {
    if (Mark != null) {
      Mark.marked = top_item;
              // System.out.println("Trail.mark: " + Mark.marked);
    }
  }


  public void append(Pro_Term pn)
  {
    Pro_Trail_Item new_top_item;
// Debug_times.enter(22); 
//    items.add(pn);
    new_top_item = new Pro_Trail_Item();
    new_top_item.prev = top_item;
    top_item = new_top_item;
    top_item.item = pn;
    currentnum ++;
    if(currentnum > maxnum) maxnum = currentnum;
// Debug_times.leave(22); 
// System.out.println("Trail.append: " + pn);
  }
  
  public void backtrack( /*Pro_Trail pBack,*/ Pro_TrailMark Mark){
    Pro_Term pn2;
// Debug_times.enter(20); 
              // System.out.println("Trail.backtrack mark: " + Mark.marked);
//	  while((!items.isEmpty()) && ((pn2 = (Pro_Term)items.lastElement()) != Mark.marked))
    while((top_item != null) && (top_item != Mark.marked))      
    {
      pn2 = top_item.item;
// System.out.println("Trail.backtrack item: " + pn2);
      pn2.clearData();  // *undo unification*
// Debug_times.enter(21); 
      // remove top element
      top_item = top_item.prev;
      currentnum --;
//      items.removeElement(pn2);
// Debug_times.leave(21); 
    }
// Debug_times.leave(20); 
  }

  public void dump(String header)
  {
    int i = 0;
    Pro_Trail_Item current_item = top_item;

    System.err.println(header);
    System.err.println("  items=");
    
    for(current_item = top_item; current_item != null; current_item = current_item.prev)
    {
      System.err.println("    top-" + i + ": " + current_item.item.Id + " " + current_item.item);
      i ++;
    }
  }
} // end class Pro_Trail

