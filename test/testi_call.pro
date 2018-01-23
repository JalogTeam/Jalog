koe :- write("X").
:- write("koe: A1"),koe,z_,write("A3"),write("A4"),fail,write("A5").
:- nl.
:- write("call: A1"),call(write("A2")),z_,write("A3"),write("A4"),fail,write("A5").


