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
package eu.derbed.openmu.cs;

import static com.notbed.muonline.util.UPacket.logTransfer;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.derbed.openmu.cs.codec.builder.GSServerEntryBuilder;
import eu.derbed.openmu.cs.codec.builder.GSServersListBuilder;
import eu.derbed.openmu.cs.codec.builder.HelloClientBuilder;
import eu.derbed.openmu.cs.codec.data.GSSerersList;
import eu.derbed.openmu.cs.codec.data.HelloClientData;
import eu.derbed.openmu.cs.codec.data.ServerEntry;
import eu.derbed.openmu.netty.abstracts.AbstractMuPackageData;

public class CSProtocolEncoder extends OneToOneEncoder {
	private final Logger log = LoggerFactory.getLogger(getClass());

	private final HelloClientBuilder helloClient = new HelloClientBuilder();
	private final GSServersListBuilder gsSererList;
	private final GSServerEntryBuilder entryBuilder = new GSServerEntryBuilder();

	/**
	 * @param serverList
	 */
	public CSProtocolEncoder(final ServerList serverList) {
		gsSererList = new GSServersListBuilder(serverList);
	}

	/* (non-Javadoc)
	 * @see org.jboss.netty.handler.codec.oneone.OneToOneEncoder#encode(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.Channel, java.lang.Object)
	 */
	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel,
			Object msg) throws Exception {
		if (!(msg instanceof AbstractMuPackageData)) {
			System.out.println("kajsklasj "+msg.toString());
			return msg;
		}
		ChannelBuffer out = ChannelBuffers.dynamicBuffer(4);
		AbstractMuPackageData mesage = (AbstractMuPackageData) msg;
		switch (mesage.getMessageID()) {
		case 0x00://hello CL
			helloClient.Build(0, (HelloClientData) mesage,out);
			break;
		case 0xf402: // server list ansfer
			gsSererList.Build(0, (GSSerersList) mesage, out);
			break;
		case 0xf403: // chosed server data
			entryBuilder.Build(0, (ServerEntry) mesage, out);
			break;
		default:
			return msg;
		}

		logTransfer(log, out.array());
		//return ChannelBuffers.EMPTY_BUFFER;

		return out;
	}

}
