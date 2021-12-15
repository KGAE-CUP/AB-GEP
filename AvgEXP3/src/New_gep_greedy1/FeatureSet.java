package New_gep_greedy1;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class FeatureSet {

	public List<String> sFeature;
	public int length;
	private Random random;
	public Constant con;

	/**
	 * ��ʼ�� ���ñ�����
	 */
	public FeatureSet(Constant con1) { // ���캯��
		con = con1;
		sFeature = new LinkedList<String>();
		String[] temp = con.featureSet; // ���ñ�����
		for (int i = 0; i < temp.length; ++i) {
			sFeature.add(temp[i]);
		}

		length = sFeature.size();

	}

	/*
	 * ��������i���õ�ȫ���е��ַ�
	 */
	public String get(int i) {
		String a = sFeature.get(i);
		return a;
	}

	/*
	 * �������һ��ȫ���еķ��ţ�������
	 */
	public String getRandom() {
		random = new Random();
		int nIndex = random.nextInt(this.length); // �������һ����
		return this.get(nIndex); // �������Ӧȫ����һ������
	}

	/*
	 * ���ַ�����ʽ����ȫ��
	 */
	public String toString() {
		return this.sFeature.toString();
	}

}