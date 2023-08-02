% frontchar - test

% string



% frontchar(Str_in, Char, Str_rest)
test1(S, R) :- writeq(S),  nl, frontchar(S, C, R),
    write("char: "), writeq(C), nl,
    write("rest:  "), writeq(R), nl.
test1(_, "") :- writeln("test1 failed").

test2(S) :- S = "", test1(S, _), !.
test2(S) :- test1(S, R), !, test2(R).

koe1 :- write("koe1: "), S = " \"hei\" ", test2(S).

koe3 :- write("koe3: "), S = "", test1(S, _).

koe4 :- write("koe4: "), concat("fu", "nk", S), test2(S).
    
koe5 :- write("koe5: "), substring(":- I=1/1. /* sdkj sdf */ 15", 2, 3, S), 
    test2(S).

koe6 :- write("koe6: "), frontchar("AB", 'A', X), writeq(X),!,nl.
koe6 :- writeln("koe6 failed").

koe7 :- write("koe7: "), frontchar("AB", 'X', X), writeq(X, " -- ERROR"),!,nl.
koe7 :- writeln("koe7 failed -- OK").

koe8 :- write("koe8: "), frontchar("AB", X, "B"), writeq(X),!,nl.
koe8 :- writeln("koe8 failed").

koe9 :- write("koe9: "), frontchar("AB", X, "X"), writeq(X, " -- ERROR"),!,nl.
koe9 :- writeln("koe9 failed -- OK").

koe10 :- write("koe10: "), frontchar(X, 'A', "B"), writeq(X),!,nl.
koe8 :- writeln("koe10 failed").

:- koe1.
:- koe3.
:- koe4.
:- koe5.
:- koe6.
:- koe7.
:- koe8.
:- koe9.
:- koe10.
