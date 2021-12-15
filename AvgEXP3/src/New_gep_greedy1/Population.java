package New_gep_greedy1;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Population {
	LinkedList<Individual> pop;
	LinkedList<Individual> most_indivs; // ÿ����Ⱥ�����ŵ�һ���ָ���

	Individual BestIndividual; // ��Ⱥ�����Ÿ���
	int BestIndivNum; // ��Ⱥ�����Ÿ���ı��
	public int MaxGeneration; // ����������
	public int GreedyConstant; // ���������ٴ��Ժ󣬽�����ת������������������������
	public double GreedyRate;
	public int Populationsize; // ��Ⱥ����
	public int mostindivs_number; // ÿ��ѡȡ�ľ�Ӣ����
	public double MutationRate; // ������
	public double OnePRecomRate; // һ���������
	public double TwoPRecomRate; // �����������
	public double GeneRecomRate; // �����������

	public List<Double> constant_dc; // ������
	private int constant_number; // �����صĴ�С

	public FullSet fullset;
	public FeatureSet featureset;
	public Constant con;
	public Random random;

	public int[] repetition; // �洢��ǰ��Ⱥ���غ϶�

	public File file;

	public Individual mother; // �����ͨ��UCB1��������������ӿռ���Ӹ���
	
	File file_out = new File("E:\\DataSet\\overlap\\4.txt"); // ���ݼ����ڵ�λ��
	
	public int tree=0;

	

	public Population(Constant con1) {
		this.con = con1;
		this.MaxGeneration = con.MaxGeneration;
		this.GreedyConstant = con.GreedyConstant;
		this.GreedyRate = con.GreedyRate;
		pop = new LinkedList<Individual>();
		most_indivs = new LinkedList<Individual>();
		this.constant_dc = new LinkedList<Double>();
		this.file = con.file;
		this.mostindivs_number = con.mostindivs_number;
		this.Populationsize = con.Populationsize;
		this.MutationRate = con.MutationRate;
		this.OnePRecomRate = con.OnePRecomRate;
		this.TwoPRecomRate = con.TwoPRecomRate;
		this.constant_number = con.constant_number;
		random = new Random();
		this.repetition = new int[3];
	}

	public String run(int constantpool_number) throws IOException {
		double historyBest=Double.MAX_VALUE; 
		File file_out = new File("E:\\DataSet6\\shiyingdu\\"+"200."+Fitness_test.yunxing+".txt");//������¼ÿ���������Ӧ��
		//FileOutputStream fos=new FileOutputStream("E:\\DataSet\\overlap\\3.txt");
		FileOutputStream fos2 = new FileOutputStream(file_out);
		//DataOutputStream dos = new DataOutputStream(fos);
		String strDouble;
		//String strDouble1;//��¼��ʷ���Ÿ���


		if (!file_out.exists()) {
			file_out.createNewFile();
		}
		int n = 0;
		this.initPopulation();

		if (constantpool_number == 1) {
			this.initialization_constantpool_Koza();
		} else if (constantpool_number == 2) {
			this.initialization_constantpool_Keijzer();
		} else if (constantpool_number == 3) {
			this.initialization_constantpool_Korns();
		}

		this.evaluateFitness0();
		InformationTree tree = new InformationTree(con, this.constant_dc);
		this.visits(tree, n);
		do {
			tree.save_population(this.pop); // ����Ⱥ��Ϣ���浽��Ϣ����
			this.visits(tree, n);

			if (n > this.GreedyConstant) { // �ﵽһ�������󣬽���̰��ѡ��
				if (random.nextDouble() < this.GreedyRate)
					this.greedyRecomOne(tree);
				else
					this.recomOnePoint(tree);
                       //this.ucbRecomOne(tree);
			} else {
				this.recomOnePoint(tree);
				//this.ucbRecomOne(tree);
			}

			// this.ucbRecomTwo(tree);

			this.mutation();

			if (constantpool_number == 1) {
				this.constant_mutation_Koza();
			} else if (constantpool_number == 2) {
				this.constant_mutation_Kerjzer();
			} else if (constantpool_number == 3) {
				this.constant_mutation_Korns();
			}

			this.evaluateFitness0();

			this.population_sort();
			if (n % 100 == 0) {
				this.overlap(n);
			}

			this.select();
			this.population_sort();
			System.out.println("������" + n);
			System.out.println("�����Ӧ��1��" + this.pop.get(0).Fitness);
			System.out.println("�����Ӧ��1����" + this.pop.get(0).toString());
			System.out.println("�����Ӧ��2��" + this.pop.get(20).Fitness);
			System.out.println("�����Ӧ��2����" + this.pop.get(20).toString());
			System.out.println("�����Ӧ��3��" + this.pop.get(40).Fitness);
			System.out.println("�����Ӧ��3����" + this.pop.get(40).toString());
			System.out.println("�����Ӧ��4��" + this.pop.get(90).Fitness);
			System.out.println("�����Ӧ��4����" + this.pop.get(90).toString());
		//	if (n % 100 == 0) {
			//	this.overlap(n);
	//	}
			if(this.pop.get(0).Fitness<historyBest)
			{
				historyBest=this.pop.get(0).Fitness;
			}
			n++;
		
			System.out.println("������Ⱥ������" + this.pop.size());
			
			//String strDouble = String.valueOf(toBeString);
             strDouble= String.valueOf(historyBest);
            // strDouble1= String.valueOf(this.most_indivs.get(0).Fitness);
			fos2.write("\r\n".getBytes()); // д��һ������
			fos2.write((n+" ").getBytes()); // д���ı�
			 fos2.write((strDouble).getBytes());
			// fos2.write(strDouble1.getBytes());
			 
			fos2.write("\r\n".getBytes()); // д��һ������

			//fos2.flush();
			//fos2.close(); // �ͷ���Դ
			
			//dos.write("\r\n".getBytes());
			//dos.writeDouble(this.pop.get(0).Fitness);


		} while (n <= this.MaxGeneration && this.most_indivs.get(0).Fitness >= 0.01);
		fos2.flush();
		fos2.close();
		String s = "";
		if (n < this.MaxGeneration) {

			s = "�ﵽ�����ˣ���������Ϊ��" + n + "   ��Ӧ��Ϊ��" + this.most_indivs.get(0).Fitness;

		} else {
			s = "û�дﵽ���ţ�������Ӧ��Ϊ��" + this.most_indivs.get(0).Fitness;

		} //

		return s;

	}
	
	public void qushu()
	{
		try {
			  PrintStream ps = new PrintStream("D:\\workspace\\newdata100.txt"); 
			  System.setOut(ps);
			  }catch(Exception e) {
					e.printStackTrace();
				}
		
	}

	public void initPopulation() { // ��Ⱥ��ʼ��

		for (int i = 0; i < Populationsize; i++) {
			Individual indiv = new Individual(con);
			indiv = indiv.Initialization();
			pop.add(indiv);

		}

	}

	/*
	 * ��ʼ��������,Kerjzer�汾����ֵΪ5�ĸ�˹�ֲ�
	 */
	public void initialization_constantpool_Keijzer() {
		for (int i = 0; i < this.constant_number; i++) { // ��ʼ��������
			double temp = 5 * random.nextGaussian();
			this.constant_dc.add(temp);
		}
	}

	/*
	 * ��ʼ��������,Koza�汾�����г���Ϊ1
	 */
	public void initialization_constantpool_Koza() {
		for (int i = 0; i < this.constant_number; i++) { // ��ʼ��������
			double temp = 1;
			this.constant_dc.add(temp);
		}
	}

	/*
	 * ��ʼ��������,Koza�汾��64λdoubleֵ
	 */
	public void initialization_constantpool_Korns() {

		for (int i = 0; i < this.constant_number; i++) { // ��ʼ��������
			double temp = random.nextDouble();
			this.constant_dc.add(temp);
		}
	}

	/*
	 * ��Ⱥ������Ӧ��
	 */
	public void evaluateFitness() throws IOException {

		for (int i = 0; i < pop.size(); i++) {
			Individual indiv = pop.get(i); // ͬһ�����ñ�����ֵ
			indiv.work = true;
			indiv.Fitness = indiv.GetFitness(this.constant_dc);

		}
	}

	// ��Ⱥ��ʼ���������������Ҫ��һ��
	public void evaluateFitness0() throws IOException { // ������Ⱥ��Ӧ��

		for (int i = 0; i < pop.size(); i++) {
			pop.get(i).work = true;
			pop.get(i).Fitness = pop.get(i).GetFitness(this.constant_dc);

		}
	}

	/**
	 * ѡ����Ⱥ���н���ѡ�񣬲����²���
	 **/
	public void select() {
		Population NewPop = new Population(con);
		this.FindBestIndividual();

		for (int i = 0; i < this.most_indivs.size(); i++) {
			NewPop.pop.add(this.most_indivs.get(i)); // ��Ӣ����
		}

		for (int i = this.mostindivs_number; i < 90; i++) {
			NewPop.pop.add(this.pop.get(i));
		}

		for (int i = NewPop.pop.size(); i < Populationsize; i++) {
			int a = random.nextInt(this.pop.size());
			NewPop.pop.add(this.pop.get(a));
		}

		System.out.println("ѡ������Ժ���Ⱥ������" + NewPop.pop.size());

		for (int i = 0; i < NewPop.pop.size(); i++) { // �Ը�����п�¡������Java�еĶ��������ظ���
			NewPop.pop.set(i, NewPop.pop.get(i).clone());
		}
		this.pop = NewPop.pop;

	}

	/**
	 * �������Ÿ���
	 */
	public void FindBestIndividual() {

		this.BestIndividual = this.most_indivs.get(0);
	}

	/*
	 * ÿ������һ����Ŀ�ľ�Ӣ����Щ��Ӣ������뽻�����
	 */

	public void population_sort() {
		for (int i = 0; i < this.pop.size(); i++) {
			for (int j = i + 1; j < this.pop.size(); j++) {
				if (this.pop.get(i).Fitness > this.pop.get(j).Fitness) {
					Individual indiv = this.pop.get(i);
					this.pop.set(i, this.pop.get(j));
					this.pop.set(j, indiv);
				}
			}

		}

		int j = 0;

		if (most_indivs.size() == 0) {
			for (int i = 0; i < this.mostindivs_number; i++) {
				most_indivs.add(this.pop.get(i).clone()); // ��ʱ��Ҫ��ֵ����¡�壬���򽻲�����ʱ����Щ���Ÿ���ͻᷢ���仯
			}
		} else {

			for (int i = 0; i < this.mostindivs_number; i++) {

				if (this.pop.get(j).Fitness < most_indivs.get(i).Fitness) {
					most_indivs.set(i, this.pop.get(j).clone());
					j++;
				}

			}
		}

	}

	// �����ӿռ䵱ǰ�������
	public void visits(InformationTree tree, int number) throws IOException {
		int[] result = tree.notvisit();

		//FileOutputStream fos=new FileOutputStream("E:\\DataSet\\overlap\\notvisits.txt");
		File file_out = new File("E:\\DataSet\\overlap\\notvisits.txt"); // ���ݼ����ڵ�λ��
		//File file_out = new File("E:\\DataSet\\new_gep\\Vladislavleva\\two_v\\4.txt");
		FileOutputStream fos2 = new FileOutputStream(file_out, true);
		// �ڶ�������Ϊtrue��ʾ����ÿ�����ж���׷���ַ�����ԭ�е��ַ���
		// ���ļ�������,�򴴽���
		if (!file_out.exists()) {
			file_out.createNewFile();
		}

		// fos2.write("\r\n".getBytes()); // д��һ������
		// fos2.write(("��" + number + "���غ϶�Ϊ��" + repetition[0] + " " +
		// repetition[1]).getBytes()); // д���ı�
		// fos2.write("\r\n".getBytes()); // д��һ������
		///////////////////////////////////�����޸�һ��
		/*
        fos2.write("----------------------------".getBytes());
		fos2.write("\r\n".getBytes()); // д��һ������
		fos2.write(("��" + number + "�������ռ�����" + result[0] + ";  û�б����ӿռ���Ϊ��" + result[1]).getBytes()); // д���ı�
		fos2.write("\r\n".getBytes()); // д��һ������

		fos2.flush();
		fos2.close(); // �ͷ���Դ. 
		*/

	}

	/*
	 * ���㵱ǰ��Ⱥ�ĸ����غ϶ȣ����������ǰ����Ĭ�ϵ�ǰ��Ⱥ�Ѿ�������Ӧ�Ƚ���������
	 */
	public void overlap(int number) throws IOException {

		double fit = this.pop.get(0).Fitness; // �������Ƚ�
        double[] fit1=new double[this.pop.size()];
        fit1[0]= this.pop.get(0).Fitness;
        
		int j = 0, q = 1;

		for (int i = 1; i < this.pop.size() && j < 3; i++) { // ��������ǰ����������ж��ٸ�
			if (this.pop.get(i).Fitness != fit) {
				this.repetition[j] = i;
				j++;
				fit = this.pop.get(i).Fitness;
			}
		}

		for (int i = 1; i < this.pop.size(); i++) { // ������������ж��ٸ���һ���ĸ���
			//fit1[i]=this.pop.get(i).Fitness ;
			//for(int k=0;k<i;k++)
			//{
			if (this.pop.get(i).Fitness != fit) {
				q++;
				fit = this.pop.get(i).Fitness;
			//	break;
			}
			//if(k==i-1)
			//{
			//	q++;
			//}
		}
			
		
		//File file_out = new File("E:\\DataSet\\overlap\\2.txt"); // ���ݼ����ڵ�λ��
		File file_out=new File("E:\\DataSet6\\zhongqun\\"+"200."+Fitness_test.yunxing+".txt");

		FileOutputStream fos2 = new FileOutputStream(file_out, true);
		// �ڶ�������Ϊtrue��ʾ����ÿ�����ж���׷���ַ�����ԭ�е��ַ���
		// ���ļ�������,�򴴽���
		if (!file_out.exists()) {
			file_out.createNewFile();
		}

		// fos2.write("\r\n".getBytes()); // д��һ������
		// fos2.write(("��" + number + "���غ϶�Ϊ��" + repetition[0] + " " +
		// repetition[1]).getBytes()); // д���ı�
		// fos2.write("\r\n".getBytes()); // д��һ������
		//fos2.write("----------------------------".getBytes());
		fos2.write("\r\n".getBytes()); // д��һ������
		//fos2.write(("��" + number + "��Ⱥ������Ϊ��" + q).getBytes()); // д���ı�
		fos2.write((number+" "+q+" "+this.pop.size()).getBytes());
		fos2.write("\r\n".getBytes()); // д��һ������

		fos2.flush();
		fos2.close(); // �ͷ���Դ

	}

	// ����
	public void mutation() {
		fullset = new FullSet(con);
		featureset = new FeatureSet(con);

		for (Individual indiv : this.pop) {

			for (int j = 0; j < con.GeneLength; ++j) { // ���ÿһλ���������б��죬�����Ǹ���

				if (random.nextDouble() < this.MutationRate) {
					int nIndex = j % con.GeneLength;

					// ����ͷ��
					if (nIndex < con.headlength) {

						indiv.Set(j, fullset.getRandom()); // ���ñ���Ļ���
					} else { // β��

						indiv.Set(j, featureset.getRandom()); // ���ñ���Ļ���
					}
				}
			}
		}
	}

	/*
	 * �����ؽ��б���,Korns�汾��Ҳ����64λdouble����
	 */
	public void constant_mutation_Korns() {

		for (int i = 0; i < this.constant_dc.size(); i++) {
			if (random.nextDouble() < this.MutationRate) {
				double temp = 5 * random.nextDouble();
				this.constant_dc.set(i, temp);
			}
		}

	}

	/*
	 * �����ر��죬Koza�汾�����г���Ϊ1�����Բ���Ҫ����
	 */
	public void constant_mutation_Koza() {

		for (int i = 0; i < this.pop.size(); i++) {
			if (random.nextDouble() < this.MutationRate) {
				this.pop.get(i).Vladislavleva_constant = random.nextDouble() * 5 * (Math.pow(-1, random.nextInt(2)));
			}
		}

	}

	/*
	 * �����ر��죬Kerjzer�汾����ֵΪ5�ĸ�˹�ֲ�
	 */
	public void constant_mutation_Kerjzer() {

		for (int i = 0; i < this.constant_dc.size(); i++) {
			if (random.nextDouble() < this.MutationRate) {
				double temp = 5 * random.nextGaussian(); // ��˹�ֲ����ֵ
				this.constant_dc.set(i, temp);
			}
		}

	}

	// ��ӡ��Ϣ
	public void print_file(String out) throws IOException {
		File file_out = new File("E:\\DataSet4\\biaoda1\\16.2.txt"); // ���ݼ����ڵ�λ��

		// FileOutputStream fos2 = new FileOutputStream(file_out);
		FileOutputStream fos2 = new FileOutputStream(file_out, true);
		// �ڶ�������Ϊtrue��ʾ����ÿ�����ж���׷���ַ�����ԭ�е��ַ���
		// ���ļ�������,�򴴽���
		if (!file_out.exists()) {
			file_out.createNewFile();
		}
		fos2.write("----------------------------".getBytes());
		fos2.write("\r\n".getBytes()); // д��һ������
		fos2.write((out).getBytes()); // д���ı�
		fos2.write("\r\n".getBytes()); // д��һ������

		fos2.flush();
		fos2.close(); // �ͷ���Դ
	}
	
	public void printbiaodashi(String out) throws IOException{
		tree++;
		File file_out=new File("E:\\DataSet6\\biaoda2\\"+"200."+Fitness_test.yunxing+".txt ");
		FileOutputStream fos2=new FileOutputStream(file_out,true);
		if(!file_out.exists()) {
			file_out.createNewFile();
		}
		//fos2.write("----------------------------".getBytes());
		//fos2.write("\r\n".getBytes()); // д��һ������
		fos2.write((tree+" "+out).getBytes()); // д���ı�
		fos2.write("\r\n".getBytes()); // д��һ������
		
		fos2.flush();
		fos2.close(); // �ͷ���Դ
        
	}
	

	// �ﵽһ�������󣬽���̰�Ľ���
	public void greedyRecomOne(InformationTree tree) throws IOException {
		int i = 0;
		int nFather;
		int nMother;
		int nPos;

		double dRate;
		Population newpop = new Population(con);

		AvgExp3values besthead = tree.greedyUCB();

		this.print_file(
				besthead.indiv.Chrom_gene.get(0) + besthead.indiv.Chrom_gene.get(1) + besthead.indiv.Chrom_gene.get(2));

		for (Individual indiv : this.pop) {
			for (i = 0; i < besthead.gene.size(); i++) {
				indiv.Set(i, besthead.gene.get(i));
			}
		}

		this.pop.add(besthead.indiv.clone());

		for (i = 0; i < this.pop.size(); ++i) {

			dRate = random.nextDouble(); // �������һ�����ʷ�Χ

			if (dRate < this.OnePRecomRate) {
				// ���ѡȡ�������� �� �����

				nFather = random.nextInt(this.pop.size());// ���ѡȡ�������
				nMother = random.nextInt(this.pop.size());

				Individual Father = this.pop.get(nFather);
				Individual Mother = this.pop.get(nMother);
				nPos = random.nextInt(con.chrome_length);
				Object temp;
				// �������彻������
				for (int j = 0; j < nPos; ++j) {
					temp = Father.get(j);
					Father.Set(j, Mother.get(j));
					Mother.Set(j, temp);
				}
				newpop.pop.add(Mother.clone());
				newpop.pop.add(Father.clone());

			}
		}

		for (i = 0; i < newpop.pop.size(); i++) {
			this.pop.add(newpop.pop.get(i));
		}

	}

	/*
	 * �����ʷ��Ϣ������һ���µĵ������鷽�����ο�UCB�㷨��ͬʱ����̰�Ĳ��ԣ����ɵ������Ӹ�������Ӧ����ߵ��Ǹ�
	 */
	public void ucbRecomOne(InformationTree tree) throws IOException {
		int i = 0;
		int nPos;

		double dRate;
		mother = tree.compareUCB();
		//this.print_file(mother.Chrom_gene.get(0) + mother.Chrom_gene.get(1) + mother.Chrom_gene.get(2));

		Population newpop = new Population(con);

		for (i = 0; i < this.pop.size(); ++i) {

			Individual mother1 = mother.clone();

			dRate = random.nextDouble(); // �������һ�����ʷ�Χ

			if (dRate < this.OnePRecomRate) {
				// ���ѡȡ�������� �� �����

				Individual father = this.pop.get(i).clone();

				nPos = random.nextInt(con.chrome_length);

				Object temp;

				// �������彻������
				for (int j = 0; j < nPos; ++j) {
					temp = father.get(j);
					father.Set(j, mother1.get(j));

				}
				mother1.Fitness = mother1.GetFitness(this.constant_dc);
				father.Fitness = father.GetFitness(this.constant_dc);

				newpop.pop.add(father.clone());
				newpop.pop.add(mother1.clone());

			}
		}

		for (i = 0; i < newpop.pop.size(); i++) {
			this.pop.add(newpop.pop.get(i));
		}

	}

	/*
	 * �����ʷ��Ϣ������һ���µ��������鷽�����ο�UCB�㷨������û����̰�Ĳ���
	 */
	public void ucbRecomTwo(InformationTree tree) throws IOException {
		int i = 0;
		int nPosPre;
		int nPosLast;

		double dRate;
		for (i = 0; i < this.pop.size(); ++i) {
			dRate = random.nextDouble();
			if (dRate < this.TwoPRecomRate) {

				Individual father = this.pop.get(i);

				nPosPre = random.nextInt(this.con.chrome_length);
				nPosLast = random.nextInt(con.chrome_length);

				if (nPosPre > nPosLast) {
					int nTemp = nPosLast;
					nPosLast = nPosPre;
					nPosPre = nTemp;
				}

				// ���򽻻�
				Object sTemp;
				for (int j = nPosPre; j < nPosLast; ++j) {
					sTemp = father.get(j);
					father.Set(j, mother.get(j));
					mother.Set(j, sTemp);
				}
				mother.Fitness = mother.GetFitness(this.constant_dc);
				father.Fitness = father.GetFitness(this.constant_dc);

				if (father.Fitness < mother.Fitness)
					this.pop.set(i, father);
				else
					this.pop.set(i, mother);

			}

		}

	}

	/**
	 * ��������
	 * 
	 * @throws IOException
	 */
	public void recomOnePoint(InformationTree tree) throws IOException {
		int i = 0;
		int nFather;
		int nMother;
		int nPos;
        String s=" ";
        String s1=" ";
        
		double dRate;
		Population newpop = new Population(con);

		AvgExp3values besthead = tree.getBestSubspace();
		
		for(int j=0;j<besthead.indiv.Chrom_gene.size();j++)
		{
			s=s+besthead.indiv.Chrom_gene.get(j);
		}
		
		//this.print_file(s);//��ӡ�����ӿռ����Ϣ
		
		for(int k=0;k<besthead.gene.size();k++)
		{
			s1=s1+besthead.gene.get(k);
		}
		this.printbiaodashi(s1);

		for (Individual indiv : this.pop) {
			for (i = 0; i < besthead.gene.size(); i++) {
				indiv.Set(i, besthead.gene.get(i));
			}
		}

		this.pop.add(besthead.indiv.clone());

		for (i = 0; i < this.pop.size(); ++i) {

			dRate = random.nextDouble(); // �������һ�����ʷ�Χ

			if (dRate < this.OnePRecomRate) {
				// ���ѡȡ�������� �� �����

				nFather = random.nextInt(this.pop.size());// ���ѡȡ�������
				nMother = random.nextInt(this.pop.size());

				Individual Father = this.pop.get(nFather);
				Individual Mother = this.pop.get(nMother);
				nPos = random.nextInt(con.chrome_length);
				Object temp;
				// �������彻������
				for (int j = 0; j < nPos; ++j) {
					temp = Father.get(j);
					Father.Set(j, Mother.get(j));
					Mother.Set(j, temp);
				}
				newpop.pop.add(Mother.clone());
				newpop.pop.add(Father.clone());

			}
		}

		for (i = 0; i < newpop.pop.size(); i++) {
			this.pop.add(newpop.pop.get(i));
		}
	}

	/**
	 * ��������
	 */
	public void recomTwoPoint() {
		int i = 0;
		int nFather;
		int nMother;
		int nPosPre;
		int nPosLast;

		double dRate;
		for (i = 0; i < this.pop.size(); ++i) {
			dRate = random.nextDouble();
			if (dRate < this.TwoPRecomRate) {

				nFather = random.nextInt(this.pop.size());// ���ѡȡ�������
				nMother = random.nextInt(this.pop.size());
				Individual Father = this.pop.get(nFather);
				Individual Mother = this.pop.get(nMother);

				nPosPre = random.nextInt(con.chrome_length);
				nPosLast = random.nextInt(con.chrome_length);

				if (nPosPre > nPosLast) {
					int nTemp = nPosLast;
					nPosLast = nPosPre;
					nPosPre = nTemp;
				}

				// ���򽻻�
				Object sTemp;
				for (int j = nPosPre; j < nPosLast; ++j) {
					sTemp = Father.get(j);
					Father.Set(j, Mother.get(j));
					Mother.Set(j, sTemp);
				}
				this.pop.set(nMother, Mother);
				this.pop.set(nFather, Father);

			}

		}

	}

}