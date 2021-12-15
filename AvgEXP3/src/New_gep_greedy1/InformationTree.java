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
	static Random r = new Random(); // 设置一个随机类对象，用来随机生成数字
	private int head_Actions; // 这里设置动作数，其实就是每个节点的最大孩子节点数目，选择节点，就是选择动作
	private int tail_Actions;
	TreeNode root; // root是一个对象，一棵信息树中的根节点
	private int DEEP;
	public int headlength; // 个体染色体头部长度，用来约束节点的扩展深度

	public Constant con;
	public List<Double> constant_dc;//constant_dc是一个double类型的集合

	public FullSet ful;//设置全集，也就是头部取值范围
	public FeatureSet FeaSet;//设置函数集，也就是尾部取值范围

	public int LDeep; // 可用历史信息层数
	
	//public double d;//产生随机数
	
	//public double[] touctvalues=null;//记录每次的所有概率值(UCB值)
	
	//double sum=0;

	//Map是一个集合
	public Map<LinkedList<String>, AvgExp3values> LMostIndivs; // 第L层每个节点领域最优个体，以及这个节点的访问次数
	private AvgExp3values AvgExp3;//bestUCB是一个对象
	private AvgExp3values bestUCB;//bestUCB是一个对象
	//Object[] keyss=null;
	
	//public double[] toestreward=new double[this.LMostIndivs.size()];
	//public double[] toestreward=null;//该数组用来记录每个子空间的累计预估奖励值
	//public double[] toestreward=null;
	//public double yita=0;
	//public double tospareward=0;
	//public double e=Math.E;
	
	public double[] toestreward=new double[2000];//记录每一个子空间的累计奖励值
	public double tospareward=0;//记录所有子空间累计奖励值求和
	//public double yita=Math.sqrt(Math.log(100)/(100000*100));//yita是个参数，学习率
	public double yita=0.0006;
	//////////////这一行是新增的，需要试一下EXP3-IX
	public double gama=0.5*yita;
	//////////////
	public double e=Math.E;
	public double[] touctvalues=new double[2000];//用来记录每个子空间被选择的概率
	public double d;//产生随机数
	public double leijiafitness=0;
	//public double totalfitness=0;//记录所有子空间的适应度，以便于归一化
	
	public int gen=1;//记录代数
	public int gen2=1;
	public int[] number=new int[100002];//记录每一代有多少个子空间
	
	int d1=0;//d1用来产生整型随机数（范围0-k)
	int bound=0;//前期使用均匀探索与后期模型搜索的分界（哪一代）
	public double[] count=new double[2000];//记录每个子空间被访问多少次
	
	public double[][] baocun=new double[2000][100];//记录每一代访问的子空间和最优值
	public int gen1=0;//记录要更新yita时的代数
	public int[] touctvalues1=new int[2000];//用来排序每个子空间被选择的概率
	int zongji=0;//用来记录子空间极值出现频率满足要求的有几个
	double jizhi=0;
    int zhuanhuan=0;
	public double[] change=new double[100];//将二维数组转换成一维数组
	public int[] huatu=new int[2000];//记录每个子空间被访问多少次
	
	//构造函数
	public InformationTree(Constant con1, List<Double> constant_dc1) throws IOException {
		con = con1;
		constant_dc = constant_dc1;
		ful = new FullSet(con);
		FeaSet = new FeatureSet(con);
		root = new TreeNode();
		this.LDeep = con.deep;
		AvgExp3 = new AvgExp3values(con1, constant_dc); // 在这棵信息树初始化的时候，进行创建一个
		

		this.head_Actions = ful.length;
		this.tail_Actions = FeaSet.length;
		this.DEEP = con.headlength * 2 + 1;
		this.headlength = con.headlength;

		root.deep = 0; // 默认根节点深度为0，所以这棵树总深度为22
		root.nVisits++;

		LMostIndivs = new LinkedHashMap<LinkedList<String>, AvgExp3values>();
		//keyss=LMostIndivs.keySet().toArray();
		//toestreward=new double[this.LMostIndivs.size()];
		//touctvalues=new double[this.LMostIndivs.size()];
		//toestreward=new double[LMostIndivs.size()];
		//yita=Math.sqrt(Math.log(LMostIndivs.size())/100000*LMostIndivs.size());
		this.initialize_expand(root); // 扩展出LDeep层节点

	}

	/*
	 * 保存种群个体到信息树中
	 */
	public void save_population(LinkedList<Individual> pop) {
		System.out.println("总节点个数：" + this.LMostIndivs.size());
		for (Individual indiv : pop) {
			TreeNode cur = this.root; // 当前节点为根节点
			for (int i = 0; i <= LDeep; i++) { // 不能存储太多层节点，5000代以后就会太慢了，因为节点信息占据了很多内存空间
				cur.deep = i; // 根节点为0层
				cur.updateStats(); // 更新当前节点的信息，目前就增加了一个访问次数

				if (i == LDeep) { // 如果到了可用历史信息层，那么就要开始比较该节点的最佳个体适应度了

					if (LMostIndivs.containsKey(cur.Gene)) { // 如果存在这个节点，直接进行对比
						double fitness = LMostIndivs.get(cur.Gene).fitness;
						if (indiv.Fitness < fitness) { // 如果这个个体适应度小的话，那就替换
							LMostIndivs.put(cur.Gene,
									new AvgExp3values(indiv.clone(), cur.Gene, this.root.nVisits, cur.nVisits));
						} else {
							LMostIndivs.get(cur.Gene).visits = cur.nVisits;
						}
					} else { // 如果这个个体所属于的节点不存在，那么就创造这个节点，因为这棵树初始化的时候，一些节点没有存进去
						LMostIndivs.put(cur.Gene, new AvgExp3values(indiv.clone(), cur.Gene, root.nVisits, cur.nVisits));
					}

				}
				cur = this.expand(indiv.Chrom_gene.get(i), cur); // 扩展孩子节点，并且将孩子节点传回来作为当前节点

			}
			cur.deep = indiv.Chrom.size();
			cur.updateStats(); // 更新叶子节点的信息，上面没有进行更新

		}

	}

	/*
	 * 根据基因位进行扩展，并返回孩子节点
	 */
	public TreeNode expand(String gene, TreeNode farther) {

		for (TreeNode children : farther.children) {
			int n = children.Gene.size();
			if (children.Gene.get(n - 1).equalsIgnoreCase(gene)) { // 如果孩子节点最后一位基因等于当前基因，那么这一位就是我们要找的孩子节点

				return children; // 找到了该孩子节点，该返回了
			}

		} // 上面没有找到个体下一位基因对应的孩子节点，所以要进行扩展
		TreeNode newchildren = new TreeNode();
		for (String s : farther.Gene) {
			newchildren.Gene.add(s);
		}
		newchildren.Gene.add(gene);
		farther.children.add(newchildren);
		newchildren.deep = farther.deep + 1;
		return newchildren;
	}

	// 为了图形可视化出来，扩展所有节点
	public int expand1(TreeNode root) {
		if (root.deep == 6) {
			return 1;
		} else {
			for (int i = 0; i < 6; i++) {
				TreeNode newchildren = new TreeNode();
				ful = new FullSet(this.con);
				for (String s : root.Gene) { // 这里的s是深拷贝，与root.Gene是无关的，至少改变s的值，后者不会发生改变
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

	// 利用前L节点，所以要提前将前L层所有节点都扩展出来，这里就是扩展前LDeep节点的
	public int initialize_expand(TreeNode root) throws IOException {
		if (root.deep == LDeep) {
			Individual indiv = completeIndiv(root); // 根据这一层的节点，诞生一个个体，这个个体暂时为该节点下最优个体
			if (indiv.Chrom.size() == 0) {
				return 1;
			}
			AvgExp3values ucbv = new AvgExp3values(this.con, this.constant_dc); // 这里面存储了第LDeep层节点的最优个体、访问次数，和总的访问次数
			ucbv.indiv = indiv;
			for (int i = 0; i < con.deep; i++) { // 因为这是基因，索引从0开始，不像树，所以这里是小于
				ucbv.gene.add(indiv.Chrom_gene.get(i));
			}
			ucbv.fitness = indiv.Fitness;
			ucbv.totvisits = (int) Math.pow(6, LDeep); // 访问总次数是指根节点的访问次数，也可以说是访问总次数
			ucbv.visits = 1;
			LMostIndivs.put(root.Gene, ucbv); // 将节点基因与节点范围内最优个体进行存储

			return 1;
		} else {
			for (int i = 0; i < 6; i++) {
				TreeNode newchildren = new TreeNode();
				ful = new FullSet(this.con);
				for (String s : root.Gene) { // 这里的s是深拷贝，与root.Gene是无关的，至少改变s的值，后者不会发生改变
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
	 * 对于L层以下的节点空间，先随机生成一个个体，作为给节点的最优个体 注意这个个体不知道是不是有效的，因此上面还要进行检测
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

	// 检查上面出现的个体是否是一个有效的个体（看是否出现除数为0的情况）
	public Individual completeIndiv(TreeNode root) throws IOException {
		Individual indiv = new Individual(this.con);
		int count = 0; // 用来计算次数，去除无效节点

		TreeNode tempnode = random_expand(root);

		indiv.AddGene(tempnode.Gene);
		indiv.Initialization_constant();
		indiv.Fitness = indiv.GetFitness(this.constant_dc);

		return indiv;

	}

	// 采用随机选择，生成一个最优子空间，然后返回该子空间的最优个体
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
	// 得到最优子空间的头部，因为这个头部长度可能是不确定的，所以不能直接通过最优个体来获得
	public AvgExp3values getBestSubspace() throws IOException {
		//System.out.println("我爱崔鹤莹");
		/*
		int i=0;
		int flag=0;//标记位，用来标记最佳的子空间
		d=Math.random();
		int flag1=1;//标记位，用来区别第一次和非第一次
		touctvalues=new double[this.LMostIndivs.size()];
		toestreward=new double[LMostIndivs.size()];
		yita=Math.sqrt(Math.log(LMostIndivs.size())/100000*LMostIndivs.size());
		
		for(int j=0;j<LMostIndivs.size();j++)
		{
			tospareward=tospareward+Math.pow(e,yita*toestreward[j]);
		}
		*/
		File file_out = new File("E:\\DataSet6\\gailv\\"+"200."+Fitness_test.yunxing+".txt ");//用来记录选中子空间的概率
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
	    
		int i=0;//用来记录是第几个子空间
		int flag=0;//标记位，用来标记最佳的子空间
		int flag1=1;//标记位，用来区别第一次和非第一次
		d=Math.random();
		double sum=0;
		//double count=0;
		number[gen]=LMostIndivs.size();//记录每一代有多少子空间
		
	    bound=LMostIndivs.size()*10;//前期使用均匀探索的策略
	    
	    
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
	    	 count[flag]++;//被选中的子空间访问数加1
	    		 
	    	 }
	    //均匀探索时的奖励值更新方式
	    if(gen<=bound)
	    {
	    	toestreward[flag]=((gen-1)*toestreward[flag]+(1/(1+leijiafitness)))/gen;
	    	/*
	    	for(int j=0;j<LMostIndivs.size();j++)
			{
				tospareward=tospareward+Math.pow(e,yita*toestreward[j]);//这边做了一下修改，加了一个负号
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
			tospareward=tospareward+Math.pow(e,yita*toestreward[j]);//这边做了一下修改，加了一个负号
		}
		//用来计算所有子空间的适应度值，以便于归一化处理
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
			touctvalues[i]=Math.pow(e,yita*toestreward[i])/tospareward;//这边做了一下修改，加了一个负号
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
		//int size=LMostIndivs.size();//用来记录当前种群的大小
		
		//toestreward[flag]=toestreward[flag]+1-(leijiafitness/100)/(touctvalues[flag]);
		//采用exp3时的奖励更新方式
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
		//如果是100代的整数，需要调整yita
	//	if(gen>bound&&gen%100==0)
	//	{
		//	touctvalues1=InformationTree.paixu(touctvalues);//按照选择概率由大到小排列
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
		//最优子空间需要按照公式进行更新
		toestreward[flag]=toestreward[flag]+1-(bestUCB.fitness/100)/bestUCB.uctvalues;
		//其余的子空间估计值均加1
		for(int j=0;j<i;j++)
		{
			if(j!=flag)
			{
				toestreward[j]=toestreward[j]+0;//修改，模型上有错误
			}
		}
		*/
		//System.out.println("当前子空间数量："+LMostIndivs.size());
		fos2.write("\r\n".getBytes()); // 写入一个换行
		if(gen<=bound)
		{
		fos2.write(("代数:"+gen+";选取的子空间为： "+flag+";被选中的概率为："+(1/(double)LMostIndivs.size())+";累计奖励值为："+toestreward[flag]+";最佳适应度为："+leijiafitness).getBytes()); // 写入文本
		
		}
		if(gen>bound)
		{
		fos2.write(("新代数:"+gen+";选取的子空间为： "+flag+";被选中的概率为："+touctvalues[flag]+";累计奖励值为："+toestreward[flag]+";最佳适应度为："+leijiafitness+"yita："+yita).getBytes()); // 写入文本
		}
		fos2.write("\r\n".getBytes()); // 写入一个换行
		fos2.flush();
		fos2.close();
		//将子空间被访问的次数写入到文件中
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
		
		 
		
		return AvgExp3;//bestUCB就是一个子空间
	}
	
	//对数组下标按由大到小的序列排列
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
       
      //判断子空间的极值出现情况
      public static double panduan(double[] a)
      {
    	 double index=a[0];
    	 double flag=0;//记录不是0的个数
    	 double flag1=0;//记录后一项大于前一项的个数
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
      
      
     
      
	// 查询当前还有多少个子空间没有被遍历到
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
	 * 比较第LDeep层节点中UCB值最大的节点，然后返回该节点的最优个体
	 */
	
	
	public Individual compareUCB() throws IOException {

		// for (LinkedList<String> tempgene : LMostIndivs.keySet()) {
		// System.out.println("输出映射key:" + tempgene.toString());
		// }
		
		
		
		for (AvgExp3values tempUCB : LMostIndivs.values()) {
			
			tempUCB.calculateValues(this.root.nVisits);
			
			
			
		
			if (tempUCB.uctvalues > bestUCB.uctvalues) {
				bestUCB = tempUCB;
			}
				//System.out.println(bestUCB.fitness);
				
		
		}
	 
		

		System.out.println("最优UCB值为：" + bestUCB.uctvalues + "  它是：" + bestUCB.gene.toString());
		System.out.println("最优个体前三位：" + bestUCB.indiv.Chrom_gene.get(0) + bestUCB.indiv.Chrom_gene.get(1)
				+ bestUCB.indiv.Chrom_gene.get(2) + "  他的适应度为：" + bestUCB.indiv.Fitness);
		
		return bestUCB.indiv;

	}

	
}