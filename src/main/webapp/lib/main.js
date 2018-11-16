window.getCookie = function(name) {
  var match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
  if (match) return match[2];
}

window.setCookie = function(name, value) {
    document.cookie = name + "=" + value;
}

window.eraseCookie = function(name) {
    document.cookie = name+'=;';
}