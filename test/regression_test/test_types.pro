% types - test

% char
% compound
% integer
% list
% real
% string

koe11 :- writeq('A'), nl.
koe12 :- B = 'A', writeq(B),nl.
koe13 :- B = 'A' + 1, writeq(B), nl.

koe14 :- 'A' = 'B', write("error 'A' = 'B'"), nl, fail.
koe14 :- 'A' = 'A', write("'A' = 'A'"), nl.

koe15 :- 'A' = $41, write("'A' = $41"), nl.
koe16 :- 'A' > 'B', write("error 'A' > 'B'"), nl, fail.
koe16 :- 'A' < 'B', write("'A' < 'B'"), nl.

koe21 :- write(a(21)), nl.
koe22 :- A = a(22), write(A), nl.
koe23 :- a(A) = a(23), write(A), nl.
koe24 :- a(A) = a(B), B = 24, write(A), nl.

koe31 :- write(31), nl.
koe32 :- B = 32, write(B),nl.
koe33 :- B = 32 + 1, write(B), nl.

koe34 :- 34 = 35, write("error 34 = 35"), nl, fail.
koe34 :- 34 = 34, write("34 = 34"), nl.

koe35 :- 64 = $40, write("64 = $40"), nl.
koe36 :- 36 > 37, write("error 36 > 37"), nl, fail.
koe36 :- 36 < 37, write("36 < 37"), nl.

koe41 :- write([41,42]), nl.
koe42 :- A = [42,43], write(A), nl.
koe43 :- [A] = [43], write(A), nl.
koe44 :- [A] = [B], B = 44, write(A), nl.

koe51 :- write(5.1), nl.
koe52 :- B = 5.2, write(B),nl.
koe53 :- B = 5.2 + 1, write(B), nl.

koe54 :- 5.4 = 5.5, write("error 5.4 = 5.5"), nl, fail.
koe54 :- 5.4 = 5.4, write("5.4 = 5.4"), nl.

koe56 :- 5.6 > 5.7, write("error 5.6 > 5.7"), nl, fail.
koe56 :- 5.6 < 5.7, write("5.6 < 5.7"), nl.

koe61 :- writeq("A"), nl.
koe62 :- B = "A", writeq(B),nl.

koe64 :- "A" = "B", write("error \"A\" = \"B\""), nl, fail.
koe64 :- "A" = "A", write("\"A\" = \"A\""), nl.

koe65 :- "A" = "\u0041", write("\"A\" = \"\\u0041\""), nl.
koe66 :- "A" > "B", write("error \"A\" > \"B\""), nl, fail.
koe66 :- "A" < "B", write("\"A\" < \"B\""), nl.


% Test main

:- koe11, 
   koe12,
   koe13,
   koe14,
   koe15,
   koe16.
   
:- nl.
:- koe21.
:- koe22.
:- koe23.
:- koe24.

:- nl.
:- koe31.
:- koe32.
:- koe33.
:- koe34.
:- koe35.
:- koe36.

:- nl.
:- koe41.
:- koe42.
:- koe43.
:- koe44.

:- nl.
:- koe51.
:- koe52.
:- koe53.
:- koe54.
:- koe56.

:- nl.
:- koe61.
:- koe62.
:- koe64.
:- koe65.
:- koe66.
