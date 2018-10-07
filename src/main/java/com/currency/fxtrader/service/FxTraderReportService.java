package com.currency.fxtrader.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.currency.fxtrader.entity.Instruction;
import com.currency.fxtrader.entity.OrderType;

public class FxTraderReportService {

	public void generateReportByOrderType( List<Instruction> instructions, OrderType orderType, LocalDate today ) {
		
		FxTraderCalculatorService fxTraderCalculatorService = new FxTraderCalculatorService();
		BigDecimal result = fxTraderCalculatorService.calculate( instructions, orderType, today );
		System.out.println("Amount in USD settled "+orderType+" "+today+" : "+result);
	}
	
	public void generateReportByAmount( List<Instruction> instructions, LocalDate today ) {
		
		FxTraderCalculatorService fxTraderCalculatorService = new FxTraderCalculatorService();
		
		List<Instruction> incomingOrderTypeInstructions = instructions.stream()
		        .filter( i -> i.getOrderType().equals(OrderType.SELL))
		        .collect(Collectors.toList());

		List<Instruction> outgoingOrderTypeInstructions = instructions.stream()
		        .filter( i -> i.getOrderType().equals(OrderType.BUY))
		        .collect(Collectors.toList());
		
		Map<Instruction, BigDecimal> rankedIncomingOrderTypeInstructions = fxTraderCalculatorService.rank( incomingOrderTypeInstructions, OrderType.SELL, today );
		System.out.println("Incoming Order Instruction Rank");
		rankedIncomingOrderTypeInstructions.entrySet().stream()
	    	.forEach(e -> System.out.println(e.getKey().getEntityName() + " : " + e.getValue()));
		
		Map<Instruction, BigDecimal> rankedOutgoingOrderTypeInstructions = fxTraderCalculatorService.rank( outgoingOrderTypeInstructions, OrderType.BUY, today );
		System.out.println("Outgoing Order Instruction Rank");
		rankedOutgoingOrderTypeInstructions.entrySet().stream()
	    	.forEach(e -> System.out.println(e.getKey().getEntityName() + " : " + e.getValue()));
		
	}
	
}