package New_gep_greedy1;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AvgExp3values {
	public Individual indiv;//Individual是类，indiv是一个对象
	public double fitness;//适应度
	
	
	
	public double reward;//奖励
	public LinkedList<String> gene;
	public int visits;//该子空间的访问次数
	public int totvisits;//总的访问次数
	public double epsilon = 1e-6; // ε=1*10的-6次方
	
	public double uctvalues;//子空间的UCB值
	public Constant con;//Constant是类，con是一个对象
	
	public double sumfitness;
	
	//public Map<LinkedList<String>, UCBvalues> LMostIndivs1=new HashMap<LinkedList<String>, UCBvalues>(); 
	//public double[] toestreward=new double[this.LMostIndivs1.size()];//该数组用来记录每个子空间的累计预估奖励值
	//public double[] toestreward=new double[informationtree.LMostIndivs.size()];//该数组用来记录每个子空间的累计预估奖励值
   
	//public double yita=Math.sqrt(Math.log(informationtree.LMostIndivs.size())/100000*informationtree.LMostIndivs.size());
	//public double e=Math.E;
	//public double tospareward=0;
	
	
	/*
	 * 构造方法1
	 */
	public AvgExp3values(Constant con, List<Double> constant_dc) throws IOException {
		this.con = con;
		indiv = new Individual(con);
		indiv = indiv.Initialization(); // 随机初始化一个个体，其他都以这个为基础，但是这个个体可能没有存储到信息树中
		indiv.separate();
		gene = new LinkedList<String>();

		fitness = indiv.GetFitness(constant_dc);//fitenss就是个体的适应度
		visits = 0;
		reward = 0;
		totvisits = 0;
	}

	/*
	 * 构造方法2
	 */
	public AvgExp3values(Individual indiv, LinkedList<String> gene, int totvisits, int visits) {
		this.indiv = indiv;
		this.fitness = indiv.Fitness;
		this.totvisits = totvisits;
		this.visits = visits;
		this.gene = gene;

		this.reward = (this.reward * (this.visits - 1) + 1 / this.fitness);
		// this.visits;
	}

	/*
	 * 通过上面几个值，计算UCB值
	 */
	
	public void calculateValues(int nVisits) {

		// uctvalues = (reward * (visits - 1) + 1 / fitness) / visits
		// + Math.sqrt(Math.log(nVisits + 1) / (visits + epsilon));

		uctvalues = 1 / (this.fitness + 1) + Math.sqrt(Math.log(nVisits + 1) / (visits + epsilon));
		//System.out.println(informationtree.LMostIndivs.size());
	}
	
	public void leijifitness()
	{
		sumfitness=this.fitness;
		//System.out.println("我爱崔鹤莹");
	}
	
	//利用exp3算法选取子空间
	/*
	public void calculateValues1(int i)
	{
		/*
		for(int j=0;j<informationtree.LMostIndivs.size();j++)
		{
			informationtree.tospareward=informationtree.tospareward+Math.pow(e,informationtree.yita*informationtree.toestreward[j]);
		}
		
		uctvalues=Math.pow(e, informationtree.yita*informationtree.toestreward[i])/informationtree.tospareward;
		//informationtree.tospareward=0;
		//System.out.println("informationtree.子空间个数:"+this.LMostIndivs1.size());
	}
	
*/
}
