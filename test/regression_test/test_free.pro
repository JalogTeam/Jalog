% free - test

% koe1 expected: "AB"

koe1 :- write("A"), free(_), write("ok").
koe2 :- nl, write("B"), free(2), write("#"),!.
koe2 :- write("ok").
koe3 :- nl, write("C"), A = 2, free(A), write("#"),!.
koe3 :- write("ok").
koe4 :- nl, write("D"), A = B, free(A), free(B), write("ok").

% Test main

:- koe1.
:- koe2.
:- koe3.
:- koe4.
