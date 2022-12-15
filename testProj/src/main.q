use "Object/Base" as BaseObject
use "QML" as qml

class Image like BaseObject {
    string path
    object<qml.image.Image> image
    
    constructor(me, path) {
    	me.path = path
    	me.image = qml.image.loadImage(path)
    }
}

my_image = Image("./img.png")
