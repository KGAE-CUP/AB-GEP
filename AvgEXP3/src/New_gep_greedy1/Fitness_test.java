package New_gep_greedy1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Fitness_test {
	public Constant con;
	int number = 1; // 测试次数
	public static int yunxing=0;

	public void Run_write(File file, int constantpoolnumber, String address) throws IOException {
		// FileOutputStream fos2 = new FileOutputStream(file); // 覆盖
		FileOutputStream fos2 = new FileOutputStream(file, true);
		// 第二個参数为true表示程序每次运行都是追加字符串在原有的字符上
		// 若文件不存在,则创建它
		if (!file.exists()) {
			file.createNewFile();
		}

		fos2.write("\r\n".getBytes()); // 写入一个换行
		fos2.write((address).getBytes()); // 写入文本
		fos2.write("\r\n".getBytes()); // 写入一个换行

		for (int i = 0; i < number; i++) { // 大的框架就是用来循环number次测试的
			Population Pop = new Population(con);
			System.out.println("最大迭代次数为：" + con.MaxGeneration);

			String s = Pop.run(constantpoolnumber);

			fos2.write((s).getBytes()); // 写入文本
			fos2.write("\r\n".getBytes()); // 写入一个换行

		}
		System.out.println("完成了！！！！");

		fos2.flush();
		fos2.close(); // 释放资源
	}
	//新加的
	public void real_data(int column, int files) throws IOException {

		int CONSTANTPOOLNUMBER = 1;

		File file = new File("E:\\DataSet\\new_gep\\real"+"\\one_v\\" + files + ".txt"); // 数据集所在的位置

		File file_out = new File("E:\\DataSet\\New_GEP_Koza_one.txt"); // 用来输出结果

		String[] fullSet = { "+", "-", "*", "/", "sin", "cos", "log", "e", "0","1","2","3","4","5" };
		String[] featureSet = { "0","1","2","3","4","5" };
		String[] functionSet = { "+", "-", "*", "/", "sin", "cos", "log", "e" };

		int row = 7; // 列数
		int deep = 2; // 信息树层数

		con = new Constant(fullSet, featureSet, functionSet, column, row, file, deep);
		String out_address = "下面输出结果所用数据集为：New_GEP_Koza_one_v\\" + files + ".txt";

		this.Run_write(file_out, CONSTANTPOOLNUMBER, out_address);

	}
	
	public void Koza_one(int column, int files) throws IOException {

		int CONSTANTPOOLNUMBER = 1;

		File file = new File("E:\\DataSet\\new_gep\\Koza\\one_v\\" + files + ".txt"); // 数据集所在的位置

		File file_out = new File("E:\\DataSet\\New_GEP_Koza_one.txt"); // 用来输出结果

		String[] fullSet = { "+", "-", "*", "/", "sin", "cos", "log", "e", "0" };
		String[] featureSet = { "0" };
		String[] functionSet = { "+", "-", "*", "/", "sin", "cos", "log", "e" };

		int row = 2; // 列数
		int deep = 3; // 信息树层数

		con = new Constant(fullSet, featureSet, functionSet, column, row, file, deep);
		String out_address = "下面输出结果所用数据集为：New_GEP_Koza_one_v\\" + files + ".txt";

		this.Run_write(file_out, CONSTANTPOOLNUMBER, out_address);

	}

	public void Koza_two(int column, int files) throws IOException {

		int CONSTANTPOOLNUMBER = 1;

		File file = new File("E:\\DataSet\\new_gep\\Koza\\two_v\\" + files + ".txt"); // 数据集所在的位置

		File file_out = new File("E:\\DataSet\\New_GEP_Koza_two.txt"); // 用来输出结果

		String[] fullSet = { "+", "-", "*", "/", "sin", "cos", "log", "e", "0", "1" };
		String[] featureSet = { "0", "1" };
		String[] functionSet = { "+", "-", "*", "/", "sin", "cos", "log", "e" };

		int row = 3; // 列数
		int deep = 3; // 信息树层数

		con = new Constant(fullSet, featureSet, functionSet, column, row, file, deep);
		con.MaxGeneration = 100000;
		String out_address = "下面输出结果所用数据集为：New_GEP_Koza_two_v\\" + files + ".txt";

		this.Run_write(file_out, CONSTANTPOOLNUMBER, out_address);

	}

	public void Korns_one(int column, int files) throws IOException {

		int CONSTANTPOOLNUMBER = 3;

		File file = new File("E:\\DataSet\\new_gep\\korns\\one_v\\" + files + ".txt"); // 数据集所在的位置

		File file_out = new File("E:\\DataSet\\New_GEP_Korns_one.txt"); // 用来输出结果

		String[] fullSet = { "+", "-", "*", "/", "sin", "cos", "log", "e", "pow2", "pow3", "tan", "sqrt", "0", "?" };
		String[] featureSet = { "0", "?" };
		String[] functionSet = { "+", "-", "*", "/", "sin", "cos", "log", "e", "pow2", "pow3", "tan", "sqrt" };

		int row = 2;
		int deep = 2; // 信息树层数

		con = new Constant(fullSet, featureSet, functionSet, column, row, file, deep);
		con.MaxGeneration = 100000;
		String out_address = "下面输出结果所用数据集为：New_GEP_Korns_one_v\\" + files + ".txt";

		this.Run_write(file_out, CONSTANTPOOLNUMBER, out_address);

	}

	public void Korns_two(int column, int files) throws IOException {

		int CONSTANTPOOLNUMBER = 3;

		File file = new File("E:\\DataSet\\new_gep\\korns\\two_v\\" + files + ".txt"); // 数据集所在的位置

		File file_out = new File("E:\\DataSet\\New_GEP_Korns_two.txt"); // 用来输出结果

		String[] fullSet = { "+", "-", "*", "/", "sin", "cos", "log", "e", "pow2", "pow3", "tan", "sqrt", "0", "1",
				"?" };
		String[] featureSet = { "0", "1", "?" };
		String[] functionSet = { "+", "-", "*", "/", "sin", "cos", "log", "e", "pow2", "pow3", "tan", "sqrt" };

		int row = 3;
		int deep = 2; // 信息树层数

		con = new Constant(fullSet, featureSet, functionSet, column, row, file, deep);
		con.MaxGeneration = 100000;
		String out_address = "下面输出结果所用数据集为：New_GEP_Korns_two_v\\" + files + ".txt";

		this.Run_write(file_out, CONSTANTPOOLNUMBER, out_address);

	}

	public void Korns_three(int column, int files) throws IOException {

		int CONSTANTPOOLNUMBER = 3;

		File file = new File("E:\\DataSet\\new_gep\\korns\\three_v\\" + files + ".txt"); // 数据集所在的位置

		File file_out = new File("E:\\DataSet\\New_GEP_Korns_three.txt"); // 用来输出结果

		String[] fullSet = { "+", "-", "*", "/", "sin", "cos", "log", "e", "pow2", "pow3", "tan", "sqrt", "0", "1", "2",
				"?" };
		String[] featureSet = { "0", "1", "2", "?" };
		String[] functionSet = { "+", "-", "*", "/", "sin", "cos", "log", "e", "pow2", "pow3", "tan", "sqrt" };

		int row = 4;

		int deep = 2; // 信息树层数

		con = new Constant(fullSet, featureSet, functionSet, column, row, file, deep);
		con.MaxGeneration = 1000;
		String out_address = "下面输出结果所用数据集为：New_GEP_Korns_three_v\\" + files + ".txt";

		this.Run_write(file_out, CONSTANTPOOLNUMBER, out_address);

	}

	public void Korns_four(int column, int files) throws IOException {

		int CONSTANTPOOLNUMBER = 3;

		File file = new File("E:\\DataSet\\new_gep\\korns\\four_v\\" + files + ".txt"); // 数据集所在的位置

		File file_out = new File("E:\\DataSet\\New_GEP_Korns_four.txt"); // 用来输出结果

		String[] fullSet = { "+", "-", "*", "/", "sin", "cos", "log", "e", "pow2", "pow3", "tan", "sqrt", "0", "1", "2",
				"3", "?" };
		String[] featureSet = { "0", "1", "2", "3", "?" };
		String[] functionSet = { "+", "-", "*", "/", "sin", "cos", "log", "e", "pow2", "pow3", "tan", "sqrt" };

		int row = 5;

		int deep = 2; // 信息树层数

		con = new Constant(fullSet, featureSet, functionSet, column, row, file, deep);
		con.MaxGeneration = 1000;
		String out_address = "下面输出结果所用数据集为：New_GEP_Korns_four_v\\" + files + ".txt";

		this.Run_write(file_out, CONSTANTPOOLNUMBER, out_address);

	}

	public void Vladislavleva1() throws IOException {

		int CONSTANTPOOLNUMBER = 1;

		File file = new File("E:\\DataSet\\new_gep\\Vladislavleva\\one_v\\1.txt"); // 数据集所在的位置

		File file_out = new File("E:\\DataSet\\New_GEP_Vladislavleva_one.txt"); // 用来输出结果

		String[] fullSet = { "+", "-", "*", "/", "pow2", "e", "e1", "sin", "cos", "C1", "C2", "C3", "0" };

		String[] featureSet = { "0" };

		String[] functionSet = { "+", "-", "*", "/", "pow2", "e", "e1", "sin", "cos", "C1", "C2", "C3" };
		int row = 2;
		int column = 101;
		int deep = 2; // 信息树层数

		con = new Constant(fullSet, featureSet, functionSet, column, row, file, deep);

		String out_address = "下面输出结果所用数据集为：New_GEP_Vladislavleva-one_v\\1.txt";

		this.Run_write(file_out, CONSTANTPOOLNUMBER, out_address);

	}

	public void Vladislavleva2() throws IOException {

		int CONSTANTPOOLNUMBER = 1;

		File file = new File("E:\\DataSet\\new_gep\\Vladislavleva\\two_v\\1.txt"); // 数据集所在的位置

		File file_out = new File("E:\\DataSet\\New_GEP_Vladislavleva_two.txt"); // 用来输出结果

		String[] fullSet = { "+", "-", "*", "/", "pow2", "e", "e1", "C1", "C2", "C3", "0", "1" };

		String[] featureSet = { "0", "1" };

		String[] functionSet = { "+", "-", "*", "/", "pow2", "e", "e1", "C1", "C2", "C3" };

		int column = 100;
		int row = 3;
		int deep = 2; // 信息树层数

		con = new Constant(fullSet, featureSet, functionSet, column, row, file, deep);

		String out_address = "下面输出结果所用数据集为：New_GEP_Vladislavleva-two_v\\1.txt";

		this.Run_write(file_out, CONSTANTPOOLNUMBER, out_address);

	}

	public void Vladislavleva3() throws IOException {

		int CONSTANTPOOLNUMBER = 1;

		File file = new File("E:\\DataSet\\new_gep\\Vladislavleva\\two_v\\2.txt"); // 数据集所在的位置

		File file_out = new File("E:\\DataSet\\New_GEP_Vladislavleva_two.txt"); // 用来输出结果

		String[] fullSet = { "+", "-", "*", "/", "pow2", "e", "e1", "sin", "cos", "C1", "C2", "C3", "0", "1" };

		String[] featureSet = { "0", "1" };

		String[] functionSet = { "+", "-", "*", "/", "pow2", "e", "e1", "sin", "cos", "C1", "C2", "C3" };
		int column = 100;
		int row = 3;
		int deep = 2; // 信息树层数

		con = new Constant(fullSet, featureSet, functionSet, column, row, file, deep);

		String out_address = "下面输出结果所用数据集为：New_GEP_Vladislavleva-two_v\\2.txt";

		this.Run_write(file_out, CONSTANTPOOLNUMBER, out_address);

	}

	public void Vladislavleva4() throws IOException {

		int CONSTANTPOOLNUMBER = 1;

		File file = new File("E:\\DataSet\\new_gep\\Vladislavleva\\two_v\\3.txt"); // 数据集所在的位置

		File file_out = new File("E:\\DataSet\\New_GEP_Vladislavleva_two.txt"); // 用来输出结果

		String[] fullSet = { "+", "-", "*", "/", "pow2", "e", "e1", "C1", "C2", "C3", "0", "1" };

		String[] featureSet = { "0", "1" };

		String[] functionSet = { "+", "-", "*", "/", "pow2", "e", "e1", "C1", "C2", "C3" };

		int column = 30;
		int row = 3;
		int deep = 2; // 信息树层数

		con = new Constant(fullSet, featureSet, functionSet, column, row, file, deep);

		String out_address = "下面输出结果所用数据集为：New_GEP_Vladislavleva-two_v\\3.txt";

		this.Run_write(file_out, CONSTANTPOOLNUMBER, out_address);

	}

	public void Vladislavleva5() throws IOException {

		int CONSTANTPOOLNUMBER = 1;

		File file = new File("E:\\DataSet\\new_gep\\Vladislavleva\\two_v\\4.txt"); // 数据集所在的位置

		File file_out = new File("E:\\DataSet\\New_GEP_Vladislavleva_two.txt"); // 用来输出结果

		String[] fullSet = { "+", "-", "*", "/", "pow2", "e", "e1", "sin", "cos", "C1", "C2", "C3", "0", "1" };

		String[] featureSet = { "0", "1" };

		String[] functionSet = { "+", "-", "*", "/", "pow2", "e", "e1", "sin", "cos", "C1", "C2", "C3" };
		int column = 300;
		int row = 3;
		int deep = 2; // 信息树层数

		con = new Constant(fullSet, featureSet, functionSet, column, row, file, deep);

		String out_address = "下面输出结果所用数据集为：New_GEP_Vladislavleva-two_v\\4.txt";

		this.Run_write(file_out, CONSTANTPOOLNUMBER, out_address);

	}

	public void Vladislavleva6() throws IOException {

		int CONSTANTPOOLNUMBER = 1;

		File file = new File("E:\\DataSet\\new_gep\\Vladislavleva\\two_v\\5.txt"); // 数据集所在的位置

		File file_out = new File("E:\\DataSet\\New_GEP_Vladislavleva_two.txt"); // 用来输出结果

		String[] fullSet = { "+", "-", "*", "/", "pow2", "C1", "C2", "C3", "0", "1" };

		String[] featureSet = { "0", "1" };

		String[] functionSet = { "+", "-", "*", "/", "pow2", "C1", "C2", "C3" };

		int column = 50;
		int row = 3;
		int deep = 2; // 信息树层数

		con = new Constant(fullSet, featureSet, functionSet, column, row, file, deep);

		String out_address = "下面输出结果所用数据集为：New_GEP_Vladislavleva-two_v\\5.txt";

		this.Run_write(file_out, CONSTANTPOOLNUMBER, out_address);

	}

	public void Vladislavleva7() throws IOException {

		int CONSTANTPOOLNUMBER = 1;

		File file = new File("E:\\DataSet\\new_gep\\Vladislavleva\\three_v\\1.txt"); // 数据集所在的位置

		File file_out = new File("E:\\DataSet\\New_GEP_Vladislavleva_three.txt"); // 用来输出结果

		String[] fullSet = { "+", "-", "*", "/", "pow2", "C1", "C2", "C3", "0", "1", "2" };

		String[] featureSet = { "0", "1", "2" };

		String[] functionSet = { "+", "-", "*", "/", "pow2", "C1", "C2", "C3" };

		int column = 300;
		int row = 4;
		int deep = 2; // 信息树层数

		con = new Constant(fullSet, featureSet, functionSet, column, row, file, deep);

		String out_address = "下面输出结果所用数据集为：New_GEP_Vladislavleva-three_v\\1.txt";

		this.Run_write(file_out, CONSTANTPOOLNUMBER, out_address);

	}

	public void Vladislavleva8() throws IOException {

		int CONSTANTPOOLNUMBER = 1;

		File file = new File("E:\\DataSet\\new_gep\\Vladislavleva\\five_v\\1.txt"); // 数据集所在的位置

		File file_out = new File("E:\\DataSet\\New_GEP_Vladislavleva_five.txt"); // 用来输出结果

		String[] fullSet = { "+", "-", "*", "/", "pow2", "C1", "C2", "C3", "0", "1", "2", "3", "4" };

		String[] featureSet = { "0", "1", "2", "3", "4" };

		String[] functionSet = { "+", "-", "*", "/", "pow2", "C1", "C2", "C3" };

		int column = 1024;
		int row = 6;
		int deep = 2; // 信息树层数

		con = new Constant(fullSet, featureSet, functionSet, column, row, file, deep);

		String out_address = "下面输出结果所用数据集为：New_GEP_Vladislavleva-five_v\\1.txt";

		this.Run_write(file_out, CONSTANTPOOLNUMBER, out_address);

	}

	public void Keijzer_one(int column, int files) throws IOException {

		int CONSTANTPOOLNUMBER = 2;

		File file = new File("E:\\DataSet\\new_gep\\Keijzer\\one_v\\" + files + ".txt"); // 数据集所在的位置

		File file_out = new File("E:\\DataSet\\New_GEP_Keijzer_one.txt"); // 用来输出结果

		String[] fullSet = { "+", "*", "daoshu", "fushu", "sqrt", "0", "?"};

		String[] featureSet = { "0", "?" };

		String[] functionSet = { "+", "*", "daoshu", "fushu", "sqrt"};

		int row = 2;
		int deep = 3; // 信息树层数

		con = new Constant(fullSet, featureSet, functionSet, column, row, file, deep);
		String out_address = "下面输出结果所用数据集为：New_GEP_Keijzer_one_v\\" + files + ".txt";

		this.Run_write(file_out, CONSTANTPOOLNUMBER, out_address);

	}

	public void Keijzer_two(int column, int files) throws IOException {

		int CONSTANTPOOLNUMBER = 2;

		File file = new File("E:\\DataSet\\new_gep\\Keijzer\\two_v\\" + files + ".txt"); // 数据集所在的位置

		File file_out = new File("E:\\DataSet\\New_GEP_Keijzer_two.txt"); // 用来输出结果

		String[] fullSet = { "+", "*", "daoshu", "fushu", "sqrt", "0", "1", "?" };

		String[] featureSet = { "0", "1", "?" };

		String[] functionSet = { "+", "*", "daoshu", "fushu", "sqrt"};

		int row = 3;
		int deep = 3; // 信息树层数

		con = new Constant(fullSet, featureSet, functionSet, column, row, file, deep);
		String out_address = "下面输出结果所用数据集为：New_GEP_Keijzer_two_v\\" + files + ".txt";

		this.Run_write(file_out, CONSTANTPOOLNUMBER, out_address);

	}

	public void Keijzer_three(int column, int files) throws IOException {

		int CONSTANTPOOLNUMBER = 2;

		File file = new File("E:\\DataSet\\new_gep\\Keijzer\\three_v\\" + files + ".txt"); // 数据集所在的位置

		File file_out = new File("E:\\DataSet\\New_GEP_Keijzer_three.txt"); // 用来输出结果

		String[] fullSet = { "+", "*", "daoshu", "fushu", "sqrt", "0", "1", "2", "?" };

		String[] featureSet = { "0", "1", "2", "?" };

		String[] functionSet = { "+", "*", "daoshu", "fushu", "sqrt", };

		int row = 4;
		int deep = 3; // 信息树层数

		con = new Constant(fullSet, featureSet, functionSet, column, row, file, deep);
		String out_address = "下面输出结果所用数据集为：New_GEP_Keijzer_three_v\\" + files + ".txt";
		con.MaxGeneration = 1000;
		this.Run_write(file_out, CONSTANTPOOLNUMBER, out_address);

	}

	public void deepKeijzer_test(int column, int files) throws IOException {
		int CONSTANTPOOLNUMBER = 1;

		File file = new File("E:\\DataSet\\new_gep\\deep\\" + files + ".txt"); // 数据集所在的位置

		File file_out = new File("E:\\DataSet\\deep.txt"); // 用来输出结果

		String[] fullSet = { "+", "*", "daoshu", "fushu", "sqrt", "0", "1", "?" };

		String[] featureSet = { "0", "1", "?" };

		String[] functionSet = { "+", "*", "daoshu", "fushu", "sqrt", };

		int row = 3;

		for (int i = 0; i < 5; i++) {
			int deep = i; // 信息树层数

			con = new Constant(fullSet, featureSet, functionSet, column, row, file, deep);
			String out_address = "下面输出结果所用数据集为：" + files + "   第" + deep + "层：";

			this.Run_write(file_out, CONSTANTPOOLNUMBER, out_address);

		}

	}

	public void deepKoza_test(int column, int files) throws IOException {

		int CONSTANTPOOLNUMBER = 1;

		File file = new File("E:\\DataSet\\new_gep\\deep\\" + files + ".txt"); // 数据集所在的位置

		File file_out = new File("E:\\DataSet\\deep.txt"); // 用来输出结果

		String[] fullSet = { "+", "-", "*", "/", "sin", "cos", "log", "e", "0" };
		String[] featureSet = { "0" };
		String[] functionSet = { "+", "-", "*", "/", "sin", "cos", "log", "e" };

		int row = 2;
		for (int i = 0; i < 4; i++) {
			int deep = i; // 信息树层数

			con = new Constant(fullSet, featureSet, functionSet, column, row, file, deep);
			String out_address = "下面输出结果所用数据集为：" + files + "   第" + deep + "层：";

			this.Run_write(file_out, CONSTANTPOOLNUMBER, out_address);

		}

	}

	public void deep_test(int column, int files) throws IOException {

		int CONSTANTPOOLNUMBER = 1;

		File file = new File("E:\\DataSet\\new_gep\\deep\\" + files + ".txt"); // 数据集所在的位置

		File file_out = new File("E:\\DataSet\\deep.txt"); // 用来输出结果

		String[] fullSet = { "+", "-", "*", "/", "0", "1" };
		String[] featureSet = { "0", "1" };
		String[] functionSet = { "+", "-", "*", "/" };

		int row = 3;
		for (int i = 0; i < 5; i++) {
			int deep = i; // 信息树层数

			con = new Constant(fullSet, featureSet, functionSet, column, row, file, deep);
			String out_address = "下面输出结果所用数据集为：" + files + "   第" + deep + "层：";

			this.Run_write(file_out, CONSTANTPOOLNUMBER, out_address);

		}

	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Fitness_test aa = new Fitness_test();
		for(int i=0;i<1;i++)
		{
			Fitness_test.yunxing++;
		aa.Koza_one(200, 1);
		// aa.Koza_one(200, 2);
		// aa.Koza_one(200, 3);
		// aa.Koza_one(200, 4); 
		// aa.Koza_one(200, 5);
		// aa.Koza_one(200, 6);
		// aa.Koza_one(200, 7);
		//aa.Koza_one(200, 8);
		 
		//aa.Koza_one(200, 9);
		 //aa.Koza_one(200, 10);
		// aa.Koza_two(200, 1);
		// aa.Koza_two(200, 2);
		//
		// aa.Keijzer_one(21, 1);
		// aa.Keijzer_one(41, 2);
		// aa.Keijzer_one(61, 3);
		// aa.Keijzer_one(200, 4);
		// aa.Keijzer_one(100, 5);
		// aa.Keijzer_one(101, 6); //18
		//
		//aa.Keijzer_two(100, 1);
		// aa.Keijzer_two(20, 2);
	    //    aa.Keijzer_two(20, 3);
		// aa.Keijzer_two(20, 4);
		// aa.Keijzer_two(20, 5);
		// aa.Keijzer_two(20, 6);
		// aa.Keijzer_three(1000, 1);

		// aa.Korns_one(10000, 1);
		// aa.Korns_one(10000, 2);
		// aa.Korns_one(10000, 3);
		// aa.Korns_one(10000, 4);
		// aa.Korns_one(10000, 5);
		 //aa.Korns_one(200, 6); //31
		//
		// aa.Korns_two(200, 1);
		// aa.Korns_two(25, 2);
		// aa.Korns_three(10000, 1);
		// aa.Korns_three(10000, 2);
		// aa.Korns_four(10000, 1);
		// aa.Korns_four(10000, 2);
		// aa.Korns_four(10000, 3);
		// aa.Korns_four(10000, 4);
		// aa.Korns_four(10000, 5);

		 //aa.Vladislavleva1();
		// aa.Vladislavleva2();
		// aa.Vladislavleva3();
		// aa.Vladislavleva4();
		//aa.Vladislavleva5();
		// aa.Vladislavleva6();
		// aa.Vladislavleva7();
		// aa.Vladislavleva8();
		// aa.deepKoza_test(200, 1);
		// aa.deepKoza_test(200, 2);
		 //aa.deepKeijzer_test(20, 3);
		// aa.deep_test(200, 4);
		 
		 //aa.real_data(62, 1);
		}
	}

}