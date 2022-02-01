% strings - test

% string


% koe1 expected: 

% substring(Str_in,Pos,Len,Str_out)

koe1 :- write("koe1: "), substring("ABCDEF", 2, 3, A), write(A), write('|'),nl. % should get "CDE"

koe2 :- write("koe2: "), concat("ABC", "DEF", A), write(A), write('|'),nl. % should get "ABCCDE"
koe3 :- write("koe3: "), concat("ABC", "DEF", "ABCDEF"), write(A), write('|'),nl. % should get "ABCCDE"

% Test main

% :- koe1.
:- koe2.
:- koe3.
