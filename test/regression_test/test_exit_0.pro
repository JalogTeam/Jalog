% exit_0 - test

koe1 :- write("koe1: "), exit(0), write(" Not OK"), nl, !.
koe1 :- write("Failed!").

:- koe1.
