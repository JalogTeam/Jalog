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

koe1x1 :- is_char('z'), write("'z' is char"), nl.
koe1x2 :- is_char(A), write("error A is not char"), nl.
koe1x2 :- write("A is not char"), nl.
koe1x3 :- is_char("z"), write("error \"z\" is not char"), nl.
koe1x3 :- write("\"z\" is not char"), nl.


koe21 :- write(a(21)), nl.
koe22 :- A = a(22), write(A), nl.
koe23 :- a(A) = a(23), write(A), nl.
koe24 :- a(A) = a(B), B = 24, write(A), nl.

koe2x1 :- is_compound(a(21)), write("a(21) is compound"), nl.
koe2x2 :- is_compound(A), write("error A is not compound"), nl.
koe2x2 :- write("A is not compound"), nl.
koe2x3 :- is_compound([a,b]), write("error [a,b] is not compound"), nl.
koe2x3 :- write("[a,b] is not compound"), nl.
koe2x4 :- is_compound(a), write("a is compound"), nl.

koe31 :- write(31), nl.
koe32 :- B = 32, write(B),nl.
koe33 :- B = 32 + 1, write(B), nl.

koe34 :- 34 = 35, write("error 34 = 35"), nl, fail.
koe34 :- 34 = 34, write("34 = 34"), nl.

koe35 :- 64 = $40, write("64 = $40"), nl.
koe36 :- 36 > 37, write("error 36 > 37"), nl, fail.
koe36 :- 36 < 37, write("36 < 37"), nl.
koe37 :- 38 >= 37, write("38 >= 37"), nl.
koe38 :- 36 <= 37, write("36 <= 37"), nl.
koe39a :- 36 <> 37, write("36 <> 37"), nl.
koe39b :- 36 >< 37, write("36 >< 37"), nl.
koe39c :- 36 != 37, write("36 != 37"), nl.

koe3x1 :- is_integer(1), write("1 is integer"), nl.
koe3x2 :- is_integer(A), write("error A is not integer"), nl.
koe3x2 :- write("A is not integer"), nl.
koe3x3 :- is_integer(1.5), write("error 1.5 is not integer"), nl.
koe3x3 :- write("1.5 is not integer"), nl.
/*
koe3x1 :- is_integer(1), write("1 is integer"), nl.
koe3x2 :- is_integer(A), write("error A is not integer"), nl.
koe3x2 :- write("A is not integer"), nl.
koe3x3 :- is_integer(1.5), write("error 1.5 is not integer"), nl.
koe3x3 :- write("1.5 is not integer"), nl.

koe3x1 :- is_integer(1), write("1 is integer"), nl.
koe3x2 :- is_integer(A), write("error A is not integer"), nl.
koe3x2 :- write("A is not integer"), nl.
koe3x3 :- is_integer(1.5), write("error 1.5 is not integer"), nl.
koe3x3 :- write("1.5 is not integer"), nl.

koe3x1 :- is_integer(1), write("1 is integer"), nl.
koe3x2 :- is_integer(A), write("error A is not integer"), nl.
koe3x2 :- write("A is not integer"), nl.
koe3x3 :- is_integer(1.5), write("error 1.5 is not integer"), nl.
koe3x3 :- write("1.5 is not integer"), nl.
*/
koe41 :- write([41,42]), nl.
koe42 :- A = [42,43], write(A), nl.
koe43 :- [A] = [43], write(A), nl.
koe44 :- [A] = [B], B = 44, write(A), nl.

koe51 :- write(5.1), nl.
koe52 :- B = 5.2, write(B),nl.
koe53 :- B = 5.2 + 1, write(B), nl.
koe54 :- 5.4 = 5.5, write("error 5.4 = 5.5"), nl, fail.
koe54 :- 5.4 = 5.4, write("5.4 = 5.4"), nl.
koe56 :- 3.6 > 3.7, write("error 3.6 > 3.7"), nl, fail.
koe56 :- 3.6 < 3.7, write("3.6 < 3.7"), nl.
koe57 :- 38 >= 3.7, write("38 >= 3.7"), nl.
koe58 :- 3.6 <= 3.7, write("38 >= 3.7"), nl.
koe59 :- 3.6 <> 3.7, write("3.6 <> 3.7"), nl.

koe61 :- writeq("A"), nl.
koe62 :- B = "A", writeq(B),nl.

koe64 :- "A" = "B", write("error \"A\" = \"B\""), nl, fail.
koe64 :- "A" = "A", write("\"A\" = \"A\""), nl.

koe65 :- "A" = "\u0041", write("\"A\" = \"\\u0041\""), nl.
koe66 :- "A" > "B", write("error \"A\" > \"B\""), nl, fail.
koe66 :- "A" < "B", write("\"A\" < \"B\""), nl.


% Test main

:- write("koe11: "), koe11. 
:- write("koe12: "), koe12.
:- write("koe13: "), koe13.
:- write("koe14: "), koe14.
:- write("koe15: "), koe15.
:- write("koe16: "), koe16.
:- write("koe1x1: "), koe1x1.
:- write("koe1x2: "), koe1x2.
:- write("koe1x3: "), koe1x3.
   
:- nl.
:- write("koe21: "), koe21.
:- write("koe22: "), koe22.
:- write("koe23: "), koe23.
:- write("koe24: "), koe24.
:- write("koe2x1: "), koe2x1.
:- write("koe2x2: "), koe2x2.
:- write("koe2x3: "), koe2x3.
:- write("koe2x4: "), koe2x4.

:- nl.
:- write("koe31: "), koe31.
:- write("koe32: "), koe32.
:- write("koe33: "), koe33.
:- write("koe34: "), koe34.
:- write("koe35: "), koe35.
:- write("koe36: "), koe36.
:- write("koe37: "), koe37.
:- write("koe38: "), koe38.
:- write("koe39a: "), koe39a.
:- write("koe39b: "), koe39b.
:- write("koe39c: "), koe39c.
:- write("koe3x1: "), koe3x1.
:- write("koe3x2: "), koe3x2.
:- write("koe3x3: "), koe3x3.

:- nl.
:- write("koe41: "), koe41.
:- write("koe42: "), koe42.
:- write("koe43: "), koe43.
:- write("koe44: "), koe44.

:- nl.
:- write("koe51: "), koe51.
:- write("koe52: "), koe52.
:- write("koe53: "), koe53.
:- write("koe54: "), koe54.
:- write("koe56: "), koe56.
:- write("koe57: "), koe57.
:- write("koe58: "), koe58.
:- write("koe59: "), koe59.

:- nl.
:- write("koe61: "), koe61.
:- write("koe62: "), koe62.
:- write("koe64: "), koe64.
:- write("koe65: "), koe65.
:- write("koe66: "), koe66.
:- nl.
