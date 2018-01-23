koe1 :-
  write(" koe1 "),exit(1).


koe2 :-
  write(" koe21 ").

koe2 :-
  write(" koe22 "),exit(2).

koe3 :- write("X1"),trap(koe2,B,write("{",B,"}")),write("X3").


:- write("A1"),trap(write("A2"),B,write("[",B,"]")),write("A3"),write("A4"),fail,write("A5").
:- write("B1"),trap(koe1,B,write("[",B,"]")),write("B3").
:- write("C1"),trap(fail,B,write("[",B,"]")),write("C3").
:- write("D1"),trap(koe2,B,write("[",B,"]")),write("D3"),fail.
:- write("E1"),trap(koe3,B,write("[",B,"]")),write("E3"),fail.


