import scala.util.parsing.combinator._

class ReversePolish extends JavaTokenParsers {
    def expr:   Parser[Float] = rep(term ~ ("*" | "/" | "+" | "-")) ^^ {
      case ops => 
        var result = 0.0f
        ops.foreach(op =>
          op match {
            case nums ~ "+" => result += nums.reduceRight((x, res) => res + x)
            case nums ~ "-" => result -= nums.reduceRight((x, res) => res - x)
            case nums ~ "*" => result *= nums.reduceRight((x, res) => res * x)
            case nums ~ "/" =>
              if (result > 0)
                result /= nums.reduceRight((x, res) => if (res > 0) (res / x) else 0.0f)
              else
                0.0f
            case _ => 0.0f
          }
        )
        result
    }
    def term:   Parser[List[Float]] = rep(factor)
    def factor: Parser[Float] = num | "(" ~> expr <~ ")" ^^ (_.toFloat)
    def num:    Parser[Float] = floatingPointNumber ^^ (_.toFloat)

    def parse(s: String) = parseAll(expr, s)
}

object RPNCalculator extends ReversePolish {
    def main(args: Array[String]) {
      println("input: " + args(0))
      println("result: " + parse(args(0)))
    }
}
