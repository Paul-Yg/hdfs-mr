package cn.hadoop.rpc;

/**
 * 用户自己需要定义一个协议接口来封装所以客户端向服务端发送的请求
 * versionID用来标识不同的协议。
 * @author admin
 *
 */
public interface Bizable {
	
	public static final long versionID = 1001;

	public String sayHi(String name);
}
