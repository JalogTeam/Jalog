% fronttoken - test

% string



% fronttoken(Str_in, Str_token, Str_rest)
test1(S, R) :- writeq(S),  nl, fronttoken(S, T, R),
    write("token: "), writeq(T), nl,
    write("rest:  "), writeq(R), nl.
test1(_, "") :- writeln("test1 failed").

test2(S) :- S = "", test1(S, _), !.
test2(S) :- test1(S, R), !, test2(R).

koe1 :- write("koe1: "), S = "  funktori(256,  'x',f(3),\"hei\"  ", test2(S).

koe2 :- write("koe2: "), S = "   ", test2(S).

koe3 :- write("koe3: "), S = "", test1(S, _).

koe4 :- write("koe4: "), concat("funk", "tori(256", S), test2(S).
    
koe5 :- write("koe5: "), substring(":- I=1/1. /* sdkj sdf */ 15", 2, 23, S), 
    test2(S).
    
koe6 :- write("koe6: "), test2(" 3.141 + 2.8 ").
    
koe7 :- write("koe7: "), substring(":- I=1/1. /* sdkj sdf */ 15", 2, 16, S), 
    test2(S).

koe8 :- write("koe8: "), fronttoken("-B", "-", X), writeq(X),!,nl.
koe8 :- writeln("koe8 failed").

koe9 :- write("koe9: "), fronttoken("-B", "X", X), writeq(X, " -- ERROR"),!,nl.
koe9 :- writeln("koe9 failed -- OK").

koe10 :- write("koe10: "), fronttoken("-B", X, "B"), writeq(X),!,nl.
koe10 :- writeln("koe10 failed").

koe11 :- write("koe11: "), fronttoken("-B", X, "X"), writeq(X, " -- ERROR"),!,nl.
koe11 :- writeln("koe11 failed -- OK").

koe12 :- write("koe12: "), fronttoken(X, "-", "B"), writeq(X),!,nl.
koe12 :- writeln("koe12 failed").
   
:- koe1.
:- koe2.
:- koe3.
:- koe4.
:- koe5.
:- koe6.
:- koe7.
:- koe8.
:- koe9.
:- koe10.
:- koe11.
:- koe12.
