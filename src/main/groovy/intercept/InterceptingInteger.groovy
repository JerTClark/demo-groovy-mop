package intercept

Integer.metaClass.invokeMethod = { String name, args ->
    System.out.println "Intercepted $name() on $delegate with ${args.size()} args";

    if(name == "toString") {
        System.out.println "You are forbidden to call any toString() methods".toUpperCase();
    } else {
        def validMethod = Integer.metaClass.getMetaMethod(name, args)
        if (validMethod == null) {
            try {
                Integer.metaClass.invokeMissingMethod(delegate, name, args)
            } catch (Exception e) {
                System.out.println e.getMessage();
            }
        } else {
            System.out.println "Pre-Filter";
            result = validMethod.invoke(delegate, args)
            System.out.println result;
            System.out.println "Post-Filter";
        }
    }
}

Integer myInteger = new Integer(64)
myInteger.doubleValue()
myInteger.compareTo(new Integer(13))
myInteger.toString()
1234.doSomethingTotallyAwesome()