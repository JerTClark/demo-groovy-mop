package expando

Integer.metaClass {

    subtract = {value -> delegate - value}
    subtractToString = {value -> 
        "$delegate minus $value is ${delegate.subtract(value)}"
    }
    
    add = {value -> delegate + value}
    addToString = {value ->
        "$delegate plus $value is ${delegate.add(value)}"
    }

    multiplyBy = {value -> delegate * value}
    multiplyByToString = {value ->
        "$delegate multiplied by $value is ${delegate.multiplyBy(value)}"
    }

    divideBy = {value ->
        def double ans = delegate / value
        ans
    }
    divideByToString = {value ->
        "$delegate divided by $value is ${delegate.divideBy(value)}"
    }

    "static" {
        isEven = {value -> value % 2 == 0}
        isEvenToString = {value ->
            value % 2 == 0 ? "$value is even" : "$value is odd"
        }
    }
    
    constructor = {Calendar calendar ->
        new Integer(calendar.get(Calendar.DAY_OF_YEAR))
    }

    constructor = {int value ->
        println "Intercepting call to constructor"
        constructor = Integer.class.getConstructor(Integer.TYPE)
        constructor.newInstance(value)
    }
}

Integer integer = new Integer(Calendar.instance)
println integer.subtract(5)
println integer.subtractToString(5)
println integer.add(5)
println integer.addToString(5)
println integer.multiplyBy(5)
println integer.multiplyByToString(5)
println integer.divideBy(3)
println integer.divideByToString(3)
println Integer.isEven(integer)
println Integer.isEvenToString(integer)