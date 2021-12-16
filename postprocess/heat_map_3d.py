import matplotlib
import notebook as notebook
from mpl_toolkits.mplot3d import Axes3D
import matplotlib.pyplot as plt
#import pandas
#import os
import numpy as np
#plt.style.use('dark_background')
from numpy.ma import arange

fig = plt.figure()
fig = plt.figure(figsize=(30,30))
ax = fig.add_subplot(111, projection='3d')

X, Y, Z, dims = np.loadtxt('sample.txt', unpack=True, skiprows=1)

#mask1 = (dims<140)
#mask2 = ((dims>=140) & (dims<180))
#mask3 = ((dims>=180) & (dims<200))

#masks = [mask1, mask2, mask3]
#colors = ['r', 'b', 'g']

cm=plt.cm.YlOrRd

#for mask, color in zip(masks, colors)8
#%matplotlib notebook
ax.scatter3D(X, Y, Z, dims, c=dims,cmap=cm, s=50,alpha=1)
ax.set_zlim(1, 10)
ax.set_xlim(1, 10)
ax.set_ylim(1, 10)

farmers = ['$+$', '$+$',r'$ \times $', r' $\frac{1}{x}$ ', '$-x$', r'$ \sqrt{x} $', '$x$',"C"]
#farmers = ['$+$', '$+$','$-$',r'$ \times $',r'$ \div $', ' $sin$ ', '$cos$','$ logx $', '$e^x$','$x$']
#farmers = [ '$+$','$-$',r'$ \times $',r'$ \div $', ' $sin$ ', '$cos$','$ logx $', '$e^x$','$x$','$y$']
#farmers = [ '$+$','$-$',r'$ \times $',r'$ \div $', ' $sin$ ', '$cos$','$ logx $', '$e^x$','$x$']

#ax.set_xlabel(farmers)
#ax.set_ylabel(farmers)
#ax.set_zlabel(farmers)
sm = plt.cm.ScalarMappable(cmap=cm,
                           norm = matplotlib.colors.Normalize(vmin=0, vmax=1000))
#sm = plt.cm.ScalarMappable(cmap=cm)
ax.set_xticklabels(farmers,fontsize=15)
ax.set_yticklabels(farmers,fontsize=15)
ax.set_zticklabels(farmers,fontsize=15)
#plt.xticks(arange(11), ('$+$','$+$','$-$',r'$ \times $',r'$ \div $', ' $sin$ ', '$cos$','$ logx $', '$e^x$','$x$','$y$') )
#plt.yticks(arange(11), ('$+$','$+$','$-$',r'$ \times $',r'$ \div $', ' $sin$ ', '$cos$','$ logx $', '$e^x$','$x$','$y$') )
#plt.zticks(arange(11), ('$+$','$+$','$-$',r'$ \times $',r'$ \div $', ' $sin$ ', '$cos$','$ logx $', '$e^x$','$x$','$y$') )
#plt.colorbar(sm)
#ax = plt.gca()
#plt.gca().patch.set_facecolor('gray')
fig.set_facecolor('white')
ax.set_facecolor('white')
ax.grid(True)
ax.w_xaxis.pane.fill = False
ax.w_yaxis.pane.fill = False
ax.w_zaxis.pane.fill = False
cb = plt.colorbar(sm)
cb.ax.tick_params(labelsize=15)
#plt.tight_layout()
plt.show()