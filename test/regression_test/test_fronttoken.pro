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
    
    
:- koe1.
:- koe2.
:- koe3.
:- koe4.
:- koe5.
:- koe6.
:- koe7.
