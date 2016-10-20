package hxy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Queue;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.Timer;

public class DProcess implements Runnable{
	private Queue<ProcessH> processQueue;
	private Queue<ProcessH> readyQueue;
	private Queue<ProcessH> waitQueue;
	private Queue<ProcessH> finishQueue;
	private ArrayList<ResourceVar> varList;
	private ProcessH swap;
	private int clockTime;
	private int cpuTime;
	private String content;
	private JTable[] tables;  //processTable,varTable,waitQueueTable,readyQueueTable,finishProcessTable,recorderTable
	private JLabel clockTimeLabel;
	private int doRecord = 0;
	public static int recordCount;
	
	public int getDoRecord() {
		return doRecord;
	}
	public void setDoRecord(int doRecord) {
		this.doRecord = doRecord;
	}
	public Queue<ProcessH> getProcessQueue() {
		return processQueue;
	}
	public void setProcessQueue(Queue<ProcessH> processQueue) {
		this.processQueue = processQueue;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public ArrayList<ResourceVar> getVarList() {
		return varList;
	}
	public void setVarList(ArrayList<ResourceVar> varList) {
		this.varList = varList;
	}
	public JLabel getClockTimeLabel() {
		return clockTimeLabel;
	}
	public void setClockTimeLabel(JLabel clockTimeLabel) {
		this.clockTimeLabel = clockTimeLabel;
	}
	public ProcessH getSwap() {
		return swap;
	}
	public void setSwap(ProcessH swap) {
		this.swap = swap;
	}
	public JTable[] getTables() {
		return tables;
	}
	public void setTables(JTable[] tables) {
		this.tables = tables;
	}
	public int getCpuTime() {
		return cpuTime;
	}
	public void setCpuTime(int cpuTime) {
		this.cpuTime = cpuTime;
	}
	public int getClockTime() {
		return clockTime;
	}
	public void setClockTime(int clockTime) {
		this.clockTime = clockTime;
	}
	public Queue<ProcessH> getFinishQueue() {
		return finishQueue;
	}
	public void setFinishQueue(Queue<ProcessH> finishQueue) {
		this.finishQueue = finishQueue;
	}
	public Queue<ProcessH> getWaitQueue() {
		return waitQueue;
	}
	public void setWaitQueue(Queue<ProcessH> waitQueue) {
		this.waitQueue = waitQueue;
	}
	public Queue<ProcessH> getReadyQueue() {
		return readyQueue;
	}
	public void setReadyQueue(Queue<ProcessH> readyQueue) {
		this.readyQueue = readyQueue;
	}
	public void initReadyProcess(ProcessH processH){
		/*
		 * ��ʼ���������̣�������״̬�ĳ�ready��ʣ��ʱ�����ó���Ҫʱ�䣬��ʼʱ������Ϊ-1,
		 */
		processH.setCondition("ready");
		processH.setLeftTime(processH.getNeedTime());
		processH.setStartTime(-1);
	}
	
	public void showRecorder(ProcessH process,String content,boolean result){
		if(!"".equals(content.trim())){
			recordCount++;
			tables[5].setValueAt(clockTime, doRecord, 0);
			tables[5].setValueAt(process==null?"����":process.getProcessName(), doRecord, 1);
			tables[5].setValueAt(content, doRecord, 2);
			tables[5].setValueAt(result?"�ɹ�":"ʧ��", doRecord++, 3);
		}else{
			System.out.println("��ʱ���û���ȷ���������");
		}

	}
	public void showW(){
		for(int i = 0;i<processQueue.size();i++){
			System.out.println(processQueue.peek().getProcessName()+"  "+processQueue.peek().getArriveTime());
			processQueue.add(processQueue.poll());
		}
	}
	public void scanWaitQueue(){
		/*
		 * ɨ��һ�ν���˯��1�룬����������̣�ÿ�ζ�ɨ����̶��п��Ƿ��н�����Ҫ����������У�
		 * 
		 */
		System.out.println("��"+(clockTime+1)+"��ɨ����̶��У�����");
		showW();
		content = null;
		int size = processQueue.size();
		for(int i = 0;i<size;i++){
			if(processQueue.peek().getArriveTime()==clockTime){
				initReadyProcess(processQueue.peek());
				if(content == null){
					content = processQueue.peek().getProcessName()+" ";
				}else{
					content += processQueue.peek().getProcessName()+" ";
				}
				System.out.println("Process "+processQueue.peek().getProcessName()+" turn to readyQueue from waitQueue!!!");
				readyQueue.add(processQueue.poll());
			}else{
				System.out.println(processQueue.peek().getProcessName()+"�������ض���");
				processQueue.add(processQueue.poll());
			}
		}
		if(content!=null){
			content+="����  ";
		}
		if(swap!=null){
			swap.setCondition("ready");
			readyQueue.add(swap);
			swap = null;
		}else{			
		}
		if(!readyQueue.isEmpty()){
			readyQueue.peek().setCondition("doing");			
		}else{			
		}
	}
	
	public void awake(){
		if(!waitQueue.isEmpty()){
			readyQueue.add(waitQueue.poll());
		}else{
			System.out.println("�ȴ�����Ϊ�գ�����");
		}
//		for(int i = 0;i<waitQueue.size();i++){
//			initReadyProcess(waitQueue.peek());
//			readyQueue.add(waitQueue.poll());
//		}
	}
	
	public String doContent(String content ,String con){
		if(content==null){
			return con;
		}else{
			return content+con;
		}
	}
	
	public void doProcess(){
		/*
		 * �������еĽ��̴���
		 * 
		 */
		System.out.println("��"+(clockTime+1)+"�δ���������У�����");
		ProcessH tempPro = readyQueue.isEmpty()?null:readyQueue.peek();
		if(tempPro!=null){
			//�жϾ������еĶ�ͷ�Ľ����Ƿ��Ѿ���ִ���ˣ���û���򽫵�ǰ��ʱ������ΪstartTime
			if(tempPro.getStartTime()==-1){
				//���ý��̵Ŀ�ʼʱ��
				/*
				 * �ж���׼��ִ�н����Ƿ�Ϊ�����ߣ��������ڽ���ִ��ǰ����ҪΪ���̷�����Դ��
				 * ����Դ��Ϊ0��ý��̷���cpu��ʹ��Ȩ�����ý��̷ŵ��ȴ������еȴ���Դ�ͷ�������
				 */
				if("-".equals(tempPro.getOperation())){
					for(int i = 0;i<varList.size();i++){
						if(tempPro.getVar().getVarName().equals(varList.get(i).getVarName())){
							if(varList.get(i).getVarValue()<=0){
								if(content == null){
									content = "������"+tempPro.getProcessName()+"��Դ����ʧ��ת��ȴ�";
								}else{
									content += "������"+tempPro.getProcessName()+"��Դ����ʧ��ת��ȴ�";
								}
								showRecorder(tempPro, content, false);
								System.out.println("��Դ����ʧ�ܣ�����");
								tempPro.setCondition("wait");
								waitQueue.add(readyQueue.poll());
								return;
							}else{
								varList.get(i).setVarValue(varList.get(i).getVarValue()-1);
								if(content == null){
									content = "������"+tempPro.getProcessName()+"��Դ����ɹ�";
								}else{
									content += "������"+tempPro.getProcessName()+"��Դ����ɹ�";
								}
								System.out.println("��Դ����ɹ�������");
								tempPro.setStartTime(clockTime);
								tempPro.setLeftTime(tempPro.getNeedTime());
							}
						}
					}
				}else{
					System.out.println("�����ߣ�����");
				}
			}else{
				System.out.println("////////////////////////////");
			}
			{
				//�жϽ��̵���Ҫʱ���Ƿ񳬹�ʱ��Ƭ��ʱ��
//				if(tempPro.getNeedTime()<=cpuTime)
				{
					//�жϽ����Ƿ�ִ�е������һ��ʱ�䵥λ�����б����Ĵ���
					if(tempPro.getLeftTime()==1){
						if("-".equals(tempPro.getOperation())){
							tempPro.setLeftTime(tempPro.getLeftTime()-1);
							tempPro.setCondition("finished");
							showRecorder(tempPro, doContent(content,"������"+tempPro.getProcessName()+"����"), true);
							finishQueue.add(readyQueue.poll());
							System.out.println(tempPro.getProcessName()+" has finsished!!!  �ó�CPU");
						}else if(tempPro.doOperation()){
							//readyQueue.poll();
							tempPro.setLeftTime(tempPro.getLeftTime()-1);
							tempPro.setCondition("finished");
							showRecorder(tempPro, doContent(content,"�ͷ���Դ�������߽��������ѵȴ�"), true);
							finishQueue.add(readyQueue.poll());
							//��������������ɺ���Ҫ���ѵȴ����п��Ƿ��н��̴��ڵȴ�״̬
							awake();
							System.out.println(tempPro.getProcessName()+" has finsished!!!  �ó�CPU");
						}else{
							//��Ϊ��ɵĽ��̷ŵ�swap�м�����У��ȴ���һ��ɨ�����¼����������
							swap = readyQueue.poll();
							showRecorder(swap, doContent(content,"����"), false);
							//swap.setCondition("ready");
							//readyQueue.add();
							System.out.println(tempPro.getProcessName()+"���̵��õı���"+tempPro.getVar().getVarName()+"����ֻ��״̬������");
						}						
					}else{
						//�жϵ�ǰ���̵���Ҫʱ���Ƿ񳬹�ʱ��Ƭ�ĳ���
//						if(tempPro.getNeedTime()<=cpuTime){
//							tempPro.setLeftTime(tempPro.getLeftTime()-1);
//							showRecorder(tempPro, doContent(content," "), true);
//							System.out.println(tempPro.getProcessName()+" is doing!!!");
//						}else
						{
							if((tempPro.getNeedTime()-tempPro.getLeftTime()+1)%cpuTime==0&&tempPro.getNeedTime()!=tempPro.getLeftTime()){
								tempPro.setLeftTime(tempPro.getLeftTime()-1);
								swap = readyQueue.poll();
								showRecorder(swap, doContent(content," ʱ��Ƭ��"+tempPro.getProcessName()+"�ó�cpu"), true);
								//swap.setCondition("ready");
								System.out.println(tempPro.getProcessName()+" is doing!!!");
								//readyQueue.add(readyQueue.poll());
								System.out.println("ʱ��Ƭ����"+tempPro.getProcessName()+"�ó�cpu������");
							}else{
								tempPro.setLeftTime(tempPro.getLeftTime()-1);
								showRecorder(tempPro, doContent(content," "), true);
								System.out.println(tempPro.getProcessName()+" is doing!!!");
							}
						}
						
						
					}
				}
//				else{
//					//������ʱ�����ʱ��Ƭʱ���ж��Ƿ�Ϊ���ʱ��Ƭ
//					if(tempPro.getLeftTime()==1){
//						if("-".equals(tempPro.getOperation())){
//							tempPro.setLeftTime(tempPro.getLeftTime()-1);
//							tempPro.setCondition("finished");
//							showRecorder(tempPro, doContent(content,"������"+tempPro.getProcessName()+"����"), true);
//							finishQueue.add(readyQueue.poll());
//							System.out.println(tempPro.getProcessName()+" has finsished!!!  �ó�CPU");
//						}else if(tempPro.doOperation()){
//							//readyQueue.poll();
//							tempPro.setLeftTime(tempPro.getLeftTime()-1);
//							tempPro.setCondition("finished");
//							showRecorder(tempPro, doContent(content,"�ͷ���Դ�������߽��������ѵȴ�"), true);
//							finishQueue.add(readyQueue.poll());
//							awake();
//							System.out.println(tempPro.getProcessName()+" has finsished!!!  �ó�CPU");
//						}else{
//							swap = readyQueue.poll();
//							showRecorder(swap, doContent(content,"����"), false);
//							//readyQueue.add(readyQueue.poll());
//							System.out.println(tempPro.getProcessName()+"���̵��õı���"+tempPro.getVar().getVarName()+"����ֻ��״̬������");
//						}		
//											
//					}else{
//						if((tempPro.getNeedTime()-tempPro.getLeftTime()+1)%cpuTime==0&&tempPro.getNeedTime()!=tempPro.getLeftTime()){
//							tempPro.setLeftTime(tempPro.getLeftTime()-1);
//							swap = readyQueue.poll();
//							showRecorder(swap, doContent(content," ʱ��Ƭ��"+tempPro.getProcessName()+"�ó�cpu"), true);
//							//swap.setCondition("ready");
//							System.out.println(tempPro.getProcessName()+" is doing!!!");
//							//readyQueue.add(readyQueue.poll());
//							System.out.println("ʱ��Ƭ����"+tempPro.getProcessName()+"�ó�cpu������");
//						}else{
//							tempPro.setLeftTime(tempPro.getLeftTime()-1);
//							showRecorder(tempPro, doContent(content," "), true);
//							System.out.println(tempPro.getProcessName()+" is doing!!!");
//						}
//					}						
//				}
				
			}
		}else{
			showRecorder(null, doContent(content,"����"), true);
			System.out.println("CPU is unuse!!!");
		}

	}
	public void clearTable(JTable table){
		for(int i = 0;i<table.getRowCount();i++){
			for(int j = 0;j<table.getColumnCount();j++){
				table.setValueAt(null, i, j);
			}
		}
	}
	
	public void collection(Queue<ProcessH> queue,ArrayList<ProcessH> allProcess){
		for(int i =0;i<queue.size();i++){
			allProcess.add(queue.peek());
			queue.add(queue.poll());
		}
	}
	
	public void sort(ArrayList<ProcessH> allProcess){
		for(int i = 0;i<allProcess.size()-1;i++){
			for(int j = 0;j<allProcess.size()-i-1;j++){
				if(allProcess.get(j).getArriveTime()>allProcess.get(j+1).getArriveTime()){
					ProcessH temp = allProcess.get(j);
					allProcess.set(j, allProcess.get(j+1));
					allProcess.set(j+1,temp);
				}
			}
		}
	}
	//��ʾ���̱����е���Ϣ
	public void showProcess(){
		clearTable(tables[0]);
		ArrayList<ProcessH> allProcess = new ArrayList<ProcessH>();
		collection(processQueue, allProcess);
		collection(waitQueue,allProcess);
		collection(readyQueue, allProcess);
		collection(finishQueue, allProcess);
		if(swap!=null){
			allProcess.add(swap);
		}
		sort(allProcess);
		for(int j = 0;j<allProcess.size();j++){
			tables[0].setValueAt(allProcess.get(j).getProcessName(), j, 0);
			tables[0].setValueAt(allProcess.get(j).getArriveTime(), j, 1);
			tables[0].setValueAt(allProcess.get(j).getNeedTime(), j, 2);
			tables[0].setValueAt(allProcess.get(j).getCondition(), j, 3);
			tables[0].setValueAt(allProcess.get(j).getLeftTime(), j, 4);
			tables[0].setValueAt(allProcess.get(j).getOperation().equals("-")?"������":"������", j, 5);
		}
	}
	public void showWaitQueue(){
		clearTable(tables[2]);
		for(int i = 0;i<waitQueue.size();i++){
			tables[2].setValueAt(i+1, i, 0);
			tables[2].setValueAt(waitQueue.peek().getProcessName(), i, 1);
			waitQueue.add(waitQueue.poll());
		}
	}
	public void showReadyQueue(){
		clearTable(tables[3]);
		int i=0;
		for(;i<readyQueue.size();i++){
			tables[3].setValueAt(i+1, i, 0);
			tables[3].setValueAt(readyQueue.peek().getProcessName(), i, 1);
			readyQueue.add(readyQueue.poll());
		}
		if(swap!=null){
			tables[3].setValueAt(i+1, i, 0);
			tables[3].setValueAt(swap.getProcessName(), i, 1);
		}
	}
	public void showFinishQueue(){
		clearTable(tables[4]);
		for(int i = 0;i<finishQueue.size();i++){
			tables[4].setValueAt(i+1, i, 0);
			tables[4].setValueAt(finishQueue.peek().getProcessName(), i, 1);
			finishQueue.add(finishQueue.poll());
		}
	}
	public void showVar(){
		clearTable(tables[1]);
		for(int i = 0;i<varList.size();i++){
			tables[1].setValueAt(varList.get(i).getVarName(), i, 0);
			tables[1].setValueAt(varList.get(i).getVarValue(), i, 1);
		}
	}
	public void shows(){
		showProcess();
		showWaitQueue();
		showReadyQueue();
		showFinishQueue();
		showVar();
	}
	
	/*
	 * �����ʱ�������м��ɨ��ȴ����У�����������У�ˢ��ҳ������
	 * 
	 */
	public void doExcute(){
		swap = null;
		Timer timer = new Timer(10, new ActionListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public synchronized void actionPerformed(ActionEvent e) {
				clockTimeLabel.setText(clockTime+"");
				scanWaitQueue();
				doProcess();
				System.out.println("���ǵ�"+(clockTime+1)+"��ˢ������");
				shows();
				if(waitQueue.isEmpty()&&readyQueue.isEmpty()&&swap==null&&processQueue.isEmpty()){
					Thread.currentThread().stop();
				}
				clockTime++;
			}
		});
		timer.start();
	}
	@Override
	public void run() {
		doExcute();
	}
}
