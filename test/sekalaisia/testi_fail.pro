koe1 :-
  write(" koe1 "),fail.


koe2 :-
  write(" koe21 ").

koe2 :-
  write(" koe22 "),fail.

koe3 :- write("X1"),koe2,write("X3").


:- write("A1"),write("A2"),write("A3").
:- write("B1"),koe1,write("B3").
:- write("C1"),fail,write("C3").
:- write("D1"),koe2,write("D3"),fail.
:- write("E1"),koe3,write("E3"),fail.


