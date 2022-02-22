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

% Test main

:- koe1.
:- koe2.
:- koe3.
:- koe4a.
:- koe4b.
:- koe4c.
:- koe5.
:- koe6.
