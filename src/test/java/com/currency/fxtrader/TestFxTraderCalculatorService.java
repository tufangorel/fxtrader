package com.currency.fxtrader;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import com.currency.fxtrader.entity.Currency;
import com.currency.fxtrader.entity.Instruction;
import com.currency.fxtrader.entity.OrderType;
import com.currency.fxtrader.service.FxTraderCalculatorService;

import junit.framework.TestCase;

public class TestFxTraderCalculatorService extends TestCase {

	public void testCalculateAmountOfTrade_Foo() {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate instructionDate1 = LocalDate.parse( "01/01/2016", formatter );
		LocalDate settlementDate1 = LocalDate.parse( "02/01/2016", formatter );
				
		Instruction instruction1 = new Instruction( "foo", OrderType.BUY, BigDecimal.valueOf( 0.5 ), Currency.SGP,
													instructionDate1, settlementDate1, 200, BigDecimal.valueOf(100.25) );
		
		FxTraderCalculatorService fxTraderCalculatorService = new FxTraderCalculatorService();
		BigDecimal result = fxTraderCalculatorService.calculate(instruction1);
		
		assertTrue( result.doubleValue() == 10025 );
	}
	
	public void testCalculateAmountOfTrade_Bar() {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate instructionDate2 = LocalDate.parse( "05/01/2016", formatter );
		LocalDate settlementDate2 = LocalDate.parse( "07/01/2016", formatter );
		
		Instruction instruction2 = new Instruction( "bar", OrderType.SELL, BigDecimal.valueOf( 0.22 ), Currency.AED,
													instructionDate2, settlementDate2, 450, BigDecimal.valueOf(150.5) );
		
		FxTraderCalculatorService fxTraderCalculatorService = new FxTraderCalculatorService();
		BigDecimal result = fxTraderCalculatorService.calculate(instruction2);
		
		assertTrue( result.doubleValue() == 14899.5 );
	}
	
	public void testCalculateAmountOfTrade_AllItems_By_OrderType_BUY_SettlementDate_Today_Sunday() {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		LocalDate instructionDate1 = LocalDate.parse( "05/10/2018", formatter );
		LocalDate settlementDate1 = LocalDate.parse( "07/10/2018", formatter );
				
		Instruction instruction1 = new Instruction( "foo", OrderType.BUY, BigDecimal.valueOf( 0.5 ), Currency.SGP,
													instructionDate1, settlementDate1, 200, BigDecimal.valueOf(100.25) );
		
		List<Instruction> instructions = Arrays.asList( instruction1 );
		
		LocalDate today = LocalDate.of(2018, Month.OCTOBER, 8);
		FxTraderCalculatorService fxTraderCalculatorService = new FxTraderCalculatorService();
		BigDecimal result = fxTraderCalculatorService.calculate( instructions, OrderType.BUY, today );
		assertTrue( result.doubleValue() == 0 );
	}
	
	public void testCalculateAmountOfTrade_AllItems_By_OrderType_SELL_SettlementDate_Today_Sunday() {
	
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		LocalDate instructionDate2 = LocalDate.parse( "05/10/2018", formatter );
		LocalDate settlementDate2 = LocalDate.parse( "07/10/2018", formatter );
		
		Instruction instruction2 = new Instruction( "bar", OrderType.SELL, BigDecimal.valueOf( 0.22 ), Currency.AED,
													instructionDate2, settlementDate2, 450, BigDecimal.valueOf(150.5) );
		
		List<Instruction> instructions = Arrays.asList( instruction2 );
		
		LocalDate today = LocalDate.of(2018, Month.OCTOBER, 7);
		FxTraderCalculatorService fxTraderCalculatorService = new FxTraderCalculatorService();
		BigDecimal result = fxTraderCalculatorService.calculate( instructions, OrderType.SELL, today );
		
		assertTrue( result.doubleValue() == (14899.5) );
		
	}
	
	public void testCalculateAmountOfTrade_AllItems_By_OrderType_SELL_SettlementDate_Today_Saturday() {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		LocalDate instructionDate2 = LocalDate.parse( "05/10/2018", formatter );
		LocalDate settlementDate2 = LocalDate.parse( "06/10/2018", formatter );
		
		Instruction instruction2 = new Instruction( "bar", OrderType.SELL, BigDecimal.valueOf( 0.22 ), Currency.AED,
													instructionDate2, settlementDate2, 450, BigDecimal.valueOf(150.5) );
		
		List<Instruction> instructions = Arrays.asList( instruction2 );
		LocalDate today = LocalDate.of(2018, Month.OCTOBER, 6);
		FxTraderCalculatorService fxTraderCalculatorService = new FxTraderCalculatorService();
		BigDecimal result = fxTraderCalculatorService.calculate( instructions, OrderType.SELL, today );
		
		assertTrue( result.doubleValue() == (0) );
	}
	
	public void testCalculateAmountOfTrade_AllItems_By_OrderType_SELL_SettlementDate_Today_Monday() {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		LocalDate instructionDate2 = LocalDate.parse( "05/10/2018", formatter );
		LocalDate settlementDate2 = LocalDate.parse( "08/10/2018", formatter );   // SettlementDate is Monday
		
		Instruction instruction2 = new Instruction( "bar", OrderType.SELL, BigDecimal.valueOf( 0.22 ), Currency.AED,
													instructionDate2, settlementDate2, 450, BigDecimal.valueOf(150.5) );
		
		List<Instruction> instructions = Arrays.asList( instruction2 );
		LocalDate today = LocalDate.of(2018, Month.OCTOBER, 8);  // Today is Monday
		FxTraderCalculatorService fxTraderCalculatorService = new FxTraderCalculatorService();
		BigDecimal result = fxTraderCalculatorService.calculate( instructions, OrderType.SELL, today );
		
		assertTrue( result.doubleValue() == (14899.5) );
	}
	
	public void testCalculateAmountOfTrade_AllItems_By_OrderType_SELL_SettlementDate_Today_Friday() {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		LocalDate instructionDate2 = LocalDate.parse( "05/10/2018", formatter );
		LocalDate settlementDate2 = LocalDate.parse( "12/10/2018", formatter );   // SettlementDate is Friday
		
		Instruction instruction2 = new Instruction( "bar", OrderType.SELL, BigDecimal.valueOf( 0.22 ), Currency.AED,
													instructionDate2, settlementDate2, 450, BigDecimal.valueOf(150.5) );
		
		List<Instruction> instructions = Arrays.asList( instruction2 );
		LocalDate today = LocalDate.of(2018, Month.OCTOBER, 12);  // Today is Friday
		FxTraderCalculatorService fxTraderCalculatorService = new FxTraderCalculatorService();
		BigDecimal result = fxTraderCalculatorService.calculate( instructions, OrderType.SELL, today );
		
		assertTrue( result.doubleValue() == (0) );
	}

	public void testCalculateAmountOfTrade_AllItems_By_OrderType_SELL_SettlementDate_Today_Friday_Multiple_Currencies() {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		LocalDate instructionDate1 = LocalDate.parse( "05/10/2018", formatter );
		LocalDate settlementDate1 = LocalDate.parse( "12/10/2018", formatter );  // SettlementDate is Friday
				
		Instruction instruction1 = new Instruction( "foo", OrderType.BUY, BigDecimal.valueOf( 0.5 ), Currency.SGP,
													instructionDate1, settlementDate1, 200, BigDecimal.valueOf(100.25) );
		
		LocalDate instructionDate2 = LocalDate.parse( "05/10/2018", formatter );
		LocalDate settlementDate2 = LocalDate.parse( "12/10/2018", formatter );   // SettlementDate is Friday
		
		Instruction instruction2 = new Instruction( "bar", OrderType.SELL, BigDecimal.valueOf( 0.22 ), Currency.AED,
													instructionDate2, settlementDate2, 450, BigDecimal.valueOf(150.5) );
		
		List<Instruction> instructions = Arrays.asList( instruction1, instruction2 );
		LocalDate today = LocalDate.of(2018, Month.OCTOBER, 12);  // Today is Friday
		FxTraderCalculatorService fxTraderCalculatorService = new FxTraderCalculatorService();
		BigDecimal result = fxTraderCalculatorService.calculate( instructions, OrderType.BUY, today );
		
		assertTrue( result.doubleValue() == (10025) );
	}
}