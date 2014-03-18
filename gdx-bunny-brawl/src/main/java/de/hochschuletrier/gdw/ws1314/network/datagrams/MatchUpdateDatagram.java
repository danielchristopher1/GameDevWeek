package de.hochschuletrier.gdw.ws1314.network.datagrams;

import de.hochschuletrier.gdw.commons.netcode.NetConnection;
import de.hochschuletrier.gdw.commons.netcode.datagram.INetDatagram;
import de.hochschuletrier.gdw.commons.netcode.message.INetMessageIn;
import de.hochschuletrier.gdw.commons.netcode.message.INetMessageOut;
import de.hochschuletrier.gdw.ws1314.network.DatagramHandler;

/**
 * Created by albsi on 17.03.14.
 */
public class MatchUpdateDatagram extends BaseDatagram{

    public MatchUpdateDatagram(byte type, short id, short param1, short param2) {
        super(MessageType.DELTA, type, id, param1, param2);
    }
    public static final byte MATCH_UPDATE_DATAGRAM = INetDatagram.Type.FIRST_CUSTOM + 0x11;

    @Override
    public void handle (DatagramHandler handler, NetConnection connection) {
        handler.handle (this, connection);
    }

    @Override
    public void writeToMessage (INetMessageOut message) {

    }

    @Override
    public void readFromMessage (INetMessageIn message) {

    }
}