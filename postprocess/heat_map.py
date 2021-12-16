
import numpy as np
import matplotlib
import matplotlib.pyplot as plt
import matplotlib
matplotlib.rcParams['pdf.fonttype'] = 42
matplotlib.rcParams['ps.fonttype'] = 42
#matplotlib.rcParams['axes.unicode_minus'] = False
#matplotlib.rcParams['text.usetex'] = True

# 这里是创建一个数据
vegetables = ['$+$', '$-$', r'$ \times $', r'$ \div $', '$sin$','$cos$', '$ logx $','$e^x$',
               '$x1$','$x2$' ,'$x3$' ,'$x4$' ,'$x5$']
farmers = ['$+$', '$-$', r'$ \times $', r'$ \div $', '$sin$','$cos$', '$ logx $','$e^x$',
               '$x1$','$x2$' ,'$x3$' ,'$x4$' ,'$x5$' ]
#vegetables = ['$+$', '$-$', r'$ \times $', r'$ \div $', '$sin$','$cos$','$logx$','$e^x$', '$x^2$','$x^3$','$tan$', r'$ \sqrt{x} $',
#              '$x$', '$y$','$z$','$C$']
#farmers = ['$+$', '$-$', r'$ \times $', r'$ \div $', '$sin$','$cos$','$logx$','$e^x$', '$x^2$','$x^3$','$tan$', r'$ \sqrt{x} $',
 #             '$x$', '$y$','$z$','$C$']

#harvest = np.array([[0.8, 2.4, 2.5, 3.9, 0.0, 4.0, 0.0],
     #               [2.4, 0.0, 4.0, 1.0, 2.7, 0.0, 0.0],
      #              [1.1, 2.4, 0.8, 4.3, 1.9, 4.4, 0.0],
       #             [0.6, 0.0, 0.3, 0.0, 3.1, 0.0, 0.0],
       #             [0.7, 1.7, 0.6, 2.6, 2.2, 6.2, 0.0],
       #             [1.3, 1.2, 0.0, 0.0, 0.0, 3.2, 5.1],
       #             [0.1, 2.0, 0.0, 1.4, 0.0, 1.9, 6.3]])

harvest = np.array([

[1191,1167,372,695,96,350,392,533,691,507,687,548,603],
[355,910,408,1025,364,566,371,400,427,429,404,122,471],
[1445,749,734,1411,371,536,709,538,792,873,184,782,742],
[439,1363,617,517,592,272,557,804,888,704,186,732,528],
[1033,829,565,699,444,309,823,1100,684,623,267,790,485],
[241,639,327,371,607,433,113,253,728,1051,355,596,643],
[451,235,297,370,653,355,512,907,238,573,893,1050,385],
[779,435,717,401,265,362,624,757,241,154,492,537,640],
[121,696,591,381,277,684,483,597,775,333,287,566,739],
[802,737,221,654,1296,685,537,835,703,1132,442,646,481],
[754,982,145,1224,566,1104,417,442,583,755,539,409,626],
[792,184,1215,513,801,909,735,613,650,404,579,409,562],
[904,259,412,812,513,443,392,703,402,222,389,888,567]


])

# 这里是创建一个画布
fig, ax = plt.subplots()
im = ax.imshow(harvest, cmap=plt.cm.OrRd, vmin=0, vmax=2000, aspect="auto")

# 这里是修改标签
# We want to show all ticks...
ax.set_xticks(np.arange(len(farmers)))
ax.set_yticks(np.arange(len(vegetables)))
# ... and label them with the respective list entries
ax.set_xticklabels(farmers)
ax.set_yticklabels(vegetables)

# 因为x轴的标签太长了，需要旋转一下，更加好看
# Rotate the tick labels and set their alignment.
#plt.setp(ax.get_xticklabels(), rotation=45, ha="right",
 #        rotation_mode="anchor")

# 添加每个热力块的具体数值
# Loop over data dimensions and create text annotations.
for i in range(len(vegetables)):
    for j in range(len(farmers)):
        text = ax.text(j, i, harvest[i, j],
                       ha="center", va="center", color="black", fontsize=20)
#ax.set_title("Harvest of local farmers (in tons/year)")
#fig.tight_layout()
fig.set_size_inches(10, 10)
cb = plt.colorbar(im)
plt.xticks(fontsize=24)
plt.yticks(fontsize=24)
cb.ax.tick_params(labelsize=24)
plt.show()