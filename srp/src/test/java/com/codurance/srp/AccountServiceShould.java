package com.codurance.srp;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceShould {

    private static final int POSITIVE_AMOUNT = 100;
    private static final int NEGATIVE_AMOUNT = -POSITIVE_AMOUNT;
    private static final LocalDate TODAY = LocalDate.of(2017, 9, 6);
    private static final List<Transaction> TRANSACTIONS = Arrays.asList(
        new Transaction(LocalDate.of(2014, 4, 1), 1000),
        new Transaction(LocalDate.of(2014, 4, 2), -100),
        new Transaction(LocalDate.of(2014, 4, 10), 500)
    );

    @Mock
    private Clock clock;

    @Mock
    private TransactionRepository transactionRepository;

    private AccountService accountService;

    @Before
    public void setUp() {
        accountService = new AccountService(transactionRepository, clock);
        given(clock.today()).willReturn(TODAY);
    }


    @Test
    public void deposit_amount_into_the_account() {

        accountService.deposit(POSITIVE_AMOUNT);

        verify(transactionRepository).add(refEq(new Transaction(TODAY, POSITIVE_AMOUNT)));
    }


    @Test
    public void withdraw_amount_from_the_account() {

        accountService.withdraw(POSITIVE_AMOUNT);

        verify(transactionRepository).add(refEq(new Transaction(TODAY, NEGATIVE_AMOUNT)));
    }

}


