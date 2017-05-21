package cn.hadoop.rpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RPC.Server;

/**
 * NameNode�ˡ����÷���ҪgetServer�õ�һ��RPC����ˡ�ָ��Э���ʵ������ָ����
 * ��ַ�Ͷ˿�����������
 * @author admin
 *
 */
public class RPCServer implements Bizable {
	
	public String sayHi(String name) {
		return "Hi~ "+name;
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Server server = new RPC.Builder(conf).setProtocol(Bizable.class).setInstance(new RPCServer()).setBindAddress("192.168.100.101").setPort(9527).build();
		server.start();
	}
}
