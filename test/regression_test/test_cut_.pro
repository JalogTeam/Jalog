% cut_ - test

% koe1 expected: 

twice(1).
twice(2).

koe1 :- write("|"), twice(A), write(A), fail. % 12
koe1 :- write("|"), twice(A), !, write(A), fail. % |1
koe1 :- write("|"), write(" cut malfunction! ").

koe2 :- koe1.
koe2 :- koe1.
koe2 :- write(" finally succeeds"), nl.

% Test main

:- koe2.