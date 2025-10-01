package com.crypto.utils;

import java.util.UUID;

public class IdGenerator {

	/**
	 * génère un nouvel identifiant unique
	 */
	private IdGenerator () {
		throw new UnsupportedOperationException("instanciation interdite");
	}
	public static UUID generateUUID() {
		return UUID.randomUUID();
	}
}
