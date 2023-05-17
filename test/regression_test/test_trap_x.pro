% trap_x - test

koe1 :- write("koe1: "),trap_x(exit(1), X, write("trap_x_ed ", X)), write(" OK"), nl, !.
koe1 :- write(" fail"), nl.

koe2 :- write("koe2: "),trap_x(write("writing "), X, write("trap_x_ed ", X)), 
    write(" OK"), nl, !.
koe2 :- write(" fail"), nl.

koe3 :- write("koe3: "),trap_x(fail, X, write("trap_x_ed ", X)), 
    write(" OK"), nl, !.
koe3 :- write(" fail"), nl.

koe4sub(1).
koe4sub(2).
koe4sub(3).

koe4 :- writeln("koe4:"),trap_x(koe4sub(A), X, writeln("trap_x_ed ", X)), 
    writeln(" A=", A), fail.
koe4 :- write("koe4: done"), nl.

koe5 :- write("koe5: "),trap_x(exit(1), 2, write("trap_x_ed")), write(" OK"), nl, !.
koe5 :- write(" fail"), nl.

koe6 :- writeln("koe6:"),trap_x(exit(1), X, koe4sub(A)), 
    writeln(" A=", A), fail.
koe6 :- write("koe6: done"), nl.

koe7sub :- trap_x(exit(1), X, exit(2)), write(" koe7sub OK"), nl, !.
koe7sub :- write(" koe7sub fail"), nl.

koe7 :- write("koe7: "), trap(koe7sub, Y, write(" koe7 trapped Y=", Y)), !, writeln(" koe7:1 success").
koe7 :- write(" koe7 fail"), nl.


:- koe1.
:- koe2.
:- koe3.
:- koe4.
:- koe5.
:- koe6.
:- koe7.
