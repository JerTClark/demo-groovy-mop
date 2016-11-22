package categories

/**
 * We create a <b>Category</b> called "StringUtil" and then <b>use</b> that
 * category in a use block, where its methods are readily available. Outside
 * of the use block, however, we will get an error if we try to use the toSSN
 * method.
 */
class StringUtil {
    def static toSSN(self) {
        if(self.size() == 9) {
            "${self[0..2]}-${self[3..4]}-${self[5..8]}"
        }
    }
}
use(StringUtil) {
    println "123456789".toSSN()
    println new StringBuilder("987654321").toSSN()
}

try {
    println "123456789".toSSN()
} catch (Exception e) {
    println e.getMessage()
}