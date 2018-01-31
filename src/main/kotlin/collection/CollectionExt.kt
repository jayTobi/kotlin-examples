package collection

/**
 * Extension function "added" to all Collections.
 *
 * @param separator Separator used between values, default value is "; " (used if parameter is not provided)
 * @param prefix Prefix used before the first element, i.e. the start of the String, default is "<"
 * @param postfix Postfix used after the first element, i.e. the end of the String, default is ">"
 */
fun <T> Collection<T>.joinToString(separator: String = ", ",
                                   prefix: String = "(",
                                   postfix: String = ")"): String {
    val result = StringBuilder(prefix)

    //all visible methods and properties of the object can be used (private ones are still encapsulated)
    for ((index, element) in this.withIndex()) {
        if (index > 0) {
            result.append(separator)
        }
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}