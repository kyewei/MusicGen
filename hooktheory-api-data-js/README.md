hooktheory-api-data-js
==========

Needs `Node.js`.

###Data collection
`mining.js` contains the code I used to mine the API. 
Their API is accessed at `http://www.hooktheory.com/api/trends/stats?cp=`. 
A typical request is the above with the end like `cp=464,b7` or something of the sort, where the commas delimit HookTheory's "metasyntax" for representing Chords, found [here][1] and [here][2].
For some reason, their server wouldn't allow more than ~50 requests per second, so I often ran into `TIMEOUT`s. 
So I developed a queue-like system for mining, where at any time 50 requests are out, and if errors occur, the address is re-added to the queue so that all addresses are mined. 

The script also auto-saves every half-hour or so all the raw data received in JSON form, and it is reimported everytime the script is run so that it can continue from where it left off, since the whole effort took a while...

Outputs are stored in the data sub-folder.

###Data analysis
To use, open up a command line, launch `node`, and import using `analyze = require("./analyze.js");` or the appropriate file location. `load`, `save` functions are self-explanatory.

A caveat of their current API is that it only gives the top (25? 28?) chords progressions, and if you wanted the rest, you needed to find them yourself. As such the probabilities given are off. 
I normalize using `fixDataProbability`. 
`makeRandom` generates random progressions using the helper `getNext` which gets an associative-array of the next possible chords and their probabilities.
Export to MusicTools (the main Java program) format using `exportToMusicToolsFormat`, which tries to reduce filesize, truncate probabilities, and remove unnecessary data.
Finally, `findAllId` outputs an object that contains all used HookTheory metasyntax and their conversions.



[1]: http://forum.hooktheory.com/t/trends-api-chord-input/272
[2]: http://forum.hooktheory.com/t/vizualitation-of-all-chord-progressions-kinda/164/3