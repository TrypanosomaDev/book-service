package com.example.bookservice.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@Getter
public class BookOrderId implements Serializable {

    @Column
    private Long bookId;

    @Column
    private Long orderId;

    public BookOrderId(Long bookId, Long orderId) {
        this.bookId = bookId;
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        BookOrderId that = (BookOrderId) o;
        return Objects.equals(bookId, that.bookId) &&
                Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, orderId);
    }
}
