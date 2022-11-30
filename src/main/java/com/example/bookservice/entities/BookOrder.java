package com.example.bookservice.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class BookOrder {

    @EmbeddedId
    private BookOrderId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookId")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    private Order order;

    @Column
    private Integer number;

    public BookOrder(Book book, Order order) {
        this.book = book;
        this.order = order;
        this.id = new BookOrderId(book.getId(), order.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        BookOrder that = (BookOrder) o;
        return Objects.equals(book, that.book) &&
                Objects.equals(order, that.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book, order);
    }
}
