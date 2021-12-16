# Introduction
AB-GEP is a symbolic regression (SR) method based on adversarial bandit technique. AB-GEP segments the mathematical space into many subspaces. It then leverages a new selection method, AvgExp3, to enhance the population jump between segmented subspaces while avoiding falling into a local optimum and mataining population diversity.
# Running 
Run 'AvgEXP3/src/New_gep_greedy1/Fitness_test.java' to run 24 SR benchmarks and 8 real-world benchmarks in 'datasets/' 30 times. The optimal result in all independent runs is shown in 'results/rmse/'.
## Get Experimental Figures
Run 'postprocess/box_plot.py/' to get the box plot figures
![image](https://github.com/KGAE-CUP/AB-GEP/blob/main/results/results_png/box_plot1.png)
