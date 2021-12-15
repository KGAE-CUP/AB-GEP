package New_gep_greedy1;

import java.io.File;

/**
 * ����洢����ȫ�ֳ�����Ҳ���ǳ�����
 * 
 * @author 
 */

public class Constant {

	public int GeneCount = 1; // ��������
	public int headlength = 21; // ͷ�����򳤶�
	public int taillength; // β�����򳤶�
	public int chrome_length; // Ⱦɫ�峤�ȣ������˳�����
	public int GeneLength; // ���򳤶�
	public int MaxGeneration = 100000; // ����������
	public int GreedyConstant = 100000; // ̰���㷨�ĵ�������
	public double GreedyRate = 0.5; // ̰���㷨��̰�Ĳ���
	public int Populationsize = 100; // ��Ⱥ����
	public int mostindivs_number = 5; // ÿ��ѡȡ�ľ�Ӣ����
	public double MutationRate = 0.1; // ������0.01//������0.03
	public double OnePRecomRate = 0.7; // һ���������0.5
	public double TwoPRecomRate = 0.3; // �����������
	public double GeneRecomRate = 0.1; // �����������

	public int column; // ���ݼ�����
	public int row; // ���ݼ�����
	public int constant_number = 4;
	public File file;
	public int deep; // ��Ϣ������

	public String[] fullSet;
	public String[] featureSet;
	public String[] functionSet;

	public Constant(String[] fullSet, String[] featureSet, String[] functionSet, int column, int row, File file,
			int deep) {

		this.taillength = this.headlength + 1;
		this.GeneLength = this.taillength + this.headlength;
		this.chrome_length = this.GeneLength + this.taillength;

		this.fullSet = fullSet; // ����ȫ����Ҳ����ͷ��ȡֵ��Χ
		this.featureSet = featureSet; // ���ú�������Ҳ����β��ȡֵ��Χ
		this.functionSet = functionSet; // ���ú�����

		this.column = column;
		this.row = row;
		this.file = file;
		this.deep = deep;
	}

}