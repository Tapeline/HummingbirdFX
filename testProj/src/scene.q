use "lang/qml" as qml
use "app" as app

class Scene {
  list objects
  object<app.Application> app
  
  constructor(me, app) {
    me.app = app
  }
  
  method add(me, obj) {
    me.objects.add(obj)
  }
  
  method draw(me, screen) {
    every obj in me.objects {
      obj.draw(me, screen)
    }
  }
}