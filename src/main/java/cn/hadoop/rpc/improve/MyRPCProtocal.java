package cn.hadoop.rpc.improve;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.ipc.VersionedProtocol;

/**
 * �û���Ҫ�Լ�����һ��Э��ӿ�����װ���пͻ��������˷��͵�����
 * �ýӿڱ���̳���org.apache.hadoop.ipc.VersionedProtocol��
 * �ӿ��е�versionID������ʶ��ͬ��Э�顣
 * @author admin
 *
 */
public interface MyRPCProtocal extends VersionedProtocol {

	public static final long versionID = 13L;
	
	public Text test(Text t);
}
