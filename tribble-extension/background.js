var bkg = chrome.extension.getBackgroundPage();

function login() {

  var email = document.getElementById('email').value;
  var password = document.getElementById('password').value;
  bkg.console.log(email);
  bkg.console.log(password);

  executeQuery(email, password).done(function(){
	alert(localStorage.userId);});
};

function executeQuery(email, password) {
  var r = $.Deferred;
  $.post("http://localhost:8080/user/auth", {
      email: email,
      password: password
    })
    .done(function(data) {
	  localStorage.userId = data;
      bkg.alert('success!!');
//	  chrome.storage.sync.set({'userId': data}, function() {
//          message('Credentials saved');
//        });
    })
    .fail(function() {
	  localStorage.userId = null;
      bkg.alert('failed');
    });
	setTimeout(function(){
		r.resolve();}, 2500);
	return r;
}

chrome.runtime.onMessage.addListener(function(request, sender, sendResponse) {
    if (request.method == "getUserId")
      sendResponse({userId: localStorage['userId']});
    else
      sendResponse({}); // snub them.
});

document.addEventListener('DOMContentLoaded', function() {
  var elem = document.getElementById('loginSubmit');
  if (elem != null) {
    elem.addEventListener("click", login)
    chrome.extension.getBackgroundPage().console.log('listener added');
  }
});
