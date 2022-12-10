use "qbootstrap" here

class App extends QLApp {

    list openedWindows

    method main() {
        # load windows, etc.

	async () -> {
	    while !areAllClosed() {}
            # when all closed
        }
    }

    method areAllClosed(this) {
	#TODO: do this method
    } 

}