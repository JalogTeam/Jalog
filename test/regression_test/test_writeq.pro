% writeq - test

% char
% compound
% integer
% list
% real
% string
% open variable


% koe1 expected: 


koe1 :- write("koe1: |"), writeq('A'), write('|'),nl.

koe2 :- write("koe2: |"), writeq(a('b', 'c')), writeq(d), write('|'),nl.

koe3 :- write("koe3: |"), writeq(1234567890), write('|'),nl.

koe4a :- write("koe4a: |"), writeq([]), write('|'),nl.
koe4b :- write("koe4b: |"), writeq(['a']), write('|'),nl.
koe4c :- write("koe4c: |"), writeq(['a','b']), write('|'), nl.
koe4d :- write("koe4d: |"), writeq(['c'|['d']]), write('|'), nl.
koe4e :- write("koe4e: |"), writeq(['e', 'f'|['g', 'h']]), write('|'), nl.
koe4f :- write("koe4f: |"), writeq(['c', []|['d']]), write('|'), nl.
koe4g :- write("koe4g: |"), writeq(['c', []|'d']), write('|'), nl.

koe5 :- write("koe5: |"), writeq(3.14159), write('|'), nl.

koe6a :- write("koe6a: |"), writeq(""), write('|') ,nl.
koe6b :- write("koe6b: |"), writeq("string"), write('|') ,nl.

koe7a :- write("koe7a: |"), writeq(A), write('|') ,nl.
koe7b :- write("koe7b: |"), A = B, writeq(A), write('|'), writeq(B), write('|'), nl.

koe10 :- write("koe10: |"), writeq('a', 'b'), write('|'), nl.

koe11 :- write("koe11: |"), writeq(), write('|'), nl.

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
:- koe4g.
:- koe5.
:- koe6a.
:- koe6b.
:- koe7a.
:- koe7b.
:- koe10.
:- koe11.
