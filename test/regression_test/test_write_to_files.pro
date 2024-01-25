% write_to_files - test
% This file must be compatible to do_test_write_to_files.bat

/*
koe1(Fname, Lines) :- writeln("| writing: ", Fname),
    trap(openwrite(output1, Fname), E, errorhandler("openwrite", E)),
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
:- koe1("test_dir/writetest3.txt", ["Line 1", "Line 2"]).

This should write
* to test_dir/writetest4.txt
4-Line1
4-Line2

* to test_dir/writetest5.txt
5-Line1

* to screen
Message to screen

*/
:-  openwrite(output1, "test_dir/writetest4.txt"),
    openwrite(output2, "test_dir/writetest5.txt"),
    writedevice(output1),
    writeln("4-Line1"),
    writedevice(output2),
    writeln("5-Line1"),
    writedevice(screen),
    writeln("Message to screen"),
    writedevice(output1),
    writeln("4-Line2"),
    closefile(output1),
    closefile(output2).
