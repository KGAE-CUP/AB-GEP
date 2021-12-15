package New_gep_greedy1;

import java.util.List;

/**
 * ��Ӧ�Ⱥ��� �Բ�ͬ��������ò�ͬ����Ӧ��
 * 
 * @author shenzhan
 *
 */
public class FitnessFunction {

	/**
	 * ��������
	 */
	protected Expression Exp;
	public boolean work = true;
	public Constant con;

	public FitnessFunction(Constant con1) {
		this.con = con1;

	}

	/**
	 * ������Ӧ��
	 * 
	 * @param Pop
	 * @param Data
	 * @param constant_dc
	 * @param Fitness
	 */
	public double GetFitness(Individual individual, double[][] Data, List<Double> constant_dc) {
		Exp = new Expression(con);
		int nRow = Data.length; // ���ݼ�������
		int nCol = Data[0].length; // ���ݼ�������
		double value = 0; // �ۻ�ÿ�����ݼ����ֵ

		int j, i = 0;

		double[] Value = Exp.GetGeneValue(individual, Data, constant_dc);

		for (j = 0; j < Value.length; j++) { // ���ÿһ��Ĺ�ʽֵ���

			if (!Double.isNaN(Value[j]) && !Double.isInfinite(Value[j])) {
				value = value + Math.pow(Value[j] - Data[nRow - 1][j], 2);
			} else {
				i++;
			}

		}

		if (i < 20) { // ��������ʽû�б�������������Ӧ��
			double fitness = Math.sqrt(value / (nCol - i)); // ��������

			return fitness;
		} else {

			return 500000;
		}

	}
}