package steve6472.sge.gfx.game.blockbench.animation;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.ValidationResult;
import net.objecthunter.exp4j.function.Function;
import steve6472.sge.main.util.MathUtil;

/**********************
 * Created by steve6472
 * On date: 24.10.2020
 * Project: CaveGame
 *
 ***********************/
public class ExpressionValue implements IKeyValue
{
	private final Expression expression;

	/*
	 * Replace sin and cos functions with ones that use degrees instead of angles
	 */
	private static final Function degSin = new Function("sin")
	{
		@Override
		public double apply(double... doubles)
		{
			return Math.sin(Math.toRadians(doubles[0]));
		}
	};

	private static final Function degCos = new Function("cos")
	{
		@Override
		public double apply(double... doubles)
		{
			return Math.cos(Math.toRadians(doubles[0]));
		}
	};

	private static final Function clamp = new Function("clamp", 3)
	{
		@Override
		public double apply(double... doubles)
		{
			final double clamp = MathUtil.clamp(doubles[0], doubles[1], doubles[2]);
			System.out.println(clamp);
			return 1;
		}
	};

	public ExpressionValue(String expression)
	{
		expression = expression.replaceAll("math\\.","");

		boolean hasAnimTime = expression.contains("query.anim_time");

		if (hasAnimTime)
			expression = expression.replaceAll("query\\.anim_time","t");

		ExpressionBuilder builder = new ExpressionBuilder(expression);
		builder.function(degSin);
		builder.function(degCos);
		builder.function(clamp);

		if (hasAnimTime)
			builder.variable("t");

		this.expression = builder.build();

		if (hasAnimTime)
			this.expression.setVariable("t", 0);

		ValidationResult validate = this.expression.validate();
		if (!validate.isValid())
		{
			System.err.println("expression '" + expression + "' has errors!");
			validate.getErrors().forEach(System.err::println);
		}
	}

	@Override
	public float getValue(double time)
	{
		return (float) expression.setVariable("t", time).evaluate();
	}
}
