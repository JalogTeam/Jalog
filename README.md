# Jalog

*An interpreter for a Prolog-like language with Java-like arithmetic written in Java*

Jalog makes artificial intelligence available to a wide variety of devices and applications.

**Version** 1.0.0 2020-08-25 First release

### Authors
Mikko Levanto<br>
Ari Okkonen
## Usage
### Stand-alone Jalog
<pre>
X:><b>java -jar jalog.jar</b> <i>Parameters</i>
<i>Parameters</i>: &lt;compiler_options> &lt;program_name> &lt;program_arguments>
  &lt;compiler_options>
      -v Show version information
  &lt;program_name> - complete file name - no default extensions
  &lt;program_arguments> - as the program needs them
</pre>
### Integrated Jalog
A Jalog program can be called from a Java program. The purpose of this arrangement is to facilitate using Java for the input and output including the user interface, and the Prolog like language for problem solving and planning purposes.

## Intended use

Jalog gives ability to use [Prolog](https://en.wikipedia.org/wiki/Prolog) style reasoning whereever [Java](https://en.wikipedia.org/wiki/Java_%28programming_language%29) can be used. Prolog helps the programmer to concentrate on the problem instead of the solution algorithm. Examples: finding routes, solving puzzles like Sudoku, ...

See examples in `test` directory for implemented features.

----------
### Version history
**1.0.0** 2020-08-25 First release

**0.4** 2019-08-06 Build system for jar file: build_jar.bat.

**0.3** 2019-07-24 Interface test completed and integrated to regression test.

**0.2** 2012-04-20 Minor corrections. Debug timing disabled.

**0.1** 2012-03-13 Initial version. Capable of solving Sudoku.
