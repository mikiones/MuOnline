package com.notbed.muonline.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Alexandru Bledea
 * @since Jul 8, 2014
 */
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
