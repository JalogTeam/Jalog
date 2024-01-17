% openwrite - test

% koe1 expected: 

koe1(Fname) :- writeln("| writing: ", Fname), trap(
    openwrite("output1", Fname), E, errorhandler(E)),
    !, 
    writeln("  success").

koe1 :- write("  failed").

errorhandler(E) :- writeln("  trapped: ", E).
:- koe1("test_dir/writetest.txt").
