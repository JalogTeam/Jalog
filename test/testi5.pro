testi(A) :- data(A).
data(1).
data(2).
data(3).
data(5).
data(8).
%koe :- foreach_(A,[1,2,3]), data(A), write(A," "), fail.
%koe.
:- dump("testiout.dump").
%:- koe.
:- testi(A), writeln("TULOS: ", A, " ;"), fail.
