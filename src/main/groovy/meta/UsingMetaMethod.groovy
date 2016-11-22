package meta

string = "Hello"
methodName = "toUpperCase"
//name may come from an input rather than being hardcoded
methodOfInterest = string.metaClass.getMetaMethod(methodName)
println methodOfInterest.invoke(string)