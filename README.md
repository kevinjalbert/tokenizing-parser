Tokenizing Parser
=====
[![Gitter Chat](http://img.shields.io/chat/gitter.png?color=brightgreen)](https://gitter.im/kevinjalbert/tokenizing-parser)

#### Step 1:
Include the source code into your project and import the classes.

#### Step 2:
Create a language definition for the language you are interested in tokenizing and parsing. This is done by extending the LanguageKeywords.java class. You can see an example of this in in the JavaKeywords.java class.

#### Step 3:
Now create an instance of the Tokenizer.java class with the language specified.

* Example:	`Tokenizer tokenizer = new Tokenizer( JavaKeywords.getInstance() );`

#### Step 4:
Now perform the tokenization of the input (there are two forms of output, one with identifiers replacing the tokens, or one without the identifiers). The output is an arraylist of string values. The first example will replace tokens with identifiers; the flag is to	keep or remove comments, while the second will tokenize without any comments or string literals

* Example:	`ArrayList<String> tokenizeInput = tokenizer.tokenizeInputWithMapping(input, false );`

* Example:	`ArrayList<String> tokenizeInput = tokenizer.tokenizeInput(input);`

#### Step 5:
If the input was tokenized with the identifiers used, there the TokenTable.java class will indirectly contain all the mapping of these values. The Tokenizer.java class has a method to acquire the TokenTable.java object being used.

* Example:	`tokenizer.getTokenTable();`

#### Step 6:
The TokenTable.java class holds AutoKeyHashMap.java objects, this is where the mapping of the identifiers to the held values are found. The way to acquire the actual value is to first acquire the AutoKeyHashMap.java object that it belongs to. After acquiring the map then it's possible to acquire the value of the tokenIdentifier.

* Example:	`tokenizer.getTokenTable().getMap(tokenIdentifier);`

* Example:	`tokenizer.getTokenTable.getMap(tokenIdentifier).getValue(tokenIdentifier);`

#### Step 7:
The tokenizer can be used for multiple inputs, it does not need to be done in one shot. The only thing to be aware of is that the TokenTable.java object will keep building up the mappings, so if you want a fresh map.

* Example:	`tokenizer.clearTokenTable();`
