@file:JvmName("StringJoin")

//this defines the name for the created Java class
//must be placed before package declaration

package strings

//top level property
//without const getter will be generated for Java (var: get + set)
//with const it will be public static final field in the generated class
const val DEFAULT_ELEMENT_SEPARATOR = "/ "

//package declaration - no need for special folder layout as in Java

//this function without an encapsulating class is called a TOP LEVEL function
// (It will be created as a static function in a Java class (StringJoin see annotation above), so it can also be used in Java)
/**
 * (TOP LEVEL) function takes a Collection of elements and appends each element to a String that is returned.
 *
 * @param collection The collection with elements that will be appended one after another
 * @param separator Separator used between values, default value is "; " (used if parameter is not provided)
 * @param prefix Prefix used before the first element, i.e. the start of the String, default is "<"
 * @param postfix Postfix used after the first element, i.e. the end of the String, default is ">"
 */
@JvmOverloads  //annotation creates overloaded Java methods, because default values for parameters are not supported in Java
fun <T> joinToString(collection: Collection<T>,
                     separator: String = "; ", //variable name with type and default value
                     prefix: String = "<",
                     postfix: String = ">"): String { //: String at the end defines the return type of the function
    val result = StringBuilder(prefix) //no var needed - although val can't be reassigned the object can be altered

    //using destructuring declarations (unpack single composite value into multiple variables) and special withIndex method
    for ((index, element) in collection.withIndex()) {
        if (index > 0) {
            result.append(separator)
        }
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}