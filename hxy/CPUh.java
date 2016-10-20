package hxy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JLabel;
import javax.swing.JTable;

public class CPUh {
	private int cpuTime;
	private int clockTime;
	private Queue<ProcessH> processQueue;//�����Ҫ���н��̵Ķ���
	private Queue<ProcessH> readyQueue;//��������
	private Queue<ProcessH> waitQueue;//�ȴ�����
	private Queue<ProcessH> finishQueue;//��ɶ���
	private ArrayList<ResourceVar> varList;//��Դ����,���Ǵ�Ź��б���
	private JLabel clockTimeLabel;
	
	public Queue<ProcessH> getProcessQueue() {
		return processQueue;
	}
	public void setProcessQueue(Queue<ProcessH> processQueue) {
		this.processQueue = processQueue;
	}
	public Queue<ProcessH> getFinishQueue() {
		return finishQueue;
	}
	public void setFinishQueue(Queue<ProcessH> finishQueue) {
		this.finishQueue = finishQueue;
	}
	public void setClockTimeLabel(JLabel label){
		this.clockTimeLabel = label;
	}
	public JLabel getClockTimeLabel(){
		return clockTimeLabel;
	}
	public CPUh(int cpuTime){
		this.cpuTime = cpuTime;
		this.varList = new ArrayList<ResourceVar>(); 
		this.processQueue = new LinkedList<ProcessH>();
		this.readyQueue = new LinkedList<ProcessH>();
		this.waitQueue = new LinkedList<ProcessH>();
		this.finishQueue = new LinkedList<ProcessH>();
	}
	
	//��ȡ��������
	public ArrayList<ResourceVar> getVarList(){
		return this.varList;
	}
	//����cpuʱ�ӵ�ʱ��
	public void setClockTime(int clockTime){
		this.clockTime = clockTime;
	}
	public int getClockTime(){
		return this.clockTime;
	}
	
	//��ȡcpu��ʱ��Ƭ����
	public int getCPUTime(){
		return cpuTime;
	}
	public void setCPUTime(int cpuTime){
		this.cpuTime = cpuTime;
	}
	public Queue<ProcessH> getReadyQueue(){
		return readyQueue;
	}
	public Queue<ProcessH> getWaitQueue(){
		return waitQueue;
	}
	//��CPU��ӹ��ñ���
	public void addVar(ResourceVar var){
		varList.add(var);
	}
	//�Ƴ�cpu�е�һ����������
	public void removeVar(ResourceVar var){
		for(int i = 0;i<varList.size();i++){
			if(varList.get(i).getVarName().equals(var.getVarName())){
				varList.remove(i);
				break;
			}
		}
		System.out.println("CPU��û�б�����Ϊ"+var.getVarName());
	}
	
	public void showVar(){
		System.out.println("===================================\nvarName value condition");
		for(int i = 0;i<varList.size();i++){
			System.out.println("     "+varList.get(i).getVarName()+"   "+varList.get(i).getVarValue()+"    "+varList.get(i).getFlag());
		}
	}
	public void showReadyQueue(){
		System.out.println("===================================\nProName startTime condition leftTime");
		int size = readyQueue.size();
		ProcessH temp ;
		for(int i = 0;i<size;i++){
			temp = readyQueue.poll();
			System.out.println("  "+temp.getProcessName()+"  "+temp.getStartTime()+"  "+temp.getCondition()+"   "+temp.getLeftTime());
			readyQueue.add(temp);
		}
	}
	public void showWaitQueue(){
		System.out.println("===================================\nProName  arriveTime needTime condition");
		int size = waitQueue.size();
		ProcessH temp ;
		for(int i = 0;i<size;i++){
			temp = waitQueue.poll();
			System.out.println(temp.getProcessName()+"    "+temp.getArriveTime()+"          "+temp.getNeedTime()+"       "+temp.getCondition());
			waitQueue.add(temp);
		}
	}
	
	public void showData(){
		System.out.println("CPU begin to process processes!!!\n=================Begin===============\n");
		showVar();
		showWaitQueue();
		showReadyQueue();
	}
	
	//cpu������̣�����һ���߳�����������Ľ���
	public boolean doExcute(JTable [] tables){
		boolean flag = false;
		DProcess dp = new DProcess();
		dp.setClockTime(0);
		dp.setCpuTime(cpuTime);
		dp.setProcessQueue(processQueue);
		dp.setReadyQueue(readyQueue);
		dp.setWaitQueue(waitQueue);
		dp.setFinishQueue(finishQueue);
		dp.setTables(tables);
		dp.setClockTimeLabel(clockTimeLabel);
		dp.setVarList(varList);
		//dp.doExcute();
		Thread thread = new Thread(dp);
		thread.start();
		return flag;
	}
	
	
}
