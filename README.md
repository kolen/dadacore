Dadacore
========

Random text generator based on ngram model on words (Markov model) with support for learning on new text dynamically and with web UI.

Features
--------
* Learning on new text dynamically, with adding of new text via web UI.
* Ngram model with configurable order (seems that order=4 yields best results).
* Ngram model based on words and punctuation character sequences between words. Punctuation is preserved when generating.
* Unicode.

### Planned
* API for external apps (generating and learning)
* Classification of generated results
* Chatbot mode (replies to input phrases with learning input phrases)

Building
--------
* Install [sbt](http://www.scala-sbt.org/)
* Run `sbt update`
* To run development server, run `sbt container:start`
* To build .war, run `sbt package-war`, as usual

Other
-----

Inspired by [MegaHAL](http://megahal.alioth.debian.org/), but created to use for different purposes. Replying to input sentences is not yet implemented.

It is created to overcome non-unicodity of MegaHAL (for example, some of MegaHAL integration modules for [Eggdrop](http://www.eggheads.org/) IRC bot framework used TCL code called from C code, to make it handle Cyrilic characters). Also, concept was to not create a chatbot, but text generator.

Dadacore uses [the same principle that MegaHal](http://megahal.alioth.debian.org/How.html):

> MegaHAL is able to construct a model of language based on the evidence it encounters while conversing with the user. To begin with, the input received from the user is parsed into an alternating sequence of words and non-words, where a word is a series of alphanumeric characters and a non-word is a series of other characters. This is done to ensure not only that new words are learned, but that the separators between them are learned as well. If the user has a habit of putting a double space after a full stop, for instance, MegaHAL will do just the same*.
>
> The resulting string of symbols[2] is used to train two 4<sup>th</sup>-order Markov models (Jelinek, 1986). One of these models can predict which symbol will following any sequence of four symbols, while the other can predict which symbol will precede any such sequence. Markov models express their predictions as a probability distribution over all known symbols, and are therefore capable of choosing likely words over unlikely ones. Models of order 4 were chosen to ensure that the prediction is based on two words; this has been found necessary to produce output resembling natural language (Hutchens, 1994).

\* Actually even MegaHAL collapses multiple spaces to one.

Dadacore has no two-directional model as MegaHAL has, and not yet measures cross-entropy against model to filter generated sentences.

MIT License
-----------

Copyright (C) 2011 by dadacore contributors

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
