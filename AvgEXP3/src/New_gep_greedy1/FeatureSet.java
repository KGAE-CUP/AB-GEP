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
	 * 初始化 设置变量集
	 */
	public FeatureSet(Constant con1) { // 构造函数
		con = con1;
		sFeature = new LinkedList<String>();
		String[] temp = con.featureSet; // 设置变量集
		for (int i = 0; i < temp.length; ++i) {
			sFeature.add(temp[i]);
		}

		length = sFeature.size();

	}

	/*
	 * 根据索引i，得到全集中的字符
	 */
	public String get(int i) {
		String a = sFeature.get(i);
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
		return this.sFeature.toString();
	}

}