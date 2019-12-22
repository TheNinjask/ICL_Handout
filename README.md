# ICL Project

## Project By

```
    Tiago Bica - nº 47207
```

## Important Notes

The Interpreter for the full language, the input below

```
let
    person1 = { name = "joe"; age = 22 }
    person2 = { name = "mary"; age = 5 }
    person3 = person1 + { tag = -2 }
in
    println person1.age + person2.age;
    println person3.tag + person3.age;
end;;
```

should be 

```
let
    person1 : struct = { name = "joe"; age = 22 }
    person2 : struct = { name = "mary"; age = 5 }
    person3 : struct = person1 + { tag = -2 }
in
    println person1.age + person2.age;
    println person3.tag + person3.age;
end;;
```

## Issues/Notes

*   Rework mechanics of boolean/references in compilation;
*   Rework how Type Checking works (due to many unneccessary? reavaluations);
*   Issue above might be false due to assignemnts and reassigments for references as example

## Pre Requirements

* Java (preferably 8)
* JavaCC
* Jasmin (assembler for the Java Virtual Machine)

## Building Project

To build this project, either run the build.bat (if on windows) or execute the following commands in this order:

*   `javacc Parser0.jj`
*   `javac *.java`

## Running Project

To run the project, simply call

*   `java Parser`

It can also presented some certain arguments:

| Flag | Description Of Value |
|:-:|:-|
|-i|The path for the input file to be used instead of user input|
|-o|The path for the output file of the compilation|
|-j|The path of the Jasmin.jar to be used additionaly for the compilation|

Extra Note: To leave the interpreter, you can type `quit();;` to leave.

## Running Your Compilation

To run your compilation if the flag -j was used, simply call

*   `java Main`

### Example

```
java Parser -j C:\Users\Utilizador\Documents\GitHub\BicaDB\folders\FCT\ICL\ICL_jasmin\jasmin-2.4\jasmin.jar -o test.j -i input.icl
```
