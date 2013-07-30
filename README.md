## What is this?

A small command line calculator built on scala which accepts operations in [Reverse Polish Notation](http://en.wikipedia.org/wiki/Reverse_Polish_notation).

## Why?

Someone posted the equivalent version of this in Ruby, and asked anyone who was up for the challenge to produce their own using a language of their choice.
Since I'm not the kind of person to let an opportunity to code in Scala go by, I sat down and wrote this out after brushing up on my parser combinators.

## Usage

To run this, you'll need scala and sbt installed, which on OSX is as simple as:

```
brew install scala sbt
```

Then, from the project directory, run `sbt assembly` to produce the executable jar, and finally, to use the calculator:

```
java -jar ./target/scala-2.XX/RPNCalculator-assembly-X.X.jar "3 4 - 5 + 2 *"
```

Alternatively you can use `sbt "run \"3 4 - 5 + 2 *\""`, to compile and run the code directly using your scala installation.

Of course, you can put whatever expression you want as the argument, but the above is a simple example.
