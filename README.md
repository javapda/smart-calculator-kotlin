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
## Stage 7/8 : 
## Stage 6/8 : 
## Stage 5/8 : 
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