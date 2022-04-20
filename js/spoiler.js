$(document).ready(function () {
    $('.price__title').click(function (event) {
        if ($('.price').hasClass('one')) {
            $('.price__title').not($(this)).removeClass('active');
            $('.price__text').not($(this).next()).slideUp(300);
        }
        $(this).toggleClass('active').next().slideToggle(300);
    });
    $('.work-process__spoiler').click(function (event) {
        $(this).toggleClass('active').next().slideToggle(300);
    });
    $('.about__spoiler').click(function (event) {
        $(this).toggleClass('active').next().slideToggle(300);
    });
});
