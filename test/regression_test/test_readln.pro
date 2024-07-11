% readln - test

read_file(Fname) :- writeln("| reading: ", Fname),
    openread(input1, Fname),
    !, 
    writeln("  opened"),
    readdevice(input1),
    !,
    read_lines,
    writeln("  end_of_file"),
%    readdevice(keyboard),
    closefile(input1),
    writeln("  done").
    
read_file(_) :- writeln("  failed").

read_lines :-
    readln(Line),
    writeln("  |", Line, "|"),
    !,
    read_lines.
    
read_lines :- writeln("  EOF").

koe1 :- write("koe1: "), 
%    trap(read_file("file:testdata_consult_data_1.pro"), E, errorhandler(E)),
    trap(read_file("file:/x.pro"), E, errorhandler(E)),
    writeln("readln OK;").

errorhandler(E) :- writeln("  trapped: ", E).

:- koe1.
