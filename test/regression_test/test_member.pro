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
:- koe("koe5", 1, [1]).
:- koe("koe6", 1, [0,1]).
:- koe("koe7", 1, [X]).
:- koe("koe8", 1, []).
:- koe("koe9", X, [Y]).
:- koe("koe10", Z, [3,1,X,4,Y|_]).
:- koe("koe11", 5, Z).
:- koe("koe12", X, Y).


