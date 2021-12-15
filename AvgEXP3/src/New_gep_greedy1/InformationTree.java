package New_gep_greedy1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class InformationTree {
	static Random r = new Random(); // ����һ�������������������������
	private int head_Actions; // �������ö���������ʵ����ÿ���ڵ������ӽڵ���Ŀ��ѡ��ڵ㣬����ѡ����
	private int tail_Actions;
	TreeNode root; // root��һ������һ����Ϣ���еĸ��ڵ�
	private int DEEP;
	public int headlength; // ����Ⱦɫ��ͷ�����ȣ�����Լ���ڵ����չ���

	public Constant con;
	public List<Double> constant_dc;//constant_dc��һ��double���͵ļ���

	public FullSet ful;//����ȫ����Ҳ����ͷ��ȡֵ��Χ
	public FeatureSet FeaSet;//���ú�������Ҳ����β��ȡֵ��Χ

	public int LDeep; // ������ʷ��Ϣ����
	
	//public double d;//���������
	
	//public double[] touctvalues=null;//��¼ÿ�ε����и���ֵ(UCBֵ)
	
	//double sum=0;

	//Map��һ������
	public Map<LinkedList<String>, AvgExp3values> LMostIndivs; // ��L��ÿ���ڵ��������Ÿ��壬�Լ�����ڵ�ķ��ʴ���
	private AvgExp3values AvgExp3;//bestUCB��һ������
	private AvgExp3values bestUCB;//bestUCB��һ������
	//Object[] keyss=null;
	
	//public double[] toestreward=new double[this.LMostIndivs.size()];
	//public double[] toestreward=null;//������������¼ÿ���ӿռ���ۼ�Ԥ������ֵ
	//public double[] toestreward=null;
	//public double yita=0;
	//public double tospareward=0;
	//public double e=Math.E;
	
	public double[] toestreward=new double[2000];//��¼ÿһ���ӿռ���ۼƽ���ֵ
	public double tospareward=0;//��¼�����ӿռ��ۼƽ���ֵ���
	//public double yita=Math.sqrt(Math.log(100)/(100000*100));//yita�Ǹ�������ѧϰ��
	public double yita=0.0006;
	//////////////��һ���������ģ���Ҫ��һ��EXP3-IX
	public double gama=0.5*yita;
	//////////////
	public double e=Math.E;
	public double[] touctvalues=new double[2000];//������¼ÿ���ӿռ䱻ѡ��ĸ���
	public double d;//���������
	public double leijiafitness=0;
	//public double totalfitness=0;//��¼�����ӿռ����Ӧ�ȣ��Ա��ڹ�һ��
	
	public int gen=1;//��¼����
	public int gen2=1;
	public int[] number=new int[100002];//��¼ÿһ���ж��ٸ��ӿռ�
	
	int d1=0;//d1���������������������Χ0-k)
	int bound=0;//ǰ��ʹ�þ���̽�������ģ�������ķֽ磨��һ����
	public double[] count=new double[2000];//��¼ÿ���ӿռ䱻���ʶ��ٴ�
	
	public double[][] baocun=new double[2000][100];//��¼ÿһ�����ʵ��ӿռ������ֵ
	public int gen1=0;//��¼Ҫ����yitaʱ�Ĵ���
	public int[] touctvalues1=new int[2000];//��������ÿ���ӿռ䱻ѡ��ĸ���
	int zongji=0;//������¼�ӿռ伫ֵ����Ƶ������Ҫ����м���
	double jizhi=0;
    int zhuanhuan=0;
	public double[] change=new double[100];//����ά����ת����һά����
	public int[] huatu=new int[2000];//��¼ÿ���ӿռ䱻���ʶ��ٴ�
	
	//���캯��
	public InformationTree(Constant con1, List<Double> constant_dc1) throws IOException {
		con = con1;
		constant_dc = constant_dc1;
		ful = new FullSet(con);
		FeaSet = new FeatureSet(con);
		root = new TreeNode();
		this.LDeep = con.deep;
		AvgExp3 = new AvgExp3values(con1, constant_dc); // �������Ϣ����ʼ����ʱ�򣬽��д���һ��
		

		this.head_Actions = ful.length;
		this.tail_Actions = FeaSet.length;
		this.DEEP = con.headlength * 2 + 1;
		this.headlength = con.headlength;

		root.deep = 0; // Ĭ�ϸ��ڵ����Ϊ0����������������Ϊ22
		root.nVisits++;

		LMostIndivs = new LinkedHashMap<LinkedList<String>, AvgExp3values>();
		//keyss=LMostIndivs.keySet().toArray();
		//toestreward=new double[this.LMostIndivs.size()];
		//touctvalues=new double[this.LMostIndivs.size()];
		//toestreward=new double[LMostIndivs.size()];
		//yita=Math.sqrt(Math.log(LMostIndivs.size())/100000*LMostIndivs.size());
		this.initialize_expand(root); // ��չ��LDeep��ڵ�

	}

	/*
	 * ������Ⱥ���嵽��Ϣ����
	 */
	public void save_population(LinkedList<Individual> pop) {
		System.out.println("�ܽڵ������" + this.LMostIndivs.size());
		for (Individual indiv : pop) {
			TreeNode cur = this.root; // ��ǰ�ڵ�Ϊ���ڵ�
			for (int i = 0; i <= LDeep; i++) { // ���ܴ洢̫���ڵ㣬5000���Ժ�ͻ�̫���ˣ���Ϊ�ڵ���Ϣռ���˺ܶ��ڴ�ռ�
				cur.deep = i; // ���ڵ�Ϊ0��
				cur.updateStats(); // ���µ�ǰ�ڵ����Ϣ��Ŀǰ��������һ�����ʴ���

				if (i == LDeep) { // ������˿�����ʷ��Ϣ�㣬��ô��Ҫ��ʼ�Ƚϸýڵ����Ѹ�����Ӧ����

					if (LMostIndivs.containsKey(cur.Gene)) { // �����������ڵ㣬ֱ�ӽ��жԱ�
						double fitness = LMostIndivs.get(cur.Gene).fitness;
						if (indiv.Fitness < fitness) { // ������������Ӧ��С�Ļ����Ǿ��滻
							LMostIndivs.put(cur.Gene,
									new AvgExp3values(indiv.clone(), cur.Gene, this.root.nVisits, cur.nVisits));
						} else {
							LMostIndivs.get(cur.Gene).visits = cur.nVisits;
						}
					} else { // ���������������ڵĽڵ㲻���ڣ���ô�ʹ�������ڵ㣬��Ϊ�������ʼ����ʱ��һЩ�ڵ�û�д��ȥ
						LMostIndivs.put(cur.Gene, new AvgExp3values(indiv.clone(), cur.Gene, root.nVisits, cur.nVisits));
					}

				}
				cur = this.expand(indiv.Chrom_gene.get(i), cur); // ��չ���ӽڵ㣬���ҽ����ӽڵ㴫������Ϊ��ǰ�ڵ�

			}
			cur.deep = indiv.Chrom.size();
			cur.updateStats(); // ����Ҷ�ӽڵ����Ϣ������û�н��и���

		}

	}

	/*
	 * ���ݻ���λ������չ�������غ��ӽڵ�
	 */
	public TreeNode expand(String gene, TreeNode farther) {

		for (TreeNode children : farther.children) {
			int n = children.Gene.size();
			if (children.Gene.get(n - 1).equalsIgnoreCase(gene)) { // ������ӽڵ����һλ������ڵ�ǰ������ô��һλ��������Ҫ�ҵĺ��ӽڵ�

				return children; // �ҵ��˸ú��ӽڵ㣬�÷�����
			}

		} // ����û���ҵ�������һλ�����Ӧ�ĺ��ӽڵ㣬����Ҫ������չ
		TreeNode newchildren = new TreeNode();
		for (String s : farther.Gene) {
			newchildren.Gene.add(s);
		}
		newchildren.Gene.add(gene);
		farther.children.add(newchildren);
		newchildren.deep = farther.deep + 1;
		return newchildren;
	}

	// Ϊ��ͼ�ο��ӻ���������չ���нڵ�
	public int expand1(TreeNode root) {
		if (root.deep == 6) {
			return 1;
		} else {
			for (int i = 0; i < 6; i++) {
				TreeNode newchildren = new TreeNode();
				ful = new FullSet(this.con);
				for (String s : root.Gene) { // �����s���������root.Gene���޹صģ����ٸı�s��ֵ�����߲��ᷢ���ı�
					newchildren.Gene.add(s);
				}
				newchildren.Gene.add(ful.get(i));
				newchildren.deep = newchildren.Gene.size();
				root.children.add(newchildren);
				expand1(newchildren);
			}
		}
		return 0;
	}

	// ����ǰL�ڵ㣬����Ҫ��ǰ��ǰL�����нڵ㶼��չ���������������չǰLDeep�ڵ��
	public int initialize_expand(TreeNode root) throws IOException {
		if (root.deep == LDeep) {
			Individual indiv = completeIndiv(root); // ������һ��Ľڵ㣬����һ�����壬���������ʱΪ�ýڵ������Ÿ���
			if (indiv.Chrom.size() == 0) {
				return 1;
			}
			AvgExp3values ucbv = new AvgExp3values(this.con, this.constant_dc); // ������洢�˵�LDeep��ڵ�����Ÿ��塢���ʴ��������ܵķ��ʴ���
			ucbv.indiv = indiv;
			for (int i = 0; i < con.deep; i++) { // ��Ϊ���ǻ���������0��ʼ��������������������С��
				ucbv.gene.add(indiv.Chrom_gene.get(i));
			}
			ucbv.fitness = indiv.Fitness;
			ucbv.totvisits = (int) Math.pow(6, LDeep); // �����ܴ�����ָ���ڵ�ķ��ʴ�����Ҳ����˵�Ƿ����ܴ���
			ucbv.visits = 1;
			LMostIndivs.put(root.Gene, ucbv); // ���ڵ������ڵ㷶Χ�����Ÿ�����д洢

			return 1;
		} else {
			for (int i = 0; i < 6; i++) {
				TreeNode newchildren = new TreeNode();
				ful = new FullSet(this.con);
				for (String s : root.Gene) { // �����s���������root.Gene���޹صģ����ٸı�s��ֵ�����߲��ᷢ���ı�
					newchildren.Gene.add(s);
				}
				newchildren.Gene.add(ful.get(i));
				newchildren.deep = newchildren.Gene.size();
				root.children.add(newchildren);
				initialize_expand(newchildren);
			}
		}
		return 0;
	}

	/**
	 * ����L�����µĽڵ�ռ䣬���������һ�����壬��Ϊ���ڵ�����Ÿ��� ע��������岻֪���ǲ�����Ч�ģ�������滹Ҫ���м��
	 */
	public TreeNode random_expand(TreeNode root) {
		while (root.deep < DEEP) {
			if (root.deep < headlength) {
				ful = new FullSet(this.con);
				String str = ful.get(r.nextInt(head_Actions));
				root = expand(str, root);
			} else {
				FeaSet = new FeatureSet(this.con);
				String str = FeaSet.get(r.nextInt(tail_Actions));
				root = expand(str, root);
			}
		}
		return root;

	}

	// ���������ֵĸ����Ƿ���һ����Ч�ĸ��壨���Ƿ���ֳ���Ϊ0�������
	public Individual completeIndiv(TreeNode root) throws IOException {
		Individual indiv = new Individual(this.con);
		int count = 0; // �������������ȥ����Ч�ڵ�

		TreeNode tempnode = random_expand(root);

		indiv.AddGene(tempnode.Gene);
		indiv.Initialization_constant();
		indiv.Fitness = indiv.GetFitness(this.constant_dc);

		return indiv;

	}

	// �������ѡ������һ�������ӿռ䣬Ȼ�󷵻ظ��ӿռ�����Ÿ���
	public AvgExp3values greedyUCB() {
		Random random = new Random();
		int pos = random.nextInt(LMostIndivs.size());
		AvgExp3values result = null;
		int i = 0;
		for (AvgExp3values tempUCB : LMostIndivs.values()) {
			if (i == pos)
				result = tempUCB;
			i++;
		}
		return result;
	}

	/**
	 * @author 
	 * @return
	 * @throws IOException
	 */
	// �õ������ӿռ��ͷ������Ϊ���ͷ�����ȿ����ǲ�ȷ���ģ����Բ���ֱ��ͨ�����Ÿ��������
	public AvgExp3values getBestSubspace() throws IOException {
		//System.out.println("�Ұ��޺�Ө");
		/*
		int i=0;
		int flag=0;//���λ�����������ѵ��ӿռ�
		d=Math.random();
		int flag1=1;//���λ�����������һ�κͷǵ�һ��
		touctvalues=new double[this.LMostIndivs.size()];
		toestreward=new double[LMostIndivs.size()];
		yita=Math.sqrt(Math.log(LMostIndivs.size())/100000*LMostIndivs.size());
		
		for(int j=0;j<LMostIndivs.size();j++)
		{
			tospareward=tospareward+Math.pow(e,yita*toestreward[j]);
		}
		*/
		File file_out = new File("E:\\DataSet6\\gailv\\"+"200."+Fitness_test.yunxing+".txt ");//������¼ѡ���ӿռ�ĸ���
		//FileOutputStream fos=new FileOutputStream("E:\\DataSet\\overlap\\3.txt");
		FileOutputStream fos2 = new FileOutputStream(file_out, true);
		if (!file_out.exists()) {
			file_out.createNewFile();
		}
		
	    File f=new File("E:\\DataSet6\\tongji\\"+"200."+Fitness_test.yunxing+".txt ");
	    FileOutputStream ff=new FileOutputStream(f,true);
	    if(!f.exists())
	    {
	    	f.createNewFile();
	    }
	    
		int i=0;//������¼�ǵڼ����ӿռ�
		int flag=0;//���λ�����������ѵ��ӿռ�
		int flag1=1;//���λ�����������һ�κͷǵ�һ��
		d=Math.random();
		double sum=0;
		//double count=0;
		number[gen]=LMostIndivs.size();//��¼ÿһ���ж����ӿռ�
		
	    bound=LMostIndivs.size()*10;//ǰ��ʹ�þ���̽���Ĳ���
	    
	    
	    if(gen<=bound)
	    {
	    	 d1=(int)(d*LMostIndivs.size());
	    	 for (AvgExp3values tempAvgExp3 : LMostIndivs.values())
	    	 {
	    		 while(count[d1]>10)
	    		 {
	    			 d=Math.random();
	    			 d1=(int)(d*LMostIndivs.size());
	    		 }
	    		      if(i==d1)
	    		   {
	    			 
	    		    	  AvgExp3=tempAvgExp3;
				     flag=i;
				     tempAvgExp3.leijifitness();
				     leijiafitness=tempAvgExp3.sumfitness;
				     break;
	    		   }
	    		      i++;
	    		 }
	    	 count[flag]++;//��ѡ�е��ӿռ��������1
	    		 
	    	 }
	    //����̽��ʱ�Ľ���ֵ���·�ʽ
	    if(gen<=bound)
	    {
	    	toestreward[flag]=((gen-1)*toestreward[flag]+(1/(1+leijiafitness)))/gen;
	    	/*
	    	for(int j=0;j<LMostIndivs.size();j++)
			{
				tospareward=tospareward+Math.pow(e,yita*toestreward[j]);//�������һ���޸ģ�����һ������
			}
	    	touctvalues[flag]=Math.pow(e,yita*toestreward[flag])/tospareward;
	    	toestreward[flag]=toestreward[flag]+1-(1-(1/(1+leijiafitness)))/(touctvalues[flag]);
			for(int j=0;j<LMostIndivs.size();j++)
			{
				if(j!=flag)
				{
					toestreward[j]=toestreward[j]+1;
				}
			}
			if(gen>0)
			{
				for(int j=number[gen-1];j<=number[gen]-1;j++)
				{
					toestreward[j]=toestreward[j]+gen;
				}
			}
	    	*/
	    }
		
		if(gen>bound)
     {
			yita=Math.sqrt((Math.log(LMostIndivs.size())+(double)(gen2-1)*gen2)/(2*gen2*LMostIndivs.size()));
			//yita=200;
		for(int j=0;j<LMostIndivs.size();j++)
		{
			tospareward=tospareward+Math.pow(e,yita*toestreward[j]);//�������һ���޸ģ�����һ������
		}
		//�������������ӿռ����Ӧ��ֵ���Ա��ڹ�һ������
		/*
		for(UCBvalues tempUCB1 : LMostIndivs.values())
		{
			
			tempUCB1.leijifitness();
			count=tempUCB1.sumfitness;
			if(count<50&&!Double.isInfinite(count))
			{
			totalfitness=totalfitness+tempUCB1.sumfitness;
			}
			
		}
		*/
		
		
		for (AvgExp3values tempUCB : LMostIndivs.values()) {
			touctvalues[i]=Math.pow(e,yita*toestreward[i])/tospareward;//�������һ���޸ģ�����һ������
			if(flag1==1)
			{
			     if(0<=d&&d<touctvalues[i])
			    {
			    	 AvgExp3=tempUCB;
				     flag=i;
				     tempUCB.leijifitness();
				     leijiafitness=tempUCB.sumfitness;
				     break;
			    }
			} 
			 if(flag1==0)
			{
				if(sum<=d&&d<touctvalues[i]+sum)
				{
					AvgExp3=tempUCB;
					flag=i;
					tempUCB.leijifitness();
					leijiafitness=tempUCB.sumfitness;
					break;
				}
			}
			 sum=sum+touctvalues[i];
			 flag1=0;
			 i++;			
			//tempUCB.calculateValues(this.root.nVisits);

			//if (tempUCB.uctvalues > bestUCB.uctvalues) {
			//	bestUCB = tempUCB;
			//}
		}
     }	
		//int size=LMostIndivs.size();//������¼��ǰ��Ⱥ�Ĵ�С
		
		//toestreward[flag]=toestreward[flag]+1-(leijiafitness/100)/(touctvalues[flag]);
		//����exp3ʱ�Ľ������·�ʽ
		if(gen>bound)
		{
		toestreward[flag]=((gen2-1)*toestreward[flag]+1-(1-(1/(1+leijiafitness)))/(touctvalues[flag]))/gen2;
		for(int j=0;j<LMostIndivs.size();j++)
		{
			if(j!=flag)
			{
				toestreward[j]=((gen2-1)*toestreward[j]+1)/gen2;
			}
		}
		if(gen>0)
		{
			for(int j=number[gen-1];j<=number[gen]-1;j++)
			{
				//toestreward[j]=toestreward[j]+gen;
				toestreward[j]=1;
			}
		}
		gen2++;
		}
	    tospareward=0;
	   // totalfitness=0;
		gen++;
	   huatu[flag]++;
	//	if(gen>bound)
		//{
	//	baocun[flag][gen1]=leijiafitness;
	//	gen1++;
	//	}
		//�����100������������Ҫ����yita
	//	if(gen>bound&&gen%100==0)
	//	{
		//	touctvalues1=InformationTree.paixu(touctvalues);//����ѡ������ɴ�С����
		//	for(int k=0;k<9;k++)
		//	{
			//	zhuanhuan=touctvalues1[k];
			//	for(int d=0;d<100;d++)
			//	{
			//		change[d]=baocun[zhuanhuan][d];
			//	}
			//	jizhi=InformationTree.panduan(change);
			//	if(jizhi>=0.5)
			//	{
			//		zongji++;
			//	}
				
			//}
			//if(zongji>=5)
			//{
			//
			//}
		//	else
			//{
		//		yita=yita-(0.0006/1000);
		//	}
		//	gen1=0;
		//}
		
		/*
     for (UCBvalues tempUCB : LMostIndivs.values()) {
			
			//tempUCB.calculateValues(this.root.nVisits);
			tempUCB.calculateValues1(i);
			
			touctvalues[i]=tempUCB.uctvalues;
			
			if(flag1==1)
			{
			     if(0<=d&&d<touctvalues[i])
			    {
				     bestUCB=tempUCB;
				     flag=i;
			    }
			    
			}
			
			 
			
			 if(flag1==0)
			{
				if(sum<=d&&d<touctvalues[i]+sum)
				{
					bestUCB=tempUCB;
					flag=i;
				}
			}
			
			 sum=sum+touctvalues[i];
			 flag1=0;
			
			if (tempUCB.uctvalues > bestUCB.uctvalues) {
				bestUCB = tempUCB;
				//System.out.println(bestUCB.fitness);
				flag=i;
			}
			
			i++;
		}
		
               tospareward=0;
		//�����ӿռ���Ҫ���չ�ʽ���и���
		toestreward[flag]=toestreward[flag]+1-(bestUCB.fitness/100)/bestUCB.uctvalues;
		//������ӿռ����ֵ����1
		for(int j=0;j<i;j++)
		{
			if(j!=flag)
			{
				toestreward[j]=toestreward[j]+0;//�޸ģ�ģ�����д���
			}
		}
		*/
		//System.out.println("��ǰ�ӿռ�������"+LMostIndivs.size());
		fos2.write("\r\n".getBytes()); // д��һ������
		if(gen<=bound)
		{
		fos2.write(("����:"+gen+";ѡȡ���ӿռ�Ϊ�� "+flag+";��ѡ�еĸ���Ϊ��"+(1/(double)LMostIndivs.size())+";�ۼƽ���ֵΪ��"+toestreward[flag]+";�����Ӧ��Ϊ��"+leijiafitness).getBytes()); // д���ı�
		
		}
		if(gen>bound)
		{
		fos2.write(("�´���:"+gen+";ѡȡ���ӿռ�Ϊ�� "+flag+";��ѡ�еĸ���Ϊ��"+touctvalues[flag]+";�ۼƽ���ֵΪ��"+toestreward[flag]+";�����Ӧ��Ϊ��"+leijiafitness+"yita��"+yita).getBytes()); // д���ı�
		}
		fos2.write("\r\n".getBytes()); // д��һ������
		fos2.flush();
		fos2.close();
		//���ӿռ䱻���ʵĴ���д�뵽�ļ���
		if(gen==100000)
		{
			for(int t=0;t<LMostIndivs.size();t++)
			{
			ff.write((t+" "+huatu[t]).getBytes());
		    ff.write("\r\n".getBytes());
			
			}
			ff.flush();
			ff.close();
		}
		
		 
		
		return AvgExp3;//bestUCB����һ���ӿռ�
	}
	
	//�������±갴�ɴ�С����������
	 public static int[] paixu(double[] a)
     {
		 int flag=0;
   	  int[] b=new int[a.length];
   	  int index=0;
   	  double max=0;
  
   	  for(int i=0;i<a.length;i++)
   	  {
   		  
   		  max=-1;
   		  index=i;
   		  for(int j=0;j<a.length;j++)
   		  {
   			  if(flag==1)
   			  {
   			  for(int k=0;k<i;k++)
   			  {
   				  if(j==b[k])
   				  {
   					  j++;
   					  k=0;
   					  
   				  }
   				  else
   				  {
   					  
   				  }
   			  }
   			  }
   			  
   			  if(j==a.length)
   			  {
   				  break;
   			  }
   			  
   			  if(max>=a[j])
   			  {
   				  //index=i;
   				 // max=a[i];
   			  }
   			  else
   			  {
   				  index=j;
   				  max=a[j];
   			  }
   			 
   	  }
   		 flag=1;
   	 b[i]=index;
   	  
     }
   	  return b;
     }
       
      //�ж��ӿռ�ļ�ֵ�������
      public static double panduan(double[] a)
      {
    	 double index=a[0];
    	 double flag=0;//��¼����0�ĸ���
    	 double flag1=0;//��¼��һ�����ǰһ��ĸ���
    	  for(int i=0;i<a.length;i++)
    	  {
    		 if(a[i]==0)
    		 {
    			 
    		 }
    		 if(a[i]!=0)
    		 {
    			 flag++;
    		 }
    		 if(a[i]<index)
    		 {
    			 index=a[i];
    			 flag1++;
    		 }
    	  }
    	   return flag1/(flag-1);
      }
      
      
     
      
	// ��ѯ��ǰ���ж��ٸ��ӿռ�û�б�������
	public int[] notvisit() {
		int[] result = new int[2];
		result[1] = 0;
		for (AvgExp3values tempUCB : LMostIndivs.values()) {

			if (tempUCB.visits < 2) {
				result[1]++;
			}
		}

		result[0] = LMostIndivs.size() - result[1];
		return result;
	}

	/*
	 * �Ƚϵ�LDeep��ڵ���UCBֵ���Ľڵ㣬Ȼ�󷵻ظýڵ�����Ÿ���
	 */
	
	
	public Individual compareUCB() throws IOException {

		// for (LinkedList<String> tempgene : LMostIndivs.keySet()) {
		// System.out.println("���ӳ��key:" + tempgene.toString());
		// }
		
		
		
		for (AvgExp3values tempUCB : LMostIndivs.values()) {
			
			tempUCB.calculateValues(this.root.nVisits);
			
			
			
		
			if (tempUCB.uctvalues > bestUCB.uctvalues) {
				bestUCB = tempUCB;
			}
				//System.out.println(bestUCB.fitness);
				
		
		}
	 
		

		System.out.println("����UCBֵΪ��" + bestUCB.uctvalues + "  ���ǣ�" + bestUCB.gene.toString());
		System.out.println("���Ÿ���ǰ��λ��" + bestUCB.indiv.Chrom_gene.get(0) + bestUCB.indiv.Chrom_gene.get(1)
				+ bestUCB.indiv.Chrom_gene.get(2) + "  ������Ӧ��Ϊ��" + bestUCB.indiv.Fitness);
		
		return bestUCB.indiv;

	}

	
}