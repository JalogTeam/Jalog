% save - test
% This file must be compatible to do_test_save.bat

% koe1 expected: 
normal_fact("shouldn't be saved").

:- dynamic("factclass1/1").
:- dynamic("factclass2/1", special).
:- dynamic("factclass3/1").

factclass1("class 1 first").
factclass1("class 1 second").

factclass2("class 2 first").
factclass2("class 2 second").

koe1 :- writeln("saving unnamed database"),
    trap(save("test_dir/savetest1.txt"), E, errorhandler("unnamed", E)),
    writeln("  unnamed database saved"),
    !, 
    writeln("saving special database"),
    trap(save("test_dir/savetest2.txt", special), E, 
        errorhandler("special", E)),
    writeln("  special database saved"),
    !.
koe1 :- write("  failed"), nl.

errorhandler(T, E) :- writedevice(screen), writeln("  saving database ", T, 
    " trapped: ", E).
    
:- koe1.
