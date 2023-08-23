% strings - test

% string


% koe1 expected: 

% substring(Str_in,Pos,Len,Str_out)

koe1 :- write("koe1: "), substring("ABCDEF", 2, 3, A), writeq(A),
    write(" |", A, "|"), nl. % should get "CDE"

koe2 :- write("koe2: "), concat("ABC", "DEF", A), writeq(A), nl. % should get "ABCCDE"
koe3 :- write("koe3.a: "), concat("ABC", "DEF", "ABCDXF"), write("Error"), !, 
    nl. % should fail
koe3 :- write("Ok"), nl, fail.
koe3 :- write("koe3.b: "), concat("ABC", "DEF", "ABCDEF"), write("Ok"), 
    nl. % should get "Ok"

koe4 :- write("koe4: "), substring("ABCDEF", 2, 3, "CDE"), write("Ok"), 
    nl.  % should get "Ok" 

% 
koe4a :- writeln("koe4a1: "),
    substring("ABCDEFGH", 1, 6, A), % BCDEFG
    write("* A="), writeq(A), nl,
    substring(A, 2, 3, B),  % DEF
    write("* B="), writeq(B), nl,
    substring(A, 2, 3, "DEX"), write("Error"), 
    nl.  % shouldn't get here
% 
koe4a :- write("koe4a2: "),
    substring("ABCDEFGH", 1, 6, A), % BCDEFG
    nl,
    substring(A, 2, 3, "XEF"), write("Error"), 
    nl.  % shouldn't get here

koe4a :- write("koe4a3: "),
    substring("ABCDEFGH", 1, 6, A), % BCDEFG
    nl, write("* A="), writeq(A), nl,
    substring(A, 2, 3, B), write("* B="), writeq(B), nl,
    substring(A, 2, 3, "DEF"), write("Ok"), 
    nl.  % should get "Ok" 

% substring past original string
koe4b :- write("koe4b: "),
    substring("ABC", 1, 3, A), % BC
    A = "BC",
    write("Ok"), 
    nl.

% substring negative index
koe4c :- write("koe4c: "),
    substring("ABC", -1, 3, A), % AB
    A = "AB",
    write("Ok"), 
    nl.

% substring wrong length
koe4d :- write("koe4d: "),
    not(substring("ABCDEF", Pos, 3, "BCDE")), % substring fails
    write("Ok"), 
    nl.

koe4e :- write("koe4e: "), nl, substring("CDABCDECDFCD", Pos, Len, "CD"), 
    write("  Pos = ", Pos, ", Len = ", Len), nl, fail. 
koe4e :- write("  no more"), nl.


koe5 :- write("koe5: "), concat("ABC", "DEF", A), 
    substring(A, 1, 4, "BCDE"), 
    write("Ok"), nl.  % should get "Ok" 

koe6 :- write("koe6: "), concat("ABC", "DEF", A), substring(A, 1, 4, B),
    concat(B, A, "BCDEABCDEF"),
    write("Ok"), nl.  % should get "Ok" 

testcmp(A, B) :-
     write("testcmp: "), writeq(A), write("<"), writeq(B),nl,
     A < B,
     writeln("yes"), fail.

testcmp(A, B) :-
     write("testcmp: "), writeq(A), write("<="), writeq(B),nl,
     A <= B,
     writeln("yes"), fail.
     
testcmp(A, B) :-
     write("testcmp: "), writeq(A), write("="), writeq(B),nl,
     A = B,
     writeln("yes"), fail.
     
testcmp(A, B) :-
     write("testcmp: "), writeq(A), write(">="), writeq(B),nl,
     A >= B,
     writeln("yes"), fail.
     
testcmp(A, B) :-
     write("testcmp: "), writeq(A), write(">"), writeq(B),nl,
     A > B,
     writeln("yes"), fail.

testcmp(A, B) :-
     write("testcmp: "), writeq(A), write("<>"), writeq(B),nl,
     A <> B,
     writeln("yes"), fail.
     
testcmp(_, _).

koe7 :- write("koe7: "),
    AS1 = "ABCDEF", AS2 = "123456",
    substring(AS1, 2, 4, AT1), substring(AS2, 1, 3, AT2),
    writeln("+++ |", AT1, "|", AT2, "|"),
    concat(AT1, AT2, AU),
    BS1 = "XCDM", BS2 = "REF234Z",
    substring(BS1, 1, 2, BT1), substring(BS2, 1, 5, BT2),
    writeln("+++ |", BT1, "|", BT2, "|"),
    concat(BT1, BT2, BU),
    writeln("---|", AU, "|", BU, "|"),
    testcmp(AU, BU),
    writeln("--- vv ---"),
    testcmp(BU, AU),
    writeln("koe7 done").

koe7a :- write("koe7a: "),
    AS1 = "ABCDEF", AS2 = "123456",
    substring(AS1, 2, 4, AT1), substring(AS2, 1, 3, AT2),
    writeln("+++ |", AT1, "|", AT2, "|"),
    concat(AT1, AT2, AU),
    BS1 = "XCDM", BS2 = "REF23YZ",
    substring(BS1, 1, 2, BT1), substring(BS2, 1, 5, BT2),
    writeln("+++ |", BT1, "|", BT2, "|"),
    concat(BT1, BT2, BU),
    writeln("---|", AU, "|", BU, "|"),
    testcmp(AU, BU),
    writeln("--- vv ---"),
    testcmp(BU, AU),
    writeln("koe7 done").

koe7b :- write("koe7b: "),
    AS1 = "ABCDEF", AS2 = "123456",
    substring(AS1, 2, 4, AT1), substring(AS2, 1, 3, AT2),
    writeln("+++ |", AT1, "|", AT2, "|"),
    concat(AT1, AT2, AU),
    BS1 = "XCDM", BS2 = "REF23YZ",
    substring(BS1, 1, 2, BT1), substring(BS2, 1, 4, BT2),
    writeln("+++ |", BT1, "|", BT2, "|"),
    concat(BT1, BT2, BU),
    writeln("---|", AU, "|", BU, "|"),
    testcmp(AU, BU),
    writeln("--- vv ---"),
    testcmp(BU, AU),
    writeln("koe7 done").

koe8 :- writeln("koe8: (o,o,o)"),
    concat(_, _, _),
    writeln("Error"),
    fail.
    
koe8 :- writeln("koe8: (i,o,o)"),
    concat("a", _, _),
    writeln("Error"),
    fail.
    
koe8 :- writeln("koe8: (o,i,o)"),
    concat(_, "a", _),
    writeln("Error"),
    fail.
    
koe8 :- writeln("koe8: (o,o,i)"),
    concat(_, _, "a"),
    writeln("Error"),
    fail.
    
koe8 :- writeln("koe8: "),
    koe8g1.
    
    
koe8g1 :-
    koe8g2("1", "V", "ABCD"), 
    fail.
    
koe8g1 :-
    concat("AB", "CD", A),
    koe8g2("2", "V", A),
    fail.
    
koe8g1 :-
    substring("1ABCD2", 1, 4, B),
    koe8g2("3", "V", B),
    fail.
    
koe8g1 :-
    koe8g2("4", "-", "ABCX"), 
    fail.
    
koe8g1 :-
    concat("AX", "CD", A),
    koe8g2("5", "-", A),
    fail.
    
koe8g1 :-
    concat("AB", "CX", A),
    koe8g2("6", "-", A),
    fail.
    
koe8g1 :-
    substring("1ABCX2", 1, 4, B),
    koe8g2("7", "-", B),
    fail.
    
koe8g1.
    
koe8g2(I, V, A) :-
    koe8g3(I, "a", V, A, _, "ABCDEFGH"),    % "EFGH"
    fail.

koe8g2(I, V, A) :-
    concat("ABC", "DEFGH", R),
    koe8g3(I, "b", V, A, _, R),    % "EFGH"
    fail.

koe8g2(I, V, A) :-
    concat("ABCDE", "FGH", R),
    koe8g3(I, "c", V, A, _, R),    % "EFGH"
    fail.

koe8g2(I, V, A) :-
    substring("7ABCDEFGH9", 1, 8, R),
    koe8g3(I, "d", V, A, _, R),    % "EFGH"
    fail.


koe8g2(I, V, B) :-
    koe8g3(I, "e", V, _, B, "EFGHABCD"),    % "EFGH"
    fail.

koe8g2(I, _, A) :-
    concat("EFG", "HABCD", R),
    koe8g3(I, "f", "-", A, _, R),    % "EFGH"
    fail.

koe8g2(I, _, A) :-
    concat("EFGHA", "BCD", R),
    koe8g3(I, "g", "-", A, _, R),    % "EFGH"
    fail.

koe8g2(I, _, A) :-
    substring("7EFGHABCD9", 1, 8, R),
    koe8g3(I, "h", "-", A, _, R),    % "EFGH"
    fail.

koe8g2(_).

koe8g3(I, J, V, A, B, R) :-  
    writeln(I, J, ": ", V, "|", A, "|", B, "|", R, "|"),
    concat(A, B, R),
    writeln(I, J, ": ^|", A, "|", B, "|", R, "|").

koe9 :-  writeln("koe9: "),
    substring("ABCDEDEDE", A, C, "DED"),
    writeln("koe9 success ", A, ",",C),
    fail.
    
koe9 :- writeln("koe9 end").

koe10 :- writeln("koe10"),
    concat(A, "xyz", "abcxyz"),
    writeln("koe10 success ", A),
    fail.
    
koe10 :- writeln("koe10"),
    concat("abc", B, "abcxyz"),
    writeln("koe10 success ", B),
    fail.
    
koe10 :- writeln("koe10 end").

koe11a :- writeln("koe11a"),
    str_char("A", X),
    write("koe11a success 'A' = "), writeq(X), nl,
    fail.

koe11a :- writeln("koe11a end").

koe11b :- writeln("koe11b"),
    str_char(X, 'A'),
    write("koe11b success 'A' = "), writeq(X), nl,
    fail.

koe11b :- writeln("koe11b end").

koe11c :- writeln("koe11c"),
    str_char("A", 'A'),
    write("koe11c success A = A"), nl,
    fail.

koe11c :- writeln("koe11c end").

koe11d :- writeln("koe11d"),
    str_char("A", 'B'),
    write("koe11d BAD success 'A' = 'B'"), nl,
    fail.

koe11d :- writeln("koe11d end").

koe11e :- writeln("koe11e"),
    str_char("", X),
    write("koe11e BAD success '' = "), writeq(X), nl,
    fail.

koe11e :- writeln("koe11e end").

koe11f :- writeln("koe11f"),
    str_char("AB", X),
    write("koe11f BAD success 'AB' = "), writeq(X), nl,
    fail.

koe11f :- writeln("koe11f end").

koe11g :- writeln("koe11g"),
    str_char(15, X),
    write("koe11g BAD success 15 = "), writeq(X), nl,
    fail.

koe11g:- writeln("koe11g end").

koe11h :- writeln("koe11h"),
    str_char(X, 42),
    write("koe11h BAD success 42 = "), writeq(X), nl,
    fail.

koe11h:- writeln("koe11h end").

koe11i :- writeln("koe11i"),
    str_char(X, Y),
    write("koe11i BAD success "), writeq(X), write(" = "), writeq(Y), nl,
    fail.

koe11i :- writeln("koe11i end").

koe12 :- writeln("koe12"),
    upper_lower("AbCd7@", A), writeln("  |", A, "|"),
    writeln("koe12 end"),!.
koe12 :- writeln("koe12 error").

koe13 :- writeln("koe13"),
    upper_lower(A, "AbCd7@"), writeln("  |", A, "|"),
    writeln("koe13 end"),!.
koe13 :- writeln("koe13 error").

koe14 :- writeln("koe14"),
    upper_lower("ABcd7@", "AbCd7@"), writeln("  Ok"),
    writeln("koe14 end"),!.
koe14 :- writeln("koe14 error").

koe14 :- writeln("koe14"),
    upper_lower("ABcd7@", "AbCd7@"), writeln("  Ok"),
    writeln("koe14 end"),!.
koe14 :- writeln("koe14 error").

koe15 :- writeln("koe15"),
    upper_lower("ABcd7@", "AbCd8@"), writeln("  Bad 1"), fail.
koe15 :- upper_lower("7", 7), writeln("  Bad 2"), fail.
koe15 :- upper_lower(7, "7"), writeln("  Bad 3"), fail.
koe15 :- upper_lower(_, _), writeln("  Bad 4"), fail.
koe15 :- writeln("koe15 end").

koe16 :- writeln("koe16"),
    /* Å Ä Ö PII */
    upper_lower("\xc5\xc4\xd6\u03a0", A), write("  |"), 
    writeq(A), writeln("|"),
    writeln("koe16 end").
koe16 :- writeln("koe16 error").
    
koe17 :- writeln("koe17"),
    /* Å Ä Ö PII */
    upper_lower("\xc5\xc4\xd6\u03a0", "\xE5\xE4\xF6\u03C0"), 
    writeln("koe17 Ok end").
koe17 :- writeln("koe17 error").

koe18 :- writeln("koe18"),
    writeln("  A: "), str_len("", A), writeln("    ", A), fail.
koe18 :- writeln("  B: "), 
    str_len("f", A), writeln("    ", A), fail.
koe18 :- writeln("  C: "), 
    str_len("d\u03a0", A), writeln("    ", A), fail.
koe18 :- writeln("  D: "), 
    str_len("muuttujanimi", A), writeln("    ", A), fail.
koe18 :- writeln("  E: "), 
    str_len("testi", 5), writeln("    Ok"), fail.
koe18 :- writeln("  F: "), 
    str_len("testi", 6), writeln("    Error"), fail.
koe18 :- writeln("  G: "), 
    str_len(testi, A), writeln("    Error: ", A), fail.
koe18 :- writeln("koe18 end").
    
% searchstring(SourceStr, SearchStr, Pos)

koe19z :- write("koe19z: "), searchstring("ABCDEF", "CDE", 1), !, write("bad Ok"), 
    nl.  % should fail 
koe19z :- write(" failed: Ok"), nl.

koe19 :- write("koe19: "), searchstring("ABCDEF", "CDE", 2), !, write("Ok"), 
    nl.  % should get "Ok" 
koe19 :- write(" failed"), nl.

koe19a :- write("koe19a: "), searchstring("ABCDEF", "CDE", 3), write("bad Ok"), 
    nl.  % should get "Ok" 
koe19a :- write(" failed: Ok"), nl.

koe19b :- write("koe19b: "), nl, searchstring("ABCDEF", "XYZ", Pos), 
    write("  Pos = ", Pos), nl, fail. 
koe19b :- write("  no more"), nl.

koe19c :- write("koe19c: "), nl, searchstring("ABCDEF", "CD", Pos), 
    write("  Pos = ", Pos), nl, fail. 
koe19c :- write("  no more"), nl.

koe19d :- write("koe19d: "), nl, searchstring("CDABCDECDFCD", "CD", Pos), 
    write("  Pos = ", Pos), nl, fail. 
koe19d :- write("  no more"), nl.

koe19e :- write("koe19e: "), nl, searchstring("ABCDCDCCEFCD", "CDC", Pos), 
    write("  Pos = ", Pos), nl, fail. 
koe19e :- write("  no more"), nl.

koe19f :- write("koe19f: "), nl, searchstring("ABC", "", Pos), 
    write("  Pos = ", Pos), nl, fail. 
koe19f :- write("  no more"), nl.

koe20 :- write("koe20: "), nl, searchchar("", 'x', Pos),
    write("  Pos = ", Pos), nl, fail. 
koe20 :- write("  no more"), nl.

koe20a :- write("koe20a: "), nl, searchchar("xaxxbx", 'x', Pos),
    write("  Pos = ", Pos), nl, fail. 
koe20a :- write("  no more"), nl.

koe20b :- write("koe20b: "), nl, searchchar("abc", C, -1),
    write("  C = '", C, "'"), nl, fail. 
koe20b :- write("  no more"), nl.

koe20c :- write("koe20c: "), nl, searchchar("abc", C, 0),
    write("  C = '", C, "'"), nl, fail. 
koe20c :- write("  no more"), nl.

koe20d :- write("koe20d: "), nl, searchchar("abc", C, 1),
    write("  C = '", C, "'"), nl, fail. 
koe20d :- write("  no more"), nl.

koe20e :- write("koe20e: "), nl, searchchar("abc", C, 2),
    write("  C = '", C, "'"), nl, fail. 
koe20e :- write("  no more"), nl.

koe20f :- write("koe20f: "), nl, searchchar("abc", C, 3),
    write("  C = '", C, "'"), nl, fail. 
koe20f :- write("  no more"), nl.

% Test main

:- koe1.
:- koe2.
:- koe3.
:- koe4a.
:- koe4b.
:- koe4c.
:- koe4d.
:- koe4e.
:- koe5.
:- koe6.
:- koe7.
:- koe7a.
:- koe7b.
:- koe8.
:- koe9.
:- koe10.
:- koe11a.
:- koe11b.
:- koe11c.
:- koe11d.
:- koe11e.
:- koe11f.
:- koe11g.
:- koe11h.
:- koe11i.
:- koe12.
:- koe13.
:- koe14.
:- koe15.
:- koe16.
:- koe17.
:- koe18.
:- koe19z.
:- koe19.
:- koe19a.
:- koe19b.
:- koe19c.
:- koe19d.
:- koe19e.
:- koe19f.
:- koe20.
:- koe20a.
:- koe20b.
:- koe20c.
:- koe20d.
:- koe20e.
:- koe20f.

