%ѡ1,5

ch1=load('C:\Users\thinkpad\Desktop\new2\ab-gep32.txt');
plot(ch1(:,1),ch1(:,2),'LineWidth',5);
axis([-1000,100000,0,1]);
hold on

ch2=load('C:\Users\thinkpad\Desktop\new2\gep32.txt');
plot1=plot(ch2(:,1),ch2(:,2),'LineWidth',5);
%plot1.Color(4) = 0.7;
%hold on
%ch3=load('C:\Users\thinkpad\Desktop\new2\sl-gep12.txt');
%plot2=plot(ch3(:,1),ch3(:,2),'LineWidth',5);
%plot2.Color(4) = 0.3;
%hold on
%ch4=load('C:\Users\thinkpad\Desktop\new3\AB-GSGP1.txt');
%plot1=plot(ch4(:,1),ch4(:,2),'LineWidth',5);
%plot1.Color(4) = 0.5;

xlabel('Iteration');
ylabel('Diversity');
legend('AB-GEP','GEP')
axis square;