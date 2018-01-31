fun main(args: Array<String>) {
    simpleCollectionCreation()
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
    println("last set element: $lastSetElement")
    val max = list.max()
    println("max in list: $max")
}