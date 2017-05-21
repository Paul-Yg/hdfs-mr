package cn.hadoop.rpc;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
/**
 * Datanode: getProxy�õ�һ��RPC�ͻ��ˡ�getProxy������ʹ�ö�̬����
 * 	����һ��ָ������˵Ĵ���һ��Զ�̶�����Ҫ����һ������
 * @author admin
 *
 */
public class RPCClient {

	public static void main(String[] args) throws Exception {
		Bizable proxy =	RPC.getProxy(Bizable.class, 1001, new InetSocketAddress("192.168.100.101", 9527), new Configuration());
		String result = proxy.sayHi("RPC");
		System.out.println(result);
		RPC.stopProxy(proxy);
	}

}
