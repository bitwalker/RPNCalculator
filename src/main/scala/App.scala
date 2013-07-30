import scala.util.parsing.combinator._

/**
 * This class handles the input parsing
 * JavaTokenParsers is extended in order to use the floatingPointNumber parser
 */
class ReversePolish extends JavaTokenParsers {
    /**
     * Takes an expression, which consists of N repetitions of a term followed by an operator
     * In case you are wondering, the parser combinators used here are as follows:
     *  |   => The alternation combinator, it parses successfully if either the left or right side match
     *  ~   => This combinator forms a sequential combination of it's operands (ex. a~b expects a followed by b)
     *  ~>  => This combinator says "ensure the left operand exists, but don't include it in the result"
     *  <~  => This combinator says "ensure the right operand exists, but don't include it in the result"
     *  ^^  => This combinator says "if parsed successfully, transform the result using the block on the right"
     *  rep => This combinator says "expect zero or more repetitions of X"
     */
    def expr:   Parser[Float] = rep(term ~ ("*" | "/" | "+" | "-")) ^^ {
      // match a list of terms
      case ops => 
        var result = 0.0f
        ops.foreach(op =>
          // match on the operator to perform the appropriate calculation
          op match {
            // for each calculation, apply the operator to the result and all of the numbers in the term
            case nums ~ "+" => result += nums.reduceRight((x, res) => res + x)
            case nums ~ "-" => result -= nums.reduceRight((x, res) => res - x)
            case nums ~ "*" => result *= nums.reduceRight((x, res) => res * x)
            case nums ~ "/" =>
              // can't divide by zero
              if (result > 0)
                result /= nums.reduceRight((x, res) => if (res > 0) (res / x) else 0.0f)
              else
                0.0f
            // this will probably never be hit, as the parser will catch invalid syntax, but just to be safe...
            case _ => 0.0f
          }
        )
        result
    }

    // Parses N factors
    def term:   Parser[List[Float]] = rep(factor)

    // Parses either a number, or another expression (wrapped in parens), and converts to Float
    def factor: Parser[Float] = num | "(" ~> expr <~ ")" ^^ (_.toFloat)

    // Parses a floating point number as a String and converts it to Float
    def num:    Parser[Float] = floatingPointNumber ^^ (_.toFloat)

    // Parse an expression and return the result as a String
    def parse(s: String) = parseAll(expr, s)
}

object RPNCalculator extends ReversePolish {
    def main(args: Array[String]) {
      println("input: " + args(0))
      println("result: " + parse(args(0)))
    }
}
