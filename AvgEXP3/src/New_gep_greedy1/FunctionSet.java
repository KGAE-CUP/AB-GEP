package New_gep_greedy1;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FunctionSet {

	public List<String> sFunction;
	private Map<String, Integer> FunMap; // 映射，将字符串也就是函数，和该运算符对应的参数个数联系起来
	public int MaxParamCount; // 设置最大的参数数量，这里的参数是指运算符需要的参数，比如加法需要2个参数，sin需要1个等等
	public Constant con;

	/**
	 * 初始化 设置函数集
	 */
	public FunctionSet(Constant con1) { // 构造函数
		con = con1;
		this.MaxParamCount = 2; // 最大的运算符就是二元运算符
		sFunction = new LinkedList<String>();
		String[] temp = con.functionSet; // 设置函数集
		for (int i = 0; i < temp.length; ++i) {
			sFunction.add(temp[i]);
		}

		// 与参数个数 建立映射关系
		FunMap = new HashMap<String, Integer>();
		for (int i = 0; i < 4; ++i) {
			FunMap.put(temp[i], 2); // 映射类中的输入函数，前四个运算符都是二元运算符，所以对应的值都是2
		}
		for (int i = 4; i < temp.length; ++i) {
			FunMap.put(temp[i], 1); // 后面都是一元运算符，对应的数值是1
		}

	}

	/**
	 * 判断是否是函数
	 * 
	 * @param Operator
	 * 
	 * @return
	 */
	public boolean IsFunction(String Operator) {
		return this.sFunction.contains(Operator);
	}

	/**
	 * 返回 运算符 操作数个数
	 */
	public int GetParamCount(String Operator) { // 输入操作符，返回对应操作符操作数个数
		Integer Inte = FunMap.get(Operator);
		if (Inte == null) {
			return 0;
		} else {
			return Inte.intValue();
		}
	}

	/**
	 * 获取计算结果，传递操作符和数据，可以得到相关的结果
	 */
	public double[] GetResult(String Operator, double[][] Data, double constant) {

		double[] result = new double[Data[0].length];

		if (Operator.equals("+")) {

			for (int i = 0; i < Data[0].length; i++) {
				result[i] = Data[0][i] + Data[1][i];
			}
			return result;
		} else if (Operator.equals("-")) {

			for (int i = 0; i < Data[0].length; i++) {
				result[i] = Data[0][i] - Data[1][i];
			}
			return result;
		} else if (Operator.equals("*")) {

			for (int i = 0; i < Data[0].length; i++) {
				result[i] = Data[0][i] * Data[1][i];
			}
			return result;
		} else if (Operator.equals("/")) {

			for (int i = 0; i < Data[0].length; i++) {
				result[i] = Data[0][i] / Data[1][i];
			}
			return result;
		} else if (Operator.equals("sin")) {
			for (int i = 0; i < Data[0].length; i++) {
				result[i] = Math.sin(Data[0][i]);
			}
			return result;
		} else if (Operator.equals("cos")) {
			for (int i = 0; i < Data[0].length; i++) {
				result[i] = Math.cos(Data[0][i]);
			}
			return result;
		} else if (Operator.equals("sqrt")) { // 开方

			for (int i = 0; i < Data[0].length; i++) {
				result[i] = Math.sqrt(Math.abs(Data[0][i]));
			}
			return result;
		} else if (Operator.equals("tan")) {
			for (int i = 0; i < Data[0].length; i++) {
				result[i] = Math.tan(Data[0][i]);
			}
			return result;
		} else if (Operator.equals("pow2")) { // 平方
			for (int i = 0; i < Data[0].length; i++) {
				result[i] = Math.pow(Data[0][i], 2);
			}
			return result;
		} else if (Operator.equals("pow3")) { // 立方

			for (int i = 0; i < Data[0].length; i++) {
				result[i] = Math.pow(Data[0][i], 3);
			}
			return result;

		} else if (Operator.equals("ln")) { // 这个函数是默认以e为底来计算的，也就是ln，如果要求其他的log函数，要用换底公式
			for (int i = 0; i < Data[0].length; i++) {
				result[i] = Math.log(Math.abs(Data[0][i]));
			}
			return result;

		} else if (Operator.equals("abs")) { // 求绝对值
			for (int i = 0; i < Data[0].length; i++) {
				result[i] = Math.abs(Data[0][i]);
			}
			return result;
		} else if (Operator.equals("e")) { // 求e的x次方
			for (int i = 0; i < Data[0].length; i++) {
				result[i] = Math.exp(Data[0][i]);
			}
			return result;

		} else if (Operator.equals("daoshu")) { // 求e的x次方
			for (int i = 0; i < Data[0].length; i++) {
				result[i] = 1 / Data[0][i];
			}
			return result;
		} else if (Operator.equals("fushu")) { // 求负数
			for (int i = 0; i < Data[0].length; i++) {
				result[i] = 0 - Data[0][i];
			}
			return result;

		} else if (Operator.equals("C1")) {
			for (int i = 0; i < Data[0].length; i++) {
				result[i] = Math.pow(Data[0][i], constant);
			}
			return result;
		} else if (Operator.equals("C2")) {

			for (int i = 0; i < Data[0].length; i++) {
				result[i] = Data[0][i] + constant;
			}
			return result;
		} else if (Operator.equals("C3")) {

			for (int i = 0; i < Data[0].length; i++) {
				result[i] = Data[0][i] * constant;
			}
			return result;
		}

		return result;
	}

}
