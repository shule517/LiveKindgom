package Lib;
import Lib.PacketHeader;

public class PacketCreater
{
	/******************************
	 * �����o�ϐ�
	*******************************/
	private PacketHeader header = PacketHeader.None;
	private String packetData = "";

	/******************************
	 * �R���X�g���N�^
	*******************************/
	public PacketCreater(PacketHeader header)
	{
		// �w�b�_�[
		this.header = header;
	}

	/******************************
	 * ���l�f�[�^��ǉ�
	*******************************/
	public void addIntData(int num)
	{
		// (int)�f�[�^�T�C�Y | (StrInt)���l�f�[�^
		String numStr = "" + num;
		char dataSize = (char)numStr.length();

		packetData += dataSize + numStr;
	}

	/******************************
	 * �����f�[�^��ǉ�
	*******************************/
	public void addStrData(String str)
	{
		// (int)�f�[�^�T�C�Y�̒��� | (StrInt)�f�[�^�T�C�Y | (str)�����f�[�^
		int dataSize = str.length();
		String dataSizeStr = "" + dataSize;
		char dataSizeLength = (char)dataSizeStr.length();
		
		packetData += dataSizeLength + dataSizeStr + str;
	}

	/******************************
	 * �p�P�b�g�쐬
	*******************************/
	public String createPacket()
	{
		// �u�p�P�b�g�T�C�Y�̒����v(1byte) + �u�p�P�b�g�T�C�Y�v + �u�w�b�_�[�v(1byte) + �u�f�[�^�T�C�Y�v
		
		int packetSize = packetData.length();
		String packetSizeStr = "" + packetSize;
		char packetSizeLength = (char)packetSizeStr.length();
		char headerByte = (char)header.toInt();

		return new String(packetSizeLength + packetSizeStr + headerByte + packetData);
	}
}

/*
public class PacketCreater
{
	/******************************
	 * �����o�ϐ�
	*******************************
	private PacketHeader header = PacketHeader.None;
	private List<byte[]> dataList = new ArrayList<byte[]>();
	
	/******************************
	 * �R���X�g���N�^
	*******************************
	public PacketCreater(PacketHeader header)
	{
		// �w�b�_�[
		this.header = header;
	}

	/******************************
	 * ���l�f�[�^��ǉ�
	*******************************
	public void addIntData(int num)
	{
		// (int)�f�[�^�T�C�Y | (StrInt)���l�f�[�^
		String numStr = "" + num;
		byte numBytes[] = numStr.getBytes();
		int size = numBytes.length;
		byte packetBytes[] = new byte[size + 1];
		
		// �f�[�^�T�C�Y
		packetBytes[0] = (byte)size;

		// �f�[�^
		for (int i = 0; i < numBytes.length; i++)
		{
			packetBytes[i + 1] = numBytes[i];
		}
		
		// �f�[�^�ǉ�
		dataList.add(packetBytes);
	}

	/******************************
	 * �����f�[�^��ǉ�
	*******************************
	public void addStrData(String str)
	{
		// (int)�f�[�^�T�C�Y�̒��� | (StrInt)�f�[�^�T�C�Y | (str)�����f�[�^
		
		
		int size = str.length();
		String sizeStr = "" + size;
		
		String packetData = size + str;
		
		
		byte packetDataByte[] = packetData.getBytes();
		
		byte packetBytes[] = new byte[packetDataByte.length + 1];
		
		// �f�[�^�T�C�Y�̒���
		packetBytes[0] = (byte)sizeStr.length();
		
		// �f�[�^�T�C�Y
		for (int i = 0; i < packetDataByte.length; i++)
		{
			packetBytes[i + 1] = packetDataByte[i];
		}
		
		dataList.add(packetDataByte);
		
		/*
		byte strBytes[] = str.getBytes();
		int size = strBytes.length;
		String sizeStr = "" + size;
		byte sizeBytes[] = sizeStr.getBytes();
		byte packetBytes[] = new byte[size + sizeBytes.length + 1];
		
		// �f�[�^�T�C�Y�̒���
		packetBytes[0] = (byte)sizeBytes.length;
		
		// �f�[�^�T�C�Y
		for (int i = 0; i < sizeBytes.length; i++)
		{
			packetBytes[i + 1] = sizeBytes[i];
		}
		
		// �f�[�^
		for (int i = 0; i < strBytes.length; i++)
		{
			packetBytes[i + 1 + sizeBytes.length] = strBytes[i];
		}
		
		// �f�[�^�ǉ�
		dataList.add(packetBytes);
		*
	}

	/******************************
	 * �p�P�b�g�쐬
	*******************************
	public String createPacket()
	{
		int dataSize = 0;
	
		// �f�[�^�̒�����ǉ�
		for (int i = 0; i < dataList.size(); i++)
		{
			byte[] bytes = (dataList.get(i));
			dataSize += bytes.length;
		}
		
		String dataSizeStr = "" + dataSize;
		byte[] dataSizeBytes = dataSizeStr.getBytes();
		int packetSizeLength = dataSizeBytes.length; // �u�p�P�b�g�T�C�Y�̒����v��ǉ�
		
		// �u�p�P�b�g�T�C�Y�̒����v(1byte) + �u�p�P�b�g�T�C�Y�v + �u�w�b�_�[�v(1byte) + �u�f�[�^�T�C�Y�v
		byte packetBytes[] = new byte[1 + packetSizeLength + 1 + dataSize];
	
		int index = 0;
		
		// �p�P�b�g�T�C�Y�̒���
		packetBytes[index] = (byte)packetSizeLength;
		index++;
		
		// �p�P�b�g�T�C�Y
		for (int i = 0; i < dataSizeBytes.length; i++)
		{
			packetBytes[index] = dataSizeBytes[i];
			index++;
		}
		
		// �w�b�_
		packetBytes[index] = (byte)header.toInt();
		index++;
		
		// �f�[�^
		for (int i = 0; i < dataList.size(); i++)
		{
			byte[] bytes = (dataList.get(i));
			for (int j = 0; j < bytes.length; j++)
			{
				packetBytes[index] = (byte)bytes[j];
				index++;
			}
		}
		
		return new String(packetBytes);
	}
}
*/