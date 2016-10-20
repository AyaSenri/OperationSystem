package hxy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.Timer;

public class DealProcess implements Runnable{

	private Queue<ProcessH> readyQueue;
	private Queue<ProcessH> waitQueue;
	private Queue<ProcessH> finishQueue = new LinkedList<ProcessH>();
	private int clockTime;
	private int cpuTime;
	private JTable[] tables;
	private ProcessH swap;
	private JLabel label;
	
	public JLabel getLabel() {
		return label;
	}
	public void setLabel(JLabel label) {
		this.label = label;
	}
	public JTable[] getTables() {
		return tables;
	}
	public void setTables(JTable[] tables) { /////processTable,varTable,waitQueueTable,readyQueueTable,finishProcessTable,recorderTable
		this.tables = tables;
	}
	public void setReadyQueue(Queue<ProcessH> readyQueue){
		this.readyQueue = readyQueue;
	}
	public void setWaitQueue(Queue<ProcessH> waitQueue){
		this.waitQueue = waitQueue;
	}
	public Queue<ProcessH> getReadyQueue(){
		return this.readyQueue;
	}
	public Queue<ProcessH> getWaitQueue(){
		return this.waitQueue;
	}
	public int getClockTime() {
		return clockTime;
	}
	public void setClockTime(int clockTime) {
		this.clockTime = clockTime;
	}
	public int getCpuTime() {
		return cpuTime;
	}
	public void setCpuTime(int cpuTime) {
		this.cpuTime = cpuTime;
	}
	public Queue<ProcessH> getFinishQueue() {
		return finishQueue;
	}
	public void setFinishQueue(Queue<ProcessH> finishQueue) {
		this.finishQueue = finishQueue;
	}
	public void initReadyProcess(ProcessH processH){
		/*
		 * ��ʼ���������̣�������״̬�ĳ�ready��ʣ��ʱ�����ó���Ҫʱ�䣬��ʼʱ������Ϊ-1,
		 */
		processH.setCondition("ready");
		processH.setLeftTime(processH.getNeedTime());
		processH.setStartTime(-1);
	}

	public void showReadyQueueTable(){
		
	}
	public void showProcess(){
		int i =0;
		for(;i<waitQueue.size();i++){
			tables[0].setValueAt(waitQueue.peek().getProcessName(), i, 0);
			tables[0].setValueAt(waitQueue.peek().getArriveTime(), i, 1);
			tables[0].setValueAt(waitQueue.peek().getNeedTime(), i, 2);
			tables[0].setValueAt(waitQueue.peek().getCondition(), i, 3);
			tables[0].setValueAt(waitQueue.peek().getLeftTime(), i, 4);
			waitQueue.add(waitQueue.poll());
		}
		for(;i-waitQueue.size()<readyQueue.size();i++){
			tables[0].setValueAt(readyQueue.peek().getProcessName(), i, 0);
			tables[0].setValueAt(readyQueue.peek().getProcessName(), i, 1);
			tables[0].setValueAt(readyQueue.peek().getProcessName(), i, 2);
			tables[0].setValueAt(readyQueue.peek().getProcessName(), i, 3);
			tables[0].setValueAt(readyQueue.peek().getProcessName(), i, 4);
			readyQueue.add(readyQueue.poll());
		}
		for(;i-waitQueue.size()-readyQueue.size()<finishQueue.size();i++){
			tables[0].setValueAt(finishQueue.peek().getProcessName(), i, 0);
			tables[0].setValueAt(finishQueue.peek().getProcessName(), i, 1);
			tables[0].setValueAt(finishQueue.peek().getProcessName(), i, 2);
			tables[0].setValueAt(finishQueue.peek().getProcessName(), i, 3);
			tables[0].setValueAt(finishQueue.peek().getProcessName(), i, 4);
			finishQueue.add(finishQueue.poll());
		}
		
	}
	public void showWaitQueueTable(){
		for(int i = 0;i<waitQueue.size();i++){
			tables[0].setValueAt(waitQueue.peek().getProcessName(), i, 0);
			tables[0].setValueAt(waitQueue.peek().getArriveTime(), i, 1);
			tables[0].setValueAt(waitQueue.peek().getNeedTime(), i, 2);
			tables[0].setValueAt(waitQueue.peek().getCondition(), i, 3);
			tables[0].setValueAt(waitQueue.peek().getLeftTime(), i, 4);
			waitQueue.add(waitQueue.poll());
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
		System.out.println("CPU begin to process processes!!!\n===================================\nClockTime:"+this.clockTime);
		//showVar();
		showWaitQueue();
		showReadyQueue();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		Timer timer = new Timer(1000, new ActionListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				label.setText(clockTime+"");
				//clockTime++;
				System.out.println("\n==============ClockTime:"+(clockTime)+"==============");
				{
					/*
					 * ɨ��һ�ν���˯��1�룬����������̣�ÿ�ζ�ɨ��ȴ����п��Ƿ��н�����Ҫ����������У�
					 * doѭ����Ϊ�˿���ͬʱ�ж�����̽���������У��˴�����ȴ����еĵ���ʱ���ǰ��Ⱥ�˳��ģ�
					 */
					for(int i = 0;i<waitQueue.size();i++){
						if(waitQueue.peek().getArriveTime()==clockTime){
							initReadyProcess(waitQueue.peek());
							System.out.println("Process "+waitQueue.peek().getProcessName()+" turn to readyQueue from waitQueue!!!");
							readyQueue.add(waitQueue.poll());
						}else{
							waitQueue.add(waitQueue.poll());
						}
					}		
	
					if(swap!=null){
						readyQueue.add(swap);
						System.out.println("δ��ɽ���"+swap.getProcessName()+"���¼��뵽�������У�����");
						swap =null;
						System.out.println("readyQueue:"+readyQueue.size());
					}
				}
				/*
				 * �����������
				 * 
				 */
				{
					ProcessH tempPro = readyQueue.peek();
					if(tempPro!=null){
						//�жϾ������еĶ�ͷ�Ľ����Ƿ��Ѿ���ִ���ˣ���û���򽫵�ǰ��ʱ������ΪstartTime
						if(tempPro.getStartTime()==-1){
							//���ý��̵Ŀ�ʼʱ��
							tempPro.setStartTime(clockTime);
							tempPro.setLeftTime(tempPro.getNeedTime());
						}
						//else
						{
							//�жϽ��̵���Ҫʱ���Ƿ񳬹�ʱ��Ƭ��ʱ��
							if(tempPro.getNeedTime()<=cpuTime){
								//�жϽ����Ƿ�ִ�е������һ��ʱ�䵥λ�����б����Ĵ���
								if(tempPro.getLeftTime()==1){
									if(tempPro.doOperation()){
										//readyQueue.poll();
										finishQueue.add(readyQueue.poll());
										System.out.println(tempPro.getProcessName()+" has finsished!!!  �ó�CPU");
									}else{
										//��Ϊ��ɵĽ��̷ŵ�swap�м�����У��ȴ���һ��ɨ�����¼����������
										swap = readyQueue.poll();
										//readyQueue.add();
										System.out.println(tempPro.getProcessName()+"���̵��õı���"+tempPro.getVar().getVarName()+"����ֻ��״̬������");
									}
								}else{
									tempPro.setLeftTime(tempPro.getLeftTime()-1);
									System.out.println(tempPro.getProcessName()+" is doing!!!");
								}
							}else{
								//������ʱ�����ʱ��Ƭʱ���ж��Ƿ�Ϊ��һ��ʱ��Ƭ
								if(tempPro.getLeftTime()==1){
									if(tempPro.doOperation()){
										//readyQueue.poll();
										finishQueue.add(readyQueue.poll());
										System.out.println(tempPro.getProcessName()+" has finsished!!!  �ó�CPU");
									}else{
										swap = readyQueue.poll();
										//readyQueue.add(readyQueue.poll());
										System.out.println(tempPro.getProcessName()+"���̵��õı���"+tempPro.getVar().getVarName()+"����ֻ��״̬������");
									}							
								}else{
									if((tempPro.getNeedTime()-tempPro.getLeftTime()+1)%cpuTime==0&&tempPro.getNeedTime()!=tempPro.getLeftTime()){
										tempPro.setLeftTime(tempPro.getLeftTime()-1);
										System.out.println(tempPro.getProcessName()+" is doing!!!");
										swap = readyQueue.poll();
										//readyQueue.add(readyQueue.poll());
										System.out.println("ʱ��Ƭ����"+tempPro.getProcessName()+"�ó�cpu������");
									}else{
										tempPro.setLeftTime(tempPro.getLeftTime()-1);
										System.out.println(tempPro.getProcessName()+" is doing!!!");
									}
								}						
							}
							
						}
					}else{
						System.out.println("CPU is unuse!!!");
					}
	
				}
	
				try {
					Thread.currentThread().sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					}
				System.out.println("========================================");
				showWaitQueueTable();
				clockTime++;
				if(readyQueue.isEmpty()&&waitQueue.isEmpty()){
					Thread.currentThread().stop();
				}
			}
		});
		timer.start();
		/*
		 * ִ��ѭ���������ֱ���ڴ��еĵȴ����к;������ж�Ϊ�գ�����ѭ����
		 * ʱ��ʱ��ÿ������һ������˿��Լ�ʱ�Ĵ���ȴ����н����������
		 */
		//ProcessH swap = null;
//		for(;!readyQueue.isEmpty()||!waitQueue.isEmpty();clockTime++){
//			/*
//			 * ɨ��ȴ����У����Ƿ��н�����Ҫ�����������
//			 * ȡ���ȴ���ͷ�Ľ��̣��ж��Ƿ���Ҫ��ȥ��������
//			 */
//			System.out.println("\n==============ClockTime:"+(clockTime)+"==============");
//			{
//				/*
//				 * ɨ��һ�ν���˯��1�룬����������̣�ÿ�ζ�ɨ��ȴ����п��Ƿ��н�����Ҫ����������У�
//				 * doѭ����Ϊ�˿���ͬʱ�ж�����̽���������У��˴�����ȴ����еĵ���ʱ���ǰ��Ⱥ�˳��ģ�
//				 */
//				for(int i = 0;i<waitQueue.size();i++){
//					if(waitQueue.peek().getArriveTime()==clockTime){
//						initReadyProcess(waitQueue.peek());
//						System.out.println("Process "+waitQueue.peek().getProcessName()+" turn to readyQueue from waitQueue!!!");
//						readyQueue.add(waitQueue.poll());
//					}else{
//						waitQueue.add(waitQueue.poll());
//					}
//				}
//				
//
//				if(swap!=null){
//					readyQueue.add(swap);
//					System.out.println("δ��ɽ���"+swap.getProcessName()+"���¼��뵽�������У�����");
//					swap =null;
//					System.out.println("readyQueue:"+readyQueue.size());
//				}
//			}
//			/*
//			 * �����������
//			 * 
//			 */
//			{
//				ProcessH tempPro = readyQueue.peek();
//				if(tempPro!=null){
//					//�жϾ������еĶ�ͷ�Ľ����Ƿ��Ѿ���ִ���ˣ���û���򽫵�ǰ��ʱ������ΪstartTime
//					if(tempPro.getStartTime()==-1){
//						//���ý��̵Ŀ�ʼʱ��
//						tempPro.setStartTime(clockTime);
//						tempPro.setLeftTime(tempPro.getNeedTime());
//					}
//					//else
//					{
//						//�жϽ��̵���Ҫʱ���Ƿ񳬹�ʱ��Ƭ��ʱ��
//						if(tempPro.getNeedTime()<=cpuTime){
//							//�жϽ����Ƿ�ִ�е������һ��ʱ�䵥λ�����б����Ĵ���
//							if(tempPro.getLeftTime()==1){
//								if(tempPro.doOperation()){
//									//readyQueue.poll();
//									finishQueue.add(readyQueue.poll());
//									System.out.println(tempPro.getProcessName()+" has finsished!!!  �ó�CPU");
//								}else{
//									//��Ϊ��ɵĽ��̷ŵ�swap�м�����У��ȴ���һ��ɨ�����¼����������
//									swap = readyQueue.poll();
//									//readyQueue.add();
//									System.out.println(tempPro.getProcessName()+"���̵��õı���"+tempPro.getVar().getVarName()+"����ֻ��״̬������");
//								}
//							}else{
//								tempPro.setLeftTime(tempPro.getLeftTime()-1);
//								System.out.println(tempPro.getProcessName()+" is doing!!!");
//							}
//						}else{
//							//������ʱ�����ʱ��Ƭʱ���ж��Ƿ�Ϊ��һ��ʱ��Ƭ
//							if(tempPro.getLeftTime()==1){
//								if(tempPro.doOperation()){
//									//readyQueue.poll();
//									finishQueue.add(readyQueue.poll());
//									System.out.println(tempPro.getProcessName()+" has finsished!!!  �ó�CPU");
//								}else{
//									swap = readyQueue.poll();
//									//readyQueue.add(readyQueue.poll());
//									System.out.println(tempPro.getProcessName()+"���̵��õı���"+tempPro.getVar().getVarName()+"����ֻ��״̬������");
//								}							
//							}else{
//								if((tempPro.getNeedTime()-tempPro.getLeftTime()+1)%cpuTime==0&&tempPro.getNeedTime()!=tempPro.getLeftTime()){
//									tempPro.setLeftTime(tempPro.getLeftTime()-1);
//									System.out.println(tempPro.getProcessName()+" is doing!!!");
//									swap = readyQueue.poll();
//									//readyQueue.add(readyQueue.poll());
//									System.out.println("ʱ��Ƭ����"+tempPro.getProcessName()+"�ó�cpu������");
//								}else{
//									tempPro.setLeftTime(tempPro.getLeftTime()-1);
//									System.out.println(tempPro.getProcessName()+" is doing!!!");
//								}
//							}						
//						}
//						
//					}
//				}else{
//					System.out.println("CPU is unuse!!!");
//				}
//
//			}
//
//			try {
//				Thread.currentThread().sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//				}
//			System.out.println("========================================");
		}
//		showData();
//		Iterator<ProcessH> iterator = finishQueue.iterator();
//		System.out.println("\n\n===========Process finish list==============");
//		while(iterator.hasNext()){
//			System.out.println(iterator.next().getProcessName());
//		}
//	}
	
}



//public void showVar(){
//	System.out.println("===================================\nvarName value condition");
//	for(int i = 0;i<varList.size();i++){
//		System.out.println("     "+varList.get(i).getVarName()+"   "+varList.get(i).getVarValue()+"    "+varList.get(i).getFlag());
//	}
//}
/*
 * ��ʼɨ��ȴ����д���			
 */
//			do{
//				ProcessH tempPro = waitQueue.peek();
//				if(tempPro!=null){
//					//�жϾ������еĶ�ͷ�ĵ���ʱ���Ƿ�͵�ǰʱ��ʱ��һ�����ǵĻ��ͽ��ý��̴ӵȴ�������ӽ���������
//					if(tempPro.getArriveTime()==clockTime){
//						readyQueue.add(waitQueue.poll());
//						initReadyProcess(tempPro);
//					System.out.println("Process "+tempPro.getProcessName()+" turn to readyQueue from waitQueue!!!");
//						//showData();
//					}else{
//						//System.out.println("There is no process turn to readyQueue from waitQueue!!!");
//						break;
//					}
//				}else{
//					System.out.println("waitQueue is empty!!!");
//					break;
//				}			
//			}while(true);	