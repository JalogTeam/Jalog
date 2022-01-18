% strings - test

% string


% koe1 expected: 

% substring(Str_in,Pos,Len,Str_out)

koe1 :- write("koe1: "), substring("ABCDEF", 2, 3, A), write(A), write('|'),nl. % should get "CDE"

% Test main

:- koe1.
