var app = {
  "debug": true,
  "log": function(message){if(app.debug){console.log(message)}}
}

window.getCookie = function(name) {
  var match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
  if (match) return match[2];
}

window.setCookie = function(name, value) {
    document.cookie = name + "=" + value;
}

window.eraseCookie = function(name) {
    document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}

window.copyObject = function(src) {
  let target = {};
  for (let prop in src) {
    if (src.hasOwnProperty(prop)) {
      target[prop] = src[prop];
    }
  }
  return target;
}