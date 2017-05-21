package cn.hadoop.mr.join;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * Join���⣺��ȡԱ�����ڲ�����Ϣ������ĸ�ʽҪ���ǣ�Ա����š�Ա���������������ơ����ű��
 * 1.ԭʼ����
 * Ա������(emp)
 * empno  ename   job              mgr         sal     deptno
 * 7499    GY     sale            7888        1500       30
 * 7782    HH    managers         9234        2450       10
 * 
 * ��������(dept)
 * deptno          dname       loc
 * 30               sale       china
 * 10              reseach     japan
 * 
 * ʵ�ֵĹ��������ڣ�
 * select e.empno,e.ename,d.dname,d.depeno from emp e join dept d on e.deptno = d.deptno;
 * 
 * ����join��˼·��
 * ��join��key��Ϊ map�����key��Ҳ����reduce�����key������ֻҪjoin��key��ͬ��shuffle����
 * �ͻ����ͬһ��reduce��key-value list��ȥ��
 * ��ҪΪjoin�����ű����һ��ͨ�õ�bean��������bean�����һ��flag�ı�־�����Ը���flag���������ű��
 * ���ݣ�recuce�׶θ���flag���ж���Ա�����ݻ��ǲ������ݣ���join��������������reduce�׶�
 * 
 * ʵ���м��bean�����������������ϴ�����Ҫ���л���hadoop�����ʱ����Ҫ���飬����
 * ����Ҫʵ��WritableComparable�ӿ�
 */
public class JoinMapper extends Mapper<LongWritable, Text, IntWritable, Emplyee> {

	@Override
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		String[] arr = line.split("\t");
		if(arr.length==3) {         //���ű�
			Emplyee e = new Emplyee();
			e.setDeptno(arr[0]);
			e.setDname(arr[1]);
			e.setFlag(0);
			context.write(new IntWritable(Integer.parseInt(arr[0])), e);
		} else if(arr.length==6) { //Ա����
			Emplyee e = new Emplyee();
			e.setEmpno(arr[0]);
			e.setEname(arr[1]);
			e.setFlag(1);
			e.setDeptno(arr[5]);
			context.write(new IntWritable(Integer.parseInt(arr[5])), e);
		}
		
	}
}
