package com.codurance.srp;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.inOrder;
import static org.mockito.BDDMockito.given;

public class PrintAccountServiceShould {


    private static final int POSITIVE_AMOUNT = 100;
    private static final int NEGATIVE_AMOUNT = -POSITIVE_AMOUNT;
    private static final LocalDate TODAY = LocalDate.of(2017, 9, 6);
    private static final List<Transaction> TRANSACTIONS = Arrays.asList(
            new Transaction(LocalDate.of(2014, 4, 1), 1000),
            new Transaction(LocalDate.of(2014, 4, 2), -100),
            new Transaction(LocalDate.of(2014, 4, 10), 500)
    );

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private Console console;

    private PrintAccountService printAccountService;


    @Before
    public void setUp() {
        printAccountService = new PrintAccountService(transactionRepository, console);
        given(clock.today()).willReturn(TODAY);
    }

    @Test
    public void print_statement() {
        given(transactionRepository.all()).willReturn(TRANSACTIONS);

        printAccountService.printStatement();

        InOrder inOrder = inOrder(console);
        inOrder.verify(console).printLine("DATE | AMOUNT | BALANCE");
        inOrder.verify(console).printLine("10/04/2014 | 500.00 | 1400.00");
        inOrder.verify(console).printLine("02/04/2014 | -100.00 | 900.00");
        inOrder.verify(console).printLine("01/04/2014 | 1000.00 | 1000.00");
    }
}
