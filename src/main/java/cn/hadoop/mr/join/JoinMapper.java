package cn.hadoop.mr.join;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * Join讲解：获取员工所在部门信息，输出的格式要求是：员工编号、员工姓名、部门名称、部门编号
 * 1.原始数据
 * 员工数据(emp)
 * empno  ename   job              mgr         sal     deptno
 * 7499    GY     sale            7888        1500       30
 * 7782    HH    managers         9234        2450       10
 * 
 * 部门数据(dept)
 * deptno          dname       loc
 * 30               sale       china
 * 10              reseach     japan
 * 
 * 实现的功能类似于：
 * select e.empno,e.ename,d.dname,d.depeno from emp e join dept d on e.deptno = d.deptno;
 * 
 * 处理join的思路：
 * 将join的key作为 map输出的key，也就是reduce输入的key，这样只要join的key相同，shuffle过后
 * 就会进入同一个reduce的key-value list中去。
 * 需要为join的两张表设计一个通用的bean，并且在bean中添加一个flag的标志，可以根据flag来区分哪张表的
 * 数据，recuce阶段根据flag来判断是员工数据还是部门数据，而join的真正处理是在reduce阶段
 * 
 * 实现中间的bean（由于数据在网络上传输需要序列化，hadoop处理的时候需要分组，排序
 * 所以要实现WritableComparable接口
 */
public class JoinMapper extends Mapper<LongWritable, Text, IntWritable, Emplyee> {

	@Override
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		String[] arr = line.split("\t");
		if(arr.length==3) {         //部门表
			Emplyee e = new Emplyee();
			e.setDeptno(arr[0]);
			e.setDname(arr[1]);
			e.setFlag(0);
			context.write(new IntWritable(Integer.parseInt(arr[0])), e);
		} else if(arr.length==6) { //员工表
			Emplyee e = new Emplyee();
			e.setEmpno(arr[0]);
			e.setEname(arr[1]);
			e.setFlag(1);
			e.setDeptno(arr[5]);
			context.write(new IntWritable(Integer.parseInt(arr[5])), e);
		}
		
	}
}
