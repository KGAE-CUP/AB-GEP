package New_gep_greedy1;

import java.util.LinkedList;
import java.util.List;

//  更改了内容的行数：31与32
//

/**
 * 基因表达 计算基因的值
 **/
public class Expression {

	private FunctionSet Fun;
	private double[][] Data;
	private double[][] GeneData; // 填充数据 的
	private List<Integer> constant;
	private int constant_number; // 这个是存储基因中常量运算符？的位置

	private int nValidLen; // 有效长度
	private int GeneLength; // 基因长度
	private int GeneCount; // 基因数量
	public boolean work = true;
	private Constant con;

	public Expression(Constant con1) { // 构造函数
		this.con = con1;
		Fun = new FunctionSet(con);
		this.constant_number = 0;
		this.constant = new LinkedList<Integer>();
		this.GeneLength = con.GeneLength; // 这里改了一些内容、、、、、、、、
		// System.out.println("染色体的长度：" + GeneLength);
		this.GeneCount = con.GeneCount; // 这里也改了一些内容；；；；；；；
		this.GeneData = new double[GeneLength][con.column]; // 这里的GeneData用来存储基因中非运算符的位
	}

	/**
	 * 计算每个基因的值
	 * 
	 * @param constant_dc
	 * 
	 * @param Gene
	 */
	public double[] GetGeneValue(Individual indiv, double[][] Data1, List<Double> constant_dc) { // 计算每个基因的值
		this.Data = Data1; // 这里的Data应该是数据集,第一位表示行数，第二位表示列数
		List<String> Gene = indiv.Chrom_gene;
		this.constant = indiv.Chrom_constant; // 染色体的常量域
		this.constant_number = 0;

		this.FillData(Gene, constant_dc); // 这个函数主要是将基因里面的数值全都导入到一个数组GeneData中
		int nButton = this.GetValidLength(Gene); // 得到基因的有效长度，这里的有效长度是实际长度，不包含0

		int nParamCount;
		int j, k;
		int i = nButton - 1; // 减1是为了包含0，指第几位
		for (; i >= 0; --i) { // 从后向前计算

			nParamCount = Fun.GetParamCount(Gene.get(i)); // 这个参数是操作符对应的参数

			if (nParamCount > 0) { // 如果对应的参数大于0，表示这个基因位是一个运算符，然后就可以进行运算了
				double[][] dData = new double[nParamCount][Data[0].length];
				k = 0;
				for (j = nButton - nParamCount; j < nButton; ++j) {
					dData[k++] = this.GeneData[j]; // 将需要的参数引入到该运算符的数组中
				}
				// System.out.println("运算符为：" + Gene.get(i) + " 所需参数：" + nParamCount + " 所给参数："
				// + dData.length);
				this.GeneData[i] = Fun.GetResult(Gene.get(i), dData, indiv.Vladislavleva_constant);

				nButton -= nParamCount;

			}
		}

		return GeneData[0]; // 返回该基因的最终结果，也可以说是表达式的输出
	}

	/**
	 * 计算基因的有效长度
	 * 
	 * @return
	 */
	public int GetValidLength(List<String> Gene) { // 参数为基因,返回值为有效长度
		int i = 0;
		int nValidLen = 1; // 这个是有效长度的计量
		int nParam; // 这个是计算参数的个数
		do {
			// System.out.println("得到长度：" + i);
			nParam = Fun.GetParamCount(Gene.get(i)); // 根据这个基因得到该函数对应的参数个数

			nValidLen += nParam; // 注意当基因的第i位不是运算符的时候，返回的参数个数为0，也就是有效长度增加为0
			++i;
		} while (i < nValidLen);
		return nValidLen;
	}

	/**
	 * 填充数据
	 * 
	 * @param Gene
	 * @param constant_dc
	 */
	private void FillData(List<String> Gene, List<Double> constant_dc) { // 这个函数得到的一个基因
		int nLen = this.GetValidLength(Gene); // 记录这个基因的有效长度
		this.constant_number = 0;
		for (int i = 0; i < nLen; ++i) { // 从一开始到有效长度开始进行循环遍历
			int nParam = Fun.GetParamCount(Gene.get(i)); // Fun是函数集类的对象，对每一个基因位计算参数
			if (0 == nParam) { // 如果基因位对应的参数位为0，则说明这一位是一个数值
				String sNum = Gene.get(i); // 得到该基因位的数值
				if (sNum.equals("?")) { // 如果是常量域，则直接找常量
					int nIndex = this.constant.get(this.constant_number);
					double temple = constant_dc.get(nIndex); // 常量赋值，要赋值一样的，不过个数比较多，要nCol个
					for (int j = 0; j < Data[0].length; j++) {
						GeneData[i][j] = temple;
					}
					this.constant_number++;
				} else {
					int nIndex = Integer.parseInt(sNum); // 基因中的数值都是代表了位置，不是真实的数值
					GeneData[i] = Data[nIndex]; // 根据基因的数值索引得到想要的数值
				}

			}
		}
	}

	public int GetIndivValidLen(Individual Indiv) { // 得到整个个体的有效长度（多个基因）
		int i;
		int res = 0;
		// 计算每个基因的值 连接函数使用 +
		// //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		for (i = 0; i < this.GeneCount; ++i) {
			List<String> listGene = Indiv.Chrom_gene.subList(i * GeneLength, i * GeneLength + GeneLength);
			// Indiv中是一个个体实例，Chrom是一个list集合（String），
			res += GetValidLength(listGene);
		}
		return res;
	}

	/**
	 * 计算使用到的属性的个数
	 * 
	 * @param Indiv
	 * @return
	 */
	public int GetAttriNum(Individual Indiv) { // 应该没有用到过

		int i;
		int res = 0;
		// 计算每个基因的值 连接函数使用 +
		// //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		for (i = 0; i < this.GeneCount; ++i) {
			List<String> listGene = Indiv.Chrom_gene.subList(i * GeneLength, i * GeneLength + GeneLength);
			int nLen = GetValidLength(listGene); // 上面那一句是得到一个基因，这句是得到整个基因的有效长度
			for (int j = 0; j < nLen; ++j) {
				if (0 == Fun.GetParamCount(listGene.get(j))) {
					++res; // 得到该基因的有效数值个数
				}
			}

		}
		return res; // 返回的是整个染色体基因中有效的数值（变量以及常量）个数
	}

}