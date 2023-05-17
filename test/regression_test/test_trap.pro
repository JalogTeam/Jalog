% trap - test

koe1 :- write("koe1: "),trap(exit(1), X, write("trapped ", X)), write(" OK"), nl, !.
koe1 :- write(" fail"), nl.

koe2 :- write("koe2: "),trap(write("writing "), X, write("trapped ", X)), 
    write(" OK"), nl, !.
koe2 :- write(" fail"), nl.

koe3 :- write("koe3: "),trap(fail, X, write("trapped ", X)), 
    write(" OK"), nl, !.
koe3 :- write(" fail"), nl.

koe4sub(1).
koe4sub(2).
koe4sub(3).

koe4 :- writeln("koe4:"),trap(koe4sub(A), X, writeln("trapped ", X)), 
    writeln(" A=", A), fail.
koe4 :- write("koe4: done"), nl.

koe5 :- write("koe5: "),trap(exit(1), 2, write("trapped")), write(" OK"), nl, !.
koe5 :- write(" fail"), nl.

koe7sub :- trap(exit(1), X, exit(2)), write(" koe7sub OK"), nl, !.
koe7sub :- write(" koe7sub fail"), nl.

koe7 :- write("koe7: "), trap(koe7sub, Y, write(" koe7 trapped Y=", Y)), !, writeln(" koe7:1 success").
koe7 :- write(" koe7 fail"), nl.

:- koe1.
:- koe2.
:- koe3.
:- koe4.
:- koe5.
:- koe7.
