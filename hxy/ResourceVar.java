package hxy;

public class ResourceVar {
	private int varValue;
	private String varName;
	//������������flagΪtrue������Ϊ
	private boolean flag = false;
	
	public ResourceVar(String varName,int varValue){
		this.varName = varName;
		this.varValue = varValue;
	}
	public void setVarName(String varName){
		this.varName = varName;
	}
	public String getVarName(){
		return varName;
	}
	public void setVarValue(int varValue){
		this.varValue = varValue;
	}
	public int getVarValue(){
		return varValue;
	}
	public void setFlag(boolean flag){
		this.flag = flag;
	}
	public boolean getFlag(){
		return flag;
	}
}
