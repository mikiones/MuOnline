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


import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.derbed.openmu.cs.codec.data.GSSerersList;
import eu.derbed.openmu.cs.codec.data.HelloClientData;
import eu.derbed.openmu.cs.codec.data.ServerEntry;
import eu.derbed.openmu.netty.abstracts.MuBaseMessage;

public class CSSesionHandler extends SimpleChannelHandler {

	private static final Logger log = LoggerFactory.getLogger(CSSesionHandler.class);

	private final ServerList serverList;
	
	/**
	 * @param serverList
	 */
	public CSSesionHandler(final ServerList serverList) {
		this.serverList = serverList;
	}

	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.SimpleChannelHandler#writeRequested(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.MessageEvent)
	 */
	@Override
	public void writeRequested(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		System.out.println(e.getMessage().toString());
		super.writeRequested(ctx, e);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jboss.netty.channel.SimpleChannelUpstreamHandler#channelConnected
	 * (org.jboss.netty.channel.ChannelHandlerContext,
	 * org.jboss.netty.channel.ChannelStateEvent)
	 */
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		e.getChannel().write(new HelloClientData());

	}
	

	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.SimpleChannelUpstreamHandler#handleUpstream(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelEvent)
	 */
	@Override
	public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e)
			throws Exception {
	super.handleUpstream(ctx, e);
	//ctx.sendUpstream(e);
	}


	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		
		MuBaseMessage m = (MuBaseMessage) e.getMessage();
		log.info(m.toString());
		 int head= m.message.readUnsignedByte();
		 short size =(short) (((head==0xc1)||(head==0xc3))? m.message.readByte():m.message.readUnsignedShort());
		switch (m.message.readUnsignedByte()) {
		case 0xf4: {
			
			 switch(m.message.readUnsignedByte())
			 {
			 case 0x02:e.getChannel().write(new GSSerersList());break;
			 case 0x03:{
				 ServerEntry s =serverList.get(m.message.getUnsignedByte(4), m.message.getUnsignedByte(5));
				 e.getChannel().write(s);}break;
			 
			 }
			 }
			break;
		}
		
	}
}
