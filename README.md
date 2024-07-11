
Jalog
=====

_An interpreter for a Prolog-like language with Java-like arithmetic written in Java_

Jalog differs significanly from ISO Standard Prolog.

> Prolog is a logic programming language associated with artificial intelligence and computational linguistics. [\[Wikipedia\]](https://en.wikipedia.org/wiki/Prolog)

Jalog makes traditional artificial intelligence techniques available to a wide variety of devices and applications.

**Version** 1.5 2024-07-11

[Other versions](../versions.html)

### Authors

Mikko Levanto  
[Ari Okkonen](https://www.linkedin.com/in/ariokkonen/) [ari.okkonen@gmail.com](mailto:ari.okkonen@gmail.com)

Download
--------

In order to use Jalog you need [Java runtime environment (JRE)](https://www.oracle.com/technetwork/java/javase/downloads/index.html).

Get Jalog interpreter: [Download JAR](jalog.jar)

New in 1.5
----------

The main addition is reading and writing of text files.

### New or improved predicates
* closefile(*SymbolicFileName*)
* consult(*Filename*)
* consult_data(*Filename*, *DatabaseName*)
* consult_dir(*ConsultDirname*)
* deletefile(*FileName*)
* dynamic(*PredicateIndicator*, *DatabaseName*)
* existfile(*FileName*)
* file_str(*FileName*, *StringVariable*)
* include(*Filename*)
* openappend(*SymbolicFileName*, *FileName*)
* openread(*SymbolicFileName*, *FileName*)
* openwrite(*SymbolicFileName*, *FileName*)
* readdevice(*SymbolicFileName*)
* readln(*StringVariable*)
* save(*FileName*)
* save(*FileName*, *DatabaseName*)
* subchar(*String*, *Position*, *Char*)
* term_str(*Domain*, *Term*, *String*)
* writedevice(*SymbolicFileName*)

Usage
-----

### Stand-alone Jalog

X:>**java -jar jalog.jar** <compiler\_options> <program\_name> <program\_arguments>

      <compiler_options>
          -v Show version information
          -r=name - Permits reading.
          -w=name - Permits writing.
          -m=name - Permits modifying, reading and writing.
          -a=name - Permits appending.
        The name can be a file name or a diretory name. If directory, refers to all
        files in the directory and subdirectories.
        The = character can be replaced with the : character.
        Multiple r, w, m, and a options are permitted.
      <program_name> - complete file name - no default extensions
      <program_arguments> - as the program needs them

This is shown when Jalog is run without parameters.

### Integrated Jalog

A Jalog program can be called from a Java program. The purpose of this arrangement is to facilitate using Java for the input and output including the user interface, and the Prolog like language for problem solving and planning purposes.

[Interface documentation](Jalog_class_doc.html)

Demo example with Java main program and called Jalog program:

[Jalog Sudoku Example](sudoku_demo/sudoku_example.html)

Demo examples about Android integration:

[Simple interfacing example](https://github.com/JalogTeam/Jalog_Android_MinimalDemo)  
[Android integration of the Sudoku example](https://github.com/JalogTeam/Jalog_Android_SudokuSolver)

Intended use
------------

Jalog gives ability to use [Prolog](https://en.wikipedia.org/wiki/Prolog) style reasoning whereever [Java](https://en.wikipedia.org/wiki/Java_%28programming_language%29) can be used. Prolog helps the programmer to concentrate on the problem instead of the solution algorithm. Examples: finding routes, solving puzzles like Sudoku, ...

See examples in [`test`](https://github.com/JalogTeam/Jalog/tree/master/test) directory for implemented features.

Jalog language
--------------

[Annotated animated execution example](Jalog_animation.html) - This shows step by step what happens when a Prolog program runs.

[Jalog Quick Reference](Jalog_quick_reference.html) - Built in predicates explained.

[Jalog Language Reference](Jalog_language_reference.html) - Lays out the elements and the syntax of the language.

Other resources
---------------

[Prolog material](https://cseweb.ucsd.edu/~goguen/courses/130w04/prolog.html) by [Prof. Joseph Goguen](https://cseweb.ucsd.edu/~goguen/)

License
-------

Jalog is distributed under [**The MIT License**](https://opensource.org/licenses/MIT)

Copyright (c) 2019 JalogTeam: Mikko Levanto, Ari Okkonen

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
"Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be included
in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

* * *

### Version history

**1.5** 2024-07-11 Added reading and writing of text files.

**1.4** 2023-09-07 More built-in predicates

**1.3** 2021-08-17 Integration to Android supported.

**1.1.0** 2021-02-02 More options for consult.

**1.0.0** 2020-08-25 First release

**0.4** 2019-08-06 Build system for jar file: build\_jar.bat.

**0.3** 2019-07-24 Interface test completed and integrated to regression test.

**0.2** 2012-04-20 Minor corrections. Debug timing disabled.

**0.1** 2012-03-13 Initial version. Capable of solving Sudoku.