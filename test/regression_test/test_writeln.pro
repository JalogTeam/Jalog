% writeln - test


% koe1 expected: 


koe1 :- write("koe1: "), writeln('A'), write('-'), nl.

koe2 :- write("koe2: "), writeln('a', 'b'), write('-'), nl.

koe3 :- write("koe3: "), writeln(), write('-'), nl.

% Test main

:- koe1.
:- koe2.
:- koe3.
