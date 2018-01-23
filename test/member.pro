:- write("<"),t([]),write(">").
a. b.
member(H,[H|_]) :- !. a. member(H,[_|T]) :-
  member(H,T).c.
:- dump("testiout.dump").


