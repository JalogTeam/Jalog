% not - test

successes :- write(" successes").
successes :- write(" successes backtracked").

fails :- write(" fails"), fail.

koe1 :- write("koe1 a: "), not(successes), write(" ERROR"), fail.
koe1 :- writeln(';'), fail.
koe1 :- write("koe1 b: "), not(fails), write(" OK"), fail.
koe1 :- writeln(';').

:- koe1.
