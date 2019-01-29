:- write("A1"),not(write("A2")),write("A3").
:- nl.
:- write("B1"),not(fail),write("B3").
:- nl.
:- write("C1"),not(not(write("C2"))),write("C3").
