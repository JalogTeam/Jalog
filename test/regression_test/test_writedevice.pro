% writedevice - test

% koe1 expected: 

koe1(Fname) :- writeln("| writing: ", Fname),
    trap(openwrite(output1, Fname), E, errorhandler("openwrite", E)),
    !, 
    writeln("  opened"),
    writedevice(WD1), write("  1: writedevice = "), writeq(WD1), nl,
    trap(writedevice(output1), E, errorhandler("writedevice", E)),
    !,
    writedevice(WD2), 
    writedevice(WD1), 
    write("  2: writedevice = "), writeq(WD2), nl,
    writedevice(WD3),
    write("  3: writedevice = "), writeq(WD3), nl,
    writeln("  success").

koe1 :- write("  failed").

errorhandler(T, E) :- writedevice(screen), writeln("  ", T, " trapped: ", E).
:- koe1("test_dir/writetest2.txt").
