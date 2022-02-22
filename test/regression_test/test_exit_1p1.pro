% exit_1 - test

koe1 :- write("koe1: "), exit(1+1), write(" Not OK"), nl, !.
koe1 :- write("Failed!").

:- koe1.
