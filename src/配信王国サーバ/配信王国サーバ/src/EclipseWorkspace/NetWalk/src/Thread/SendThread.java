package Thread;

import java.io.PrintWriter;
import java.util.concurrent.ConcurrentLinkedQueue;
import Frame.Frame;
import Lib.PacketCreater;

public class SendThread extends Thread
{
	/******************************
	 * �����o�ϐ�
	*******************************/
	private ConcurrentLinkedQueue<PacketCreater> queue = null;
	private PrintWriter out = null;		// �o�̓X�g���[��
	private boolean isAlive = true;

	/******************************
	 * �R���X�g���N�^
	 * @param out 
	*******************************/
	public SendThread(PrintWriter out)
	{
		this.out = out;
		queue = new ConcurrentLinkedQueue<PacketCreater>();
		
		start();
	}

	/******************************
	 * �p�P�b�g��ǉ�
	*******************************/
	public void addPacket(PacketCreater pc)
	{
		queue.add(pc);
	}
	
	/******************************
	 * ���C�����[�v
	*******************************/
	public void run()
	{
		while (isAlive)
		{
			if (queue.size() > 0)
			{
				PacketCreater pc = queue.poll();

				try
				{
					String packet = pc.createPacket();
					Frame.appendDebugText("���M(" + packet + ") out(" + out + ")");
					out.write(packet);
					out.flush();
				}
				catch (Exception e)
				{
					System.out.println(e.toString());
				}
			}
			
			// Sleep
			try
			{
				Thread.sleep(1);
			}
			catch (InterruptedException e)
			{
				Frame.appendDebugText("SendThread.run : " + e.getMessage());
			}
		}
	}

	/******************************
	 * �I������
	*******************************/
	public void close()
	{
		isAlive = false;
	}
}
