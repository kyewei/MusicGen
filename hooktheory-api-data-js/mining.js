var request = require("request");
var fs = require("fs");

var apiURL = "http://www.hooktheory.com/api/trends/stats?cp=";

//var idStr = [["1","I"],["142","I⁴₂"],["143","I⁴₃"],["16","I⁶"],["164","I⁶₄"],["165","I⁶₅"],["17","I⁷"],["2","ii"],["242","ii⁴₂"],["243","ii⁴₃"],["26","ii⁶"],["264","ii⁶₄"],["265","ii⁶₅"],["27","ii⁷"],["3","iii"],["342","iii⁴₂"],["343","iii⁴₃"],["36","iii⁶"],["364","iii⁶₄"],["365","iii⁶₅"],["37","iii⁷"],["4","IV"],["4/3","IV/iii"],["442","IV⁴₂"],["46","IV⁶"],["46/4","IV⁶/IV"],["46/5","IV⁶/V"],["464","IV⁶₄"],["464/4","IV⁶₄/IV"],["464/6","IV⁶₄/vi"],["465","IV⁶₅"],["47","IV⁷"],["47/2","IV⁷/ii"],["47/4","IV⁷/IV"],["5","V"],["5/2","V/ii"],["5/3","V/iii"],["5/4","V/IV"],["5/5","V/V"],["5/6","V/vi"],["5/7","V/vii"],["542","V⁴₂"],["542/3","V⁴₂/iii"],["542/4","V⁴₂/IV"],["542/5","V⁴₂/V"],["542/6","V⁴₂/vi"],["543","V⁴₃"],["543/2","V⁴₃/ii"],["543/3","V⁴₃/iii"],["543/5","V⁴₃/V"],["543/6","V⁴₃/vi"],["56","V⁶"],["56/2","V⁶/ii"],["56/5","V⁶/V"],["56/6","V⁶/vi"],["564","V⁶₄"],["564/2","V⁶₄/ii"],["564/6","V⁶₄/vi"],["565","V⁶₅"],["565/2","V⁶₅/ii"],["565/3","V⁶₅/iii"],["565/5","V⁶₅/V"],["565/6","V⁶₅/vi"],["57","V⁷"],["57/2","V⁷/ii"],["57/3","V⁷/iii"],["57/4","V⁷/IV"],["57/5","V⁷/V"],["57/6","V⁷/vi"],["57/7","V⁷/vii"],["6","vi"],["642","vi⁴₂"],["643","vi⁴₃"],["66","vi⁶"],["664","vi⁶₄"],["665","vi⁶₅"],["67","vi⁷"],["7","vii°"],["7/2","vii°/ii"],["7/3","vii°/iii"],["7/5","vii°/V"],["7/6","vii°/vi"],["742","viiø⁴₂"],["743/5","viiø⁴₃/V"],["76","vii°⁶"],["764/4","vii°⁶₄/IV"],["77","viiø⁷"],["77/5","viiø⁷/V"],["77/6","viiø⁷/vi"],["C3","♭iii"],["C37","♭iii⁷"],["C5","♭V"],["C57","♭V⁷"],["C6","♭VI"],["C642","♭VI⁴₂"],["C67","♭VIb7"],["C7","♭vii"],["C76","♭vii⁶"],["C764","♭vii⁶₄"],["D142","i⁴₂"],["D17","i⁷"],["D2","ii"],["D265","ii⁶₅"],["D3","♭III"],["D36","♭III⁶"],["D4","IV"],["D442","IV⁴₂"],["D46","IV⁶"],["D465","IV⁶₅"],["D47","IVb7"],["D5","v"],["D6","vi°"],["D67","viø⁷"],["D742","♭VII⁴₂"],["D77","♭VII⁷"],["L17","I⁷"],["L264","II⁶₄"],["L27","II⁷"],["L47","♯ivø⁷"],["L5","V"],["L542","V⁴₂"],["L7","vii"],["L77","vii⁷"],["M1","I"],["M17","Ib7"],["M365","iiiø⁶₅"],["M4","IV"],["M5","v"],["M565","v⁶₅"],["Y2","♭II"],["Y27","♭II⁷"],["Y3","♭III"],["Y342","♭III⁴₂"],["Y37","♭IIIb7"],["Y4","iv"],["Y6","♭VI"],["Y642","♭VI⁴₂"],["b1","i"],["b142","i⁴₂"],["b16","i⁶"],["b164","i⁶₄"],["b165","i⁶₅"],["b17","i⁷"],["b242","iiø⁴₂"],["b243","iiø⁴₃"],["b26","ii°⁶"],["b264","ii°⁶₄"],["b265","iiø⁶₅"],["b3","♭III"],["b364","♭III⁶₄"],["b37","♭III⁷"],["b4","iv"],["b46","iv⁶"],["b464","iv⁶₄"],["b47","iv⁷"],["b5","v"],["b543","v⁴₃"],["b56","v⁶"],["b564","v⁶₄"],["b57","v⁷"],["b6","♭VI"],["b642","♭VI⁴₂"],["b66","♭VI⁶"],["b664","♭VI⁶₄"],["b67","♭VI⁷"],["b7","♭VII"],["b742","♭VII⁴₂"],["b76","♭VII⁶"],["b764","♭VII⁶₄"],["b77","♭VIIb7"]];
var idStr = [["1","I"],["142","I<sup>4</sup><sub>2</sub>"],["143","I<sup>4</sup><sub>3</sub>"],["16","I<sup>6</sup>"],["164","I<sup>6</sup><sub>4</sub>"],["165","I<sup>6</sup><sub>5</sub>"],["17","I<sup>7</sup>"],["2","ii"],["242","ii<sup>4</sup><sub>2</sub>"],["243","ii<sup>4</sup><sub>3</sub>"],["26","ii<sup>6</sup>"],["264","ii<sup>6</sup><sub>4</sub>"],["265","ii<sup>6</sup><sub>5</sub>"],["27","ii<sup>7</sup>"],["3","iii"],["342","iii<sup>4</sup><sub>2</sub>"],["343","iii<sup>4</sup><sub>3</sub>"],["36","iii<sup>6</sup>"],["364","iii<sup>6</sup><sub>4</sub>"],["365","iii<sup>6</sup><sub>5</sub>"],["37","iii<sup>7</sup>"],["4","IV"],["4/2","IV/ii"],["4/3","IV/iii"],["4/7","IV/vii"],["442","IV<sup>4</sup><sub>2</sub>"],["442/2","IV<sup>4</sup><sub>2</sub>/ii"],["443","IV<sup>4</sup><sub>3</sub>"],["46","IV<sup>6</sup>"],["46/4","IV<sup>6</sup>/IV"],["46/5",null],["46/6","IV<sup>6</sup>/vi"],["46/7","IV<sup>6</sup>/vii"],["464","IV<sup>6</sup><sub>4</sub>"],["464/3","IV<sup>6</sup><sub>4</sub>/iii"],["464/4","IV<sup>6</sup><sub>4</sub>/IV"],["464/6","IV<sup>6</sup><sub>4</sub>/vi"],["464/7","IV<sup>6</sup><sub>4</sub>/vii"],["465","IV<sup>6</sup><sub>5</sub>"],["465/4","IV<sup>6</sup><sub>5</sub>/IV"],["47","IV<sup>7</sup>"],["47/2","IV<sup>7</sup>/ii"],["47/3","IV<sup>7</sup>/iii"],["47/4","IV<sup>7</sup>/IV"],["5","V"],["5/2","V/ii"],["5/3","V/iii"],["5/4",null],["5/5","V/V"],["5/6","V/vi"],["5/7","V/vii"],["542","V<sup>4</sup><sub>2</sub>"],["542/2","V<sup>4</sup><sub>2</sub>/ii"],["542/3","V<sup>4</sup><sub>2</sub>/iii"],["542/4","V<sup>4</sup><sub>2</sub>/IV"],["542/5","V<sup>4</sup><sub>2</sub>/V"],["542/6","V<sup>4</sup><sub>2</sub>/vi"],["542/7","V<sup>4</sup><sub>2</sub>/vii"],["543","V<sup>4</sup><sub>3</sub>"],["543/2","V<sup>4</sup><sub>3</sub>/ii"],["543/3","V<sup>4</sup><sub>3</sub>/iii"],["543/4","V<sup>4</sup><sub>3</sub>/IV"],["543/5","V<sup>4</sup><sub>3</sub>/V"],["543/6","V<sup>4</sup><sub>3</sub>/vi"],["56","V<sup>6</sup>"],["56/2","V<sup>6</sup>/ii"],["56/3","V<sup>6</sup>/iii"],["56/4","V<sup>6</sup>/IV"],["56/5","V<sup>6</sup>/V"],["56/6","V<sup>6</sup>/vi"],["56/7","V<sup>6</sup>/vii"],["564","V<sup>6</sup><sub>4</sub>"],["564/2","V<sup>6</sup><sub>4</sub>/ii"],["564/3","V<sup>6</sup><sub>4</sub>/iii"],["564/4","V<sup>6</sup><sub>4</sub>/IV"],["564/5","V<sup>6</sup><sub>4</sub>/V"],["564/6","V<sup>6</sup><sub>4</sub>/vi"],["565","V<sup>6</sup><sub>5</sub>"],["565/2","V<sup>6</sup><sub>5</sub>/ii"],["565/3","V<sup>6</sup><sub>5</sub>/iii"],["565/4","V<sup>6</sup><sub>5</sub>/IV"],["565/5","V<sup>6</sup><sub>5</sub>/V"],["565/6","V<sup>6</sup><sub>5</sub>/vi"],["57","V<sup>7</sup>"],["57/2","V<sup>7</sup>/ii"],["57/3","V<sup>7</sup>/iii"],["57/4","V<sup>7</sup>/IV"],["57/5","V<sup>7</sup>/V"],["57/6","V<sup>7</sup>/vi"],["57/7","V<sup>7</sup>/vii"],["6","vi"],["642","vi<sup>4</sup><sub>2</sub>"],["643","vi<sup>4</sup><sub>3</sub>"],["66","vi<sup>6</sup>"],["664","vi<sup>6</sup><sub>4</sub>"],["665","vi<sup>6</sup><sub>5</sub>"],["67","vi<sup>7</sup>"],["7","vii&deg;"],["7/2","vii&deg;/ii"],["7/3","vii&deg;/iii"],["7/4","vii&deg;/IV"],["7/5","vii&deg;/V"],["7/6","vii&deg;/vi"],["7/7","vii&deg;/vii"],["742",null],["743/5",null],["76","vii&deg;<sup>6</sup>"],["76/2","vii&deg;<sup>6</sup>/ii"],["76/3","vii&deg;<sup>6</sup>/iii"],["76/4","vii&deg;<sup>6</sup>/IV"],["76/5","vii&deg;<sup>6</sup>/V"],["76/6","vii&deg;<sup>6</sup>/vi"],["76/7","vii&deg;<sup>6</sup>/vii"],["764/4",null],["77","vii<sup>??</sup><sup>7</sup>"],["77/2","vii<sup>??</sup><sup>7</sup>/ii"],["77/4","vii<sup>??</sup><sup>7</sup>/IV"],["77/5","vii<sup>??</sup><sup>7</sup>/V"],["77/6","vii<sup>??</sup><sup>7</sup>/vi"],["C1","i&deg;"],["C16","i&deg;<sup>6</sup>"],["C164","i&deg;<sup>6</sup><sub>4</sub>"],["C17","i<sup>??</sup><sup>7</sup>"],["C2","&#9837;II"],["C26","&#9837;II<sup>6</sup>"],["C27","&#9837;II<sup>7</sup>"],["C3","&#9837;iii"],["C36","&#9837;iii<sup>6</sup>"],["C37","&#9837;iii<sup>7</sup>"],["C4","iv"],["C46","iv<sup>6</sup>"],["C464","iv<sup>6</sup><sub>4</sub>"],["C47","iv<sup>7</sup>"],["C5","&#9837;V"],["C57","&#9837;V<sup>7</sup>"],["C6","&#9837;VI"],["C642",null],["C66","&#9837;VI<sup>6</sup>"],["C67","&#9837;VIb7"],["C7","&#9837;vii"],["C76","&#9837;vii<sup>6</sup>"],["C764",null],["D142","i<sup>4</sup><sub>2</sub>"],["D16","i<sup>6</sup>"],["D164","i<sup>6</sup><sub>4</sub>"],["D165","i<sup>6</sup><sub>5</sub>"],["D17","i<sup>7</sup>"],["D2","ii"],["D242","ii<sup>4</sup><sub>2</sub>"],["D26","ii<sup>6</sup>"],["D264","ii<sup>6</sup><sub>4</sub>"],["D265","ii<sup>6</sup><sub>5</sub>"],["D27","ii<sup>7</sup>"],["D3","&#9837;III"],["D36","&#9837;III<sup>6</sup>"],["D37","&#9837;III<sup>7</sup>"],["D4","IV"],["D442","IV<sup>4</sup><sub>2</sub>"],["D443","IV<sup>4</sup><sub>3</sub>"],["D46",null],["D465","IV<sup>6</sup><sub>5</sub>"],["D47","IVb7"],["D5","v"],["D56","v<sup>6</sup>"],["D564","v<sup>6</sup><sub>4</sub>"],["D565","v<sup>6</sup><sub>5</sub>"],["D57","v<sup>7</sup>"],["D6","vi&deg;"],["D67","vi<sup>??</sup><sup>7</sup>"],["D742",null],["D76","&#9837;VII<sup>6</sup>"],["D77","&#9837;VII<sup>7</sup>"],["L16","I<sup>6</sup>"],["L17","I<sup>7</sup>"],["L242","II<sup>4</sup><sub>2</sub>"],["L243","II<sup>4</sup><sub>3</sub>"],["L26","II<sup>6</sup>"],["L264","II<sup>6</sup><sub>4</sub>"],["L265","II<sup>6</sup><sub>5</sub>"],["L27","II<sup>7</sup>"],["L3","iii"],["L364","iii<sup>6</sup><sub>4</sub>"],["L37","iii<sup>7</sup>"],["L4","&#9839;iv&deg;"],["L46","&#9839;iv&deg;<sup>6</sup>"],["L47",null],["L5","V"],["L542","V<sup>4</sup><sub>2</sub>"],["L56","V<sup>6</sup>"],["L564","V<sup>6</sup><sub>4</sub>"],["L57","V#7"],["L6","vi"],["L7","vii"],["L742","vii<sup>4</sup><sub>2</sub>"],["L743","vii<sup>4</sup><sub>3</sub>"],["L76","vii<sup>6</sup>"],["L764","vii<sup>6</sup><sub>4</sub>"],["L765","vii<sup>6</sup><sub>5</sub>"],["L77","vii<sup>7</sup>"],["M1","I"],["M142","I<sup>4</sup><sub>2</sub>"],["M143","I<sup>4</sup><sub>3</sub>"],["M165","I<sup>6</sup><sub>5</sub>"],["M17","Ib7"],["M27","ii<sup>7</sup>"],["M3","iii&deg;"],["M36","iii&deg;<sup>6</sup>"],["M365",null],["M37","iii<sup>??</sup><sup>7</sup>"],["M4","IV"],["M443","IV<sup>4</sup><sub>3</sub>"],["M47","IV<sup>7</sup>"],["M5","v"],["M56","v<sup>6</sup>"],["M564","v<sup>6</sup><sub>4</sub>"],["M565","v<sup>6</sup><sub>5</sub>"],["M57","v<sup>7</sup>"],["M6","vi"],["M642","vi<sup>4</sup><sub>2</sub>"],["M66","vi<sup>6</sup>"],["M67","vi<sup>7</sup>"],["M76","&#9837;VII<sup>6</sup>"],["M77","&#9837;VII<sup>7</sup>"],["Y1","i"],["Y164","i<sup>6</sup><sub>4</sub>"],["Y17","i<sup>7</sup>"],["Y2","&#9837;II"],["Y26","&#9837;II<sup>6</sup>"],["Y27","&#9837;II<sup>7</sup>"],["Y3","&#9837;III"],["Y342",null],["Y37","&#9837;IIIb7"],["Y4","iv"],["Y47","iv<sup>7</sup>"],["Y5","v&deg;"],["Y56","v&deg;<sup>6</sup>"],["Y564","v&deg;<sup>6</sup><sub>4</sub>"],["Y57","v<sup>??</sup><sup>7</sup>"],["Y6","&#9837;VI"],["Y642",null],["Y66","&#9837;VI<sup>6</sup>"],["Y67","&#9837;VI<sup>7</sup>"],["Y7","&#9837;vii"],["Y76","&#9837;vii<sup>6</sup>"],["Y77","&#9837;vii<sup>7</sup>"],["b1","i"],["b142","i<sup>4</sup><sub>2</sub>"],["b143","i<sup>4</sup><sub>3</sub>"],["b16","i<sup>6</sup>"],["b164","i<sup>6</sup><sub>4</sub>"],["b165","i<sup>6</sup><sub>5</sub>"],["b17","i<sup>7</sup>"],["b2","ii&deg;"],["b242",null],["b243",null],["b26","ii&deg;<sup>6</sup>"],["b264",null],["b265",null],["b27","ii<sup>??</sup><sup>7</sup>"],["b3","&#9837;III"],["b36","&#9837;III<sup>6</sup>"],["b364",null],["b37","&#9837;III<sup>7</sup>"],["b4","iv"],["b442","iv<sup>4</sup><sub>2</sub>"],["b443","iv<sup>4</sup><sub>3</sub>"],["b46","iv<sup>6</sup>"],["b464","iv<sup>6</sup><sub>4</sub>"],["b465","iv<sup>6</sup><sub>5</sub>"],["b47","iv<sup>7</sup>"],["b5","v"],["b542","v<sup>4</sup><sub>2</sub>"],["b543","v<sup>4</sup><sub>3</sub>"],["b56","v<sup>6</sup>"],["b564","v<sup>6</sup><sub>4</sub>"],["b565","v<sup>6</sup><sub>5</sub>"],["b57","v<sup>7</sup>"],["b6","&#9837;VI"],["b642",null],["b66","&#9837;VI<sup>6</sup>"],["b664",null],["b67","&#9837;VI<sup>7</sup>"],["b7","&#9837;VII"],["b742",null],["b76","&#9837;VII<sup>6</sup>"],["b764",null],["b77","&#9837;VIIb7"]];
var numToRoman = {};
var romanToNum = {};
idStr.map(function (arr) { 
    numToRoman[arr[0]] = arr[1]; 
    romanToNum[arr[1]] = arr[0];
});

var empty = {};
var nonempty = {};

fs.readFile("empty.json", "utf-8", function (err,data) {
    if (err) {
        console.log("Read empty",err);
        return;
    }
    empty = JSON.parse(data);
});
fs.readFile("nonempty.json", "utf-8", function (err,data) {
    if (err) {
        console.log("Read nonempty",err);
        return;
    }
    nonempty = JSON.parse(data);
});


var result = {};
var reqCount = 0;

var queryStack = [];
var i = 0;
var working = false;

function nextSubtreeHandler(err, res, body) {
    function restart() { 
        working = true; 
    }
    function writeHandle (err) {
        if (err) {
            console.log("Write", err);
            return;
        }
    }
    function findIdx (id) {
        if (!store[id]) {
            store[id] = {};
            store[id].possibilities = {};
        }
        
        store = store[id].possibilities;
    }
    function copyData(o) {
        // o is in the form of {"chord_ID":"1","chord_HTML":"I","probability":0.149,"child_path":"1"}
        var id = o.chord_ID;//numToRoman[o.chord_ID];
        store[id] = {};

        store[id].probability = o.probability;
        store[id].id = o.chord_ID;
        store[id].roman = o.chord_HTML;
        store[id].path = o.child_path;
        store[id].possibilities = {};
    }
    function getProbability(o) { 
        return o.probability; 
    }
    function sum(x,y) { 
        return x+y; 
    }
    function callRecurse(assoc) {
        var id = assoc[0];//numToRoman[assoc[0]];
        if (!store[id]) {
            if (array.length >=20 || sum <=0.99) {
                store[id] = {};
                store[id].possibilities = {};
            } else {
                return;
            }
        }
        queryStack.push(uri.concat(id).join(","));
    }
    
    reqCount++;
    if (err || body.charAt(0)!="\[") {
        console.log("Error: ",err && err.code,!res || res.request.uri.href);
        if (res) {
            queryStack.push(res.request.uri.href.substring(res.request.uri.href.indexOf("cp")));
        }
        if (working) {
            working = false;
            setTimeout(restart, 8000);
        }
        return;
    }
    
    var array = JSON.parse(body);
    var url = res.request.uri.href;
    
    var filename = url.substring(url.indexOf("cp")).replace(/\//g,"_");
    if (array.length === 0) {
        //reqCount++;
        empty[filename] = true;
        return;
    }
    if (!nonempty[filename]) {
        nonempty[filename]=body;
    }
    /*if (!fs.existsSync(filename)) {
        fs.writeFile(filename, body, writeHandle);
    }*/
    
    var uri = array[0].child_path.split(",");
    uri.pop();
    var path = uri;
    /*if (url.substring(url.indexOf("cp")+3) != uri.join(",")) {
        console.log("errorrrrr", url.substring(url.indexOf("cp")), uri.join(","));
    }*/
    
    var store = result;
    path.map(findIdx);

    array.map(copyData);
    var sum = array.map (getProbability).reduce (sum);
    idStr.map(callRecurse);
}



function init () {
    queryStack.push("");
    working = true;
};
init();


setTimeout(function wait() {
    var len = queryStack.length;
    if (i<len && working && i-reqCount < 50) {
        var req = queryStack[i];
        
        function writeHandler(err,data) {
            if (err) {
                console.log("Read",err);
                return;
            }
            console.log("Cached: "+req);
            setTimeout(nextSubtreeHandler, 1, null, { request:{ uri:{ href:apiURL+req} }}, data);
        }
        
        var filename = "cp="+req.replace(/\//g,"_");
        if (empty[filename]) {
            setTimeout(nextSubtreeHandler, 1, null, { request:{ uri:{ href:apiURL+req} }}, "[]");
            console.log("Cached: "+req);
        } else if (nonempty[filename]) {
            setTimeout(nextSubtreeHandler, 1, null, { request:{ uri:{ href:apiURL+req} }}, nonempty[filename]);
            console.log("Cached: "+req);
        } else if (fs.existsSync(filename)) {
            fs.readFile(filename, "utf-8", writeHandler);
        } else {
            var data = {};
            data.uri = apiURL+req;
            //data.agent = false;
            data.method = "GET";
            data.followRedirect = true;
            data.maxRedirects = 10;
            data.timeout = 0;

            console.log("Fired: "+ req);
            request(data, nextSubtreeHandler);
        
        }
        ++i;
        
    }
    setTimeout(wait,5);
},5);


setTimeout(function wait2() {
    fs.writeFile("output.json", JSON.stringify(result), function(err) {
        if (err) {
            console.log("Write1", err);
            return;
        }
    });
    fs.writeFile("empty.json", JSON.stringify(empty), function(err) {
        if (err) {
            console.log("Write2", err);
            return;
        }
    });
    fs.writeFile("nonempty.json", JSON.stringify(nonempty), function(err) {
        if (err) {
            console.log("Write3", err);
            return;
        }
        console.log("Written to out");
    });
    setTimeout(wait2, 1000*60*30);
},1000*10);




// windows ctrl-c
// http://stackoverflow.com/questions/10021373/what-is-the-windows-equivalent-of-process-onsigint-in-node-js/14861513#14861513

var readLine = require ("readline");
if (process.platform === "win32"){
    var rl = readLine.createInterface ({
        input: process.stdin,
        output: process.stdout
    });

    rl.on ("SIGINT", function (){
        process.emit ("SIGINT");
    });

}

function end(){
    //graceful shutdown
    fs.writeFile("output.json", JSON.stringify(result), function(err) {
        if (err) {
            console.log("Write1", err);
            return;
        }
        fs.writeFile("empty.json", JSON.stringify(empty), function(err) {
            if (err) {
                console.log("Write2", err);
                return;
            }
            fs.writeFile("nonempty.json", JSON.stringify(nonempty), function(err) {
                if (err) {
                    console.log("Write3", err);
                    return;
                }
                console.log("Written to out");
                process.exit ();
            });
        });
    });
    
}
process.on("SIGINT", end);
process.on("uncaughtException", end);