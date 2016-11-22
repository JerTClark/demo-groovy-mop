package compiletimeintercept

class CheckingAccount {
    def audit(amount) {if(amount > 10000) print "Auditing..."}
    def deposit(amount) {println "Depositing ${amount}..."}
    def withdraw(amount) {println "Withdrawing ${amount}..."}
}

def account = new CheckingAccount()
account.deposit(1000)
account.deposit(12000)
account.withdraw(11000)