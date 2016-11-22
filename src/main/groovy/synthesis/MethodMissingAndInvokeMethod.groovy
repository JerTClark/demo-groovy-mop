package synthesis

class PersonTwo implements GroovyInterceptable {
    def work() {"working"}
    def plays = ["Tennis", "Volleyball", "Basketball"]
    def invokeMethod(String name, args) {
        System.out.println("intercepting call for $name")
        def method = metaClass.getMetaMethod(name, args)
        if(method) {
            method.invoke(this, args)
        } else {
            metaClass.invokeMethod(this, name, args)
        }
    }
    def methodMissing(String name, args) {
        System.out.println("methodMissing called for $name")
        def methodInList = plays.find {it == name.split('play')[1]}
        if(methodInList) {
            def impl = {Object[] vargs ->
                "playing ${name.split('play')[1]}..."
            }
            /**
             * <b>future calls will use this</b>
             */
            PersonTwo instance = this
            instance.metaClass."$name" = impl
            impl(args)

        } else {
            throw new MissingMethodException(name, Person.class, args)
        }
    }
}


jack = new PersonTwo()
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
try {
    println jack.playMinecraft()
} catch (Exception e) {
    println "${e.getMessage()}"
}