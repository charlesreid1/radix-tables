$(function() {

    var newHash      = "",
        $mainContent = $("#main-content"),
        baseHeight   = 0,
        $el;
        
    $("div#primes").delegate("a", "click", function() {
        window.location.hash = $(this).attr("href");
        return false;
    });
    
    $(window).bind('hashchange', function(){
    
        newHash = window.location.hash.substring(1);
        
        if (newHash) {
            // if you don't load it once here, it doesn't load below.
            // (I f*#&$&@ hate Javascript.)
            $mainContent.load(newHash + " #guts");
            $mainContent
                .find("#guts")
                .fadeOut(200, function() {
                    //$mainContent.hide().load(newHash + " #guts");
                    $mainContent.load(newHash + " #guts");
                });
        };
        
    });
    
    $(window).trigger('hashchange');

});
