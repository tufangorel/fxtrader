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
import com.currency.fxtrader.service.FxTraderReportService;

import junit.framework.TestCase;

public class TestFxTraderReportService extends TestCase {

	public void testGenerateReport_AllItems_OrderType_BUY_SettlementDate_Today_Friday_Multiple_Currencies() {
		
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
		FxTraderReportService fxTraderReportService = new FxTraderReportService();
		fxTraderReportService.generateReportByOrderType(instructions, OrderType.BUY, today);
	}
	
	public void testGenerateReport_AllItems_OrderType_SELL_SettlementDate_Today_Friday_Multiple_Currencies() {
		
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
		FxTraderReportService fxTraderReportService = new FxTraderReportService();
		fxTraderReportService.generateReportByOrderType(instructions, OrderType.BUY, today);
	}
	
	public void testGenerateReportByAmount_AllItems_SettlementDate_Today_Friday_Multiple_Currencies() {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		LocalDate instructionDate1 = LocalDate.parse( "05/10/2018", formatter );
		LocalDate settlementDate1 = LocalDate.parse( "12/10/2018", formatter );  // SettlementDate is Friday
		Instruction instruction1 = new Instruction( "foo", OrderType.BUY, BigDecimal.valueOf( 0.5 ), Currency.SGP,
				instructionDate1, settlementDate1, 200, BigDecimal.valueOf(100.25) );
		
		LocalDate instructionDate2 = LocalDate.parse( "05/10/2018", formatter );
		LocalDate settlementDate2 = LocalDate.parse( "12/10/2018", formatter );  // SettlementDate is Friday
		Instruction instruction2 = new Instruction( "foo2", OrderType.BUY, BigDecimal.valueOf( 0.5 ), Currency.SGP,
				instructionDate2, settlementDate2, 300, BigDecimal.valueOf(100.25) );
		
		LocalDate instructionDate3 = LocalDate.parse( "05/10/2018", formatter );
		LocalDate settlementDate3 = LocalDate.parse( "12/10/2018", formatter );  // SettlementDate is Friday
		Instruction instruction3 = new Instruction( "bar", OrderType.BUY, BigDecimal.valueOf( 0.5 ), Currency.SGP,
				instructionDate3, settlementDate3, 400, BigDecimal.valueOf(100.25) );
		
		LocalDate instructionDate4 = LocalDate.parse( "05/10/2018", formatter );
		LocalDate settlementDate4 = LocalDate.parse( "12/10/2018", formatter );  // SettlementDate is Friday
		Instruction instruction4 = new Instruction( "bar2", OrderType.BUY, BigDecimal.valueOf( 0.5 ), Currency.SGP,
				instructionDate4, settlementDate4, 500, BigDecimal.valueOf(100.25) );
		
		LocalDate instructionDate5 = LocalDate.parse( "05/10/2018", formatter );
		LocalDate settlementDate5 = LocalDate.parse( "12/10/2018", formatter );  // SettlementDate is Friday
		Instruction instruction5 = new Instruction( "seller1", OrderType.SELL, BigDecimal.valueOf( 0.5 ), Currency.SGP,
				instructionDate5, settlementDate5, 200, BigDecimal.valueOf(100.25) );
		
		LocalDate instructionDate6 = LocalDate.parse( "05/10/2018", formatter );
		LocalDate settlementDate6 = LocalDate.parse( "12/10/2018", formatter );  // SettlementDate is Friday
		Instruction instruction6 = new Instruction( "seller2", OrderType.SELL, BigDecimal.valueOf( 0.5 ), Currency.SGP,
				instructionDate6, settlementDate6, 300, BigDecimal.valueOf(100.25) );
		
		LocalDate instructionDate7 = LocalDate.parse( "05/10/2018", formatter );
		LocalDate settlementDate7 = LocalDate.parse( "12/10/2018", formatter );  // SettlementDate is Friday
		Instruction instruction7 = new Instruction( "seller3", OrderType.SELL, BigDecimal.valueOf( 0.5 ), Currency.SGP,
				instructionDate7, settlementDate7, 400, BigDecimal.valueOf(100.25) );
		
		List<Instruction> instructions = Arrays.asList( instruction1, instruction2, instruction3, instruction4, instruction5, instruction6, instruction7 );
		LocalDate today = LocalDate.of(2018, Month.OCTOBER, 12);  // Today is Friday
		FxTraderReportService fxTraderReportService = new FxTraderReportService();
		fxTraderReportService.generateReportByAmount(instructions, today);
	}
	
}