$.ui.ready(function () {
    var startLocation = $('#startLocation').find('h3');
    var endLocation = $('#endLocation').find('h3');

    // 交换
    $('#changeLocation').click(function() {
        var temp = startLocation.text();
        startLocation.text(endLocation.text());
        endLocation.text(temp);
    });
    // 出发地
    $('#startLocation, #endLocation').click(function () {

    });

    $('#panel_Location').find('a').click(function () {
        $.ui.loadContent('#airTicket', false, false);
    });
});
