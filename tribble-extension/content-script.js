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
                console.log("Data: " + data + "\nStatus: " + status);
                var translation = JSON.parse(data).data.translations[0];
                res = translation.translatedText;
                sourceLang = translation.detectedSourceLanguage;
                console.log("translated: " + res);
            }
        });

		showPopup(function() {
//                        var userId = getUserId();
//                        console.log("before redirect ==> " + userId);
                        addTranslation(sourceLang, targetLang, text, res).done(function() {
                            alert('done, check db');
                        });});
//        bootbox.dialog({
//            message: 'Translated text: ' + res,
//            title: 'tribble',
//            buttons: {
//                main: {
//                    label: 'Add translation',
//                    className: 'btn-success',
//                    callback: function() {
////                        var userId = getUserId();
////                        console.log("before redirect ==> " + userId);
//                        addTranslation(sourceLang, targetLang, text, res).done(function() {
//                            alert('done, check db');
//                        });
//                    }
//                },
//                close: {
//                    label: 'Close',
//                    className: 'btn-primary',
//                    callback: function() {
//                        return;
//                    }
//                }
//            }
//        });
    });
});

function showPopup(addcallback){
	 bootbox.dialog({
            message: 'Translated text: ' + res,
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
//                    callback: function() {
////                        var userId = getUserId();
////                        console.log("before redirect ==> " + userId);
//                        addTranslation(sourceLang, targetLang, text, res).done(function() {
//                            alert('done, check db');
//                        });
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
    var r = $.Deferred;

    chrome.runtime.sendMessage({
        method: "getUserId"
    }, function(response) {
        console.log("id from background: " + response.userId);
        userId = response.userId;
//		console.log(userId);
//		console.log(sourceLang);
//		console.log(targetLang);
//		console.log(source);
//		console.log(target);
		
		
//		 $.ajax({
//            async: false,
//            type: 'POST',
//            url: 'http://localhost:8080/translate/create',
//            data: {
//                userId: userId,
//                sourceLang: sourceLang,
//                targetLang: targetLang,
//                source: source,
//                target: target
//            },
//            success: function(data, status) {
//                alert('added!!');
//            }
//        });
		
        $.post("http://localhost:8080/translate/create", {
                userId: userId,
                sourceLang: sourceLang,
                targetLang: targetLang,
                source: source,
                target: target
            })
            .done(function(data) {
                alert('added!!');
            })
            .fail(function() {
                alert('you have to login first');
            });
        setTimeout(function() {
            r.resolve();
        }, 2500);
    });
    return r;
}
