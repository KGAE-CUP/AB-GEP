package New_gep_greedy1;

import java.util.LinkedList;
import java.util.List;

//  ���������ݵ�������31��32
//

/**
 * ������ ��������ֵ
 **/
public class Expression {

	private FunctionSet Fun;
	private double[][] Data;
	private double[][] GeneData; // ������� ��
	private List<Integer> constant;
	private int constant_number; // ����Ǵ洢�����г������������λ��

	private int nValidLen; // ��Ч����
	private int GeneLength; // ���򳤶�
	private int GeneCount; // ��������
	public boolean work = true;
	private Constant con;

	public Expression(Constant con1) { // ���캯��
		this.con = con1;
		Fun = new FunctionSet(con);
		this.constant_number = 0;
		this.constant = new LinkedList<Integer>();
		this.GeneLength = con.GeneLength; // �������һЩ���ݡ���������������
		// System.out.println("Ⱦɫ��ĳ��ȣ�" + GeneLength);
		this.GeneCount = con.GeneCount; // ����Ҳ����һЩ���ݣ�������������
		this.GeneData = new double[GeneLength][con.column]; // �����GeneData�����洢�����з��������λ
	}

	/**
	 * ����ÿ�������ֵ
	 * 
	 * @param constant_dc
	 * 
	 * @param Gene
	 */
	public double[] GetGeneValue(Individual indiv, double[][] Data1, List<Double> constant_dc) { // ����ÿ�������ֵ
		this.Data = Data1; // �����DataӦ�������ݼ�,��һλ��ʾ�������ڶ�λ��ʾ����
		List<String> Gene = indiv.Chrom_gene;
		this.constant = indiv.Chrom_constant; // Ⱦɫ��ĳ�����
		this.constant_number = 0;

		this.FillData(Gene, constant_dc); // ���������Ҫ�ǽ������������ֵȫ�����뵽һ������GeneData��
		int nButton = this.GetValidLength(Gene); // �õ��������Ч���ȣ��������Ч������ʵ�ʳ��ȣ�������0

		int nParamCount;
		int j, k;
		int i = nButton - 1; // ��1��Ϊ�˰���0��ָ�ڼ�λ
		for (; i >= 0; --i) { // �Ӻ���ǰ����

			nParamCount = Fun.GetParamCount(Gene.get(i)); // ��������ǲ�������Ӧ�Ĳ���

			if (nParamCount > 0) { // �����Ӧ�Ĳ�������0����ʾ�������λ��һ���������Ȼ��Ϳ��Խ���������
				double[][] dData = new double[nParamCount][Data[0].length];
				k = 0;
				for (j = nButton - nParamCount; j < nButton; ++j) {
					dData[k++] = this.GeneData[j]; // ����Ҫ�Ĳ������뵽���������������
				}
				// System.out.println("�����Ϊ��" + Gene.get(i) + " ���������" + nParamCount + " ����������"
				// + dData.length);
				this.GeneData[i] = Fun.GetResult(Gene.get(i), dData, indiv.Vladislavleva_constant);

				nButton -= nParamCount;

			}
		}

		return GeneData[0]; // ���ظû�������ս����Ҳ����˵�Ǳ��ʽ�����
	}

	/**
	 * ����������Ч����
	 * 
	 * @return
	 */
	public int GetValidLength(List<String> Gene) { // ����Ϊ����,����ֵΪ��Ч����
		int i = 0;
		int nValidLen = 1; // �������Ч���ȵļ���
		int nParam; // ����Ǽ�������ĸ���
		do {
			// System.out.println("�õ����ȣ�" + i);
			nParam = Fun.GetParamCount(Gene.get(i)); // �����������õ��ú�����Ӧ�Ĳ�������

			nValidLen += nParam; // ע�⵱����ĵ�iλ�����������ʱ�򣬷��صĲ�������Ϊ0��Ҳ������Ч��������Ϊ0
			++i;
		} while (i < nValidLen);
		return nValidLen;
	}

	/**
	 * �������
	 * 
	 * @param Gene
	 * @param constant_dc
	 */
	private void FillData(List<String> Gene, List<Double> constant_dc) { // ��������õ���һ������
		int nLen = this.GetValidLength(Gene); // ��¼����������Ч����
		this.constant_number = 0;
		for (int i = 0; i < nLen; ++i) { // ��һ��ʼ����Ч���ȿ�ʼ����ѭ������
			int nParam = Fun.GetParamCount(Gene.get(i)); // Fun�Ǻ�������Ķ��󣬶�ÿһ������λ�������
			if (0 == nParam) { // �������λ��Ӧ�Ĳ���λΪ0����˵����һλ��һ����ֵ
				String sNum = Gene.get(i); // �õ��û���λ����ֵ
				if (sNum.equals("?")) { // ����ǳ�������ֱ���ҳ���
					int nIndex = this.constant.get(this.constant_number);
					double temple = constant_dc.get(nIndex); // ������ֵ��Ҫ��ֵһ���ģ����������Ƚ϶࣬ҪnCol��
					for (int j = 0; j < Data[0].length; j++) {
						GeneData[i][j] = temple;
					}
					this.constant_number++;
				} else {
					int nIndex = Integer.parseInt(sNum); // �����е���ֵ���Ǵ�����λ�ã�������ʵ����ֵ
					GeneData[i] = Data[nIndex]; // ���ݻ������ֵ�����õ���Ҫ����ֵ
				}

			}
		}
	}

	public int GetIndivValidLen(Individual Indiv) { // �õ������������Ч���ȣ��������
		int i;
		int res = 0;
		// ����ÿ�������ֵ ���Ӻ���ʹ�� +
		// //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		for (i = 0; i < this.GeneCount; ++i) {
			List<String> listGene = Indiv.Chrom_gene.subList(i * GeneLength, i * GeneLength + GeneLength);
			// Indiv����һ������ʵ����Chrom��һ��list���ϣ�String����
			res += GetValidLength(listGene);
		}
		return res;
	}

	/**
	 * ����ʹ�õ������Եĸ���
	 * 
	 * @param Indiv
	 * @return
	 */
	public int GetAttriNum(Individual Indiv) { // Ӧ��û���õ���

		int i;
		int res = 0;
		// ����ÿ�������ֵ ���Ӻ���ʹ�� +
		// //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		for (i = 0; i < this.GeneCount; ++i) {
			List<String> listGene = Indiv.Chrom_gene.subList(i * GeneLength, i * GeneLength + GeneLength);
			int nLen = GetValidLength(listGene); // ������һ���ǵõ�һ����������ǵõ������������Ч����
			for (int j = 0; j < nLen; ++j) {
				if (0 == Fun.GetParamCount(listGene.get(j))) {
					++res; // �õ��û������Ч��ֵ����
				}
			}

		}
		return res; // ���ص�������Ⱦɫ���������Ч����ֵ�������Լ�����������
	}

}