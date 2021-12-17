
#include "GP.h"
#include <algorithm>
#include <string>
#include <limits>
#include <map> 
#include <iostream>
#include <sstream>


using namespace std;
int my_run(int argc, const char **argv,int k);


/*!
* \fn              int main(int argc, const char **argv)
* \brief           main method that runs the GP algorithm
* \param           int argc: number of parameters of the program
* \param           const char **argv: array of strings that contains the parameters of the program
* \return          int: 0 if the program ends without errors
* \date            01/09/2016
* \author          Mauro Castelli
* \file            GP.cc
*/
int main(int argc, const char **argv){
   for(int k=10;k<11;k++){
      my_run(argc,argv,k);
   }
   return 0;
}
int my_run(int argc, const char **argv,int k){
	
	
	 
	double space_reward[16];//记录子空间的奖励值 
	int space_number = sizeof(space_reward) / sizeof(space_reward[0]);//子空间的大小 
	for(int i=0;i<16;i++)
	{
		space_reward[i]=0;//奖励值初始化为0 
	 } 
	 bool selectornot=true;//标记是否要选子空间
	 double yita=0;//学习率 
	 double total_space_reward=0;
	 double random_number[10];//随机数，模拟选择概率 
	 double random_gailv=0;
	 double space_possibility[16];
	 	for(int i=0;i<16;i++)
	{
		space_possibility[i]=0;//子空间选择概率初始化为0 
	 } 
	 int best_space_id =0;
	 
	 
	//string first_level;
    //string second_level;
    //stringstream st1;
   // stringstream st2;
    int first_level_num;
    int second_level_num;
	int best_first_id;
	int best_second_id; 
	string tem_space_per;
	
	string best_first_per;
	string best_second_per;
	
	int final_space_id;
	
	 ostringstream str1;
    ostringstream str2;
    
    
    double space_histort_reward[16];//记录子空间的最优个体
    for(int j=0;j<16;j++)
    {
        space_histort_reward[j]=numeric_limits<double>::max();
    }
    
	 
	map<int,string> tem_map;//存放过渡map （2，a）（3，b） 
    tem_map[2]="a";
    tem_map[3]="b";
    tem_map[4]="c";
    tem_map[5]="d";
    tem_map[6]="e";
    tem_map[7]="f";
    tem_map[8]="g";
    tem_map[9]="h";
    tem_map[10]="i";
    tem_map[11]="j";
    tem_map[12]="k";
    tem_map[13]="l";
    tem_map[14]="m";
    tem_map[15]="n";
    tem_map[16]="o";
    tem_map[17]="p";
	 
    map<int,string> mymap;
    //ostringstream s1;
   // ostringstream s2;
    
   // string space_div[2];
    int count=0;
    	//这边要写一个map保存子空间id和性状的映射  1（0，aa) (1, ab)
			for(int i=2;i<6;i++)
			{
				//s1 << i;
				//s1.str();
				for(int j=2;j<6;j++)
				{
				//	s2 << j;
					//space_div[1]=s2.str();
					mymap[count]=tem_map[i]+tem_map[j];
					 //memcpy(mymap[count],space_div,sizeof(space_div));
					//s2.str(""); 
					count++;
				}
				//s1.str("");
			}
			
			//return 0;
			//记录子空间的id对不对 
			map<int, string>::iterator iter;
			ofstream space("space.txt",ios::out);
			 for(iter = mymap.begin(); iter != mymap.end(); iter++) {
        space << iter->first << " : " << iter->second<< endl;
    }
			
    double historybest_train=numeric_limits<double>::max();//历史最优个体 
    double historybest_test=numeric_limits<double>::max();//历史最优个体 

    // name of the file with training instances 
    char path_in[50]="/data1/users/Teachers/luq/GSGP/airfoiltrain.txt";//数据集路径 
    // name of the file with test instances
    char path_test[50]="/data1/users/Teachers/luq/GSGP/airfoiltest.txt";//数据集路径 
   	for (int i=1; i<argc-1; i++) {
        if(strncmp(argv[i],"-train_file",11) == 0) {
            strcat(path_in,argv[++i]);
        }
        else{
            if(strncmp(argv[i],"-test_file",10) == 0) {
                strcat(path_test,argv[++i]);
	      	}        
       	}
   	}

	// initialization of the seed for the generation of random numbers
	srand(time (NULL));
	// reading the parameters of the GP algorithm
	read_config_file(&config);	
	// creation of an empty population
	population *p=new population();
	
	//population *p_copy=new population();
	
	// if USE_TEST_SET is equal to 1 the system will apply the best model to newly provided unseen data
	if(config.USE_TEST_SET==1){
//		ofstream OUT("evaluation_on_unseen_data.txt",ios::out);		
//		fstream in(path_test,ios::in);
//		
//		if (!in.is_open()) {
//			cout<<endl<<"ERRORE: IMPOSSIBILE APRIRE IL FILE" << endl;
//			exit(-1);
//		}
//		else{
//			char n_v[255];
//			in >> n_v;
//			nvar=atoi(n_v);
//			// creation of terminal and functional symbols
//			create_T_F();
//			// initialization of the population
//			create_population((population **)&p, config.init_type);		
//			set = new Instance[1];
//			set[0].vars = new double[nvar];
//			while(!in.eof()){
//				char str[255];    
//				for (int j=0; j<nvar; j++) {
//					in >> str;
//					set[0].vars[j] = atof(str);
//				}
//				update_terminal_symbols(0);
//				evaluate_unseen_new_data((population**)&p, OUT);       	    
//			}
//		}
	}
	// if USE_TEST_SET is different from 1 the system will perform the usual evolutionary process
	else{
		ofstream executiontime("execution_time.txt",ios::out);
		timeval start1, stop1;
		gettimeofday(&start1, NULL);
		double elapsedTime=0;
	
		/*
		pointer to the file fitnesstrain.txt containing the training fitness of the best individual at each generation
		*/
		ofstream fitness_train("/data1/users/Teachers/luq/GSGP/ABtrain/shiyingdu/8."+std::to_string(k)+".txt",ios::out);
		/*
		pointer to the file fitnesstest.txt containing the training fitness of the best individual at each generation
		*/
		ofstream fitness_test("/data1/users/Teachers/luq/GSGP/ABtest/shiyingdu/8."+std::to_string(k)+".txt",ios::out);
   
    ofstream zhongqun_train("/data1/users/Teachers/luq/GSGP/ABtrain/zhongqun/8."+std::to_string(k)+".txt",ios::out);
    ofstream zhongqun_test("/data1/users/Teachers/luq/GSGP/ABtest/zhongqun/8."+std::to_string(k)+".txt",ios::out);
    
    
		// reading training and test files
		read_input_data(path_in,path_test);
		// creation of terminal and functional symbols,创建符号集合 
		create_T_F();
		
		//这里生成一个公式的时候可以重复，然后计算一个最好的子空间 
		//individuals是个数组 
		  
		// initialization of the population
		create_population((population **)&p, config.init_type);	//生成一个种群 
		
		
		// evaluation of the individuals in the initial population
		evaluate((population**)&p);
		// writing the  training fitness of the best individual on the file fitnesstrain.txt
		fitness_train<<Myevaluate(p->individuals[p->index_best])<<endl;
		// writing the  test fitness of the best individual on the file fitnesstest.txt
		fitness_test<<Myevaluate_test(p->individuals[p->index_best])<<endl;
		// index of the best individual stored in the variable best_index
		index_best=best_individual();
		
		
		gettimeofday(&stop1, NULL);
		elapsedTime += ((stop1.tv_sec - start1.tv_sec) * 1000.0) + ((stop1.tv_usec - start1.tv_usec) / 1000.0);
		executiontime<<elapsedTime<<endl;
	
		//File containing the individuals and the random trees
		ofstream expression_file("individuals.txt",ios::out);
		
		for(int i=0;i<config.population_size+config.random_tree;i++){
			string s="";
			print_math_style(p->individuals[i],s);
			expression_file<<s<<endl;
		}
		
		int a=(p->individuals[p->index_best])->root->id;
		int b=(p->individuals[p->index_best])->children[0]->root->id;
		int c=(p->individuals[p->index_best])->children[1]->root->id;
		cout<<(p->individuals[p->index_best])->root->name<<" "<<a<<endl;
		cout<<(p->individuals[p->index_best])->children[0]->root->name<<" "<<b<<endl;
		cout<<(p->individuals[p->index_best])->children[1]->root->name<<" "<<c<<endl;
		
	//	update_tables_copy();
		//这边是可以得到那个最优个体的表达性状的，也就是说最优子空间 
		//开始重新生成一个种群，这个种群是在上面这个子空间的 
	
	//	create_population_copy((population **)&p_copy, config.init_type,a,b);
	//		evaluate((population**)&p_copy);
		// writing the  training fitness of the best individual on the file fitnesstrain.txt
	//	fitness_train<<Myevaluate(p_copy->individuals[p_copy->index_best])<<endl;
		// writing the  test fitness of the best individual on the file fitnesstest.txt
	//	fitness_test<<Myevaluate_test(p_copy->individuals[p_copy->index_best])<<endl;
		// index of the best individual stored in the variable best_index
//		index_best=best_individual();
		
	//	ofstream expression_file_copy("individuals_copy.txt",ios::out);
		
	//	for(int i=0;i<config.population_size+config.random_tree;i++){
//			string s="";
//			//print_math_style(p->individuals[i],s);
//			expression_file_copy<<(p_copy->individuals[i])->root->name<<" "<<(p_copy->individuals[i])->children[0]->root->name<<" "<<(p_copy->individuals[i])->children[1]->root->name<<endl;
		//	string s="";
		//	print_math_style(p_copy->individuals[i],s);
		//	expression_file_copy<<s<<endl;
	//	}
		 
		
		
		
		//P_copy是我的种群 
		
		
		
		//现在开始就进入正式的AB-GSGP，现在的种群都是在初始化中最优的种群 ***** 
	
		// main GP cycle
		for(int num_gen=0; num_gen<config.max_number_generations; num_gen++){
		
		    double sum=0;
			int select_first = 1;	
      
			timeval start, stop;
			//Register execution time
			gettimeofday(&start, NULL);
		
			cout<<"Generation "<<num_gen+1<<endl;
			// creation of a new population (without building trees!!)
			
			
			
			
			//从这边开始选择子空间，第一次不用选
			 if(selectornot==true)
			 {
			 	//进来选子空间 
			 	//1.计算学习率yita
				 yita= sqrt((log(space_number)+(double)(num_gen+1)*(double)num_gen)/(2*(double)(num_gen+1)*(double)space_number));
				 //2.计算每个子空间被选择的奖励值
				 for(int e=0;e<space_number;e++)
				 {
				 	total_space_reward = total_space_reward + pow(2.7182,yita*space_reward[e]);
				  } 
				  //3.计算选择哪个子空间 
				//  srand((unsigned)time(NULL));
				  
				  for(int f=0;f<10;f++)
				  {
				  	random_number[f]=rand() / double(RAND_MAX);
				   } 
		          
                  random_gailv = random_number[4];//这个就是我得到的随机数，以这个数为基础去选择子空间
				  
				  //开始模拟选择 
				  for(int t=0;t<space_number;t++)
				  {
				  	space_possibility[t]=pow(2.7182,yita*space_reward[t]) / total_space_reward; //每个子空间被选择的概率 
				  	if(select_first==1)
				  	{
				  		if(0<=random_gailv&&random_gailv<space_possibility[t])
					  {
					  	 best_space_id = t;
					  	 break;
					   } 
					  }
				  	if(select_first==0)
				  	{
				  		if(sum<=random_gailv&&random_gailv<space_possibility[t]+sum)
				  		{
				  			 best_space_id = t;
					  	     break;
						  }
				  		
					  }
					   
					   sum=sum+space_possibility[t];
					   select_first=0;
					
				  }
             
				  
				  //到这里为止，最优的子空间已经选择出来了 （id) 
				  //先把id取出来 
				  
//						map<int, string[2]>::iterator iter2;
//							 for(iter2 = mymap.begin(); iter2 != mymap.end(); iter2++) {
//				        if(iter2->first==best_space_id)
//				        {
//				        	 first_level=(iter2->second)[0];
//				        	 second_level=(iter2->second)[1];
//				        	st1<<first_level;
//				        	st2<<second_level;
//							 st1>>first_level_num;
//				        	st2>>second_level_num;
//				        	break;
//		}
//		
//		
//    }              
                     cout<<"yita"<<yita<<endl;
                     cout<<"random_gailv"<<random_gailv<<endl;
                     cout<<"total_space_reward:"<<total_space_reward<<endl;
                     cout<<"space_reward:"<<space_reward[best_space_id]<<endl;
                     cout<<"select_possibility:"<<space_possibility[best_space_id]<<endl; 
                     cout<<"best_space_id:"<<best_space_id<<endl;
                     tem_space_per=mymap[best_space_id];//取出来这个字符串，也就是string，类似于aa
                     total_space_reward=0;
					// tem_space_per.substr(0, 1);
					 
					 
					 		map<int, string>::iterator iter2;
							 for(iter2 = tem_map.begin(); iter2 != tem_map.end(); iter2++) {
				        if(iter2->second==tem_space_per.substr(0, 1) )
				        {
//				        	 first_level=(iter2->second)[0];
//				        	 second_level=(iter2->second)[1];
//				        	st1<<first_level;
//				        	st2<<second_level;
//							 st1>>first_level_num;
//				        	st2>>second_level_num;
				        	
				        	first_level_num = iter2->first;
		               }
		                if(iter2->second==tem_space_per.substr(1, 1) )
				        {
//				        	 first_level=(iter2->second)[0];
//				        	 second_level=(iter2->second)[1];
//				        	st1<<first_level;
//				        	st2<<second_level;
//							 st1>>first_level_num;
//				        	st2>>second_level_num;
				        	
				        	second_level_num = iter2->first;
		               }
		
		
    }
		
				  //update_tables_copy();
				  //接下来就是跳转到这个选择的子空间 
				  for(int u=0;u<config.population_size;u++)
				  {
             if((p->individuals[u])->root->id>=2&&(p->individuals[u])->root->id<=5&&(p->individuals[u])->children[0]->root->id>=2&&(p->individuals[u])->children[0]->root->id<=5)
             {
                (p->individuals[u])->root=symbols[first_level_num];
				  	    (p->individuals[u])->children[0]->root=symbols[second_level_num];
                 double tem_var = Myevaluate_test(p->individuals[u]); 
                 if(tem_var <  space_histort_reward[best_space_id])
                 {
                    space_histort_reward[best_space_id] = tem_var;
                 }                                                                               
             }
				  	
				  }
				  //释放资源，清空 
				  //st1.str("");
				 // st2.str("");
				  
				  //跳转完毕之后...,让我想想 
				  //evaluate((population**)&p_copy);
		// writing the  training fitness of the best individual on the file fitnesstrain.txt
		//fitness_train<<Myevaluate(p_copy->individuals[p_copy->index_best])<<endl;
		// writing the  test fitness of the best individual on the file fitnesstest.txt
		//fitness_test<<Myevaluate_test(p_copy->individuals[p_copy->index_best])<<endl;
		// index of the best individual stored in the variable best_index
		//index_best=best_individual();
				  
				  
			 }
			
			
			
			
			for(int k=0;k<config.population_size;k++){
				double rand_num=frand();
				// geometric semantic crossover
				if(rand_num<config.p_crossover)
					geometric_semantic_crossover(k);
				// geometric semantic mutation    
				if(rand_num>=config.p_crossover && rand_num<config.p_crossover+config.p_mutation){
					reproduction(k,true);
					geometric_semantic_mutation(k);
				}
				// reproduction
				if(rand_num>=config.p_crossover+config.p_mutation){
					reproduction(k,false);
				}
			}
			
			
			
			
			
			
			
			
			  
			 
			 
			 
			 
			 
			 
			 
        
			// updating the tables used to store semantics and fitness values
			update_tables();
			// index of the best individual stored in the variable best_index
			index_best=best_individual(); 
			
			
			
			//在这里更新子空间的奖励值
			//首先要找到那个最优的子空间 
			// best_first_id=(p->individuals[p->index_best])->root->id;
		    // best_second_id=(p->individuals[p->index_best])->children[0]->root->id;
		     
//		     str1<<best_first_id;
//		     str2<<best_second_id;
		     
		    // best_first_per=tem_map[best_first_id];
		  //   best_second_per=tem_map[best_second_id];
		     
			//	map<int, string>::iterator iter3;
		//	 for(iter3 = mymap.begin(); iter3 != mymap.end(); iter3++) {
     //   if((iter3->second).compare(best_first_per+best_second_per))
     //   {
       //   	final_space_id=iter3->first;//最优子空间的id 
      //    	break;
	//	}
//	}
//	    str1.str("");
//	    str2.str("");
	    
	    //然后就是更新奖励值了 
			for(int h=0;h<space_number;h++)
			{
				if(h==best_space_id)
				{
					space_reward[h]=((double)num_gen*space_reward[h]+(1-(((double)1-(double)1/((double)1+fit_test[index_best])))/space_possibility[h]))/(double)(num_gen+1);
				}
				else{
					space_reward[h]=((double)num_gen*space_reward[h]+1)/(double)(num_gen+1);
				}
        //space <<h<<":"<<space_reward[h]<< endl;
			 } 
			  
           
      
			
			 
			// updating the information used to explore the DAG whose nodes are the GP individuals
			vector_traces.push_back(traces_generation);
			traces_generation.clear();
			// writing the  training fitness of the best individual on the file fitnesstrain.txt
      cout<<historybest_train;    
      if(fit_[index_best]<historybest_train){
          historybest_train=fit_[index_best];
      }   
			fitness_train<<num_gen+1<<" "<<historybest_train<<endl;
			// writing the  test fitness of the best individual on the file fitnesstest.txt
      if(fit_test[index_best]<historybest_test){
          historybest_test=fit_test[index_best];
      } 
			fitness_test<<num_gen+1<<" "<<historybest_test<<endl;
      int count=1;
      if(num_gen%100==0){
      cout<<"saassssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
      vector <double> copy_fit_test(fit_test);
      sort(copy_fit_test.begin(),copy_fit_test.end());
      double flag=copy_fit_test[0];
      for(int i=0;i<copy_fit_test.size();i++){
      	if(copy_fit_test[i]!=flag){
       		count=count+1;
          flag=copy_fit_test[i];
       }
      }
      zhongqun_test<<num_gen<<" "<<count<<" "<<copy_fit_test.size()<<endl;
     }
     
      int count1=1;
      if(num_gen%100==0){
      cout<<"saassssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
      vector <double> copy_fit_train(fit_);
      sort(copy_fit_train.begin(),copy_fit_train.end());
      double flag1=copy_fit_train[0];
      for(int i=0;i<copy_fit_train.size();i++){
      	if(copy_fit_train[i]!=flag1){
       		count1=count1+1;
          flag1=copy_fit_train[i];
       }
      }
      zhongqun_train<<num_gen<<" "<<count1<<" "<<copy_fit_train.size()<<endl;
     }
      
      
      
		   // selectornot=true; 
			gettimeofday(&stop, NULL);
			elapsedTime += ((stop.tv_sec - start.tv_sec) * 1000.0) + ((stop.tv_usec - start.tv_usec) / 1000.0);
			executiontime<<elapsedTime<<endl;
		}	    
		// marking procedure whose output is saved on the file "trace.txt"
		mark_trace();
		save_trace();
	}
    // at the end of the execution all the data structures are deleted in order to deallocate memory
	for(int k=0; k<config.population_size+config.random_tree; k++){
        delete_individual(p->individuals[k]);
	}
	delete[] p->fitness;
	delete[] p->fitness_test;
	delete p;	
	for(int i=0; i<nrow+nrow_test; i++){
        delete[] set[i].vars;
	}
	delete[] set;
	for(int i=symbols.size()-1;i>=0;i--){
        delete symbols[i];
		symbols.erase(symbols.begin()+i);
	}
	symbols.clear();
	return 0;
}
