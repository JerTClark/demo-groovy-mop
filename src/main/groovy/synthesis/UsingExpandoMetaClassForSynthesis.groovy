package synthesis

class PersonThree {
    def work() { "working" }
}

PersonThree.metaClass.invokeMethod = { String name, args ->
    System.out.println "intercepting call to $name"
    def method = PersonThree.metaClass.getMetaMethod(name, args)
    if(method) {
        method.invoke(delegate, args)
    } else {
        PersonThree.metaClass.invokeMissingMethod(delegate, name, args)
    }
}

PersonThree.metaClass.methodMissing = { String name, args ->
    def plays = ["Tennis", "Volleyball", "Basketball"]
    System.out.println("methodMissing called for $name")
    def methodInList = plays.find { it == name.split('play')[1] }
    if (methodInList) {
        def impl = { Object[] vargs ->
            "playing ${name.split('play')[1]}..."
        }
        /**
         * <b>future calls will use this</b>
         */
        PersonThree.metaClass."$name" = impl
        impl(args)

    } else {
        throw new MissingMethodException(name, Person.class, args)
    }

}

jack = new PersonThree()
println jack.work()
println jack.playTennis()
println jack.playBasketball()
println jack.playVolleyball()
try {
    println jack.playMinecraft()
} catch (Exception e) {
    println "${e.getMessage()}"
}
println jack.playTennis()
println jack.playBasketball()
println jack.playVolleyball()