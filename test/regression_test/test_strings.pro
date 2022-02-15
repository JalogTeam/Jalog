% strings - test

% string


% koe1 expected: 

% substring(Str_in,Pos,Len,Str_out)

koe1 :- write("koe1: "), substring("ABCDEF", 2, 3, A), write(A), write('|'),nl. % should get "CDE"

koe2 :- write("koe2: "), concat("ABC", "DEF", A), write(A), write('|'),nl. % should get "ABCCDE"
koe3 :- write("koe3.a: "), concat("ABC", "DEF", "ABCDXF"), write("Error"), !, 
    write('|'),nl. % should fail
koe3 :- write("Ok"), nl, fail.
koe3 :- write("koe3.b: "), concat("ABC", "DEF", "ABCDEF"), write("Ok"), 
    write('|'),nl. % should get "Ok"

koe4 :- write("koe4: "), substring("ABCDEF", 2, 3, "CDE"), write("Ok"), 
    write('|'),nl.  % should get "Ok" 

koe4a :- write("koe4a: "),
    substring("ABCDEFGH", 1, 6, A), % BCDEFG
    substring(A, 2, 3, "DEX"), write("Ok"), 
    write('|'),nl.  % should get "Ok" 

koe5 :- write("koe5: "), concat("ABC", "DEF", A), substring(A, 1, 4, "BCDE"), 
    write("Ok"), write('|'),nl.  % should get "Ok" 

koe6 :- write("koe6: "), concat("ABC", "DEF", A), substring(A, 1, 4, B),
    concat(B, A, "BCDEABCDEF"),
    write("Ok"), write('|'),nl.  % should get "Ok" 

% Test main

%:- koe1.
%:- koe2.
%:- koe3.
:- koe4a.
