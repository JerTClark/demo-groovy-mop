package access

/**
 * We can dynamically access the properties and methods of an object using
 * dot notation or by using the invokeMethod method (so easy with Groovy!)
 */
def printInfo(object, property, method) {
    userRequestedProperty = property
    userRequestedMethod = method

    println object."$userRequestedProperty"
    println object."$userRequestedMethod"()
    //or
    println object.invokeMethod(userRequestedMethod, null)
}

printInfo("Hello", "empty", "toLowerCase")