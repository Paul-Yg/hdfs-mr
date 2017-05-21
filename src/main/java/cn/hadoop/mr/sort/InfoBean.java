package cn.hadoop.mr.sort;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

/**
 * 实现WritableComparable可以实现虚拟化和排序
 * @author admin
 *
 */
public class InfoBean implements WritableComparable<InfoBean> {

	private String user;
	private double income;                                    
	private double expense;
	private double surplus;
	
	public InfoBean() {}
	
	public void set(String user,double income , double expense) {
		this.user = user;
		this.income = income;
		this.expense = expense;
		this.surplus = income-expense;
	}
	
	public void write(DataOutput out) throws IOException {
		out.writeUTF(user);
		out.writeDouble(income);
		out.writeDouble(expense);
		out.writeDouble(surplus);
	}

	public void readFields(DataInput in) throws IOException {
		this.user = in.readUTF();
		this.income = in.readDouble();
		this.expense = in.readDouble();
		this.surplus = in.readDouble();
	}

	public int compareTo(InfoBean o) {
		if (this.income==o.getIncome()) {
			return this.expense>o.getExpense() ? 1 : -1;
		} else {
			return this.income>o.getIncome() ? -1 :1;
		}
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public double getExpense() {
		return expense;
	}

	public void setExpense(double expense) {
		this.expense = expense;
	}

	public double getSurplus() {
		return surplus;
	}

	public void setSurplus(double surplus) {
		this.surplus = surplus;
	}
	
	@Override
	public String toString() {
		return this.income+"\t"+this.expense+"\t"+this.surplus;
	}
	
}

