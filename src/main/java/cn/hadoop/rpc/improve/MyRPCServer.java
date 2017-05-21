package cn.hadoop.rpc.improve;

import java.io.IOException;

import org.apache.hadoop.HadoopIllegalArgumentException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.ipc.ProtocolSignature;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RPC.Server;

public class MyRPCServer implements MyRPCProtocal{

	/**
	 *  ipc.Server����Ҫ�����ǽ������Կͻ��˵�RPC���󣬾���������Ӧ�ĺ�����ȡ����󣬷��ظ���Ӧ�Ŀͻ���.
	 * 
	 */
	Server server = null;
	
	public MyRPCServer() throws Exception{
		RPC.Builder ins =new RPC.Builder(new Configuration());
		ins.setInstance(this);
		ins.setBindAddress("192.168.8.100");
		ins.setPort(9527);
		ins.setProtocol(MyRPCProtocal.class);
		server = ins.build();                     //����һ��Serverʵ��
		server.start();
		server.join();
	}
	
	public long getProtocolVersion(String protocol, long clientVersion)
			throws IOException {
		return MyRPCProtocal.versionID;
	}

	public ProtocolSignature getProtocolSignature(String protocol,
			long clientVersion, int clientMethodsHash) throws IOException {
		return new ProtocolSignature();
	}

	public Text test(Text t) {
		if(t.toString().equals("RPC")) {
			return new Text("true");
		} 
		return new Text("false");
	}

	public static void main(String[] args) throws Exception {
		new MyRPCServer();
	}
}
