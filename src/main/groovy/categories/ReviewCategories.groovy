package categories

class MyCategory {
    def static toPhoneNumber(String self) {
        if(self.size() == 10) {
            println "(${self[0..2]}) ${self[3..5]}-${self[6..9]}"
        } else println "${self} is not a valid phone number for this area"
    }
}

use(MyCategory) {
    def String stringNumber = "5551234567"
    def int intAreaCode = 555
    def int intFirstThree = 123
    def int intLastFour = 4567

    stringNumber.toPhoneNumber()
    "$intAreaCode$intFirstThree$intLastFour".toPhoneNumber()

}