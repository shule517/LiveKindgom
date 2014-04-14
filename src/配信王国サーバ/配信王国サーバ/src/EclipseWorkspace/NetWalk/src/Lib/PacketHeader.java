package Lib;

public enum PacketHeader
{
	None(0),

	// ���O�C��
	PacketLoginAuth(1),			// ���O�C��
	PacketLoginResult(2),		// ���O�C������

	// �Ȃ����w�b�_�[10�Ԃ͎g���Ȃ�
	
	// �`���b�g
	PacketChat(11),				// �`���b�g
	PacketChatPersonal(12),		// �l���`���b�g
	PacketLiveChat(13),			// �z�M�p�`���b�g
	
	// �L�����N�^�[
	PacketAddCharactor(20),		// �L�����ǉ�
	PacketRemoveCharactor(21),	// �L�����폜
	PacketMoveCharactor(22),	// �L�����ړ�
	PacketOxOx(23),				// ��������
	
	// �z�M
	PacketLiveStart(30),		// �z�M�J�n
	PacketLiveStop(31),			// �z�M�I��
	
	// ����
	PacketListenStart(40),		// �����J�n
	PacketListenStop(41);		// �����I��
	
	/*	
	PacketGetMap(40),
	
	PAcketGetFriend(50);
	*/
	
	
	
	private int value;
	
	private PacketHeader(int n)
	{
		this.value = n;
	}
	
	// enum�萔���琮���֕ϊ�
    public int toInt()
	{
        return value;
    }
	
    // ���l����enum�萔�֕ϊ�
    public static PacketHeader toEnum(final int anIntValue)
	{
        for (PacketHeader d : values())
        {
            if (d.toInt() == anIntValue)
            {
                return d;
            }
        }
        return null;
    }
}
