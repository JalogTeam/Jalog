% strings - test

% string


% koe1 expected: 

% substring(Str_in,Pos,Len,Str_out)

koe1 :- write("koe1: "), substring("ABCDEF", 2, 3, A), writeq(A),nl. % should get "CDE"

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

% Test main
/*
:- koe1.
:- koe2.
:- koe3.
:- koe4a.
:- koe4b.
:- koe4c.
:- koe5.
:- koe6.
:- koe7.
:- koe7a.
:- koe7b.
*/
:- koe8.
