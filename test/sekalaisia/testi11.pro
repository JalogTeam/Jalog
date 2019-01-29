data(1).
data(2).
sano(A) :- writeln("data(", A, ").").
sano(A) :- writeln("2: data(", A, ").").
%:- data(A), writeln("data(", A, ")."), fail.
:- data(A), writeln("koe", A), assertz(koe), sano(A), fail.