% exit_50 - test

koe1 :- write("koe1: "), exit(50), write(" Not OK"), nl, !.
koe1 :- write("Failed!").

:- koe1.
