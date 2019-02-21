package projectd.server;
import java.net.InetAddress;


public class User {
	
	
	private String hostName;
	private String hostIP;
	private InetAddress address;
	
	public User() {
		
	}
	public User(InetAddress address,String hostIP,String hostName){
		this.hostName=hostName;
		this.address=address;
		this.hostIP=hostIP;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getHostIP() {
		return hostIP;
	}

	public void setHostIP(String hostIP) {
		this.hostIP = hostIP;
	}

	public InetAddress getAddress() {
		return address;
	}

	public void setAddress(InetAddress address) {
		this.address = address;
	}
}
