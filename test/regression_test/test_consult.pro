% consult - test

koe1 :- write("koe1 a: "), data(X), write(' ', X), fail.
koe1 :- writeln(';'), fail.
koe1 :- write("koe1 b: "), consult("testdata_consult_1.pro"), writeln(';'),
        fail.
koe1 :- write("koe1 c: "), data(X), write(' ', X), fail.
koe1 :- writeln(';'), fail.
koe1 :- write("koe1 d: "), consult("test_dir/testdata_consult_1.pro"),
        writeln(';'), fail.
koe1 :- write("koe1 e: "), data(X), write(' ', X), fail.
koe1 :- writeln(';').

koe2 :- writeln("koe2"),
    trap(consult("..\\regression_test_material/consult_test_file.pro"), K,
        writeln("trapped: ", K)),
    writeln("consulted"), !.
koe2 :- writeln("koe2 failed").
    
:- koe1.

:- koe2.

