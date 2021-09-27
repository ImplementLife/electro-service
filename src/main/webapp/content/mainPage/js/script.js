"use strict"

$('#sendStatusClose').click(function(){$('#sendStatus').addClass('hide');});

document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('submit').addEventListener('click', formSend);
    async function formSend(e) {
        e.preventDefault();
        let error = formValidate(form);
    }

    function formValidate(form) {
        let error = 0;
        let formReq = document.querySelectorAll('._req');
        for (let input of formReq) {
            formRemoveError(input);
            if (input.value === '') {
                formAddError(input);
                error++;
            }
        }
        if (error == 0) {
            let body = new Map();
            for (let input of document.querySelectorAll('.in')) body.set(input.name, input.value);
            postData('http://localhost:8080/order', Object.fromEntries(body.entries()));
        }
    }

    function formAddError(input) {
        input.parentElement.classList.add('_error');
        input.classList.add('_error');
    }
    function formRemoveError(input) {
        input.parentElement.classList.remove('_error');
        input.classList.remove('_error');
    }
});

async function postData(url = '', data = {}) {
    const response = await fetch(url, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    });
    function d(res) {
        if (res.ok) {
            $('#sendStatusText').text('Спасибо за оставленную заявку, в ближайшее время мы вам перезвоним.');
            $('#sendStatus').removeClass('hide');
        } else {
            $('#sendStatusText').text('Упс, произошла ошибка, попробуйте позже или позвоните нам');
            $('#sendStatus').removeClass('hide');
        }
        return res;
    }
    return await d(response);
}

var menu = $('header');
var live_search = $('.wraper_ancors:eq(1)').find('.live-search-wrapper');
function scroll() {
    if ($(window).scrollTop() >= 100) {
        if (!menu.hasClass('fixed')) {
            menu.addClass('fixed').delay(10).queue(function () {$(this).addClass('show').dequeue();});
            live_search.hide();
        }
    } else if (menu.hasClass('fixed')) {
        menu.removeClass('fixed').removeClass('show');
        live_search.show();
    }
}
document.onscroll = function(){scroll();};