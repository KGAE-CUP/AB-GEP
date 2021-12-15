package New_gep_greedy1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Data {

	public double Data[][];
	public int row;
	public int column;
	public File file; // ���ݼ����ڵ�λ��

	public Constant con;

	public Data(Constant con1) {

		con = con1;

		this.row = con.column;
		this.file = con.file;

		this.column = con.row;
		Data = new double[column][row];

	}

	/*
	 * �����㴫�ݹ������ı������������ļ���ȡ����ȡ���ݼ������������������ö���Run����
	 */

	public double[][] getData() throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(this.file));
		String s = null;
		int j = 0;

		while ((s = br.readLine()) != null) {
			// System.out.println(s);
			String[] str = s.split("  ");
			for (int i = 0; i < column; i++) {

				Data[i][j] = Double.valueOf(str[i]);

			}

			j++; // ����������
		}
		br.close();

		return Data;
	}

}
