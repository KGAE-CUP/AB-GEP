package New_gep_greedy1;

import java.util.LinkedList;
import java.util.Random;

//�ռ仮��������ز���
public class TreeNode {
	static Random r = new Random(); // ����һ�������������������������
	private int head_Actions = 6; // �������ö���������ʵ����ÿ���ڵ������ӽڵ���Ŀ��ѡ��ڵ㣬����ѡ����
	private int tail_Actions = 2;
	String NodeName = ""; // �����洢��Ϣ����ÿһ���ڵ�Ļ���
	LinkedList<TreeNode> children; // �����ӽڵ�
	public int nVisits; // nVisits�Ƿ��ʴ�����totValue���ۻ�����ֵ
	double fitness;

	public int deep; // ��ǰ�ڵ�����

	InformationTree newNode; // �����洢ÿ������չ�����Ľڵ�
	public LinkedList<String> Gene; // ÿ���ڵ㶼��һ������ģʽ�����Խ��Ľڵ��Ӧ�Ļ���ģʽλԽ��
	public String name; // ���name����Gene���ַ�����ʽ

	//���캯��
	public TreeNode() {
		this.nVisits = 0;
		children = new LinkedList<TreeNode>();
		Gene = new LinkedList<String>();
		name = "";
	}

	/*
	 * ����ÿ���ڵ�����֣�����ÿ��ģʽ���ַ�����ʽ
	 */
	public String out_name() {
		for (String s : Gene) {
			name = name + s;
		}
		return name;
	}

	/*
	 * �ж��Ƿ�ΪҶ�ӽڵ�
	 */
	public boolean isLeaf() {

		return children == null;

	}

	/*
	 * ���½ڵ��״ֵ̬
	 */
	//updateStats������¼�ӿռ�ķ��ʴ���
	public void updateStats() { //

		nVisits++; // �ýڵ���ʴ�����һ

	}

	/*
	 * �жϸýڵ��Ƿ��Ǻ��ӽڵ�
	 */
	public int arity() {
		return children == null ? 0 : children.size();
	}
}