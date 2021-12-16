ch1=load('C:\Users\thinkpad\Desktop\new1\ab-gep25.txt');

%ch2=load('E:\matlab\gep5.1.txt');
plot(ch1(:,1),ch1(:,2),'-','LineWidth',2);
%errorbar(ch1(:,1),ch1(:,2),ch1(:,3));
axis([-2000,100000,0,0.1]);
%plot(ch2(:,1),ch2(:,2))
hold on
ch2=load('C:\Users\thinkpad\Desktop\new1\spj-gep25.txt');
plot(ch2(:,1),ch2(:,2),'--','LineWidth',2);
hold on
ch3=load('C:\Users\thinkpad\Desktop\new1\sl-gep25.txt');
plot(ch3(:,1),ch3(:,2),':','LineWidth',2);
hold on
ch4=load('C:\Users\thinkpad\Desktop\new1\gep25.txt');
plot(ch4(:,1),ch4(:,2),'-.','LineWidth',2);
hold on 
xlabel('Iteration');
ylabel('Fitness');
legend('AB-GEP','SPJ-GEP','SL-GEP','GEP');
%title('(a) F1');
set(gca,'looseInset',[0 0 0 0]);
axis square;