% file_str - test

read_file(Fname) :- writeln("| reading: ", Fname),
    trap(file_str(Fname, Content), E, writeln("  trapped: ", E)),
    writeq(Content), nl,
    writeln("  success").
    
read_file(_) :- writeln("  failed").

:- read_file("test_dir/testdata_consult_1.pro").
