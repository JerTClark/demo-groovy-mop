package meta
string = "Hello"
println "Does String respondTo toUpperCase()?"
println String.metaClass.respondsTo(string, "toUpperCase") ? "Yep" : "Nope"

println "Does String respondTo compareTo(String)?"
println String.metaClass.respondsTo(string, "compareTo", "aString") ? "Yep" : "Nope"

println "Does String respondTo toUpperCase(int)?"
println String.metaClass.respondsTo(string, "toUpperCase", 2) ? "Yep" : "Nope"