package New_gep_greedy1;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class FullSet {

	public List<String> sFullSet;
	public int length;
	private Random random;
	public Constant con;

	/*
	 * ��ʼ�� ���ú�����
	 */
	public FullSet(Constant con1) {
		con = con1;
		sFullSet = new LinkedList<String>();
		String[] temp = con.fullSet; // ����ȫ����Ҳ����ͷ����ȡֵ��Χ,fullSet���Լ�¼�������
		for (int i = 0; i < temp.length; ++i) {
			sFullSet.add(temp[i]);
		}
		this.length = sFullSet.size();

	}

	/*
	 * ��������i���õ�ȫ���е��ַ�
	 */
	public String get(int i) {
		String a = sFullSet.get(i);
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
		return this.sFullSet.toString();
	}

}