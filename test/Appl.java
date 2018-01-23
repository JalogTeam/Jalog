import java.io.*;

class Appl
{
  Pro_TermData a = new Pro_TermData();
  Pro_TrailMark d = new Pro_TrailMark();
  
  
  public static void main(String args[])
  {
    Pro_TermData_Integer b = new Pro_TermData_Integer(42);
    Pro_TermData_String c = new Pro_TermData_String("joo");
    
    System.out.println("Jee " + b.value + " \"" + c.value + "\"");
    
    test_backtrack();
  }
  
  static void test_backtrack()
  {
    Pro_Trail trail1 = new Pro_Trail();
    Pro_TrailMark mark1 = new Pro_TrailMark();
    
    Pro_Term tyhja[] = {};
    Pro_TermData_Compound X = new Pro_TermData_Compound("kompound",tyhja);
    System.out.println("Compound " + X.name + " created");
    
    Pro_Term termA = Pro_Term.m_open();
    System.out.println("termA=" + termA);
    Pro_Term term1 = Pro_Term.m_unified(termA);
    Pro_Term term2 = Pro_Term.m_unified(termA);
    Pro_Term term3 = Pro_Term.m_unified(termA);
    Pro_Term term4 = Pro_Term.m_unified(termA);
    System.out.println("term1=" + term1);
    System.out.println("term2=" + term2);
    System.out.println("term3=" + term3);
    System.out.println("term4=" + term4);
    
    
    trail1.dump("empty");
    
    trail1.backtrack(mark1);
    trail1.dump("still empty?");
    
    trail1.append(term1);
    trail1.append(term2);
    trail1.dump("terms 1 & 2");
    trail1.mark(mark1);
    trail1.append(term3);
    trail1.append(term4);
    trail1.dump("terms 1 ... 4");
    
    System.out.println("backtrack2: " + trail1.items.size() + " = 4 ?");
    
    trail1.backtrack(mark1);
    trail1.dump("terms 1 & 2?");
    
    System.out.println("backtrack3: " + trail1.items.size() + " = 2 ?");
    
    Pro_Term termC1 = Pro_Term.m_char('1');
    System.out.println("termC1=" + termC1);

    if(termA.unify(termC1,trail1,mark1))
    {
      System.out.println("termA=" + termA);
      trail1.dump("Success");
    }
    else
    {
      trail1.dump("Fail");
    }
    System.out.println("term1=" + term1);
    System.out.println("term2=" + term2);
    System.out.println("term3=" + term3);
    System.out.println("term4=" + term4);
    System.out.println("----------");
      
    Pro_Term termI1 = Pro_Term.m_integer(42);
    System.out.println("termI1=" + termI1);

    Pro_Term termS1 = Pro_Term.m_string("jiihaa");
    System.out.println("termS1=" + termS1);

    Pro_Term[] empty_array={};
    Pro_Term termL1 = Pro_Term.m_list(empty_array,Pro_Term.EMPTY_LIST);
    System.out.println("termL1=" + termL1);

    Pro_Term termA1 = Pro_Term.m_compound("funktori",empty_array);
    System.out.println("termA1=" + termA1);

    Pro_Term[] array1={termI1};
    Pro_Term termL2 = Pro_Term.m_list(array1,Pro_Term.EMPTY_LIST);
    System.out.println("termL2=" + termL2);

    Pro_Term termA2 = Pro_Term.m_compound("singletti",array1);
    System.out.println("termA2=" + termA2);

    Pro_Term[] array2={termI1,termA2};
    Pro_Term termL3 = Pro_Term.m_list(array2,Pro_Term.EMPTY_LIST);
    System.out.println("termL3=" + termL3);

    Pro_Term termA3 = Pro_Term.m_compound("dupletti",array2);
    System.out.println("termA3=" + termA3);

    Pro_Term termL4 = Pro_Term.m_list(array2,termL3);
    System.out.println("termL4=" + termL4);

    Pro_Term termL5 = Pro_Term.m_list(empty_array,termL3);
    System.out.println("termL5=" + termL5);

    Pro_Term termL6 = Pro_Term.m_list(array2,term4);
    System.out.println("termL6=" + termL6);

// Unification tests
//    a(A,'x') = a(10,B)

    Pro_Term[] arrayA3a={Pro_Term.m_open(),Pro_Term.m_char('x')};
    Pro_Term[] arrayA3b={Pro_Term.m_integer(10),Pro_Term.m_open()};
    
    Pro_Term termA3a = Pro_Term.m_compound("a",arrayA3a);
    Pro_Term termA3b = Pro_Term.m_compound("a",arrayA3b);

    System.out.println("termA3a=" + termA3a);
    System.out.println("termA3b=" + termA3b);
    
    if(termA3a.unify(termA3b,trail1,mark1))
    {
      trail1.dump("Success");
    }
    else
    {
      trail1.dump("Fail");
    }
    System.out.println("termA3a=" + termA3a);
    System.out.println("termA3b=" + termA3b);

//    a(10,[Y,'x']) = a(X,['y','z'])

    Pro_Term[] arrayL4a={Pro_Term.m_open(),Pro_Term.m_char('x')};
    Pro_Term[] arrayA4a={Pro_Term.m_integer(10),Pro_Term.m_list(arrayL4a,Pro_Term.EMPTY_LIST)};
    Pro_Term[] arrayL4b={Pro_Term.m_char('y'),Pro_Term.m_char('z')};
    Pro_Term[] arrayA4b={Pro_Term.m_open(),Pro_Term.m_list(arrayL4b,Pro_Term.EMPTY_LIST)};
    
    Pro_Term termA4a = Pro_Term.m_compound("a",arrayA4a);
    Pro_Term termA4b = Pro_Term.m_compound("a",arrayA4b);

    System.out.println("termA4a=" + termA4a);
    System.out.println("termA4b=" + termA4b);
    
    if(termA4a.unify(termA4b,trail1,mark1))
    {
      trail1.dump("Success");
    }
    else
    {
      trail1.dump("Fail");
    }
    System.out.println("termA4a=" + termA4a);
    System.out.println("termA4b=" + termA4b);
    
  }
}

