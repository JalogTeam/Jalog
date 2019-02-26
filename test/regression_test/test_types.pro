% types - test

% char
% compound
% integer
% list
% real
% string

koe11 :- write('A'), nl.
koe12 :- B = 'A', write(B),nl.
koe13 :- B = 'A' + 1, write(B), nl.

koe14 :- 'A' = 'B', write("error 'A' = 'B'"), nl, fail.
koe14 :- 'A' = 'A', write("'A' = 'A'"), nl.

koe15 :- 'A' = $40, write("'A' = $40"), nl.
koe16 :- 'A' > 'B', write("error 'A' > 'B'"), nl, fail.


% Test main

:- koe11, 
   koe12,
   koe13,
   koe14,
   koe15,
   koe16.