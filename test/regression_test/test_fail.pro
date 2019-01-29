% fail - test

% koe1 expected: "AB"

koe1 :- write("A"), fail, write("*").
koe1 :- write("B").

% Test main

:- koe1.