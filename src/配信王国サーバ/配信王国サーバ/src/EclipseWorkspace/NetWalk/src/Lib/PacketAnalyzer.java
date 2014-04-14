package Lib;

import Frame.Frame;

public class PacketAnalyzer
{
	/******************************
	 * �����o�ϐ�
	*******************************/
	public PacketHeader header = PacketHeader.toEnum(0);
	private String packet = "";
	private int index = 0;

	/******************************
	 * �R���X�g���N�^
	*******************************/
	public PacketAnalyzer(String packet)
	{
		this.packet = packet;
		
		analyze();
	}

	/******************************
	 * �f�[�^�iInt�j���擾
	*******************************/
	public int getIntData()
	{
		// (int)�f�[�^�T�C�Y | (StrInt)���l�f�[�^

		int num = 0;
		int dataSize = getDataInt();
		String strNum = getDataStr(dataSize);

		try
		{
			num = Integer.parseInt(strNum);
		}
		catch (Exception e)
		{
			System.out.println("getPacketInt : " + e.toString());
		}
		
		return num;
	}

	/******************************
	 * �f�[�^�iStr�j���擾
	*******************************/
	public String getStrData()
	{
		// (int)�f�[�^�T�C�Y�̒��� | (StrInt)�f�[�^�T�C�Y | (str)�����f�[�^

		int dataSizeLength = getDataInt();
		int dataSize = getDataInt(dataSizeLength);
		
		return getDataStr(dataSize);
	}
	
	/******************************
	 * ����
	*******************************/
	private void analyze()
	{
		// �p�P�b�g�T�C�Y�̒������擾
		int packetSizeLength = getDataInt();
		//Frame.appendDebugText("packetSizeLength : " + packetSizeLength);

		// �p�P�b�g�T�C�Y���擾
		int packetSize = getDataInt(packetSizeLength);
		//Frame.appendDebugText("packetSize : " + packetSize);

		// ���ۂɎ擾�����p�P�b�g�̃T�C�Y
		int realPacketLength = packet.length() - index - 1;
		//Frame.appendDebugText("realPacketLength : " + realPacketLength);
		
		// �f�[�^�T�C�Y����v
		if (realPacketLength == packetSize)
		{
			//Frame.appendDebugText("�G���h�}�[�J��v");
		}
		// �f�[�^������
		else if (realPacketLength > packetSize)
		{
			// ��������
			Frame.appendDebugText("PacketAnalyzer.analyze : �f�[�^�������i�G���h�}�[�J�s��v�j");
		}
		// �f�[�^�����Ȃ�
		else if (realPacketLength < packetSize)
		{
			Frame.appendDebugText("PacketAnalyzer.analyze : �f�[�^�����Ȃ��i�G���h�}�[�J�s��v�j");
			//throw new Exception("�p�P�b�g�̃T�C�Y�����Ȃ��ł�");
		}
		
		// �w�b�_�[���擾
		int headerNo = getDataInt();
		//Frame.appendDebugText("headerNo : " + headerNo);
		header = PacketHeader.toEnum(headerNo);
		//Frame.appendDebugText("header : " + header);
	}

	/******************************
	 * ������f�[�^���擾(size byte)
	*******************************/
	private String getDataStr(int size)
	{
		String str = "";
		
		for (int i = 0; i < size; i++)
		{
			str += (char)packet.charAt(index);
			index++;
		}
		
		return str;
	}
	
	/******************************
	 * ���l�f�[�^���擾(1byte)
	*******************************/
	private int getDataInt()
	{
		char n = 0;
		
		try
		{
			n = packet.charAt(index);
			index++;
		}
		catch (Exception e)
		{
			Frame.appendDebugText("PacketAnalyzer.getDataInt : " + e.getMessage());
		}
		
		return n;
	}
	
	/******************************
	 * ���l�f�[�^���擾(size byte)
	*******************************/
	private int getDataInt(int size)
	{
		String str = "";
		int num = 0;
		
		try
		{
			for (int i = 0; i < size; i++)
			{
				str += (char)packet.charAt(index);
				index++;
			}
		
			num = Integer.parseInt(str);
		}
		catch (Exception e)
		{
			Frame.appendDebugText("PacketAnalyzer.getDataInt : " + e.getMessage());
		}
		
		return num;
	}
}
