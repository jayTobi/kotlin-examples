package collection

import strings.DEFAULT_ELEMENT_SEPARATOR
import strings.joinToString
import strings.joinToString as join //an alias can be used and sometimes must be used to avoid name collisions

//imports needed for using the top-level functions/properties because they reside in different package

fun main(args: Array<String>) {
    simpleCollectionCreation()
    callingTopLevelFunctions()
    selfDefinedExtensionFunction()
}

/**
 * This function uses kotlin stdlib to create different types of collections.
 *
 * Standard Java Collection classes are used (no special Kotlin implementation) so
 * they can easily be used in Java and Kotlin without casts or conversions.
 *
 * But they still have additional functionality implemented using Kotlin extension functions. (see later)
 */
fun simpleCollectionCreation() {
    val set = hashSetOf(1, 7, 53)
    val list = arrayListOf(1, 723, 42)
    val map = hashMapOf(1 to "one", 7 to "seven", 53 to "fifty-three")

    //string interpolation is used here for easier readability - Kotlin idiom see https://kotlinlang.org/docs/reference/idioms.html
    println("Type of variables: set=${set.javaClass}, list=${list.javaClass}, map=${map.javaClass}")

    //last() is an example for an extension function defined on any Iterable, e.g a Set in Java misses this method
    val lastSetElement = set.last()
    println("Last set element: $lastSetElement")
    val max = list.max()
    println("Max value in list: $max")
}

/**
 * Function for calling top-level function with different (and default) parameters.
 */
fun callingTopLevelFunctions() {
    val list = listOf(1, 3, 9, 11)
    println("Calling the top level function with all default parameters ${joinToString(list)}")
    println("Some named parameters provided ${joinToString(list, prefix = "[", postfix = "]")}")
    println("Using a different separator ${join(list, DEFAULT_ELEMENT_SEPARATOR)}")
    //DEFAULT_ELEMENT_SEPARATOR is a top level property
    //if the order of the parameter is kept, it's not necessary to provide the names
}

/**
 * Function calling an extension function that we directly assigned to Collection types.
 */
fun selfDefinedExtensionFunction() {
    val list = listOf(12, 44, 99, 9)
    println("List as string ${list.joinToString(separator = ": ")}")
    //same functionality as in collection.callingTopLevelFunctions, but the collection is no parameter
    //we added the function to the collection class itself
}