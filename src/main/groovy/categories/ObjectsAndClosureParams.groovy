package categories

//using a closure with our category to filter what we want from a String
class FindUtil {
    def static extractOnly(String self, closure) {
        def result = ""
        self.each {
            if(closure(it)) {result += it}
        }
        result
    }
}

use(FindUtil) {
    println "1234567890".extractOnly({
        def asInt = Integer.valueOf(it)
        asInt % 2 == 0 && asInt != 0
    })
}