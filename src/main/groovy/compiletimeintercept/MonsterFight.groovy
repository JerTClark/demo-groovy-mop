package compiletimeintercept

/**
 * A goofy little class to demonstrate "InjectIsDead."
 * A monster is created, given a max health field, and attacked over and over.
 * The AST transformation tells the compiler to insert a call to the isDead
 * method within the getsHit method, causing the execution of this class to
 * evaluate whether or not the monster needs to be attacked any more or if its all
 * finally over (...until next time...).
 */
class Monster {

    String name
    int health = 10
    Monster(String name) {
        this.name = name
    }

    /**
     * This is the method we are injecting to the getsHit method.
     * It will be called from that method to see if the monster is dead.
     */
    def isDead() {
        if(health == 0) {
            println "${name} is dead"
            println "...GAME OVER..."
            System.exit(0);
        } else {
            println "${health} left!"
        }
    }

    def attack(damage) {println "${name} attacks dealing ${damage}!"}

    def heals(damage) {
        println "${name} heals ${damage} HP"
        health += damage
    }

    def getsHit(damage) {
        println "${name} receives ${damage}!"
        health -= damage
    }

}

godzilla = new Monster("Godzilla")
godzilla.attack(5)
godzilla.getsHit(6)
godzilla.heals(2)
godzilla.attack(8)
godzilla.getsHit(6)
/*He should be dead before this... (but he'll be back!)*/
println "Nuclear bomb drops!"
godzilla.getsHit(10)