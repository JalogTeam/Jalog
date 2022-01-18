% write - test

% char
% compound
% integer
% list
% real
% string
% open variable


% koe1 expected: 


koe1 :- write("koe1: "), write('A'), write('|'),nl.

koe2 :- write("koe2: "), write(a('b', 'c')), write(d), write('|'),nl.

koe3 :- write("koe3: "), write(1234567890), write('|'),nl.

koe4a :- write("koe4a: "), write([]), write('|'),nl.
koe4b :- write("koe4b: "), write(['a']), write('|'),nl.
koe4c :- write("koe4c: "), write(['a','b']), write('|'), nl.
koe4d :- write("koe4d: "), write(['c'|['d']]), write('|'), nl.
koe4e :- write("koe4e: "), write(['e', 'f'|['g', 'h']]), write('|'), nl.
koe4f :- write("koe4f: "), write(['c', []|['d']]), write('|'), nl.

koe5 :- write("koe5: "), write(3.14159), write('|'), nl.

koe6a :- write("koe6a: "), write('|'), write(""), write('|') ,nl.
koe6b :- write("koe6b: "), write('|'), write("string"), write('|') ,nl.

koe7a :- write("koe7a: "), write('|'), write(A), write('|') ,nl.
koe7b :- write("koe7b: "), A = B, write('|'), write(A), write('|'), write(B), write('|'), nl.

koe10 :- write("koe10: "), write('a', 'b'), write('|'), nl.

koe11 :- write("koe11: "), write(), write('|'), nl.

% Test main

:- koe1.
:- koe2.
:- koe3.
:- koe4a.
:- koe4b.
:- koe4c.
:- koe4d.
:- koe4e.
:- koe4f.
:- koe5.
:- koe6a.
:- koe6b.
:- koe7a.
:- koe7b.
:- koe10.
:- koe11.
