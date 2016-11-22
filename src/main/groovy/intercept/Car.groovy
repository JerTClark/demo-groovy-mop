package intercept

class Car implements GroovyInterceptable {

    def check() {System.out.println "check called";}
    def start() {System.out.println "start called";}
    def drive() {System.out.println "drive called";}

    def invokeMethod(String name, args) {
        System.out.println "Call to $name intercepted";
        if(name != "check") {
            System.out.println "Running filter...";
            Car.metaClass.getMetaMethod("check").invoke(this, null)
        }
        //get a metamethod to call
        def validMethod = Car.metaClass.getMetaMethod(name, args)

        if(validMethod != null) {
            validMethod.invoke(this, args)
        } else {
            Car.metaClass.invokeMethod(this, name, args)
        }
    }

    public static void main(String[] args) {
        Car car = new Car()
        car.start()
        car.drive()
        car.check()
        try {
            car.speed()
        } catch (Exception e) {
            println e.getMessage()
        }
    }
}