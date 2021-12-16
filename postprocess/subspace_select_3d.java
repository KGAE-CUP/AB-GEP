package EXPtest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Select {

	public static void main(String[] args)
	{
		Select.readFileByLines("E:\\geccodata\\SPJ-GEP\\13.txt","E:\\geccodata\\SPJ-GEP1\\13.txt");
		System.out.println("完成了！");
	}
	 public static void readFileByLines(String fileName1,String fileName4) {
		// int i=0;
		    Map<String,String> map1=new HashMap<>();//用来保存(+++ 198) （子空间表达式，选择数量）
		    
	        File file1 = new File(fileName1);
	       
	        BufferedReader reader1 = null;
	       
	        try {
	           // System.out.println("以行为单位读取文件内容，一次读一整行：");
	            reader1 = new BufferedReader(new FileReader(file1));
	            String tempString1 = null;//记录读取结果
	            int line1 = 1;
	            // 一次读入一行，直到读入null为文件结束
	            while ((tempString1 = reader1.readLine()) != null) {
	                // 显示行号
	               // System.out.println("line " + line + ": " + tempString);
	            	if(map1.containsKey(tempString1.split(" ")[1]))
	            	{
	            		System.out.println(tempString1.split(" ")[1]);
	            	}
	            	map1.put(tempString1.split(" ")[1], tempString1.split(" ")[2]);
	                line1++;
	            }
	            
	          String[] arr1=new String[] {"+", "*", "daoshu", "fushu", "sqrt", "0", "1", "?" };
	          String[] arr2=new String[] { "+", "*", "daoshu", "fushu", "sqrt", "0", "1", "?"};
	          String[] arr3=new String[] {"+", "*", "daoshu", "fushu", "sqrt", "0", "1", "?" };
	            
	         
	            
	            File writeName = new File(fileName4); // 相对路径，如果没有则要建立一个新的output.txt文件
                writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
                FileWriter writer = new FileWriter(writeName);
                BufferedWriter out = new BufferedWriter(writer);
                System.out.println("读完了");
                System.out.println(map1.size());
       
//                out.write("1111");
//                out.flush();
	            //现在就得到了三个map,然后去写结果
               
                for(int i=0;i<arr1.length;i++)
                {
                	for(int j=0;j<arr2.length;j++)
                	{
                		for(int k=0;k<arr3.length;k++)
                		{
                			 StringBuilder str=new StringBuilder();
                			 str.append(arr1[i]);
                			 str.append(arr2[j]);
                			 str.append(arr3[k]);
                			 for(String s:map1.keySet())
                			 {
                				 if(s.equals(str.toString()))
                				 {
                					//out.write("1111");
        			            	 //out.flush(); 
        	        	                    out.write(map1.get(s)+"\r\n"); // \r\n即为换行
        	        	                   // out.write("我会写入文件啦2\r\n"); // \r\n即为换行
        	        	                	//out.write("1111");
        	        	                    out.flush(); // 把缓存区内容压入文件
                				 }
                					 
                			 }
                			 
                		}
                	}
                }
                
                
                
                
                
                
                
                
                
//	            for(String str:map1.keySet())
//	         {
//	            	i++;
//	            	System.out.println(str);
////	            	out.write("1111");
////	            	 out.flush();
//	            	for(String str1:map2.keySet())
//	            	{
//	            		//out.write("1111");
//		            	 //out.flush();
//	            		if(map2.get(str1).equals(str))
//	            		{
//	            			//out.write("1111");
//			            	 //out.flush(); 
//	        	                    out.write(i+" "+map3.get(str1)+"  "+map1.get(str)+"\r\n"); // \r\n即为换行
//	        	                   // out.write("我会写入文件啦2\r\n"); // \r\n即为换行
//	        	                	//out.write("1111");
//	        	                    out.flush(); // 把缓存区内容压入文件
//	            			
//	        	                    break;
//	        	                
//	        	            
//	            		}
//	            	}
//	            	
//	              System.out.println("guheihie");
//	        }
	 
	            
	            
	            
	            
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
