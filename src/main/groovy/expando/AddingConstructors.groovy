package expando

/**
 * adding a constructor to Integer's ExpandoMetaClass that will accept a Calendar
 * instance as a parameter.
 */

Integer.metaClass.constructor << {Calendar calendar ->
    new Integer(calendar.get(Calendar.DAY_OF_YEAR))
}
/**
 * intercepting Integer's constructor
 */
Integer.metaClass.constructor = {int value ->
    println "Intercepting constructor"
    constructor = Integer.class.getConstructor(Integer.TYPE)
    constructor.newInstance(value)
}

println new Integer(100)
println new Integer(Calendar.instance)