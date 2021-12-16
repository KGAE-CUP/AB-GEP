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
		System.out.println("完成了！");
	}
	 public static void readFileByLines(String fileName1,String fileName2,String fileName3,String fileName4) {
		 int i=0;
		    Map<String,String> map1=new HashMap<>();//用来保存(0 198) （子空间号，选择数量）
		    Map<String,String> map2=new HashMap<>();//用来保存（28 0）  （代数，子空间号）
		    Map<String,String> map3=new HashMap<>();//用来保存（28 +++） （代数 ,空间表达式）
	        File file1 = new File(fileName1);
	        File file2 = new File(fileName2);
	        File file3 = new File(fileName3);
	        BufferedReader reader1 = null;
	        BufferedReader reader2= null;
	        BufferedReader reader3 = null;
	        try {
	           // System.out.println("以行为单位读取文件内容，一次读一整行：");
	            reader1 = new BufferedReader(new FileReader(file1));
	            String tempString1 = null;//记录读取结果
	            int line1 = 1;
	            // 一次读入一行，直到读入null为文件结束
	            while ((tempString1 = reader1.readLine()) != null) {
	                // 显示行号
	               // System.out.println("line " + line + ": " + tempString);
	            	map1.put(tempString1.split(" ")[0], tempString1.split(" ")[1]);
	                line1++;
	            }
	            
	            reader2 = new BufferedReader(new FileReader(file2));
	            String tempString2 = null;//记录读取结果
	            int line2 = 1;
	            // 一次读入一行，直到读入null为文件结束
	            while ((tempString2 = reader2.readLine()) != null) {
	                // 显示行号
	               // System.out.println("line " + line + ": " + tempString);
	            	map2.put(tempString2.split(" ")[0], tempString2.split(" ")[1]);
	                line2++;
	            }
	            
	            reader3 = new BufferedReader(new FileReader(file3));
	            String tempString3 = null;//记录读取结果
	            int line3 = 1;
	            // 一次读入一行，直到读入null为文件结束
	            while ((tempString3 = reader3.readLine()) != null) {
	                // 显示行号
	               // System.out.println("line " + line + ": " + tempString);
	            	map3.put(tempString3.split(" ")[0], tempString3.split(" ")[1]);
	                line3++;
	            }
	            
	            File writeName = new File(fileName4); // 相对路径，如果没有则要建立一个新的output.txt文件
                writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
                FileWriter writer = new FileWriter(writeName);
                BufferedWriter out = new BufferedWriter(writer);
                System.out.println("读完了");
//                out.write("1111");
//                out.flush();
	            //现在就得到了三个map,然后去写结果
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
	        	                    out.write(i+" "+map3.get(str1)+" "+map1.get(str)+"\r\n"); // \r\n即为换行
	        	                   // out.write("我会写入文件啦2\r\n"); // \r\n即为换行
	        	                	//out.write("1111");
	        	                    out.flush(); // 把缓存区内容压入文件
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
