% member test
% ===========

koe(T, E, L) :- writeln(T, ": begin"), 
        writeln("  testing: ", E, " ", L), 
        member(E, L), 
        writeln("  SUCCESS: ", E, " ", L), fail. 
koe(T, _, _) :- writeln(T, ": end").

%------------
:- koe("koe1", 1, [3,1,4]).
:- koe("koe2", 5, [3,1,4]).
:- koe("koe3", 5, [3,1,X,4,Y]).
:- koe("koe4", 5, [3,1,X,4,Y|_]).
:- koe("koe5", 7, [7|X]).
:- koe("koe6", 1, [1]).
:- koe("koe7", 1, [0,1]).
:- koe("koe8", 1, [X]).
:- koe("koe9", 1, []).
:- koe("koe10", X, [Y]).
:- koe("koe11", Z, [3,1,X,4,Y|_]).
:- koe("koe12", 5, Z).
:- koe("koe13", X, Y).
:- koe("koe14", X, 15). % not list
:- L1 = L2, writeln("koe15: ", L1, " ", L2),
   member(5, L1),
   writeln("koe15a: X=", X, " L1=", L1, " L2=", L2),
   member(X, L2),
   writeln("koe15b: X=", X, " L1=", L1, " L2=", L2).

