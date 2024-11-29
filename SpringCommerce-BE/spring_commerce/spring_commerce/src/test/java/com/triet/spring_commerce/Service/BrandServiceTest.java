package com.triet.spring_commerce.Service;

import com.triet.spring_commerce.Entity.Brand;
import com.triet.spring_commerce.Repository.BrandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BrandServiceTest {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private BrandService brandService;

    private Brand brand1;
    private Brand brand2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize test data
        brand1 = new Brand(1L, "Brand 1");
        brand2 = new Brand(2L, "Brand 2");
    }

    @Test
    public void testGetBrands() {
        // Arrange
        when(brandRepository.findAll()).thenReturn(Arrays.asList(brand1, brand2));

        // Act
        var result = brandService.getBrands();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(brand1));
        assertTrue(result.contains(brand2));
        verify(brandRepository, times(1)).findAll();
    }

    @Test
    public void testGetBrandById_ExistingId() {
        // Arrange
        when(brandRepository.findById(1L)).thenReturn(Optional.of(brand1));

        // Act
        var result = brandService.getBrandById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(brand1.getName(), result.getName());
        verify(brandRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetBrandById_NonExistingId() {
        // Arrange
        when(brandRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            brandService.getBrandById(999L);
        });

        assertEquals("Không tìm thấy thương hiệu này", exception.getMessage());
        verify(brandRepository, times(1)).findById(999L);
    }
}
