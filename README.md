MusicTools
==========

This is a little tool I made that can generate random progressions of 4-part harmony for SATB. It produces notes for 4 voices that are approximately 8-10 notes long. The output is visualized on a graphic staff accompanied by their respective functional chord symbols. 


##Motivation
The primary reason I built this was mostly because I wanted a way of organizing the harmony knowledge I learned in preparation for the RCM Harmony exam I did this summer. 
Proper MVC design was fun too. It ended up being a bigger project than I thought. 
The core part was mapping out the matrices that summarized patterns in the tonic, predominant and dominant harmony sections of a piece of music, which ended up being a central part of my implementation. 


##Implementation and Rationale

My idea was to first generate an appropriate functional chord progression. The progression would end in a cadence of some sort. It would then automatically produce a SATB voicing following said progression that would adhere to traditional rules of 4 part writing. 

I had brainstormed many ways to potentially do this. I ended up doing it in two ways:

* Chaining through a stochastic matrix, specifically a Markov Chain
    * Discrete probabilities would be provided
    * Probabilities could give interesting steady-states over the long run
    * Numbers would come from published analysis (there are many articles online analysing chord progressions in classical and modern music)
    * The hope was that similar patterns would emerge
	* But patterns would be simplistic (see [Here][1] and [Here][2])
* Using multiple matrices, each of which addresses the Tonic, Predominant, and Dominant harmonies, to chain through
    * Wouldn't have probabilities (too subjective)
    * Compiled through my own work
    * Used adjacency matrices to show paths to and from a chord
    * More variations possible of diatonic chord positions (root, inversions), qualities (major, minor, diminished, etc) and types (triads, 7ths, etc)
    * Actually had the added effect of being able to generate appropriate passing chords and suspensions, which was a plus
    * I designed the matrices with root movement in mind


In the end, I would say that the second way was a lot more successful, in that the music generated made more sense from an analysis point of view.


##Rundown of Features

* Can specify key (only major for now) piece is generated in
* Choice between stochastic matrix and tonic-predominant-dominant matrix
* Entry of custom progressions with auto-completion for 4 voicings (try something complicated like `I-V/vi-vi-bVI-IV-ii6-V42-I`)
* Able to select which rules of harmony to follow
* Can also identify intervals, make scales in natural and single sharp/flat keys (this needed to exist for the rest to work)
* Export to Lilypond .ly file, which creates pdf score and midi
* Play progression directly through midi sound synthesis
* Tonicization (still need to fill up the matrix though)


##Generation Bounds
* Start and end on tonic
* Soprano within C4 and A5
* Alto within G3 and D5
* Tenor within C3 and G4
* Bass within E2 and D4
* Soprano, alto, tenor cannot be more than one octave apart
* Bass and tenor cannot be more than Perfect 12 apart
* No parallel octaves/unison (toggle-able)
* No parallel fifths (toggle-able)
* No hidden octaves/unison (toggle-able)
* No hidden fifths (toggle-able)


##Features in the (Near?) Future
* Minor key
* Modulation (have secondary dominants/sub-dominants, etc, now)

I would be happy if anyone wants to contribute to the the T-P-D matrices.


This project uses the Musica (ver 3.12) font found [here][3]. The font provides Unicode support in the 1D100-1D1DD range, which represents the Musical Symbols. The font is offered as "free for any use".

[1]: http://www.hooktheory.com/blog/i-analyzed-the-chords-of-1300-popular-songs-for-patterns-this-is-what-i-found/
[2]: http://www.hooktheory.com/trends
[3]: http://users.teilar.gr/~g1951d/