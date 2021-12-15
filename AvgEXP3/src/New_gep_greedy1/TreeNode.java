package New_gep_greedy1;

import java.util.LinkedList;
import java.util.Random;

//空间划分树的相关参数
public class TreeNode {
	static Random r = new Random(); // 设置一个随机类对象，用来随机生成数字
	private int head_Actions = 6; // 这里设置动作数，其实就是每个节点的最大孩子节点数目，选择节点，就是选择动作
	private int tail_Actions = 2;
	String NodeName = ""; // 用来存储信息树的每一个节点的基因串
	LinkedList<TreeNode> children; // 树孩子节点
	public int nVisits; // nVisits是访问次数，totValue是累积奖赏值
	double fitness;

	public int deep; // 当前节点的深度

	InformationTree newNode; // 用来存储每次新扩展出来的节点
	public LinkedList<String> Gene; // 每个节点都有一个基因模式，深度越大的节点对应的基因模式位越多
	public String name; // 这个name就是Gene的字符串形式

	//构造函数
	public TreeNode() {
		this.nVisits = 0;
		children = new LinkedList<TreeNode>();
		Gene = new LinkedList<String>();
		name = "";
	}

	/*
	 * 计算每个节点的名字，就是每个模式的字符串格式
	 */
	public String out_name() {
		for (String s : Gene) {
			name = name + s;
		}
		return name;
	}

	/*
	 * 判断是否为叶子节点
	 */
	public boolean isLeaf() {

		return children == null;

	}

	/*
	 * 更新节点的状态值
	 */
	//updateStats用来记录子空间的访问次数
	public void updateStats() { //

		nVisits++; // 该节点访问次数加一

	}

	/*
	 * 判断该节点是否是孩子节点
	 */
	public int arity() {
		return children == null ? 0 : children.size();
	}
}