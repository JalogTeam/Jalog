% operators - test

% koe1 expected: 

koe1 :- write(3 + 2), writeln.
koe2 :- A = 3 + 4, write(A), nl.
koe3 :- A = 1.2 + 3.4, write(A), nl.
koe4 :- A = 1.2 + 3, write(A), nl.
koe5 :- A = 1 + 3.4, write(A), nl.
koe6 :- A = $7FFFFFFFFFFFFFFF + 1, write(A), nl.

koe11 :- A = 5 - 2, write(A), nl.
koe12 :- A = 2 - 5, write(A), nl.
koe13 :- A = 6.3 - 4.8, write(A), nl.
koe14 :- A = 8.14 - 12, write(A), nl.

koe21 :- A = 5 * 2, write(A), nl.
koe22 :- A = 2 * (-5), write(A), nl.
koe23 :- A = 6.3 * 4.8, write(A), nl.
koe24 :- A = 8.14 * 12, write(A), nl.

koe31 :- A = 5 / 2, write(A), nl.
koe32 :- A = 2 / (-5), write(A), nl.
koe33 :- A = 6.3 / 4.8, write(A), nl.
koe34 :- A = 8.14 / 12, write(A), nl.

koe41 :- A = 5 div 2, write(A), nl.
koe42 :- A = 2 div (-5), write(A), nl.
koe43 :- A = 6.3 div 4.8, write(A), nl.
koe44 :- A = 8.14 div 3, write(A), nl.

koe51 :- A = 5 mod 2, write(A), nl.
koe52 :- A = 2 mod (-5), write(A), nl.
koe53 :- A = 6.3 mod 4.8, write(A), nl.
koe54 :- A = 8.14 mod 3, write(A), nl.

koe61 :- A = mod_(5, 2), write(A), nl.

% Test main

:- koe1, 
   koe2,
   koe3,
   koe4,
   koe5,
   koe6,
   nl,
   koe11,
   koe12,
   koe13,
   koe14,
   nl,
   koe21,
   koe22,
   koe23,
   koe24,
   nl,
   koe31,
   koe32,
   koe33,
   koe34,
   nl,
   koe41,
   koe42,
   koe43,
   koe44,
   nl,
   koe51,
   koe52,
   koe53,
   koe54,
   nl,
   koe61.