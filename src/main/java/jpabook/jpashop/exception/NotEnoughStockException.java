package jpabook.jpashop.exception;

public class NotEnoughStockException extends Throwable {
    public NotEnoughStockException(String message) {
        super(message);
    }
}
