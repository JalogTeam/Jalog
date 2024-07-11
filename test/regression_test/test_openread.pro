% openread - test

% koe1 expected: 

koe1(Fname) :- write("| reading: ", Fname), trap(
    openread("input1", Fname), E, errorhandler(E)),
    !, 
    writeln("  success").

koe1 :- write("  failed").

errorhandler(E) :- writeln("  trapped: ", E).
:- koe1("file:/x.pro").
:- koe1("file:/z.pro").