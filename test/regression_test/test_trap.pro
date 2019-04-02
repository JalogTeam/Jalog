% trap - test

koe1 :- write("koe1: "),trap(exit(1), X, write("trapped ", X)), write(" OK"), nl, !.
koe1 :- write(" fail"), nl.

koe2 :- write("koe2: "),trap(write("writing "), X, write("trapped ", X)), 
    write(" OK"), nl, !.
koe2 :- write(" fail"), nl.

koe3 :- write("koe3: "),trap(fail, X, write("trapped ", X)), 
    write(" OK"), nl, !.
koe3 :- write(" fail"), nl.


:- koe1.
:- koe2.
:- koe3.
