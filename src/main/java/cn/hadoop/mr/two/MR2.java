package cn.hadoop.mr.two;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/** 
 * ����ȥ�����⣺
 * ԭʼ���ݣ�
 * file1��                        file2:            �����
 * 2012-3-1 a  		2012-3-1 b		2012-3-1 a
 * 2012-3-2 b       2012-3-2 a		2012-3-1 b	
 * 2012-3-3 c 		2012-3-3 b		2012-3-2 a
 * 2012-3-4 d		2012-3-4 d		2012-3-2 b
 * 2012-3-5 a		2012-3-5 a		2012-3-3 b
 * 2012-3-6 b		2012-3-6 c		2012-3-3 c
 * 2012-3-7 c		2012-3-6 d		2012-3-4 d
 * 2012-3-4 d		2012-3-3 c		2012-3-5 a
 * 2012-3-3 c						2012-3-6 b
 * 									2012-3-6 c
 * 									2012-3-7 c
 *									2012-3-7 d
 *
 *˵��������ȥ�ص�����Ŀ������ԭʼ�����г��ֳ���һ�ε����������
 *�ļ���ֻ����һ�Σ�������Ȼ���뵽��ͬһ�����ݵ����м�¼������һ̨reduce
 *����������������ݳ��ֶ��ٴΣ�ֻҪ�����ս�����һ�ξͿ�����
 *����ľ���reduce������Ӧ����������Ϊkey������value-listû��Ҫ��
 *��reduce���յ�һ��<key,value-list>ʱ��ֱ�ӽ�key���Ƶ������
 *key�У�����value��Ϊ��ֵ��
 */
public class MR2 {
	
		public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
			Configuration conf = new Configuration();
			Job job = Job.getInstance(conf);
			job.setJarByClass(MR2.class);
			job.setMapperClass(DeMapper.class);
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(Text.class);
			FileInputFormat.setInputPaths(job, new Path(args[0]));
			job.setReducerClass(DeReucer.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(NullWritable.class);
			job.setNumReduceTasks(1);
			Path outputDir = new Path(args[1]);
			FileSystem fs = FileSystem.get(conf);
			if(fs.exists(outputDir)) {
				fs.delete(outputDir, true);
				System.out.println("outputDir has existed,but it has deleted !");
			}
			FileOutputFormat.setOutputPath(job, outputDir);
			System.exit(job.waitForCompletion(true)?0:1);
		}
}
