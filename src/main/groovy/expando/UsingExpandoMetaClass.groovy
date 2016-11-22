package expando

/**
 * We've added a method called <b>getDaysFromNow</b> (a closure property to be exact)
 * to Number and thus, all subclasses of Number. In order for it to work with all subclasses
 * we have to cast the delegate to int when satisfying the Calendar class' add method.
 */
Number.metaClass.getDaysFromNow = { ->
    Calendar today = Calendar.instance
    today.add(Calendar.DAY_OF_MONTH, (int)delegate)
    today.time
}

byte b = 1
short s = 2
int i = 3
long l = 4
float f = 5
double d = 6

println b.daysFromNow
println s.daysFromNow
println i.daysFromNow
println l.daysFromNow
println f.daysFromNow
println d.daysFromNow