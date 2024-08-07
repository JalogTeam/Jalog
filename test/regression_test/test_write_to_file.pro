% write_to_file - test
% This file must be compatible to do_test_write_to_file.bat

% koe1 expected: 

koe1(Fname, Lines) :- writeln("| writing: ", Fname),
    trap(openwrite(output1, Fname), E, errorhandler("openwrite", E)),
    !, 
    writeln("  opened"),
    writedevice(WD1), % get initial device
    trap(writedevice(output1), E, errorhandler("writedevice", E)),
    !,
    koe_write_lines(Lines),
    closefile(output1), % close BEFORE changing writedevice 
    writedevice(WD1), 
    writeln("  success").
koe1 :- write("  failed").

koe2(Fname, Lines) :- writeln("| writing: ", Fname),
    trap(openwrite(output1, Fname), E, errorhandler("openwrite", E)),
    !, 
    writeln("  opened"),
    writedevice(WD1), % get initial device
    trap(writedevice(output1), E, errorhandler("writedevice", E)),
    !,
    koe_write_lines(Lines),
    writedevice(WD1), 
    closefile(output1), % close AFTER changing writedevice 
    writeln("  success").
koe2 :- write("  failed").

koe_write_lines([Line|RestLines]) :- 
    trap(writeln(Line), E, errorhandler("writeln", E)),
    !,
    koe_write_lines(RestLines).
koe_write_lines([]).
        
        

errorhandler(T, E) :- writedevice(screen), writeln("  ", T, " trapped: ", E).

:- koe1("test_dir/writetest3.txt", ["Line 1", "Line 2"]).
:- koe2("test_dir/writetest3a.txt", ["Line 1a", "Line 2a"]).
