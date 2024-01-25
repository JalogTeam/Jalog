% append_to_file - test
% This file must be compatible to do_test_append_to_file.bat

% koe1 expected: 

koe1(Fname, Lines) :- writeln("| writing: ", Fname),
    trap(openappend(output1, Fname), E, errorhandler("openappend", E)),
    !, 
    writeln("  opened"),
    writedevice(WD1), % get initial device
    trap(writedevice(output1), E, errorhandler("writedevice", E)),
    !,
    koe_write_lines(Lines),
    writedevice(WD1), 
    closefile(output1),
    writeln("  success").
koe1 :- write("  failed").

koe_write_lines([Line|RestLines]) :- 
    trap(writeln(Line), E, errorhandler("writeln", E)),
    !,
    koe_write_lines(RestLines).
koe_write_lines([]).
        
        

errorhandler(T, E) :- writedevice(screen), writeln("  ", T, " trapped: ", E).
:- koe1("test_dir/appendtest.txt", ["appended"]).
