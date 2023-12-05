% readdevice - test

% koe1 expected: 

koe1(Fname) :- write("| reading: ", Fname),
    trap(openread(input1, Fname), E, errorhandler("openread", E)),
    !, 
    writeln("  opened"),
    readdevice(RD1), write("  readdevice = "), writeq(RD1), nl,
    trap(readdevice(input1), E, errorhandler("readdevice", E)),
    !,
    readdevice(RD2), write("  readdevice = "), writeq(RD2), nl,
    writeln("  success").

koe1 :- write("  failed").

errorhandler(T, E) :- writeln("  ", T, " trapped: ", E).
:- koe1("file:/x.pro").
:- koe1("file:/z.pro").