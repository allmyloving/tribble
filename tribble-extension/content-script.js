function getSelectedText() {
    var text = "";
    if (window.getSelection) {
        text = window.getSelection().toString();
    } else
    if (document.selection && document.selection.type != "Control") {
        text = document.selection.createRange().text;
    }
    return text;
}


function getLang() {
    if (navigator.languages != undefined) {
        return navigator.languages[0];
    } else {
        return navigator.language;
    }
}

$(document).ready(function(){
    document.addEventListener("dblclick", function() {
        var text = getSelectedText();
        var targetLang = getLang().substring(0, 2);

        console.log("lang ==> " + targetLang);
        console.log("selection=" + getSelectedText());

        $.ajax({
            async: false,
            type: 'GET',
            url: 'http://localhost:8080/translate/get',
            data: {
                target: targetLang,
                q: text
            },
            success: function(data, status) {
                console.log("Data: " + JSON.parse(data) + "\nStatus: " + status);
                var translation = JSON.parse(data);
                var res = translation.text;
                sourceLang = translation.lang.slice(0,2);
                console.log("translated: " + res);
                
            showSuccessPopup(res, function() {
//                        var userId = getUserId();
//                        console.log("before redirect ==> " + userId);
                        addTranslation(sourceLang, targetLang, text, res);
                        });
            }
            
            });

    });
});

function showSuccessPopup(text, addcallback){
	 bootbox.dialog({
            message: 'Translated text: ' + text,
            title: 'tribble',
            buttons: {
                close: {
                    label: 'Close',
                    className: 'btn-primary',
                    callback: function() {
                        return;
                    }
                },
                main: {
                    label: 'Add translation',
                    className: 'btn-success',
					callback: addcallback
                    }
                }
            }
        );
}

function getUserId(callback) {
    chrome.runtime.sendMessage({
        method: "getUserId"
    }, function(response) {
        //console.log("id from background: " + response.userId);
        userId = response.userId;


    });
}

function addTranslation(sourceLang, targetLang, source, target) {
    console.log("target: " + target);
    target="asdf";
        var userId = 1;
		  $.ajax({
            async: false,
            type: 'POST',
            url: 'http://localhost:8080/translate/create',
            data: {
                 userId: 1,
                sourceLang: sourceLang,
                targetLang: targetLang,
                source: source,
                target: target
            }}).success(function(data){
                       alert('added');
                       })
          .fail(function(data) {
            console.log(data);
                alert('you have to login first');
            });

}
