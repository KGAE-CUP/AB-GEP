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
	 * 初始化 设置函数集
	 */
	public FullSet(Constant con1) {
		con = con1;
		sFullSet = new LinkedList<String>();
		String[] temp = con.fullSet; // 设置全集，也就是头部的取值范围,fullSet是自己录入的数据
		for (int i = 0; i < temp.length; ++i) {
			sFullSet.add(temp[i]);
		}
		this.length = sFullSet.size();

	}

	/*
	 * 根据索引i，得到全集中的字符
	 */
	public String get(int i) {
		String a = sFullSet.get(i);
		return a;
	}

	/*
	 * 随机产生一个全集中的符号，并返回
	 */
	public String getRandom() {
		random = new Random();
		int nIndex = random.nextInt(this.length); // 随机生成一个数
		return this.get(nIndex); // 这个数对应全集中一个符号
	}

	/*
	 * 以字符串形式返回全集
	 */
	public String toString() {
		return this.sFullSet.toString();
	}

}