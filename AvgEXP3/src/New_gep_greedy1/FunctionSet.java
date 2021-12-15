package New_gep_greedy1;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FunctionSet {

	public List<String> sFunction;
	private Map<String, Integer> FunMap; // ӳ�䣬���ַ���Ҳ���Ǻ������͸��������Ӧ�Ĳ���������ϵ����
	public int MaxParamCount; // �������Ĳ�������������Ĳ�����ָ�������Ҫ�Ĳ���������ӷ���Ҫ2��������sin��Ҫ1���ȵ�
	public Constant con;

	/**
	 * ��ʼ�� ���ú�����
	 */
	public FunctionSet(Constant con1) { // ���캯��
		con = con1;
		this.MaxParamCount = 2; // ������������Ƕ�Ԫ�����
		sFunction = new LinkedList<String>();
		String[] temp = con.functionSet; // ���ú�����
		for (int i = 0; i < temp.length; ++i) {
			sFunction.add(temp[i]);
		}

		// ��������� ����ӳ���ϵ
		FunMap = new HashMap<String, Integer>();
		for (int i = 0; i < 4; ++i) {
			FunMap.put(temp[i], 2); // ӳ�����е����뺯����ǰ�ĸ���������Ƕ�Ԫ����������Զ�Ӧ��ֵ����2
		}
		for (int i = 4; i < temp.length; ++i) {
			FunMap.put(temp[i], 1); // ���涼��һԪ���������Ӧ����ֵ��1
		}

	}

	/**
	 * �ж��Ƿ��Ǻ���
	 * 
	 * @param Operator
	 * 
	 * @return
	 */
	public boolean IsFunction(String Operator) {
		return this.sFunction.contains(Operator);
	}

	/**
	 * ���� ����� ����������
	 */
	public int GetParamCount(String Operator) { // ��������������ض�Ӧ����������������
		Integer Inte = FunMap.get(Operator);
		if (Inte == null) {
			return 0;
		} else {
			return Inte.intValue();
		}
	}

	/**
	 * ��ȡ�����������ݲ����������ݣ����Եõ���صĽ��
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
		} else if (Operator.equals("sqrt")) { // ����

			for (int i = 0; i < Data[0].length; i++) {
				result[i] = Math.sqrt(Math.abs(Data[0][i]));
			}
			return result;
		} else if (Operator.equals("tan")) {
			for (int i = 0; i < Data[0].length; i++) {
				result[i] = Math.tan(Data[0][i]);
			}
			return result;
		} else if (Operator.equals("pow2")) { // ƽ��
			for (int i = 0; i < Data[0].length; i++) {
				result[i] = Math.pow(Data[0][i], 2);
			}
			return result;
		} else if (Operator.equals("pow3")) { // ����

			for (int i = 0; i < Data[0].length; i++) {
				result[i] = Math.pow(Data[0][i], 3);
			}
			return result;

		} else if (Operator.equals("ln")) { // ���������Ĭ����eΪ��������ģ�Ҳ����ln�����Ҫ��������log������Ҫ�û��׹�ʽ
			for (int i = 0; i < Data[0].length; i++) {
				result[i] = Math.log(Math.abs(Data[0][i]));
			}
			return result;

		} else if (Operator.equals("abs")) { // �����ֵ
			for (int i = 0; i < Data[0].length; i++) {
				result[i] = Math.abs(Data[0][i]);
			}
			return result;
		} else if (Operator.equals("e")) { // ��e��x�η�
			for (int i = 0; i < Data[0].length; i++) {
				result[i] = Math.exp(Data[0][i]);
			}
			return result;

		} else if (Operator.equals("daoshu")) { // ��e��x�η�
			for (int i = 0; i < Data[0].length; i++) {
				result[i] = 1 / Data[0][i];
			}
			return result;
		} else if (Operator.equals("fushu")) { // ����
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
