package hxy;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VarAddForm extends JDialog {
	private static final long serialVersionUID = 1L;
	private JTextField varNameTextField;
	private JTextField varValueTextField;
	private ArrayList<ResourceVar> varList;

	public VarAddForm(ArrayList<ResourceVar> varList) {
		this.setVarList(varList);
		initialize();
	}
	private void initialize(){
		setTitle("��Ʒ�������");
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel varLabel = new JLabel("ϵͳ����");
		varLabel.setHorizontalAlignment(SwingConstants.CENTER);
		varLabel.setFont(new Font("����", Font.PLAIN, 15));
		varLabel.setBounds(156, 26, 102, 25);
		getContentPane().add(varLabel);
		
		JLabel varNameLabel = new JLabel("������");
		varNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		varNameLabel.setFont(new Font("����", Font.PLAIN, 14));
		varNameLabel.setBounds(79, 72, 65, 15);
		getContentPane().add(varNameLabel);
		
		JLabel varValueLabel = new JLabel("����ֵ");
		varValueLabel.setFont(new Font("����", Font.PLAIN, 14));
		varValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		varValueLabel.setBounds(90, 121, 54, 15);
		getContentPane().add(varValueLabel);
		
		varNameTextField = new JTextField();
		varNameTextField.setBounds(208, 69, 109, 21);
		getContentPane().add(varNameTextField);
		varNameTextField.setColumns(10);
		
		varValueTextField = new JTextField();
		varValueTextField.setBounds(208, 118, 109, 21);
		getContentPane().add(varValueTextField);
		varValueTextField.setColumns(10);
		
		JButton addButton = new JButton("���");
		addButton.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent arg0) {
				String varName = varNameTextField.getText().trim();
				String varValue = varValueTextField.getText().trim();
				if("".equals(varName)||"".equals(varValue)){
					new JOptionPane().showMessageDialog(addButton, "������ϵͳ������Ϣ������");
				}else{
					int i;
					for(i = 0;i<varList.size();i++){
						if(varName.trim().equals(varList.get(i).getVarName())){
							new JOptionPane().showMessageDialog(addButton, "ϵͳ�Ѿ�����ͬ������������");
							break;
						}
					}
					if(i==varList.size()){
						varList.add(new ResourceVar(varName, Integer.parseInt(varValue)));
						dispose();
					}					
				}
			}
		});
		addButton.setBounds(90, 180, 93, 23);
		getContentPane().add(addButton);
		
		JButton cancelButton = new JButton("ȡ��");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		cancelButton.setBounds(226, 180, 93, 23);
		getContentPane().add(cancelButton);
	}
	public ArrayList<ResourceVar> getVarList() {
		return varList;
	}
	public void setVarList(ArrayList<ResourceVar> varList) {
		this.varList = varList;
	}
}
