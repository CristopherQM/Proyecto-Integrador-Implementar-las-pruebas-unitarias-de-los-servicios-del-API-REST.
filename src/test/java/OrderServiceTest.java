import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.example.Controllerç.Order;
import org.example.Controllerç.OrderRepository;
import org.example.Controllerç.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetOrderById_OrderExists() {
        Order order = new Order(1L, "Order1");
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        Order result = orderService.getOrderById(1L);
        assertEquals(order, result);
    }

    @Test
    public void testGetOrderById_OrderDoesNotExist() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        Order result = orderService.getOrderById(1L);
        assertNull(result);
    }

    @Test
    public void testCreateOrder() {
        Order order = new Order(null, "Order2");
        Order savedOrder = new Order(1L, "Order2");
        when(orderRepository.save(order)).thenReturn(savedOrder);
        Order result = orderService.createOrder(order);
        assertEquals(savedOrder, result);
    }

    @Test
    public void testUpdateOrder_OrderExists() {
        Order order = new Order(null, "Order2");
        Order updatedOrder = new Order(1L, "Order2");
        when(orderRepository.existsById(1L)).thenReturn(true);
        when(orderRepository.save(order)).thenReturn(updatedOrder);
        Order result = orderService.updateOrder(1L, order);
        assertEquals(updatedOrder, result);
    }

    @Test
    public void testUpdateOrder_OrderDoesNotExist() {
        Order order = new Order(null, "Order2");
        when(orderRepository.existsById(1L)).thenReturn(false);
        Order result = orderService.updateOrder(1L, order);
        assertNull(result);
    }

    @Test
    public void testDeleteOrder_OrderExists() {
        when(orderRepository.existsById(1L)).thenReturn(true);
        doNothing().when(orderRepository).deleteById(1L);
        boolean result = orderService.deleteOrder(1L);
        assertTrue(result);
    }

    @Test
    public void testDeleteOrder_OrderDoesNotExist() {
        when(orderRepository.existsById(1L)).thenReturn(false);
        boolean result = orderService.deleteOrder(1L);
        assertFalse(result);
    }



    @Test
    public void testUpdateOrder_InvalidId() {
        Order order = new Order(null, "Order2");
        when(orderRepository.existsById(null)).thenReturn(false);
        Order result = orderService.updateOrder(null, order);
        assertNull(result);
    }



    @Test
    public void testGetOrderById_Exception() {
        when(orderRepository.findById(anyLong())).thenThrow(new RuntimeException("Database error"));
        assertThrows(RuntimeException.class, () -> {
            orderService.getOrderById(1L);
        });
    }
}
