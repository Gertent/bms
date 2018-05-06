/*$(function() {
	var Accordion = function(el, multiple) {
		this.el = el || {};
		this.multiple = multiple || false;

		// Variables privadas
		var links = this.el.find('.link');
		// Evento
		links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown)
	}

	Accordion.prototype.dropdown = function(e) {
		var $el = e.data.el;
			$this = $(this),
			$next = $this.next();

		$next.slideToggle();
		$this.parent().toggleClass('open');
	}	

	var accordion = new Accordion($('#accordion'), false);
});*/
/**
 * Created by win7 on 2016/9/27.
 */
$(function(){
    //leftscroll
    $(".nav_bar").slimScroll({
        height: "100%", railOpacity: .9, alwaysVisible: !1
    });
    $(".nav_left>li>a").click(function(){
        $(this).parent().addClass("active").siblings().removeClass("active");
    });
    //header
    $(".nav_header").hover(function(){
        $(".downBox").css("display","block");
    },function(){
        $(".downBox").css("display","none");
    });
    $(".nav_left>li:last-child a").css("padding-bottom","20px");
});
//leftmenuslider
$(function() {
    var Accordion = function(el, multiple) {
        this.el = el || {};
        this.multiple = multiple || false;
        // Variables privadas
        var links = this.el.find('.link');
        // Evento
        links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown)
    }
    Accordion.prototype.dropdown = function(e) {
        var $el = e.data.el,
            $this = $(this),
            $next = $this.next();
        $next.slideToggle();
        $this.parent().toggleClass('active');
        if (!e.data.multiple) {
            $el.find('.nav_left').not($next).slideUp().parent().removeClass('active');
        };
    }
    var accordion = new Accordion($('.menuN'), false);
});