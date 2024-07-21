# [smart-calculator-kotlin](https://github.com/javapda/smart-calculator-kotlin)
* based on the Hyperskill [Smart Calculator (Kotlin)](https://hyperskill.org/projects/88?track=18) project
* project [smart-calculator-kotlin](https://github.com/javapda/smart-calculator-kotlin) on github

## build and run
```
.\gradlew.bat clean build 
java -jar .\build\libs\smart-calculator-kotlin-0.0.1-SNAPSHOT-all.jar
# - or - 
#  java -cp  .\build\libs\smart-calculator-kotlin-0.0.1-SNAPSHOT-all.jar  com.javapda.contacts.ContactsKt
```


# resources
* [regex tester](https://www.freeformatter.com/regex-tester.html)

## Hyperskill
* [my profile](https://hyperskill.org/profile/615178637)
* [Troubleshooting: no tests have run](https://plugins.jetbrains.com/plugin/10081-jetbrains-academy/docs/troubleshooting-guide.html#no_tests_have_run)

## Solve in IDE?
### ERROR: Solve in IDE - java.io.Serializable
* Started in [Stage 2/2](https://hyperskill.org/projects/88/stages/487/implement) - getting the following error:
```courseignore
C:\Users\javap\Smart Calculator (Kotlin)\Smart Calculator (Kotlin)\task\src\calculator\Main.kt:3:54
Kotlin: Cannot access 'java.io.Serializable' which is a supertype of 'kotlin.String'. Check your module 
classpath for missing or conflicting dependencies
```
* Fix: go to Project Settings (ctrl-alt-shift-S) : go to Project tab and set the SDK properly (e.g. 17 java version "17.0.9")
### ERROR: Solve in IDE - Kotlin: [Internal Error] java.lang.NoSuchFieldError: FILE_HASHING_STRATEGY
* [Article](https://youtrack.jetbrains.com/issue/KTIJ-29067/JPS-NoSuchFieldError-FILEHASHINGSTRATEGY-caused-by-Maven-project-with-1.6)
  * FIX: upgrade Kotlin Compiler to 1.8 (from 1.6)
```courseignore
Kotlin: [Internal Error] java.lang.NoSuchFieldError: FILE_HASHING_STRATEGY
	at org.jetbrains.kotlin.jps.targets.KotlinJvmModuleBuildTarget.updateChunkMappings(KotlinJvmModuleBuildTarget.kt:357)
	at org.jetbrains.kotlin.jps.build.KotlinBuilder.doBuild(KotlinBuilder.kt:468)
	at org.jetbrains.kotlin.jps.build.KotlinBuilder.build(KotlinBuilder.kt:308)
	at org.jetbrains.jps.incremental.IncProjectBuilder.runModuleLevelBuilders(IncProjectBuilder.java:1609)
	at org.jetbrains.jps.incremental.IncProjectBuilder.runBuildersForChunk(IncProjectBuilder.java:1238)
	at org.jetbrains.jps.incremental.IncProjectBuilder.buildTargetsChunk(IncProjectBuilder.java:1389)
	at org.jetbrains.jps.incremental.IncProjectBuilder.buildChunkIfAffected(IncProjectBuilder.java:1203)
	at org.jetbrains.jps.incremental.IncProjectBuilder.buildChunks(IncProjectBuilder.java:971)
	at org.jetbrains.jps.incremental.IncProjectBuilder.runBuild(IncProjectBuilder.java:527)
	at org.jetbrains.jps.incremental.IncProjectBuilder.build(IncProjectBuilder.java:236)
	at org.jetbrains.jps.cmdline.BuildRunner.runBuild(BuildRunner.java:135)
	at org.jetbrains.jps.cmdline.BuildSession.runBuild(BuildSession.java:387)
	at org.jetbrains.jps.cmdline.BuildSession.run(BuildSession.java:212)
	at org.jetbrains.jps.cmdline.BuildMain$MyMessageHandler.lambda$channelRead0$0(BuildMain.java:211)
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136)
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635)
	at java.base/java.lang.Thread.run(Thread.java:842)

```
* Once switched to JDK 21 we ended up with the following:
```courseignore
Kotlin: [Internal Error] java.lang.NoSuchFieldError: Class com.intellij.openapi.util.io.FileUtil does not have member field 'gnu.trove.TObjectHashingStrategy FILE_HASHING_STRATEGY'
	at org.jetbrains.kotlin.jps.targets.KotlinJvmModuleBuildTarget.updateChunkMappings(KotlinJvmModuleBuildTarget.kt:357)
	at org.jetbrains.kotlin.jps.build.KotlinBuilder.doBuild(KotlinBuilder.kt:468)
	at org.jetbrains.kotlin.jps.build.KotlinBuilder.build(KotlinBuilder.kt:308)
	at org.jetbrains.jps.incremental.IncProjectBuilder.runModuleLevelBuilders(IncProjectBuilder.java:1609)
	at org.jetbrains.jps.incremental.IncProjectBuilder.runBuildersForChunk(IncProjectBuilder.java:1238)
	at org.jetbrains.jps.incremental.IncProjectBuilder.buildTargetsChunk(IncProjectBuilder.java:1389)
	at org.jetbrains.jps.incremental.IncProjectBuilder.buildChunkIfAffected(IncProjectBuilder.java:1203)
	at org.jetbrains.jps.incremental.IncProjectBuilder.buildChunks(IncProjectBuilder.java:971)
	at org.jetbrains.jps.incremental.IncProjectBuilder.runBuild(IncProjectBuilder.java:527)
	at org.jetbrains.jps.incremental.IncProjectBuilder.build(IncProjectBuilder.java:236)
	at org.jetbrains.jps.cmdline.BuildRunner.runBuild(BuildRunner.java:135)
	at org.jetbrains.jps.cmdline.BuildSession.runBuild(BuildSession.java:387)
	at org.jetbrains.jps.cmdline.BuildSession.run(BuildSession.java:212)
	at org.jetbrains.jps.cmdline.BuildMain$MyMessageHandler.lambda$channelRead0$0(BuildMain.java:211)
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1144)
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:642)
	at java.base/java.lang.Thread.run(Thread.java:1583)


```
* When you go to enter your solution in [Stage 4/4](https://hyperskill.org/projects/261/stages/1324/implement) 
you discover you must use the Hyperskill IDE integration. There is a button labeled "Solve in IDE" and
the "Code Editor" tab is disabled with tooltip text of _**"You can solve it only in IDE, no web-version available here"**_.
* Once the "Solve in IDE" button is clicked there is some interaction with IntelliJ IDEA
and two green labeled areas:
  1. IDE responding
  2. the JetBrains Academy plugin responding
* Issue?
```courseignore
FEEDBACK:
Failed to launch checking. <a href="reload_gradle">Reload Gradle project</a>. For more information, see <a href="https://plugins.jetbrains.com/plugin/10081-jetbrains-academy/docs/troubleshooting-guide.html#no_tests_have_run">the Troubleshooting guide</a>
```
### plugin path issue?
* when attempting to run a simple main() in the IDE connected to Hyperskill, the errors below came up. The
fix was to go into Project Settings (ctrl-alt-shift-S) and go to the Project tab. There the setting of the SDK was
incorrect - was pointing to a WSL version of the JDK. Once I changed it to "17 java version 17.0.9" things began to work.
```courseignore
Kotlin: Plugin classpath entry points to a non-existent location: /home/jkroub/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-scripting-jvm/1.8.20/51c8efbe177ebcaa89c82d01663c60060a120dd2/kotlin-scripting-jvm-1.8.20.jar

Kotlin: Plugin classpath entry points to a non-existent location: /home/jkroub/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-scripting-common/1.8.20/f19996e3a40658541fe2108c483fd3301c4a3416/kotlin-scripting-common-1.8.20.jar

Kotlin: Plugin classpath entry points to a non-existent location: /home/jkroub/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-stdlib/1.8.20/e72fc5e03ec6c064c678a6bd0d955c88d55b0c4a/kotlin-stdlib-1.8.20.jar

Kotlin: Plugin classpath entry points to a non-existent location: /home/jkroub/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-stdlib-common/1.8.20/5eddaaf234c8c49d03eebeb6a14feb7f90faca71/kotlin-stdlib-common-1.8.20.jar

Kotlin: Plugin classpath entry points to a non-existent location: /home/jkroub/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-script-runtime/1.8.20/c850771e723701f9d63dbcf641429c0f29290074/kotlin-script-runtime-1.8.20.jar
```

## Stage 8/8 : 
## Stage 7/8 : I’ve got the power
### Description
In the final stage, it remains to add operations: multiplication *, integer division / and parentheses (...). They have a higher priority than addition + and subtraction -.

Here is an example of an expression that contains all possible operations:
```
3 + 8 * ((4 + 3) * 2 + 1) - 6 / (2 + 1)
```
The result is 121.

A general expression can contain many parentheses and operations with different priorities. It is difficult to calculate such expressions if you do not use special methods. Fortunately, there is a fairly effective and universal solution, using a stack, to calculate the most general expressions.

### From infix to postfix

Earlier we processed expressions written in infix notation. This notation is not very convenient if an expression has operations with different priorities, especially when brackets are used. But we can use postfix notation, also known as Reverse Polish notation (RPN). In this notation, operators follow their operands. See several examples below.

### Infix notation 1:
```
3 + 2 * 4
```

### Postfix notation 1:
```
3 2 4 * +
```
### Infix notation 2:
```
2 * (3 + 4) + 1
```
### Postfix notation 2:
```
2 3 4 + * 1 +
```
To better understand the postfix notation, you can play with a [converter](https://www.calcont.in/Conversion/infix_to_postfix).

As you can see, in postfix notation operations are arranged according to their priority and parentheses are not used at all. So, it is easier to calculate expressions written in postfix notation.

You can use a stack (LIFO) to convert an expression from infix to postfix notation. The stack is used to store operators for reordering. Here are some rules that describe how to create an algorithm that converts an expression from infix to postfix notation.

1. Add operands (numbers and variables) to the result (postfix notation) as they arrive.
2. If the stack is empty or contains a left parenthesis on top, push the incoming operator on the stack.
3. If the incoming operator has higher precedence than the top of the stack, push it on the stack.
4. If the precedence of the incoming operator is lower than or equal to that of the top of the stack, pop the stack and add operators to the result until you see an operator that has smaller precedence or a left parenthesis on the top of the stack; then add the incoming operator to the stack.
5. If the incoming element is a left parenthesis, push it on the stack.
6. If the incoming element is a right parenthesis, pop the stack and add operators to the result until you see a left parenthesis. Discard the pair of parentheses.
7. At the end of the expression, pop the stack and add all operators to the result.

No parentheses should remain on the stack. Otherwise, the expression has unbalanced brackets. It is a syntax error.

### Calculating the result

When we have an expression in postfix notation, we can calculate it using another stack. To do that, scan the postfix expression from left to right:

* If the incoming element is a number, push it into the stack (the whole number, not a single digit!).
* If the incoming element is the name of a variable, push its value into the stack.
* If the incoming element is an operator, then pop twice to get two numbers and perform the operation; push the result on the stack.
* When the expression ends, the number on the top of the stack is a final result.

Here you can find [an example and additional explanations on postfix expressions](http://www.cs.nthu.edu.tw/~wkhon/ds/ds10/tutorial/tutorial2.pdf).

### Objectives
* Your program should support multiplication *, integer division / and parentheses (...). To do this, use infix to postfix conversion algorithm above and then calculate the result using stack.
* Do not forget about variables; they, and the unary minus operator, should still work.
* Modify the result of the /help command to explain all possible operators. You can write the output for the command in free form.
* The program should not stop until the user enters the /exit command.
* Note that a sequence of + (like +++ or +++++) is an admissible operator that should be interpreted as a single plus. A sequence of - (like -- or ---) is also an admissible operator and its meaning depends on the length. If a user enters a sequence of * or /, the program must print a message that the expression is invalid.
* **As a bonus**, you may add the power operator ^ that has a higher priority than * and /.
```
> 2^2
4
> 2*2^3
16
```
### Examples
The greater-than symbol followed by a space (>) represents the user input.
```
> 8 * 3 + 12 * (4 - 2)
48
> 2 - 2 + 3
3
> 4 * (2 + 3
Invalid expression
> -10
-10
> a=4
> b=5
> c=6
> a*2+b*3+c*(2+3)
53
> 1 +++ 2 * 3 -- 4
11
> 3 *** 5
Invalid expression
> 4+3)
Invalid expression
> /command
Unknown command
> /exit
Bye!
```
## Stage 6/8 : [Variables](https://hyperskill.org/projects/88/stages/491/implement)
### Description
Now, the calculator will be able to store the results of previous calculations. Do you have any idea how to do that? Of course! This can be achieved by introducing variables. Storing results in variables and then operating on them at any time is a very convenient function.

### Objectives
So, your program should support variables.

Go by the following rules for variables:

* We suppose that the name of a variable (identifier) can contain only Latin letters.
* A variable can have a name consisting of more than one letter.
* The case is also important; for example, n is not the same as N.
* The value can be an integer number or a value of another variable.
* It should be possible to set a new value to an existing variable.
* To print the value of a variable you should just type its name.

The example below shows how variables can be declared and displayed.
```
> n = 3
> m=4
> a  =   5
> b = a
> v=   7
> n =9
> count = 10
> a = 1
> a = 2
> a = 3
> a
3
```
Incorrect spelling or declaration of variables should also throw an exception with the corresponding message to the user:

* First, the variable is checked for correctness. If the user inputs an invalid variable name, then the output should be "Invalid identifier".
```
> a2a
Invalid identifier
> n22
Invalid identifier
```
* If a variable is valid but not declared yet, the program should print "Unknown variable".
```
> a = 8
> b = c
Unknown variable
> e
Unknown variable
```
* If an identifier or value of a variable is invalid during variable declaration, the program must print a message like the one below.
```
> a1 = 8
Invalid identifier
> n1 = a2a
Invalid identifier
> n = a2a
Invalid assignment
> a = 7 = 8
Invalid assignment
```
Please note that the program should print "Invalid identifier" if the left part of the assignment is incorrect. If the part after the "=" is wrong then use the "Invalid assignment". First we should check the left side.

Handle as many incorrect inputs as possible. The program must never throw an exception of any kind.

It is important to note, all variables must store their values between calculations of different expressions.

Do not forget about previously implemented commands: /help and /exit.

### Examples
The greater-than symbol followed by a space (>) represents the user input.
```
> a  =  3
> b= 4
> c =5
> a + b - c
2
> b - c + 4 - a
0
> a = 800
> a + b + c
809
> BIG = 9000
> BIG
9000
> big
Unknown variable
> /exit
Bye!
```
**Tip**: Think of your program as of a set of instructions to different cases. For example, if it's a command, you perform one set of actions, or if it's an assignment operation, then you perform other actions if it's an expression that needs calculation it's also another thing. Refactoring your program at this stage is not a bad idea!
## Stage 5/8 : [Error!](https://hyperskill.org/projects/88/stages/490/implement)
### Description
Now you need to consider the reaction of the calculator when users enter expressions in the wrong format. The program only knows numbers, a plus sign, a minus sign, and two commands. It cannot accept all other characters and it is necessary to warn the user about this.

### Objectives
* The program should print Invalid expression in cases when the given expression has an invalid format. If a user enters an invalid command, the program must print Unknown command. All messages must be printed without quotes. The program must never throw an exception.
* To handle incorrect input, you should remember that the user input that starts with / is a command, in other situations, it is an expression.

* Like before, /help command should print information about your program. When the command /exit is entered, the program must print Bye! , and then stop.
### Examples
The greater-than symbol followed by a space (>) represents the user input.
```
> 8 + 7 - 4
11
> abc
Invalid expression
> 123+
Invalid expression
> +15
15
> 18 22
Invalid expression
>
> -22
-22
> 22-
Invalid expression
> /go
Unknown command
> /exit
Bye!
```
## Stage 4/8 : [Add subtractions](https://hyperskill.org/projects/88/stages/489/implement)
### Description
Finally, we got to the next operation: subtraction. It means that from now on the program must receive the addition + and subtraction - operators as an input to distinguish operations from each other. It must support both unary and binary minus operators. Moreover, If the user has entered several same operators following each other, the program still should work (like Java or Python REPL). Also, as you remember from school math, two adjacent minus signs turn into a plus. Therefore, if the user inputs --, it should be read as +; if they input ----, it should be read as ++, and so on. The smart calculator ought to have such a feature.

Pay attention to the /help command, it is important to maintain its relevance depending on the changes (in the next stages too). You can write information about your program in free form, but the main thing is that it should be understandable to you and other users.

### Objectives
* The program must calculate expressions like these: 4 + 6 - 8, 2 - 3 - 4, and so on.
* Modify the result of the /help command to explain these operations.
* Decompose your program using functions to make it easy to understand and edit later.
* The program should not stop until the user enters the /exit command.
* If you encounter an empty line, do not output anything.
### Examples
The greater-than symbol followed by a space (>) represents the user input.
```
> 8
8
> -2 + 4 - 5 + 6
3
> 9 +++ 10 -- 8
27
> 3 --- 5
-2
> 14       -   12
2
> /exit
Bye!
```
## Stage 3/8 : [Count them all](https://hyperskill.org/projects/88/stages/488/implement)
### Description
In rare cases, we need to calculate the sum of only two numbers. Now it is time to teach the calculator to read an unlimited sequence of numbers. Also, let's take care of ourselves if after a while we want to remember what our program does. For this purpose, we'll introduce a new command /help to our calculator. Users who have first exposure to this program may use /help as well to know more about it!

### Objectives
* Add to the calculator the ability to read an unlimited sequence of numbers.
* Add a /help command to print some information about the program.
* If you encounter an empty line, do not output anything.
### Examples
The greater-than symbol followed by a space (>) represents the user input.
```
> 4 5 -2 3
10
> 4 7
11
> 6
6
> /help
The program calculates the sum of numbers
> /exit
Bye!
```
## Stage 2/8 : [2+2+](https://hyperskill.org/projects/88/stages/487/implement)
### Description
It is high time to improve the previous version of the calculator. What if we have many pairs of numbers, the sum of which we need to find? It will be very inconvenient to run the program every time. So then let's add a loop to continuously calculate the sum of two numbers. Be sure to have a safeword to break the loop. Also, It would be nice to think through situations where users enter only one number or do not enter numbers at all.

### Objectives
Write a program that reads two numbers in a loop and prints the sum in the standard output.
If a user enters only a single number, the program should print the same number. If a user enters an empty line, the program should ignore it.
When the command /exit is entered, the program must print "Bye!" (without quotes), and then stop.
### Examples
The greater-than symbol followed by a space (>) represents the user input.
```
> 17 9
26
> -2 5
3
>
> 7
7
> /exit
Bye!
```

## Stage 1/8 : [2+2](https://hyperskill.org/projects/88/stages/486/implement)
## Objective
Write a program that reads two integer numbers from the same line and prints their sum in the standard output. Numbers can be positive, negative, or zero.

## Example
The example below shows the input and the corresponding output. Your program should work in the same way. Do not add extra characters after the output!

The greater-than symbol followed by a space (> ) represents the user input. Notice that it's not the part of the input.
```
> 5 8
13
```