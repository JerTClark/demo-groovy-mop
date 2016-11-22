package categories

/**Similar to creating a category as in the other example, we can simply use the @Category annotation.*/
@Category(String)
class StringUtilAnnotated {
    def toSSN() {
        if(size() == 9) {
            "${this[0..2]}-${this[3..4]}-${this[5..8]}"
        }
    }
}
use(StringUtilAnnotated) {
    println "123456789".toSSN()
}