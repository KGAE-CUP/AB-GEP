package thinking;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Test6 {
	public static void main(String[] args)
	{
		Test6.readFileByLines("E:\\IEEE\\tongji\\8.10.txt","E:\\IEEE\\gailv1\\8.10.txt","E:\\IEEE\\biaoda2\\8.10.txt","E:\\IEEE\\AB-GEP\\8.1.txt");
		System.out.println("����ˣ�");
	}
	 public static void readFileByLines(String fileName1,String fileName2,String fileName3,String fileName4) {
		 int i=0;
		    Map<String,String> map1=new HashMap<>();//��������(0 198) ���ӿռ�ţ�ѡ��������
		    Map<String,String> map2=new HashMap<>();//�������棨28 0��  ���������ӿռ�ţ�
		    Map<String,String> map3=new HashMap<>();//�������棨28 +++�� ������ ,�ռ���ʽ��
	        File file1 = new File(fileName1);
	        File file2 = new File(fileName2);
	        File file3 = new File(fileName3);
	        BufferedReader reader1 = null;
	        BufferedReader reader2= null;
	        BufferedReader reader3 = null;
	        try {
	           // System.out.println("����Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�һ���У�");
	            reader1 = new BufferedReader(new FileReader(file1));
	            String tempString1 = null;//��¼��ȡ���
	            int line1 = 1;
	            // һ�ζ���һ�У�ֱ������nullΪ�ļ�����
	            while ((tempString1 = reader1.readLine()) != null) {
	                // ��ʾ�к�
	               // System.out.println("line " + line + ": " + tempString);
	            	map1.put(tempString1.split(" ")[0], tempString1.split(" ")[1]);
	                line1++;
	            }
	            
	            reader2 = new BufferedReader(new FileReader(file2));
	            String tempString2 = null;//��¼��ȡ���
	            int line2 = 1;
	            // һ�ζ���һ�У�ֱ������nullΪ�ļ�����
	            while ((tempString2 = reader2.readLine()) != null) {
	                // ��ʾ�к�
	               // System.out.println("line " + line + ": " + tempString);
	            	map2.put(tempString2.split(" ")[0], tempString2.split(" ")[1]);
	                line2++;
	            }
	            
	            reader3 = new BufferedReader(new FileReader(file3));
	            String tempString3 = null;//��¼��ȡ���
	            int line3 = 1;
	            // һ�ζ���һ�У�ֱ������nullΪ�ļ�����
	            while ((tempString3 = reader3.readLine()) != null) {
	                // ��ʾ�к�
	               // System.out.println("line " + line + ": " + tempString);
	            	map3.put(tempString3.split(" ")[0], tempString3.split(" ")[1]);
	                line3++;
	            }
	            
	            File writeName = new File(fileName4); // ���·�������û����Ҫ����һ���µ�output.txt�ļ�
                writeName.createNewFile(); // �������ļ�,��ͬ�����ļ��Ļ�ֱ�Ӹ���
                FileWriter writer = new FileWriter(writeName);
                BufferedWriter out = new BufferedWriter(writer);
                System.out.println("������");
//                out.write("1111");
//                out.flush();
	            //���ھ͵õ�������map,Ȼ��ȥд���
	            for(String str:map1.keySet())
	         {
	            	i++;
	            	System.out.println(str);
//	            	out.write("1111");
//	            	 out.flush();
	            	for(String str1:map2.keySet())
	            	{
	            		//out.write("1111");
		            	 //out.flush();
	            		if(map2.get(str1).equals(str))
	            		{
	            			//out.write("1111");
			            	 //out.flush(); 
	        	                    out.write(i+" "+map3.get(str1)+" "+map1.get(str)+"\r\n"); // \r\n��Ϊ����
	        	                   // out.write("�һ�д���ļ���2\r\n"); // \r\n��Ϊ����
	        	                	//out.write("1111");
	        	                    out.flush(); // �ѻ���������ѹ���ļ�
	            			         System.out.println(map3.get(str1));
	        	                    break;
	        	                
	        	            
	            		}
	            	}
	            	
	              System.out.println("guheihie");
	        }
	 
	            
	            
	            
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader1 != null) {
	                try {
	                    reader1.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
	    }

}
