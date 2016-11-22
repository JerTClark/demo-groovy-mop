package intercept

class Review implements GroovyInterceptable {

    def useThisData(args) {
        args.each {System.out.println("methodOne printing: $it")}
    }

    def persistThisData(args) {
        args.each {
            System.out.println("methodTwo printing: $it")
        }
    }

    def before(methodName, args) {
        System.out.println("Before $methodName with args $args")
        args.collect {
            if(it instanceof String) it.toUpperCase()
            else if(it instanceof Number) ++it
        }
    }

    def after(methodName, args) {
        System.out.println("After $methodName with args $args")
        args.each {
            if(it instanceof String) System.out.println("Saving $it to StringsDB")
            else if(it instanceof Number) System.out.println("Saving $it to NumbersDB")
        }
    }

    def invokeMethod(String methodName, args) {
        System.out.println(methodName + " intercepted");
        def newArgs
        switch (methodName) {
            case "useThisData":
                newArgs = Review.metaClass.getMetaMethod("before", methodName, args).invoke(this, methodName, args)
                def realMethod = Review.metaClass.getMetaMethod(methodName, newArgs)
                if(realMethod != null) realMethod.invoke(this, newArgs)
                break;
            case "persistThisData":
                args = Review.metaClass.getMetaMethod("before", methodName, args).invoke(this, methodName, args)
                def actualMethod = Review.metaClass.getMetaMethod(methodName, args)
                if(actualMethod != null) actualMethod.invoke(this, args)
                Review.metaClass.getMetaMethod("after", methodName, args).invoke(this, methodName, args)
                break;
        }

    }
}

Review review = new Review()
review.useThisData(12.3, 9e3, "whatdas?")
println ""
review.persistThisData(20, "hello", 12)