package com.server.loan.service;

import com.server.loan.calculator.exception.DataNotFoundException;
import com.server.loan.calculator.model.AdminData;
import com.server.loan.calculator.repository.AdminRepository;
import com.server.loan.calculator.service.AdminService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @Mock
    private AdminRepository adminRepositoryMock;

    @InjectMocks
    private AdminService adminService;

    @Test
    public void fetchFromDatabase_returnAdminDataFromDatabase_whenAdminDataInDatabase () {
        final AdminData expected = new AdminData(1, "2023-01-01", new BigDecimal("3"), new BigDecimal("2"),
                new BigDecimal("100"), new BigDecimal("10"), new BigDecimal("50"), new BigDecimal("10000"), new BigDecimal("500000"),
                new BigDecimal("50000"), new BigDecimal("15"));
        when(adminRepositoryMock.findById(1)).thenReturn(Optional.of(expected));
        final AdminData actual = adminService.fetchFromDatabase();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void fetchFromDatabase_returnsDataNotFoundException_whenAdminDataNotInDatabase () throws DataNotFoundException {
        final DataNotFoundException expected = new DataNotFoundException("Data not found in database");
        when(adminRepositoryMock.findById(1)).thenThrow(expected);
        assertThrows(DataNotFoundException.class, () -> adminService.fetchFromDatabase());

    }

    @Test
    public void addToDatabase_returnsDataNotFoundException_whenAdminDataNotInDatabase () throws DataNotFoundException {
        final DataNotFoundException expected = new DataNotFoundException("Data not found in database");
        when(adminRepositoryMock.findById(1)).thenThrow(expected);
        assertThrows(DataNotFoundException.class, () -> adminService.fetchFromDatabase());
    }
}