# Introduction
AB-GEP is a symbolic regression (SR) method based on adversarial bandit technique. AB-GEP segments the mathematical space into many subspaces. It then leverages a new selection method, AvgExp3, to enhance the population jump between segmented subspaces while avoiding falling into a local optimum and mataining population diversity.
# Running 
Run 'AvgEXP3/src/New_gep_greedy1/Fitness_test.java' and 'AB-GSGP/main.cpp' to run AB-GEP and AB-GSGP respectively on 24 SR benchmarks and 8 real-world benchmarks in 'datasets/' 30 times. The optimal result in all independent runs is shown in 'results/rmse/'.
## Get Experimental Figures
Run 'postprocess/box_plot.py' to get the box plot figures.
![image](https://github.com/KGAE-CUP/AB-GEP/blob/main/results/results_png/box_plot1.png)
![image](https://github.com/KGAE-CUP/AB-GEP/blob/main/results/results_png/box_plot2.png)
![image](https://github.com/KGAE-CUP/AB-GEP/blob/main/results/results_png/box_plot3.png)

Run 'postprocess/population_diversity.m' to get the population diversity figures.
![image](https://github.com/KGAE-CUP/AB-GEP/blob/main/results/results_png/population_diversity1.png)
![image](https://github.com/KGAE-CUP/AB-GEP/blob/main/results/results_png/population_diversity2.png)
![image](https://github.com/KGAE-CUP/AB-GEP/blob/main/results/results_png/population_diversity3.png)

Run 'postprocess/heat_map.py' and 'postprocess/heat_map_3d.py' to get the heat map of selecting subapces.
![image](https://github.com/KGAE-CUP/AB-GEP/blob/main/results/results_png/heat_map1.png)
![image](https://github.com/KGAE-CUP/AB-GEP/blob/main/results/results_png/heat_map2.png)
![image](https://github.com/KGAE-CUP/AB-GEP/blob/main/results/results_png/heat_map3.png)
![image](https://github.com/KGAE-CUP/AB-GEP/blob/main/results/results_png/heat_map4.png)

Run 'postprocess/convergence_curve.m' to get the convergence cruve figures.
![image](https://github.com/KGAE-CUP/AB-GEP/blob/main/results/results_png/convergence1.png)
![image](https://github.com/KGAE-CUP/AB-GEP/blob/main/results/results_png/convergence2.png)
![image](https://github.com/KGAE-CUP/AB-GEP/blob/main/results/results_png/convergence3.png)

# Related Work
Previous basic work is available：

Congwen Xu, Qiang Lu, Jake Luo, and Zhiguang Wang. 2021. Adversarial bandit gene expression programming for symbolic regression. In <i>Proceedings of the Genetic and Evolutionary Computation Conference Companion</i> (<i>GECCO '21</i>). Association for Computing Machinery, New York, NY, USA, 269–270. DOI:https://doi.org/10.1145/3449726.3459499
