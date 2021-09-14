
import java.io.PrintWriter;

import javax.jms.Message;
import javax.jms.Session;

import com.ibm.disthub2.impl.formats.bridge.EndOfCatchup;
import com.ibm.jms.JMSTextMessage;
import com.ibm.mq.jms.JMSC;
import com.ibm.mq.jms.MQQueue;
import com.ibm.mq.jms.MQQueueConnection;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.mq.jms.MQQueueReceiver;
import com.ibm.mq.jms.MQQueueSender;
import com.ibm.mq.jms.MQQueueSession;

public class FCL_STUB {
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	// Define MQ properties
	String Host="abcd";
	String QueueManager="MQ124r";
	int Port=1419;
	String Channel="MOP.cl";
	String Name="";
	
	String InputQueue="MLP.FSS.MN";
	String OutputQueue="MK.ASD.FG";
	
	String Rename_Tag="anytag";
	String Folder_Path="C:\\Stubs\\FCL";
	
	MQQueueConnectionFactory cf= new MQQueueConnectionFactory();
	MQQueueConnection connection;
	MQQueueSession session;
	
	String strmsg=null;
	
	try {
		cf.setHostName(Host);
		cf.setPort(Port);
		cf.setQueueManager(QueueManager);
		cf.setChannel(Channel);
		cf.setTransportType(JMSC.MQJMS_CLIENT_NONJMS_MQ);
		connection=(MQQueueConnection) cf.createQueueConnection();
		session=(MQQueueSession) connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		
		com.ibm.mq.jms.MQQueue queue=(MQQueue) session.createQueue("queue://"+InputQueue);
		queue.setTargetClient(JMSC.MQJMS_CLIENT_NONJMS_MQ);
		MQQueueReceiver rec= (MQQueueReceiver) session.createReceiver(queue);
		
		com.ibm.mq.jms.MQQueue Res_queue=(MQQueue) session.createQueue("queue://"+OutputQueue);
		queue.setTargetClient(JMSC.MQJMS_CLIENT_NONJMS_MQ);
		MQQueueSender Res_rec= (MQQueueSender) session.createReceiver(Res_queue);
		
		connection.start();
		int x=0;
		do
		{
		//Request reading and storing part
			strmsg=((JMSTextMessage) rec.receive()).getText();
			System.out.println("your message is--"+strmsg);
			
			//Store message
			int lengthOftag= Rename_Tag.length();
			Name=strmsg.substring(strmsg.indexOf("<"+ Rename_Tag+">")+lengthOftag+2,strmsg.indexOf("</"+Rename_Tag+">"));
			
			try
			{
				PrintWriter out= new PrintWriter(Folder_Path+Name+".xml");
				out.write(strmsg);
				out.close();
			}
			catch(Exception e)
			{
				System.out.println(e);
				break;
			}
			
			//Response processing part
			String ref=strmsg.substring(strmsg.indexOf("<SourceSystemReference>")+23,strmsg.indexOf("</SourceSystemReference>"));
		    String Acc1= strmsg.substring(strmsg.indexOf("<AccountngSystem>")+18,strmsg.indexOf("</AccountngSystem>"));
	        String out="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
	        		+"<FundsControlResp><VersionId>1.0</VersionId>"
	        		+"<Reference>"+ref+"</Reference>"
	        		+"<Name>"+Name+"</Name>"
	        		+"<ID>12334</ID>"
	        		+"<Acc>"+Acc1+"</Acc>"
	        		+"<Status>00</Status></FundsControlResp>";
	       
	        System.out.println("response is--"+out);
	        Message message=session.createTextMessage(out);
	        Res_rec.send(message);
	        System.out.println(message);
		}
		while (x==0);
		session.close();
		connection.close();
	}
	catch(Exception e)
	{
		System.out.println(e);
	        
	   
		}
	}
}
	
