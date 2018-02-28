# kotlin-examples
Simple Kotlin (https://kotlinlang.org/) examples using gradle.


# Classes, Objects and interfaces
## Interfaces in Kotlin
Interfaces are similar to Java 8.
* can contain definitions of abstract methods
* implementations of non-abstract methods (default methods in Java 8+)
* can NOT contain any state

Definition starts with `interface`

```kotlin
  interface MyInterface { 
    fun bar()
    fun foo() {
      println("I'm an implementation, but I don't need a special keyword like default in Java")
    }
}
 ```
 This example provides 2 methods - `bar()` that must be implemented by all
 non-abstract classes implementing this interface and `foo()` with a default
 implementation that can be overridden.
 
 A class or object can implement one or more interfaces (using `:`), e.g. 
 
 ```kotlin
 class Child : MyInterface {
   override fun bar() {
     printfĺn("Hello child")
   }
 }
 ````
 
---
 
 **NOTE:** 
Kotlin replaces both `implements` and `extends` with the semicolon `:`.

---

The `override` modifier is similar to Java's `@Override` annotation but is **mandatory** to 
prevent accidentally overriding a later added method.

If a class implements two (or more) interfaces with the same implemented default method,
the compiler will throw an error - you have to explicitly define your own method then.

```kotlin
 class AnotherChild : MyInterface, Foo {
   //foo is default method in both interfaces implemented here
   override fun foo() {
     //calling the base/super methods
     super<MyInterface>.foo()
     super<Foo>.foo()
   }
 }
````

## Modifiers in base classes
In Java all methods that are not marked with `final` can be overridden in
any subclass. This can lead to problems in subclasses if the base behavior changes.

Therefore in Kotlin ALL methods are **final** by default.

If the creation of subclasses or overriding of methods should be possible
you have to declare them as `open`

```kotlin
open class Admin : User {
    fun delete() { /* */ } //final method that could not be overridden 
    open fun activate() { /* */ } //may be overridden in subclasses
    override fun deactivate() { /* */ }  //overrides an open function that is still open for further subclasses 
    //final override fun deactivate() { /* */ }  //prevent further overriding 
}
```

Overridden methods are open by default for all subclasses, if you don't
want your method to be overridden you have to add `final` to the method signature.

## Smart casts
Final classes allow a wide range of smart cast but some requirements must be met:
* only for variables that could not have changed after type check
* for a class this means the property must be `val` and doesn't have custom accessor 
* the property has to be final (default for properties)

Details and examples: Smart Cast: https://kotlinlang.org/docs/reference/typecasts.html

## Abstract classes 
A class may be declared `abstract` (this implies `open`)
so it can't be instantiated and normally contains abstract functions
that must be implemented.
```kotlin
abstract class Foo {
    abstract func implementMe()
    
    open fun iAmImplemented(){
        //do sth but you can override me
    }
    
    fun iAmClosed(){
        //doing sth but i am not open - you have to specify it
    }
}
```
## Access modifiers for classes and interfaces
| Modifier        | Member           | Note  |
| --------------- |------------------| ------|
| final     | can NOT be overridden  | default for class members  |
| open | CAN be overridden | should be specified explicitly |
| abstract | MUST be overridden | only in abstract classes; can NOT have an (default) implementation |
| override | overrides a member in superclass or interface | overridden members are open by default unless declared final |

**A member in an interface is always open; 
can’t be declared as final. 
It’s abstract if it has no body, but the keyword isn’t required.**

## Visibility 
The visibility modifiers are the same as in Java `public, protected, private`.

**If omitted it will be `public`** ! 

There is no package-private as in Java.
It is replaced by the `internal` modifier for better access control
because these classes are only visible in the compiled artifact (= module) which they belong to.
 
Kotlin allows top-level declarations (classes, function, etc.) to be private.

| Modifier        | Class member           | Top level declaration  |
| --------------- |------------------| ------|
| public (default modifier) | Visible everywhere | Visible everywhere |
| internal | Visible in a module | Visible in a module |
| protected | Visible in subclass | na |
| private | Visible in same class | Visible within same file |

**Note**: For compatibility issues with Java and lack of certain modifiers 
get compiled 'strange' (e.g. internal to public in bytecode) and might lead to 
leakage of members that become visible in Java but not in Kotlin, but those 
details are out of scope.

## Nested classes
Reference: https://kotlinlang.org/docs/reference/nested-classes.html

As in Java classes can be nested in each other, but in
Kotlin the inner class does NOT have access to the outer unless
explicitly defined.

In Java a class declared inside another class becomes an inner class 
and stores a reference to the outer class unless the inner is declared
as static.

In Kotlin it is the exact opposite. You don't have access
to the outer class by default (it's a nested not an inner class).
If it is needed the `inner` modifier must be used.
To access the outer class `this@OuterClass` must be used.

### Sealed classes 
Reference: https://kotlinlang.org/docs/reference/sealed-classes.html

Kotlin provides a way of representing restricted class 
hierarchies, when a value can have one of the types from 
a limited set, but cannot have any other type.

It is useful for when expressions and compiler safety in them.
You don't have to provide an else branch and if the sealed class
gets additional subclasses, the compiler can alarm you of the missing 
branch in whens.

```kotlin
sealed class Expr
data class Const(val number: Double) : Expr()
data class Sum(val e1: Expr, val e2: Expr) : Expr()
object NotANumber : Expr()
```
A sealed class is abstract by default, has a private constructor 
and can have abstract members / methods.

Subclasses of a sealed class could be placed anywhere, not necessarily
in the same file.

## Advanced constructors 
In Kotlin the handling of Constructors and initialization differs from Java.
You have **1 primary constructor**, declared outside the class body, used for convenient
object creation and one or more secondary constructors.

Primary constructor in the class header, before {}

The constructor keyword can be omitted If the primary constructor 
does not have any annotations or visibility modifiers
```kotlin
class Person(firstName: String) {
}
```
The primary constructor can NOT contain any code - init code must
be placed in special `init` blocks inside the class.

```kotlin
class InitOrderDemo(name: String) {
    val firstProperty = "First property: $name".also(::println)
    
    init {
        println("First initializer block that prints ${name}")
    }
}
```
Parameters in primary constructor can also be used for property initialisation. 

The normal use case where you need class properties that get initialized in the constructor you
can use the short syntax 
```kotlin
class Person(val firstName: String = "Joe", var lastName: String = "Doe", var age: Int) {
    // this will generate and initialize the properties and
    //the val props are read only and the var are mutable  
}
```

Important (default constructors for Java)
-------
If all primary constructor parameters have default values
an additional constructor with NO parameters using the default values is generated on compiling,
which is essential for Java libraries like JPA, Jackson, etc. and saves you a lot of problems later.



Super class constructors must also be called by the primary const.
```kotlin
class Admin(nickname: String) : Person(nickname) { /*  */ }
```

If no constructor is provided (no () after name), a default one is created.
If a super class has a default constructor you still have to call it
with empty parameters.
```kotlin
class Admin : Person() { /*  */ }
```

To make a constructor private and prevent instantiating your class
the following can be used:
```kotlin
class Admin private constructor() { /*  */ }
```
You could also use singleton `object`s or better top-level function,
to replace mostly utility classes with private constructors.
(But generally beware of singletons)

### Secondary constructors



## Properties in interfaces 

## Accessors: Getters and Setters, Backing fields

# Data classes