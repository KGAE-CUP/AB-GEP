package New_gep_greedy1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class WriteDataFile {
	static int c = 9;
	public static double data[][];
	public static double x[];
	public static double y[];
	public static double z[];
	private int column;
	private int row;
	Random random = new Random();

	FileOutputStream fop = null;
	File file;

	public WriteDataFile() {

		this.column = 200;
		this.row = 2;
	}

	public int jiecheng() {
		Random a = new Random();
		int b = a.nextInt(2);
		int c = -1;
		int p = 1;
		for (int j = 0; j <= b; j++) {
			p = p * c;

		}

		return p;

	}

	public double[] Data(int column) {
		Random a = new Random();
		double[] x = new double[column];

		for (int j = 0; j < column; j++) {
			x[j] = a.nextInt(20);
		}

		return x;

	}

	public void onex() throws IOException {
		file = new File("E:\\DataSet\\test11.txt");
		data = new double[row][column];
		x = new double[column];
		y = new double[column];
		z = new double[column];

		FileOutputStream fos2 = new FileOutputStream(file); // 覆盖
		// FileOutputStream fos2 = new FileOutputStream(file, true);
		// 第二個参数为true表示程序每次运行都是追加字符串在原有的字符上
		// 若文件不存在,则创建它
		if (!file.exists()) {
			file.createNewFile();
		}

		x = Data(column); // 调用Data方法，然后返回一个数组，数组大小为column
		y = Data(column); // 数组里面的值是随机生成的是-100到100的随机整数

		for (int j = 0; j < column; j++) {

			// z[j] = x[j] * x[j] * x[j] * x[j] * x[j] * x[j] + x[j] * x[j] * x[j] * x[j] *
			// x[j]
			// + x[j] * x[j] * x[j] * x[j] + x[j] * x[j] * x[j] + x[j] * x[j] + x[j]; //
			// one_v,1

			// z[j] = x[j] * x[j] * x[j] * x[j] * x[j] + x[j] * x[j] * x[j] * x[j] + x[j] *
			// x[j] * x[j] + x[j] * x[j]
			// + x[j]; // one_v,2

			// z[j] = x[j] * x[j] * x[j] * x[j] + x[j] * x[j] * x[j] + x[j] * x[j] + x[j];
			// // one_v,3

			// z[j] = x[j] * x[j] * x[j] + x[j] * x[j] + x[j]; // one_v,4

			// z[j] = x[j] * x[j] * x[j] * x[j] * x[j] * x[j] - 2 * x[j] * x[j] * x[j] *
			// x[j] + x[j] * x[j]; // one_v,5

			// z[j] = x[j] * x[j] * x[j] * x[j] * x[j] - 2 * x[j] * x[j] * x[j] + x[j]; //
			// one_v,6

			// z[j] = Math.sin(x[j] * x[j]) * Math.cos(x[j]) - 1; // one_v,7

			// z[j] = Math.sin(x[j] * x[j] + x[j]) + Math.sin(x[j]); // one_v,8

			// z[j] = Math.log(x[j] + 1) + Math.log(x[j] * x[j] + 1); // koza.one_v,9

			// z[j] = Math.sqrt(x[j]);

			// z[j] = 1.57 + 24.3 * x[j]; // korns.one_v,1

			// z[j] = -2.3 + 0.13 * Math.sin(x[j]); // korns.one_v,2

			// z[j] = 3 + 2.13 * Math.log(Math.abs(x[j])); // korns.one_v,3

			// z[j] = 1.3 + 0.13 * Math.sqrt(Math.abs(x[j])); // korns.one_v,4

			// z[j] = 213.80940889 * (1 - Math.exp(-0.54723748542 * x[j])); // korns.one_v,5

			// z[j] = 6.87 + 11 * Math.cos(7.23 * Math.pow(x[j], 3)); // korns.one_v,6

			z[j] = 2 * x[j] * x[j] * x[j] + 2 * x[j];

			// z[j] = 0.3 * x[j] * Math.sin(2 * Math.PI * x[j]); // keijzer.one_v,1

			// z[j] = x[j] * x[j] * x[j] * Math.exp(-x[j]) * Math.cos(x[j]) * Math.sin(x[j])
			// * (Math.sin(x[j]) * Math.sin(x[j]) * Math.cos(x[j]) - 1); // keijzer.one_v,4

			// z[j] = Math.log(x[j]); // keijzer.one_v,5

			// z[j] = Math.sqrt(x[j]); // keijzer.one_v,6

			// z[j] = Math.exp(-x[j]) * x[j] * x[j] * x[j] * Math.sin(x[j]) * Math.cos(x[j])
			// * (Math.cos(x[j]) * Math.sin(x[j]) * Math.sin(x[j]) - 1); //
			// Vladislavleva.one_V,1

			// fos2.write((x[j] + " " + y[j] + " " + z[j]).getBytes()); // 写入表达式，两个向量
			fos2.write((x[j] + "  " + z[j]).getBytes()); // 写入表达式，单向量
			fos2.write("\r\n".getBytes()); // 写入一个换行

		}
		fos2.flush();
		fos2.close(); // 释放资源

	}

	public static void main(String[] args) throws Exception {
		WriteDataFile a = new WriteDataFile();
		a.onex(); // 调用上面的方法，写出数据集
		System.out.println("已经完成！");

	}
}