# Speaker Notes

##Intro
Gradle is buildsystem
It only consists of tasks doing stuff, everything else is how to get new tasks and how to configure them
order of evaluation is confusing

## 00 Syntax
Everything is a script
Groovy is magic
Kts is a bit less magic
But (almost) everything is an API you call


## 01 Phases
configuration phase runs always and should be kept short

this is also why tasks are evaluated lazily and you cannot to certain things

gradle starts from the task you want and then lazily configures tasks this task depends on 


## 02 Simple tasks
sometimes ad hoc tasks are fine, but for reusability or more complex things you should write a class

classes do not have to be inline as shown, they can be external java or whatever classes

what do we do if we have problems? more indirection!!!

could also call methods to configure task

## 03 Up-To-Date Tasks 

need ouput for up to date

can add manual handling




# Future Ideas


write your own plugins?

extensions


dependencies, configurations
