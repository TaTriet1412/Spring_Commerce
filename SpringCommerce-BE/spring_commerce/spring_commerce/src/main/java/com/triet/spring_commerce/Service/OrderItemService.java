package com.triet.spring_commerce.Service;

import com.triet.spring_commerce.Entity.Order;
import com.triet.spring_commerce.Entity.OrderItem;
import com.triet.spring_commerce.Repository.OrderItemRepository;
import com.triet.spring_commerce.Request.OrderItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductService productService;

    public List<OrderItem> getOrderItems(){
        return orderItemRepository.findAll();
    }

    public OrderItem getOrderItemListById(Long id){
        return orderItemRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy chi tiết đơn hàng này"));
    }

    public List<OrderItem> getOrderItemListByOrderId(Long orderId){
        List<OrderItem> orderItemList = new ArrayList<>();
        for(OrderItem orderItem:getOrderItems()){
            if(Objects.equals(orderItem.getOrderId(), orderId)){
                orderItemList.add(orderItem);
            }
        }
        return orderItemList;
    }

    public void processOrderItem(Order order, List<OrderItemRequest> requestList) {
        List<OrderItem> currOrderItemList = getOrderItemListByOrderId(order.getId());

        // Duyệt qua danh sách các request
        for (OrderItemRequest request : requestList) {
            boolean updated = false; // Cờ để kiểm tra xem item đã được cập nhật chưa

            // Duyệt qua các OrderItem hiện có trong đơn hàng
            for (OrderItem orderItem : currOrderItemList) {
                if (Objects.equals(orderItem.getOrderId(), order.getId())
                        && Objects.equals(orderItem.getProductId(), request.getProductId())) {
                    // Nếu tìm thấy sản phẩm trùng với request, cập nhật OrderItem
                    orderItem.setQuantity(request.getQuantity());
                    orderItem.setPrice(request.getPrice());
                    orderItemRepository.save(orderItem);
                    updated = true;
                    break; // Dừng vòng lặp khi đã cập nhật xong
                }
            }

            // Nếu không có OrderItem nào trùng khớp, tạo mới
            if (!updated) {
                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(productService.getProductById(request.getProductId()));
                orderItem.setOrder(order);
                orderItem.setPrice(request.getPrice());
                orderItem.setQuantity(request.getQuantity());
                orderItemRepository.save(orderItem);
            }
        }

        // Xóa các OrderItem không còn tồn tại trong requestList
        for (OrderItem orderItem : currOrderItemList) {
            boolean found = false;

            // Kiểm tra xem OrderItem có trong requestList không
            for (OrderItemRequest request : requestList) {
                if (Objects.equals(orderItem.getProductId(), request.getProductId())) {
                    found = true;
                    break;
                }
            }

            // Nếu không tìm thấy, xóa OrderItem
            if (!found) {
                orderItemRepository.delete(orderItem);
            }
        }
    }

}
