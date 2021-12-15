package New_gep_greedy1;

import java.io.File;

/**
 * 这里存储所有全局常量，也就是超参数
 * 
 * @author 
 */

public class Constant {

	public int GeneCount = 1; // 基因数量
	public int headlength = 21; // 头部基因长度
	public int taillength; // 尾部基因长度
	public int chrome_length; // 染色体长度，包含了常量域
	public int GeneLength; // 基因长度
	public int MaxGeneration = 100000; // 最大迭代次数
	public int GreedyConstant = 100000; // 贪心算法的迭代次数
	public double GreedyRate = 0.5; // 贪心算法的贪心参数
	public int Populationsize = 100; // 种群数量
	public int mostindivs_number = 5; // 每代选取的精英数量
	public double MutationRate = 0.1; // 变异率0.01//变异率0.03
	public double OnePRecomRate = 0.7; // 一点重组概率0.5
	public double TwoPRecomRate = 0.3; // 两点重组概率
	public double GeneRecomRate = 0.1; // 基因重组概率

	public int column; // 数据集列数
	public int row; // 数据集行数
	public int constant_number = 4;
	public File file;
	public int deep; // 信息树层数

	public String[] fullSet;
	public String[] featureSet;
	public String[] functionSet;

	public Constant(String[] fullSet, String[] featureSet, String[] functionSet, int column, int row, File file,
			int deep) {

		this.taillength = this.headlength + 1;
		this.GeneLength = this.taillength + this.headlength;
		this.chrome_length = this.GeneLength + this.taillength;

		this.fullSet = fullSet; // 设置全集，也就是头部取值范围
		this.featureSet = featureSet; // 设置函数集，也就是尾部取值范围
		this.functionSet = functionSet; // 设置函数集

		this.column = column;
		this.row = row;
		this.file = file;
		this.deep = deep;
	}

}