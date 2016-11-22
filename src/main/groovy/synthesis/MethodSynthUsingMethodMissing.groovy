package synthesis

class Person {
    def work() {"working"}
    def plays = ["Tennis", "Volleyball", "Basketball"]
    def methodMissing(String name, args) {
        def methodInList = plays.find {it == name.split('play')[1]}
        if(methodInList) {
            "playing ${name.split('play')[1]}"
        } else {
            throw new MissingMethodException(name, Person.class, args)
        }
    }
}

jack = new Person()
println jack.work()
println jack.playTennis()
println jack.playBasketball()
println jack.playVolleyball()
try {
    println jack.playMinecraft()
} catch (Exception e) {
    println "${e.getMessage()}"
}