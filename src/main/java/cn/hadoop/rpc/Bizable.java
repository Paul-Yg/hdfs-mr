package cn.hadoop.rpc;

/**
 * �û��Լ���Ҫ����һ��Э��ӿ�����װ���Կͻ��������˷��͵�����
 * versionID������ʶ��ͬ��Э�顣
 * @author admin
 *
 */
public interface Bizable {
	
	public static final long versionID = 1001;

	public String sayHi(String name);
}
