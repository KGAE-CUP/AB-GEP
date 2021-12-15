package New_gep_greedy1;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AvgExp3values {
	public Individual indiv;//Individual���࣬indiv��һ������
	public double fitness;//��Ӧ��
	
	
	
	public double reward;//����
	public LinkedList<String> gene;
	public int visits;//���ӿռ�ķ��ʴ���
	public int totvisits;//�ܵķ��ʴ���
	public double epsilon = 1e-6; // ��=1*10��-6�η�
	
	public double uctvalues;//�ӿռ��UCBֵ
	public Constant con;//Constant���࣬con��һ������
	
	public double sumfitness;
	
	//public Map<LinkedList<String>, UCBvalues> LMostIndivs1=new HashMap<LinkedList<String>, UCBvalues>(); 
	//public double[] toestreward=new double[this.LMostIndivs1.size()];//������������¼ÿ���ӿռ���ۼ�Ԥ������ֵ
	//public double[] toestreward=new double[informationtree.LMostIndivs.size()];//������������¼ÿ���ӿռ���ۼ�Ԥ������ֵ
   
	//public double yita=Math.sqrt(Math.log(informationtree.LMostIndivs.size())/100000*informationtree.LMostIndivs.size());
	//public double e=Math.E;
	//public double tospareward=0;
	
	
	/*
	 * ���췽��1
	 */
	public AvgExp3values(Constant con, List<Double> constant_dc) throws IOException {
		this.con = con;
		indiv = new Individual(con);
		indiv = indiv.Initialization(); // �����ʼ��һ�����壬�����������Ϊ��������������������û�д洢����Ϣ����
		indiv.separate();
		gene = new LinkedList<String>();

		fitness = indiv.GetFitness(constant_dc);//fitenss���Ǹ������Ӧ��
		visits = 0;
		reward = 0;
		totvisits = 0;
	}

	/*
	 * ���췽��2
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
	 * ͨ�����漸��ֵ������UCBֵ
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
		//System.out.println("�Ұ��޺�Ө");
	}
	
	//����exp3�㷨ѡȡ�ӿռ�
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
		//System.out.println("informationtree.�ӿռ����:"+this.LMostIndivs1.size());
	}
	
*/
}
