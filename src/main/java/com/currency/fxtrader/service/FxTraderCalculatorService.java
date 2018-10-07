package com.currency.fxtrader.service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.currency.fxtrader.entity.Currency;
import com.currency.fxtrader.entity.Instruction;
import com.currency.fxtrader.entity.OrderType;

public class FxTraderCalculatorService {

	public BigDecimal calculate( Instruction instruction ) {
		if( instruction != null && instruction.getPricePerUnit() != null && instruction.getUnits() != null && instruction.getAgreedFx() != null )
			return instruction.getPricePerUnit().multiply( BigDecimal.valueOf( instruction.getUnits() )).multiply( instruction.getAgreedFx());
		else 
			return BigDecimal.ZERO;
	}
	
	public BigDecimal calculate( List<Instruction> instructions ) {
		
		BigDecimal currentAmount = BigDecimal.ZERO;
		if( instructions != null && instructions.size() > 0 ) {
			for( Instruction instruction : instructions ) {
				currentAmount = currentAmount.add( calculate( instruction )  );
			}
		}
		return currentAmount;
	}
	
	public BigDecimal calculate( List<Instruction> instructions, OrderType orderType ) {
		
		BigDecimal currentAmount = BigDecimal.ZERO;
		if( instructions != null && instructions.size() > 0 ) {
			for( Instruction instruction : instructions ) {
				if( instruction.getOrderType().equals(orderType) )
					currentAmount = currentAmount.add( calculate( instruction )  );
			}
		}
		return currentAmount;
	}
	
	public BigDecimal calculate( List<Instruction> instructions, OrderType orderType, LocalDate today ) {
		
		BigDecimal currentAmount = BigDecimal.ZERO;
		if( instructions != null && instructions.size() > 0 ) {
			
			DayOfWeek dow = today.getDayOfWeek();
			Set<DayOfWeek> weekend = EnumSet.of( DayOfWeek.SATURDAY , DayOfWeek.SUNDAY );
			Boolean todayIsWeekend = weekend.contains( dow );
			
			// Traverse all the instructions
			for( Instruction instruction : instructions ) {
				
				// Search for instructions with orderType equals to input parameter and settlementDate equals to TODAY.
				if( instruction.getOrderType() != null && instruction.getOrderType().equals(orderType)
					&& instruction.getSettlementDate() != null && instruction.getSettlementDate().equals(today) ) {

					// If TODAY is weekend then update the settlementDate of instruction to coming Monday.
					if( todayIsWeekend ) {
						
						// if it is Sunday only calculate for AED and SAR, no update for settlementDate of SAR and AED currencies
						if( dow.equals(DayOfWeek.SUNDAY) ) {
							if( instruction.getCurrency().equals(Currency.AED) || instruction.getCurrency().equals(Currency.SAR) )
							{	
								currentAmount = currentAmount.add( calculate( instruction ) );
							}
							else {  // If day of week is Sunday but the currency is other than SAR and AED then just update its settlement day to next Monday
								LocalDate nextMonday = instruction.getSettlementDate().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
								instruction.setSettlementDate(nextMonday);
							}
						}
						else if( dow.equals(DayOfWeek.SATURDAY) ) {  // if it is Saturday then update its settlement day to next working day
							
							// If the current currency is AED or SAR and today is Saturday then update its settlement day to Sunday
							if( instruction.getCurrency().equals(Currency.AED) || instruction.getCurrency().equals(Currency.SAR) ) {
								LocalDate nextSunday = instruction.getSettlementDate().with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
								instruction.setSettlementDate(nextSunday);
							}
							else {
								// If the currency is not AED or SAR then update its settlement date to next Monday
								LocalDate nextMonday = instruction.getSettlementDate().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
								instruction.setSettlementDate(nextMonday);
							}
						}
					}
					else {  // If today is not weekend then calculate the amount
						
						if( dow.equals(DayOfWeek.FRIDAY) )
						{
							// If it is Friday which is holiday for AED and SAR then set settlement date to Sunday which is the first working day.
							if( instruction.getCurrency().equals(Currency.AED) || instruction.getCurrency().equals(Currency.SAR) ) {
								LocalDate nextSunday = instruction.getSettlementDate().with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
								instruction.setSettlementDate(nextSunday);
							}
							else  // If the currency is not AED or SAR then calculate for this instruction
								currentAmount = currentAmount.add( calculate( instruction ) );
						}
						else { // From Monday to Thursday there is no restriction
							currentAmount = currentAmount.add( calculate( instruction ) );
						}
					}
				}
			}
		}
		return currentAmount;
	}

	public Map<Instruction, BigDecimal> rank( List<Instruction> instructions, OrderType orderType, LocalDate today ) {
		
		Map<Instruction, BigDecimal> instructionAmountMap = new HashMap<>();
		
		if( instructions != null && instructions.size() > 0 ) {
			
			DayOfWeek dow = today.getDayOfWeek();
			Set<DayOfWeek> weekend = EnumSet.of( DayOfWeek.SATURDAY , DayOfWeek.SUNDAY );
			Boolean todayIsWeekend = weekend.contains( dow );
			
			// Traverse all the instructions
			for( Instruction instruction : instructions ) {
				
				// Search for instructions with orderType equals to input parameter and settlementDate equals to TODAY.
				if( instruction.getOrderType() != null && instruction.getOrderType().equals(orderType)
					&& instruction.getSettlementDate() != null && instruction.getSettlementDate().equals(today) ) {

					// If TODAY is weekend then update the settlementDate of instruction to coming Monday.
					if( todayIsWeekend ) {
						
						// if it is Sunday only calculate for AED and SAR, no update for settlementDate of SAR and AED currencies
						if( dow.equals(DayOfWeek.SUNDAY) ) {
							if( instruction.getCurrency().equals(Currency.AED) || instruction.getCurrency().equals(Currency.SAR) )
							{	
								BigDecimal currentInstructionAmount = calculate( instruction );
								instructionAmountMap.put( instruction, currentInstructionAmount );
							}
							else {  // If day of week is Sunday but the currency is other than SAR and AED then just update its settlement day to next Monday
								LocalDate nextMonday = instruction.getSettlementDate().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
								instruction.setSettlementDate(nextMonday);
							}
						}
						else if( dow.equals(DayOfWeek.SATURDAY) ) {  // if it is Saturday then update its settlement day to next working day
							
							// If the current currency is AED or SAR and today is Saturday then update its settlement day to Sunday
							if( instruction.getCurrency().equals(Currency.AED) || instruction.getCurrency().equals(Currency.SAR) ) {
								LocalDate nextSunday = instruction.getSettlementDate().with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
								instruction.setSettlementDate(nextSunday);
							}
							else {
								// If the currency is not AED or SAR then update its settlement date to next Monday
								LocalDate nextMonday = instruction.getSettlementDate().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
								instruction.setSettlementDate(nextMonday);
							}
						}
					}
					else {  // If today is not weekend then calculate the amount
						
						if( dow.equals(DayOfWeek.FRIDAY) )
						{
							// If it is Friday which is holiday for AED and SAR then set settlement date to Sunday which is the first working day.
							if( instruction.getCurrency().equals(Currency.AED) || instruction.getCurrency().equals(Currency.SAR) ) {
								LocalDate nextSunday = instruction.getSettlementDate().with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
								instruction.setSettlementDate(nextSunday);
							}
							else  // If the currency is not AED or SAR then calculate for this instruction
							{
								BigDecimal currentInstructionAmount = calculate( instruction );
								instructionAmountMap.put( instruction, currentInstructionAmount );
							}
						}
						else { // From Monday to Thursday there is no restriction
							BigDecimal currentInstructionAmount = calculate( instruction );
							instructionAmountMap.put( instruction, currentInstructionAmount );
						}
					}
				}
			}
		}

        return instructionAmountMap.entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

	}
	
}