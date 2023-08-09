% frontstr - test

% string



% frontstr(N, Str_in, Str_string, Str_rest)
test1(N, S) :- writeq(S),  nl, frontstr(N, S, T, R),
    write("frontstr: "), writeq(T), nl,
    write("rest:  "), writeq(R), nl.
test1(_, _) :- writeln("test1 failed").
/*
test2(S) :- S = "", test1(S, _), !.
test2(S) :- test1(S, R), !, test2(R).
*/
koe1 :- write("koe1: "), S = "", test1(0, S).

koe2 :- write("koe2: "), S = "", test1(1, S).

koe3 :- write("koe3: "), S = "X", test1(0, S).

koe4 :- write("koe4: "), S = "X", test1(1, S).

koe5 :- write("koe5: "), S = "X", test1(2, S).

koe6 :- write("koe6: "), S = "XY", test1(0, S).

koe7 :- write("koe7: "), S = "XY", test1(1, S).

koe8 :- write("koe8: "), S = "XY", test1(2, S).

koe9 :- write("koe9: "), S = "XY", test1(3, S).

koe10 :- write("koe10: "), S = "  funktori(256,  'x',f(3),\"hei\"  ", 
        test1(30, S).

koe11 :- write("koe11: "), frontstr(3, "funktori", _, "ktori"), 
         !, writeln("success").
koe11 :- writeln("fail").

koe12 :- write("koe12: "), frontstr(3, "funktori", _, "ktoriX"), 
         !, writeln("bad success").
koe12 :- writeln("fail Ok").


koe13 :- write("koe13: "), frontstr(3, "funktori", "fun", _), 
         !, writeln("success").
koe13 :- writeln("fail").

koe14 :- write("koe14: "), frontstr(3, "funktori", "funk", _), 
         !, writeln("bad success").
koe14 :- writeln("fail Ok").


koe15 :- write("koe15: "), frontstr(3, "funktori", "fun", "ktori"), 
         !, writeln("success").
koe15 :- writeln("fail").

   
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
:- koe13.
:- koe14.
:- koe15.
