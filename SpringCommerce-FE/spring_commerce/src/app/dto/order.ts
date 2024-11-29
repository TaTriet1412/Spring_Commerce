export interface Order{
    id: number;
    total_amount: number;
    order_status: boolean;
    delivery_address: string;
    payment_method: string;
    order_date: string;
    userId: number
}