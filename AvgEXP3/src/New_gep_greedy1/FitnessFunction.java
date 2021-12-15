package New_gep_greedy1;

import java.util.List;

/**
 * 适应度函数 对不同的问题采用不同的适应度
 * 
 * @author shenzhan
 *
 */
public class FitnessFunction {

	/**
	 * 个体表达类
	 */
	protected Expression Exp;
	public boolean work = true;
	public Constant con;

	public FitnessFunction(Constant con1) {
		this.con = con1;

	}

	/**
	 * 计算适应度
	 * 
	 * @param Pop
	 * @param Data
	 * @param constant_dc
	 * @param Fitness
	 */
	public double GetFitness(Individual individual, double[][] Data, List<Double> constant_dc) {
		Exp = new Expression(con);
		int nRow = Data.length; // 数据集的行数
		int nCol = Data[0].length; // 数据集的列数
		double value = 0; // 累积每行数据计算的值

		int j, i = 0;

		double[] Value = Exp.GetGeneValue(individual, Data, constant_dc);

		for (j = 0; j < Value.length; j++) { // 针对每一类的公式值求和

			if (!Double.isNaN(Value[j]) && !Double.isInfinite(Value[j])) {
				value = value + Math.pow(Value[j] - Data[nRow - 1][j], 2);
			} else {
				i++;
			}

		}

		if (i < 20) { // 如果这个公式没有被抛弃，返回适应度
			double fitness = Math.sqrt(value / (nCol - i)); // 求均方误差

			return fitness;
		} else {

			return 500000;
		}

	}
}