package New_gep_greedy1;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

//注意一下，在GEP中染色体是包含多个基因的，每个基因都是一个子表达式树

/**
 * 染色体Chromosome
 * 
 **/
public class Individual implements Cloneable {
	/**
	 * 基因的适应值
	 **/
	public double Fitness;

	public double Value;

	public double Wheelvalue; // 因为赌轮盘时，值大的容易被选中，而我的适应度是设置的越小越好，这里要设置一个单独的赌轮盘值
	public boolean live; // 表明该个体是否已经被赋值，如果没有被赋值则为false，否则为ture，没什么大用，主要是用于最优适应度上
	public boolean work = true; // 表明这个公式个体是否有效，如果无效要被删除！
	private int chrome_length;
	private int GeneLength; // 基因长度
	private int GeneCount; // 基因数量
	private int headlength;
	private int taillength;
	private double[][] Dataset;
	Random random = new Random();
	public Constant con;
	public File file;

	public double Vladislavleva_constant;

	public List<Object> Chrom; // 染色体 字符串
	public List<String> Chrom_gene; // 染色体中的基因区域
	public List<Integer> Chrom_constant; // 染色体中的常量区域，这两个的加和等于上面的染色体Chrome

	/*
	 * 构造函数，个体对象一旦创建，就生成对应的染色体字符串
	 */
	public Individual(Constant con1) {

		con = con1;
		this.Chrom = new LinkedList<Object>();
		this.Chrom_gene = new LinkedList<String>();
		this.Chrom_constant = new LinkedList<Integer>();
		this.live = false;
		this.file = con.file;
		this.chrome_length = con.chrome_length; // 染色体长度，包含常量域
		this.GeneLength = con.GeneLength; // 只是基因长度
		this.GeneCount = con.GeneCount;
		this.headlength = con.headlength;
		this.taillength = con.taillength;

		this.Vladislavleva_constant = random.nextDouble() * 5 * (Math.pow(-1, random.nextInt(2))); // 创造常量

	}

	/*
	 * 求该个体的适应度
	 */
	public double GetFitness(List<Double> constant_dc) throws IOException {

		Data data = new Data(con); // 根节点生成时，创建数据集
		Dataset = data.getData();

		this.separate();

		FitnessFunction fit = new FitnessFunction(con);

		this.Fitness = fit.GetFitness(this, Dataset, constant_dc); // 调用适应度函数，得到适应度

		return Fitness;

	}

	/*
	 * 个体初始化，也就随机产生一个个体
	 */
	public Individual Initialization() {
		FullSet full = new FullSet(con);
		FeatureSet feature = new FeatureSet(con);

		int j = 0;
		Individual indiv = new Individual(con);
		for (; j < indiv.headlength; j++) {
			String gene = full.getRandom();

			indiv.Chrom.add(gene);
		}
		for (j = 0; j < indiv.taillength; j++) {
			String gene = feature.getRandom();
			indiv.Chrom.add(gene);

		}
		indiv.Initialization_constant(); // 常量域初始化

		return indiv;

	}

	/*
	 * 常量域初始化
	 */
	public void Initialization_constant() {
		for (int j = 0; j < this.taillength; j++) { // 添加常量域， 长度与尾部长度一样
			int gene = random.nextInt(this.con.constant_number);
			this.Chrom.add(gene);

		}
	}

	/**
	 * 添加基因
	 * 
	 * @param gene
	 */
	public void AddGene(List<String> gene) { // 添加基因，需要传递参数，将该基因添加到染色体中

		for (int i = 0; i < gene.size(); ++i) {
			String a = gene.get(i);
			this.Chrom.add(a); // 字符串域+常量域
		}
	}

	/**
	 * 将染色体的全部基因分成两部分，求解适应度的时候好求
	 */
	public void separate() {
		this.Chrom_gene = new LinkedList<String>();
		this.Chrom_constant = new LinkedList<Integer>();

		for (int i = 0; i < con.GeneLength; i++) {

			this.Chrom_gene.add((String) this.Chrom.get(i));
		}
		for (int i = con.GeneLength; i < con.chrome_length; i++) {
			int temp = (int) this.Chrom.get(i);

			this.Chrom_constant.add(temp);
		}
	}

	/**
	 * Get
	 * 
	 * @param nIndex
	 * @return
	 */
	public String Get(int nIndex) { // 这里是传递索引参数，然后返回染色体中该索引对应的字符串
		return Chrom_gene.get(nIndex);
	}

	/*
	 * 和上面的大写Get有所区别
	 */
	public Object get(int nIndex) { // 这里是传递索引参数，然后返回染色体中该索引对应的字符串
		return this.Chrom.get(nIndex);
	}

	public int getConstant(int number) {
		return this.Chrom_constant.get(number);
	}

	/**
	 * Set
	 * 
	 * @param nIndex
	 * @param str
	 */
	public void Set(int nIndex, Object str) { // 这里是更改索引对应的字符串，更改的是整个染色体，包括常量域
		Chrom.set(nIndex, str);
	}

	/*
	 * 输出个体的全部基因
	 */
	public String toString() {
		return Chrom.toString();

	}

	/**
	 * 个体克隆，深拷贝
	 */
	public Individual clone() {

		Individual Indiv = null; // 创建一个空个体
		try {
			Indiv = (Individual) super.clone(); // 克隆一个个体，完全一样，某种意义上可以认为是赋值
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		Indiv.Chrom = new LinkedList<Object>();
		Indiv.Chrom_gene = new LinkedList<String>();
		Indiv.Chrom_constant = new LinkedList<Integer>();

		for (int i = 0; i < this.Chrom_gene.size(); ++i) {
			String sCode = new String(this.Chrom_gene.get(i));
			Indiv.Chrom.add(sCode); // 这里貌似染色体没有克隆过来，所以在这里赋值一遍染色体
			Indiv.Chrom_gene.add(sCode);
		}

		for (int i = 0; i < this.Chrom_constant.size(); i++) {
			int number = new Integer(this.Chrom_constant.get(i));
			Indiv.Chrom.add(number);
			Indiv.Chrom_constant.add(number);
		}

		Indiv.Fitness = this.Fitness; // 因为适应度是double类型，可以直接复制
		Indiv.Value = this.Value; // 复制个体的值
		Indiv.Wheelvalue = this.Wheelvalue;
		Indiv.live = this.live;

		return Indiv; // 返回克隆的个体
	}

	/*
	 * 获取基因 基因编号从0开始
	 */
	public List<String> GetGene(int n) {
		if (n < 0 || n >= GeneCount) {
			return null;
		}

		return this.Chrom_gene.subList(n * GeneLength, n * GeneLength + GeneLength);

	}

}