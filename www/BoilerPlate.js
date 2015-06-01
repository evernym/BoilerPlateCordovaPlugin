var BoilerPlate = function() {
};


// Call this to register for push notifications. Content of [options] depends on whether we are working with APNS (iOS) or GCM (Android)
BoilerPlate.prototype.register = function(successCallback, errorCallback, options) {
  if (errorCallback == null) { errorCallback = function() {}}

    if (typeof errorCallback != "function")  {
      console.log("BoilerPlate.register failure: failure parameter not a function");
      return
    }

    if (typeof successCallback != "function") {
      console.log("BoilerPlate.register failure: success callback parameter must be a function");
      return
    }

    cordova.exec(successCallback, errorCallback, "BoilerPlate", "register", [options]);
  };



  BoilerPlate.prototype.pushMessage = function (successCallback, errorCallback, options) {
    if (errorCallback == null) { errorCallback = function () { } }

      if (typeof errorCallback != "function") {
        console.log("BoilerPlate.register failure: failure parameter not a function");
        return
      }

      cordova.exec(successCallback, errorCallback, "BoilerPlate", "pushMessage", [options]);
    };


//-------------------------------------------------------------------

if(!window.plugins) {
  window.plugins = {};
}
if (!window.plugins.BoilerPlate) {
  window.plugins.BoilerPlate = new BoilerPlate();
}

if (typeof module != 'undefined' && module.exports) {
  module.exports = BoilerPlate;
}