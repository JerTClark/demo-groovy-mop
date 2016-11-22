package intercept

CarMeta.metaClass.invokeMethod = {String name, args ->
    System.out.println "Call to $name intercepted in Car MetaClass invokeMethod()";

    if(name != "check") {
        System.out.println "Running filter...";
        CarMeta.metaClass.getMetaMethod("check").invoke(delegate, null)
    }

    def validMethod = CarMeta.metaClass.getMetaMethod(name, args)
    if(validMethod != null) {
        validMethod.invoke(delegate, args)
    } else {
        CarMeta.metaClass.invokeMissingMethod(delegate, name, args)
    }
}

CarMeta carMeta = new CarMeta()

carMeta.start()
carMeta.drive()
carMeta.check()