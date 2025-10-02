package com.crypto.service;

import com.crypto.model.Enum.Priority;
import com.crypto.model.Enum.WalletType;

public class FeeCalculatorService {

	public double calculateFee(WalletType type, Priority priority) {
		switch (type) {
		case BITCOIN:
			return calculateBitcoinFee(priority);
		case ETHEREUM:
			return calculateEthereumFee(priority);
		default:
			throw new IllegalArgumentException("Type de wallet non supporté.");
		}
	}

	private double calculateBitcoinFee(Priority priority) {
		int tailleTxBytes = 250;
		int satPerByte;
		switch (priority) {
		case ECONOMIQUE:
			satPerByte = 20;
			break;
		case STANDARD:
			satPerByte = 50;
			break;
		case RAPIDE:
			satPerByte = 80;
			break;
		default:
			satPerByte = 50;
		}
		double totalSatoshi = tailleTxBytes * satPerByte;
		return totalSatoshi * 0.00000001; // Converti en BTC
	}

	private double calculateEthereumFee(Priority priority) {
		int gasLimit = 21000;
		int gasPriceGwei;
		switch (priority) {
		case ECONOMIQUE:
			gasPriceGwei = 20;
			break;
		case STANDARD:
			gasPriceGwei = 50;
			break;
		case RAPIDE:
			gasPriceGwei = 100;
			break;
		default:
			gasPriceGwei = 50;
		}
		return gasLimit * gasPriceGwei * 0.000000001; // En ETH
	}
}
