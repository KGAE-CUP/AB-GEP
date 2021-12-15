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
	LinkedList<Individual> most_indivs; // 每代种群中最优的一部分个体

	Individual BestIndividual; // 种群中最优个体
	int BestIndivNum; // 种群中最优个体的编号
	public int MaxGeneration; // 最大迭代次数
	public int GreedyConstant; // 迭代到多少次以后，进行跳转，这个常量和最大迭代次数相关
	public double GreedyRate;
	public int Populationsize; // 种群数量
	public int mostindivs_number; // 每代选取的精英数量
	public double MutationRate; // 变异率
	public double OnePRecomRate; // 一点重组概率
	public double TwoPRecomRate; // 两点重组概率
	public double GeneRecomRate; // 基因重组概率

	public List<Double> constant_dc; // 常量池
	private int constant_number; // 常量池的大小

	public FullSet fullset;
	public FeatureSet featureset;
	public Constant con;
	public Random random;

	public int[] repetition; // 存储当前种群的重合度

	public File file;

	public Individual mother; // 这个是通过UCB1计算出来的最优子空间的子个体
	
	File file_out = new File("E:\\DataSet\\overlap\\4.txt"); // 数据集所在的位置
	
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
		File file_out = new File("E:\\DataSet6\\shiyingdu\\"+"200."+Fitness_test.yunxing+".txt");//用来记录每代的最佳适应度
		//FileOutputStream fos=new FileOutputStream("E:\\DataSet\\overlap\\3.txt");
		FileOutputStream fos2 = new FileOutputStream(file_out);
		//DataOutputStream dos = new DataOutputStream(fos);
		String strDouble;
		//String strDouble1;//记录历史最优个体


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
			tree.save_population(this.pop); // 将种群信息保存到信息树中
			this.visits(tree, n);

			if (n > this.GreedyConstant) { // 达到一定代数后，进行贪心选择
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
			System.out.println("代数：" + n);
			System.out.println("最佳适应度1：" + this.pop.get(0).Fitness);
			System.out.println("最佳适应度1基因：" + this.pop.get(0).toString());
			System.out.println("最佳适应度2：" + this.pop.get(20).Fitness);
			System.out.println("最佳适应度2基因：" + this.pop.get(20).toString());
			System.out.println("最佳适应度3：" + this.pop.get(40).Fitness);
			System.out.println("最佳适应度3基因：" + this.pop.get(40).toString());
			System.out.println("最佳适应度4：" + this.pop.get(90).Fitness);
			System.out.println("最佳适应度4基因：" + this.pop.get(90).toString());
		//	if (n % 100 == 0) {
			//	this.overlap(n);
	//	}
			if(this.pop.get(0).Fitness<historyBest)
			{
				historyBest=this.pop.get(0).Fitness;
			}
			n++;
		
			System.out.println("当代种群个数：" + this.pop.size());
			
			//String strDouble = String.valueOf(toBeString);
             strDouble= String.valueOf(historyBest);
            // strDouble1= String.valueOf(this.most_indivs.get(0).Fitness);
			fos2.write("\r\n".getBytes()); // 写入一个换行
			fos2.write((n+" ").getBytes()); // 写入文本
			 fos2.write((strDouble).getBytes());
			// fos2.write(strDouble1.getBytes());
			 
			fos2.write("\r\n".getBytes()); // 写入一个换行

			//fos2.flush();
			//fos2.close(); // 释放资源
			
			//dos.write("\r\n".getBytes());
			//dos.writeDouble(this.pop.get(0).Fitness);


		} while (n <= this.MaxGeneration && this.most_indivs.get(0).Fitness >= 0.01);
		fos2.flush();
		fos2.close();
		String s = "";
		if (n < this.MaxGeneration) {

			s = "达到最优了，迭代次数为：" + n + "   适应度为：" + this.most_indivs.get(0).Fitness;

		} else {
			s = "没有达到最优，最优适应度为：" + this.most_indivs.get(0).Fitness;

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

	public void initPopulation() { // 种群初始化

		for (int i = 0; i < Populationsize; i++) {
			Individual indiv = new Individual(con);
			indiv = indiv.Initialization();
			pop.add(indiv);

		}

	}

	/*
	 * 初始化常量池,Kerjzer版本，峰值为5的高斯分布
	 */
	public void initialization_constantpool_Keijzer() {
		for (int i = 0; i < this.constant_number; i++) { // 初始化常量池
			double temp = 5 * random.nextGaussian();
			this.constant_dc.add(temp);
		}
	}

	/*
	 * 初始化常量池,Koza版本，所有常量为1
	 */
	public void initialization_constantpool_Koza() {
		for (int i = 0; i < this.constant_number; i++) { // 初始化常量池
			double temp = 1;
			this.constant_dc.add(temp);
		}
	}

	/*
	 * 初始化常量池,Koza版本，64位double值
	 */
	public void initialization_constantpool_Korns() {

		for (int i = 0; i < this.constant_number; i++) { // 初始化常量池
			double temp = random.nextDouble();
			this.constant_dc.add(temp);
		}
	}

	/*
	 * 种群评估适应度
	 */
	public void evaluateFitness() throws IOException {

		for (int i = 0; i < pop.size(); i++) {
			Individual indiv = pop.get(i); // 同一个引用变量赋值
			indiv.work = true;
			indiv.Fitness = indiv.GetFitness(this.constant_dc);

		}
	}

	// 种群初始化后的评估，这里要改一下
	public void evaluateFitness0() throws IOException { // 评估种群适应度

		for (int i = 0; i < pop.size(); i++) {
			pop.get(i).work = true;
			pop.get(i).Fitness = pop.get(i).GetFitness(this.constant_dc);

		}
	}

	/**
	 * 选择在群体中进行选择，采用新策略
	 **/
	public void select() {
		Population NewPop = new Population(con);
		this.FindBestIndividual();

		for (int i = 0; i < this.most_indivs.size(); i++) {
			NewPop.pop.add(this.most_indivs.get(i)); // 精英策略
		}

		for (int i = this.mostindivs_number; i < 90; i++) {
			NewPop.pop.add(this.pop.get(i));
		}

		for (int i = NewPop.pop.size(); i < Populationsize; i++) {
			int a = random.nextInt(this.pop.size());
			NewPop.pop.add(this.pop.get(a));
		}

		System.out.println("选择完毕以后种群数量：" + NewPop.pop.size());

		for (int i = 0; i < NewPop.pop.size(); i++) { // 对个体进行克隆，避免Java中的对象引用重复！
			NewPop.pop.set(i, NewPop.pop.get(i).clone());
		}
		this.pop = NewPop.pop;

	}

	/**
	 * 查找最优个体
	 */
	public void FindBestIndividual() {

		this.BestIndividual = this.most_indivs.get(0);
	}

	/*
	 * 每代保留一定数目的精英，这些精英不会参与交叉变异
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
				most_indivs.add(this.pop.get(i).clone()); // 这时候要赋值给克隆体，否则交叉变异的时候，这些最优个体就会发生变化
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

	// 计算子空间当前遍历情况
	public void visits(InformationTree tree, int number) throws IOException {
		int[] result = tree.notvisit();

		//FileOutputStream fos=new FileOutputStream("E:\\DataSet\\overlap\\notvisits.txt");
		File file_out = new File("E:\\DataSet\\overlap\\notvisits.txt"); // 数据集所在的位置
		//File file_out = new File("E:\\DataSet\\new_gep\\Vladislavleva\\two_v\\4.txt");
		FileOutputStream fos2 = new FileOutputStream(file_out, true);
		// 第二個参数为true表示程序每次运行都是追加字符串在原有的字符上
		// 若文件不存在,则创建它
		if (!file_out.exists()) {
			file_out.createNewFile();
		}

		// fos2.write("\r\n".getBytes()); // 写入一个换行
		// fos2.write(("第" + number + "代重合度为：" + repetition[0] + " " +
		// repetition[1]).getBytes()); // 写入文本
		// fos2.write("\r\n".getBytes()); // 写入一个换行
		///////////////////////////////////稍稍修改一下
		/*
        fos2.write("----------------------------".getBytes());
		fos2.write("\r\n".getBytes()); // 写入一个换行
		fos2.write(("第" + number + "代遍历空间数：" + result[0] + ";  没有遍历子空间数为：" + result[1]).getBytes()); // 写入文本
		fos2.write("\r\n".getBytes()); // 写入一个换行

		fos2.flush();
		fos2.close(); // 释放资源. 
		*/

	}

	/*
	 * 计算当前种群的个体重合度，这个函数的前提是默认当前种群已经根据适应度进行了排序
	 */
	public void overlap(int number) throws IOException {

		double fit = this.pop.get(0).Fitness; // 用来做比较
        double[] fit1=new double[this.pop.size()];
        fit1[0]= this.pop.get(0).Fitness;
        
		int j = 0, q = 1;

		for (int i = 1; i < this.pop.size() && j < 3; i++) { // 用来记载前两个个体各有多少个
			if (this.pop.get(i).Fitness != fit) {
				this.repetition[j] = i;
				j++;
				fit = this.pop.get(i).Fitness;
			}
		}

		for (int i = 1; i < this.pop.size(); i++) { // 这个用来记载有多少个不一样的个体
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
			
		
		//File file_out = new File("E:\\DataSet\\overlap\\2.txt"); // 数据集所在的位置
		File file_out=new File("E:\\DataSet6\\zhongqun\\"+"200."+Fitness_test.yunxing+".txt");

		FileOutputStream fos2 = new FileOutputStream(file_out, true);
		// 第二個参数为true表示程序每次运行都是追加字符串在原有的字符上
		// 若文件不存在,则创建它
		if (!file_out.exists()) {
			file_out.createNewFile();
		}

		// fos2.write("\r\n".getBytes()); // 写入一个换行
		// fos2.write(("第" + number + "代重合度为：" + repetition[0] + " " +
		// repetition[1]).getBytes()); // 写入文本
		// fos2.write("\r\n".getBytes()); // 写入一个换行
		//fos2.write("----------------------------".getBytes());
		fos2.write("\r\n".getBytes()); // 写入一个换行
		//fos2.write(("第" + number + "种群多样性为：" + q).getBytes()); // 写入文本
		fos2.write((number+" "+q+" "+this.pop.size()).getBytes());
		fos2.write("\r\n".getBytes()); // 写入一个换行

		fos2.flush();
		fos2.close(); // 释放资源

	}

	// 变异
	public void mutation() {
		fullset = new FullSet(con);
		featureset = new FeatureSet(con);

		for (Individual indiv : this.pop) {

			for (int j = 0; j < con.GeneLength; ++j) { // 针对每一位基因来进行变异，而不是个体

				if (random.nextDouble() < this.MutationRate) {
					int nIndex = j % con.GeneLength;

					// 基因头部
					if (nIndex < con.headlength) {

						indiv.Set(j, fullset.getRandom()); // 设置变异的基因
					} else { // 尾部

						indiv.Set(j, featureset.getRandom()); // 设置变异的基因
					}
				}
			}
		}
	}

	/*
	 * 常量池进行变异,Korns版本，也就是64位double类型
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
	 * 常量池变异，Koza版本，所有常量为1，所以不需要变异
	 */
	public void constant_mutation_Koza() {

		for (int i = 0; i < this.pop.size(); i++) {
			if (random.nextDouble() < this.MutationRate) {
				this.pop.get(i).Vladislavleva_constant = random.nextDouble() * 5 * (Math.pow(-1, random.nextInt(2)));
			}
		}

	}

	/*
	 * 常量池变异，Kerjzer版本，峰值为5的高斯分布
	 */
	public void constant_mutation_Kerjzer() {

		for (int i = 0; i < this.constant_dc.size(); i++) {
			if (random.nextDouble() < this.MutationRate) {
				double temp = 5 * random.nextGaussian(); // 高斯分布随机值
				this.constant_dc.set(i, temp);
			}
		}

	}

	// 打印信息
	public void print_file(String out) throws IOException {
		File file_out = new File("E:\\DataSet4\\biaoda1\\16.2.txt"); // 数据集所在的位置

		// FileOutputStream fos2 = new FileOutputStream(file_out);
		FileOutputStream fos2 = new FileOutputStream(file_out, true);
		// 第二個参数为true表示程序每次运行都是追加字符串在原有的字符上
		// 若文件不存在,则创建它
		if (!file_out.exists()) {
			file_out.createNewFile();
		}
		fos2.write("----------------------------".getBytes());
		fos2.write("\r\n".getBytes()); // 写入一个换行
		fos2.write((out).getBytes()); // 写入文本
		fos2.write("\r\n".getBytes()); // 写入一个换行

		fos2.flush();
		fos2.close(); // 释放资源
	}
	
	public void printbiaodashi(String out) throws IOException{
		tree++;
		File file_out=new File("E:\\DataSet6\\biaoda2\\"+"200."+Fitness_test.yunxing+".txt ");
		FileOutputStream fos2=new FileOutputStream(file_out,true);
		if(!file_out.exists()) {
			file_out.createNewFile();
		}
		//fos2.write("----------------------------".getBytes());
		//fos2.write("\r\n".getBytes()); // 写入一个换行
		fos2.write((tree+" "+out).getBytes()); // 写入文本
		fos2.write("\r\n".getBytes()); // 写入一个换行
		
		fos2.flush();
		fos2.close(); // 释放资源
        
	}
	

	// 达到一定代数后，进行贪心交叉
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

			dRate = random.nextDouble(); // 随机产生一个概率范围

			if (dRate < this.OnePRecomRate) {
				// 随机选取两个个体 和 交叉点

				nFather = random.nextInt(this.pop.size());// 随机选取交叉个体
				nMother = random.nextInt(this.pop.size());

				Individual Father = this.pop.get(nFather);
				Individual Mother = this.pop.get(nMother);
				nPos = random.nextInt(con.chrome_length);
				Object temp;
				// 两个个体交叉重组
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
	 * 针对历史信息，进行一种新的单点重组方法，参考UCB算法，同时采用贪心策略，生成的两个子个体用适应度最高的那个
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

			dRate = random.nextDouble(); // 随机产生一个概率范围

			if (dRate < this.OnePRecomRate) {
				// 随机选取两个个体 和 交叉点

				Individual father = this.pop.get(i).clone();

				nPos = random.nextInt(con.chrome_length);

				Object temp;

				// 两个个体交叉重组
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
	 * 针对历史信息，进行一种新的两点重组方法，参考UCB算法，但是没有用贪心策略
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

				// 基因交换
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
	 * 单点重组
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
		
		//this.print_file(s);//打印最优子空间的信息
		
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

			dRate = random.nextDouble(); // 随机产生一个概率范围

			if (dRate < this.OnePRecomRate) {
				// 随机选取两个个体 和 交叉点

				nFather = random.nextInt(this.pop.size());// 随机选取交叉个体
				nMother = random.nextInt(this.pop.size());

				Individual Father = this.pop.get(nFather);
				Individual Mother = this.pop.get(nMother);
				nPos = random.nextInt(con.chrome_length);
				Object temp;
				// 两个个体交叉重组
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
	 * 两点重组
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

				nFather = random.nextInt(this.pop.size());// 随机选取交叉个体
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

				// 基因交换
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