var labelType, useGradients, nativeTextSupport, animate;

(function() {
    var ua = navigator.userAgent,
            iStuff = ua.match(/iPhone/i) || ua.match(/iPad/i),
            typeOfCanvas = typeof HTMLCanvasElement,
            nativeCanvasSupport = (typeOfCanvas == 'object' || typeOfCanvas == 'function'),
            textSupport = nativeCanvasSupport
                    && (typeof document.createElement('canvas').getContext('2d').fillText == 'function');
    //I'm setting this based on the fact that ExCanvas provides text support for IE
    //and that as of today iPhone/iPad current text support is lame
    labelType = (!nativeCanvasSupport || (textSupport && !iStuff)) ? 'Native' : 'HTML';
    nativeTextSupport = labelType == 'Native';
    useGradients = nativeCanvasSupport;
    animate = !(iStuff || !nativeCanvasSupport);
})();

var Log = {
    elem: false,
    write: function(text) {
        if (!this.elem)
            this.elem = document.getElementById('log');
        this.elem.innerHTML = text;
        this.elem.style.left = (500 - this.elem.offsetWidth / 2) + 'px';
    }
};


function init() {
    //init data
    var json = {"children":[{"children":[],"data":{"progress":100,"tests":2,"pending":0,"$color":"#00ff00","$area":10,"steps":10,"failing":0,"type":"tag","passing":2},"id":"search","name":"search"}],"data":{},"id":"root","name":"Application"};
    //end
    //init TreeMap
    var tm = new $jit.TM.Squarified({
                //where to inject the visualization
                injectInto: 'infovis',
                //parent box title heights
                titleHeight: 20,
                //enable animations
                //animate: animate,
                //box offsets
                offset: 1,
                width: 700,
                height: 700,
                levelsToShow: 2,

                cushion: useGradients,
                //Attach left and right click events
                Events: {
                    enable: true,
                    onClick: function(node) {
                        if (node) {
                            var data = node.data;
                            if (data.report) {
                               window.document.location = data.report
                            } else {
                                tm.enter(node);
                            }
                        }
                    },
                    onRightClick: function() {
                        tm.out();
                    }
                },
                duration: 1000,
                //Enable tips
                Tips: {
                    enable: true,
                    //add positioning offsets
                    offsetX: 20,
                    offsetY: 20,
                    //implement the onShow method to
                    //add content to the tooltip when a node
                    //is hovered
                    onShow: function(tip, node, isLeaf, domElement) {
                        var html = "<div class=\"tip-title\">" + node.name
                                + "</div><div class=\"tip-text\">";
                        var data = node.data;
                        if (data.stories) {
                            html += "User stories: " + data.stories + "<br/>";
                        }
                        if (data.tests) {
                            html += "Tests: " + data.tests + "<br/>";
                        }
                        if (data.passing) {
                            html += "&nbsp;&nbsp;-&nbsp;Passing: " + data.passing + "<br/>";
                        }
                        if (data.pending) {
                            html += "&nbsp;&nbsp;-&nbsp;Pending: " + data.pending + "<br/>";
                        }
                        if (data.pending) {
                            html += "&nbsp;&nbsp;-&nbsp;Failing: " + data.failing + "<br/>";
                        }
                        if (data.steps) {
                            html += "Total steps: " + data.steps + "<br/>";
                        }
                        tip.innerHTML = html;
                    }
                },
                //Add the name of the node in the correponding label
                //This method is called once, on label creation.
                onCreateLabel: function(domElement, node) {
                    domElement.innerHTML = node.name;
                    var style = domElement.style;

  					if (node.id == 'root' ) {
	                	style.color = '#ffffff';
					}
					if (node.data.progress) {
						if (node.data.progress > 50) {
							style.color = '#ffffff';
						} else {
							style.color = '#000000';
						}
					}

                    style.display = '';
                    style.border = '1px solid transparent';
                    domElement.onmouseover = function() {
                        style.border = '1px solid #9FD4FF';
                    };
                    domElement.onmouseout = function() {
                        style.border = '1px solid transparent';
                    };
                }
            });
    tm.loadJSON(json);
    tm.refresh();
    //end
    //add events to radio buttons
    var sq = $jit.id('r-sq'),
            st = $jit.id('r-st'),
            sd = $jit.id('r-sd');
    var util = $jit.util;
    util.addEvent(sq, 'change', function() {
        if (!sq.checked) return;
        util.extend(tm, new $jit.Layouts.TM.Squarified);
        tm.refresh();
    });
    util.addEvent(st, 'change', function() {
        if (!st.checked) return;
        util.extend(tm, new $jit.Layouts.TM.Strip);
        tm.layout.orientation = "v";
        tm.refresh();
    });
    util.addEvent(sd, 'change', function() {
        if (!sd.checked) return;
        util.extend(tm, new $jit.Layouts.TM.SliceAndDice);
        tm.layout.orientation = "v";
        tm.refresh();
    });
    //add event to the back button
    var back = $jit.id('back');
    $jit.util.addEvent(back, 'click', function() {
        tm.out();
    });
}
