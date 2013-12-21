  /*
    * Copyright [mikiones] [Michal Kinasiewicz]
    * Licensed under the Apache License, Version 2.0 (the "License");
    * you may not use this file except in compliance with the License.
    * You may obtain a copy of the License at
    *
    *   http://www.apache.org/licenses/LICENSE-2.0
    *
    * Unless required by applicable law or agreed to in writing, software
    * distributed under the License is distributed on an "AS IS" BASIS,
    * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    * See the License for the specific language governing permissions and
    * limitations under the License.
    */
package eu.derbed.openmu.netty.abstracts;

import static eu.derbed.openmu.utils.UPacket.logTransfer;

import org.jboss.netty.buffer.ChannelBuffer;

import eu.derbed.util.LoggableObject;

/**
 *
 * @author mikiones
 * The representation of mu message
 * after reived bufor and fragmented it into each package piece is wraped into this class
 * the buffor have 2 states:
 * To_DERYPT need to be decrypted
 * READY     data ready to move on next layer (decoding)
 */
public class MuBaseMessage extends LoggableObject {

    public static final String[] StrStatus={"To Decrypt" , "Ready"};
    public static final byte To_DECRYPT = 0x00;
    public static final byte READY = 0x01;
    /**
     * Message
     */
    public short messageID;
    public ChannelBuffer message;
    /**
     * Status of message
     */
    public byte status;

    @Override
	@Deprecated
    public String toString() {
//    	AAA what to do here?
		logTransfer(log, message.array());
		return "Message [status:" + StrStatus[status] + "] ";
    }
}
