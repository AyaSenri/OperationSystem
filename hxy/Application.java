package hxy;

public class Application {
/*
 * ��������ҵ���߼�����
 * ����cpu���Լ�����
 */
	public static void main(String[] args) {		
		//����CPU���󣬲���Ϊʱ��Ƭ�Ĵ�С
		CPUh cpuh = new CPUh(5);
//		//����cpu�й��ñ�������������������ֵ��
		ResourceVar varX = new ResourceVar("x", 0);
		//ResourceVar varY = new ResourceVar("y", 0);
		//������������ӽ�cpu
		cpuh.addVar(varX);
//		//cpuh.addVar(varY);
//		//�������̣���������������Ҫ����ʱ�䣬���̽����ڴ�ʱ�䣬������Ҫʹ�õĹ��б��������̶Թ��б����Ĳ�����
//		ProcessH process1 = new ProcessH("process1", 13, 1, varX, "+");
//		ProcessH process2 = new ProcessH("process2", 13, 2, varX, "-");
//		ProcessH process3 = new ProcessH("process3", 13, 3, varX, "-");
//		ProcessH process4 = new ProcessH("process4", 13, 4, varX, "+");
//		//��������ӽ��ȴ�����
//		cpuh.getProcessQueue().add(process1);
//		//���ý��̵�״̬
//		process1.setCondition("free");
//		process1.setLeftTime(process1.getNeedTime());
//		cpuh.getProcessQueue().add(process2);
//		process2.setCondition("free");
//		process2.setLeftTime(process2.getNeedTime());
//		cpuh.getProcessQueue().add(process3);
//		process3.setCondition("free");
//		process3.setLeftTime(process3.getNeedTime());
//		cpuh.getProcessQueue().add(process4);
//		process4.setCondition("free");
//		process4.setLeftTime(process4.getNeedTime());
		new MainActivityForm(cpuh);
		//new MainForm(cpuh.getWaitQueue(), cpuh.getVarList());
		//new ProcessAddForm(cpuh.getWaitQueue(), cpuh.getVarList());
		//����cpu���󣬴������
		//cpuh.doExcute();
		//new BackgroundFrame();
	}

}
