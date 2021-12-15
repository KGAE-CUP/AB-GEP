package New_gep_greedy1;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

//ע��һ�£���GEP��Ⱦɫ���ǰ����������ģ�ÿ��������һ���ӱ��ʽ��

/**
 * Ⱦɫ��Chromosome
 * 
 **/
public class Individual implements Cloneable {
	/**
	 * �������Ӧֵ
	 **/
	public double Fitness;

	public double Value;

	public double Wheelvalue; // ��Ϊ������ʱ��ֵ������ױ�ѡ�У����ҵ���Ӧ�������õ�ԽСԽ�ã�����Ҫ����һ�������Ķ�����ֵ
	public boolean live; // �����ø����Ƿ��Ѿ�����ֵ�����û�б���ֵ��Ϊfalse������Ϊture��ûʲô���ã���Ҫ������������Ӧ����
	public boolean work = true; // ���������ʽ�����Ƿ���Ч�������ЧҪ��ɾ����
	private int chrome_length;
	private int GeneLength; // ���򳤶�
	private int GeneCount; // ��������
	private int headlength;
	private int taillength;
	private double[][] Dataset;
	Random random = new Random();
	public Constant con;
	public File file;

	public double Vladislavleva_constant;

	public List<Object> Chrom; // Ⱦɫ�� �ַ���
	public List<String> Chrom_gene; // Ⱦɫ���еĻ�������
	public List<Integer> Chrom_constant; // Ⱦɫ���еĳ��������������ļӺ͵��������Ⱦɫ��Chrome

	/*
	 * ���캯�����������һ�������������ɶ�Ӧ��Ⱦɫ���ַ���
	 */
	public Individual(Constant con1) {

		con = con1;
		this.Chrom = new LinkedList<Object>();
		this.Chrom_gene = new LinkedList<String>();
		this.Chrom_constant = new LinkedList<Integer>();
		this.live = false;
		this.file = con.file;
		this.chrome_length = con.chrome_length; // Ⱦɫ�峤�ȣ�����������
		this.GeneLength = con.GeneLength; // ֻ�ǻ��򳤶�
		this.GeneCount = con.GeneCount;
		this.headlength = con.headlength;
		this.taillength = con.taillength;

		this.Vladislavleva_constant = random.nextDouble() * 5 * (Math.pow(-1, random.nextInt(2))); // ���쳣��

	}

	/*
	 * ��ø������Ӧ��
	 */
	public double GetFitness(List<Double> constant_dc) throws IOException {

		Data data = new Data(con); // ���ڵ�����ʱ���������ݼ�
		Dataset = data.getData();

		this.separate();

		FitnessFunction fit = new FitnessFunction(con);

		this.Fitness = fit.GetFitness(this, Dataset, constant_dc); // ������Ӧ�Ⱥ������õ���Ӧ��

		return Fitness;

	}

	/*
	 * �����ʼ����Ҳ���������һ������
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
		indiv.Initialization_constant(); // �������ʼ��

		return indiv;

	}

	/*
	 * �������ʼ��
	 */
	public void Initialization_constant() {
		for (int j = 0; j < this.taillength; j++) { // ��ӳ����� ������β������һ��
			int gene = random.nextInt(this.con.constant_number);
			this.Chrom.add(gene);

		}
	}

	/**
	 * ��ӻ���
	 * 
	 * @param gene
	 */
	public void AddGene(List<String> gene) { // ��ӻ�����Ҫ���ݲ��������û�����ӵ�Ⱦɫ����

		for (int i = 0; i < gene.size(); ++i) {
			String a = gene.get(i);
			this.Chrom.add(a); // �ַ�����+������
		}
	}

	/**
	 * ��Ⱦɫ���ȫ������ֳ������֣������Ӧ�ȵ�ʱ�����
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
	public String Get(int nIndex) { // �����Ǵ�������������Ȼ�󷵻�Ⱦɫ���и�������Ӧ���ַ���
		return Chrom_gene.get(nIndex);
	}

	/*
	 * ������Ĵ�дGet��������
	 */
	public Object get(int nIndex) { // �����Ǵ�������������Ȼ�󷵻�Ⱦɫ���и�������Ӧ���ַ���
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
	public void Set(int nIndex, Object str) { // �����Ǹ���������Ӧ���ַ��������ĵ�������Ⱦɫ�壬����������
		Chrom.set(nIndex, str);
	}

	/*
	 * ��������ȫ������
	 */
	public String toString() {
		return Chrom.toString();

	}

	/**
	 * �����¡�����
	 */
	public Individual clone() {

		Individual Indiv = null; // ����һ���ո���
		try {
			Indiv = (Individual) super.clone(); // ��¡һ�����壬��ȫһ����ĳ�������Ͽ�����Ϊ�Ǹ�ֵ
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		Indiv.Chrom = new LinkedList<Object>();
		Indiv.Chrom_gene = new LinkedList<String>();
		Indiv.Chrom_constant = new LinkedList<Integer>();

		for (int i = 0; i < this.Chrom_gene.size(); ++i) {
			String sCode = new String(this.Chrom_gene.get(i));
			Indiv.Chrom.add(sCode); // ����ò��Ⱦɫ��û�п�¡���������������︳ֵһ��Ⱦɫ��
			Indiv.Chrom_gene.add(sCode);
		}

		for (int i = 0; i < this.Chrom_constant.size(); i++) {
			int number = new Integer(this.Chrom_constant.get(i));
			Indiv.Chrom.add(number);
			Indiv.Chrom_constant.add(number);
		}

		Indiv.Fitness = this.Fitness; // ��Ϊ��Ӧ����double���ͣ�����ֱ�Ӹ���
		Indiv.Value = this.Value; // ���Ƹ����ֵ
		Indiv.Wheelvalue = this.Wheelvalue;
		Indiv.live = this.live;

		return Indiv; // ���ؿ�¡�ĸ���
	}

	/*
	 * ��ȡ���� �����Ŵ�0��ʼ
	 */
	public List<String> GetGene(int n) {
		if (n < 0 || n >= GeneCount) {
			return null;
		}

		return this.Chrom_gene.subList(n * GeneLength, n * GeneLength + GeneLength);

	}

}