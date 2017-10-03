package cc

import org.sonarlint.cli.Main

class App {
    String getGreeting() {
        return 'Hello world.'
    }

    static void main(String[] args) {
        println new App().greeting
        println ">>>"
        Main.main(args)
    }
}
