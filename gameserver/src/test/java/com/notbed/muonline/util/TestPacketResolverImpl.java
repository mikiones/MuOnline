package com.notbed.muonline.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Alexandru Bledea
 * @since Jul 31, 2014
 */
@SuppressWarnings("static-method")
public class TestPacketResolverImpl {

	/**
	 * @throws RegistrationConflictException
	 */
	@Test
	public void testFailRegistration() throws RegistrationException {
		final PacketResolverImpl<Packet> resolver = new PacketResolverImpl<>(Packet.class);

		final Packet problem1 = new Packet_0x5B_0x59_0x0();
		final Packet problem2 = new Packet_0x5B_0x60();
		final Collection<Object> reason = Arrays.<Object> asList(problem1, problem2);

		final Packet failure = new Packet_0x5B();

		resolver.register(problem1);
		resolver.register(problem2);
		try {
			resolver.register(failure);
			Assert.fail("Should have thrown an error!");
		} catch (final RegistrationConflictException ex) {
			Assert.assertSame(failure, ex.getFailedObject());
			assertSameElements(reason, ex.getReasons());
		}
	}

	/**
	 * @throws RegistrationConflictException
	 */
	@Test
	public void testFailRegistration2() throws RegistrationException {
		final PacketResolverImpl<Packet> resolver = new PacketResolverImpl<>(Packet.class);

		final Packet reason = new Packet_0x5B();
		final Packet failure = new Packet_0x5B_0x59_0x0();

		resolver.register(reason);
		try {
			resolver.register(failure);
			Assert.fail("Should have thrown an error!");
		} catch (final RegistrationConflictException ex) {
			Assert.assertSame(failure, ex.getFailedObject());
			assertSameElements(Collections.<Object> singleton(reason), ex.getReasons());
		}
	}

	/**
	 * @throws RegistrationException
	 */
	@Test (expected = RegistrationException.class)
	public void testFailNoHeader() throws RegistrationException {
		final PacketResolverImpl<Packet> resolver = new PacketResolverImpl<>(Packet.class);

		final Packet reason = new Packet();
		resolver.register(reason);
		Assert.fail("Should have thrown an error!");
	}

	/**
	 * @throws RegistrationException
	 */
	@Test (expected = RegistrationException.class)
	public void testFailEmptyHeader() throws RegistrationException {
		final PacketResolverImpl<Packet> resolver = new PacketResolverImpl<>(Packet.class);

		final Packet reason = new PacketEmptyHeader();
		resolver.register(reason);
		Assert.fail("Should have thrown an error!");
	}

	/**
	 * @throws RegistrationException
	 */
	@Test
	public void testResolveHeader() throws RegistrationException {
		final PacketResolverImpl<Packet> resolver = new PacketResolverImpl<>(Packet.class);

		final Packet p5b60 = new Packet_0x5B_0x60();
		final Packet p5b590 = new Packet_0x5B_0x59_0x0();
		final Packet_0xC pc = new Packet_0xC();

		resolver.register(p5b60);
		resolver.register(p5b590);
		resolver.register(pc);

		final Packet nothing = resolver.resolvePacket(new byte[] {0x0, 0x5});
		Assert.assertNull(nothing);

		final Packet packet = resolver.resolvePacket(new byte[] {0x5B, 0x60, 0x55});
		Assert.assertSame(p5b60, packet);
	}

	/**
	 * @param expected
	 * @param actual
	 */
	private static <E> void assertSameElements(final Collection<E> expected, final Collection<E> actual) {
		final Set<E> c1a = new HashSet<>(expected);
		final Set<E> c2a = new HashSet<>(actual);
		Assert.assertEquals(c1a.size(), c2a.size());
		Assert.assertEquals(c1a, c2a);
	}

}
