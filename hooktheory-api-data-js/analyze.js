var fs = require("fs");

var exports = module.exports = {};
//exports.filename = "";
//exports.data = null;

//var name = "outputpretty.json";

function load (name) {
    return JSON.parse(fs.readFileSync(name, 'utf8'));
}
exports.load = load;

function getProb(data) {
    var keys = Object.keys(data);
    var result = [];
    for (var i=0; i<keys.length; ++i) {
        result[i]=[data[keys[i]].probability,keys[i]];
    }
    return result;
}
exports.getProb = getProb;

function getNext(str) {
    // str is "1-6-4" etc
    var progression = str.split("-");
    var obj = data;
    for (var i=0; i< progression.length; ++i) {
        var chord = progression[i];
        if (obj[chord]) {
            obj = obj[chord].possibilities;
        } else {
            console.log("Progression doesn't exist.");
            return null;
            break;
        }
    }
    return getProb(obj).sort().reverse();
}
exports.getNext = getNext;

function makeRandom(data) {
    var prog = [];
    var obj = data;
    var ids = findAllId(data);
    var lookup = {};
    ids.map(function(x) { lookup[x[0]]=x[1]});
    function romanize(x) { return lookup[x] };
    while (Object.keys(obj).length > 0) {
        var prob = getProb(obj);
        
        //normalize
        var sum = 0;
        for (var i=0; i< prob.length; ++i) {
            sum+=prob[i][0];
        }
        for (var i=0; i< prob.length; ++i) {
            prob[i][0] *= 1.0/sum;
        }
        var rand = Math.random();
        var acc=0;
        var idx = prob.length-1;
        for (var i=0; i< prob.length; ++i) {
            if (rand<=acc+prob[i][0]) {
                idx = i;
                break;
            } else {
                acc +=prob[i][0];
            }
        }
        obj = obj[prob[i][1]].possibilities;
        prog.push(prob[i][1]);
    }
    return prog.map(romanize).join("-");
}
exports.makeRandom = makeRandom;

function findAllId(data) {
    var lookup = {}
    function findAllRecurse(obj) {
        var keys = Object.keys(obj);
        for (var i=0; i< keys.length; ++i) {
            lookup[keys[i]] = obj[keys[i]].roman || lookup[keys[i]];
            findAllRecurse(obj[keys[i]].possibilities);
        }
    }
    findAllRecurse(data);
    var keys = Object.keys(lookup);
    var arr = [];
    for (var i=0; i< keys.length; ++i) {
        arr.push([keys[i], lookup[keys[i]]]);
    }
    arr.sort();
    return arr;
    //return lookup;
}
exports.findAllId = findAllId;